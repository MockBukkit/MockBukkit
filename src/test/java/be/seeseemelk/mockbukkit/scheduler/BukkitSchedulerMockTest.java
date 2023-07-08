package be.seeseemelk.mockbukkit.scheduler;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.TestPlugin;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BukkitSchedulerMockTest
{

	/**
	 * How long, in milliseconds, to sleep when testing async tasks.
	 */
	private static final long SLEEP_TIME = 50L;

	private BukkitSchedulerMock scheduler;

	@BeforeEach
	void setUp()
	{
		scheduler = new BukkitSchedulerMock();
	}

	@Test
	void getCurrentTick()
	{
		assertEquals(0, scheduler.getCurrentTick());
		scheduler.performOneTick();
		assertEquals(1, scheduler.getCurrentTick());
		scheduler.performTicks(2L);
		assertEquals(3, scheduler.getCurrentTick());
	}

	@Test
	void runTask()
	{
		AtomicBoolean executed = new AtomicBoolean(false);
		Runnable task = () -> executed.set(true);
		scheduler.runTask(null, task);
		assertFalse(executed.get());
		scheduler.performOneTick();
		assertTrue(executed.get());
	}

	@Test
	void runTaskLater()
	{
		AtomicBoolean executed = new AtomicBoolean(false);
		Runnable callback = () -> executed.set(true);
		BukkitTask task = scheduler.runTaskLater(null, callback, 20L);
		assertNotNull(task);
		assertFalse(executed.get());
		scheduler.performTicks(10L);
		assertFalse(executed.get());
		scheduler.performTicks(20L);
		assertTrue(executed.get());
	}

	@Test
	void runTaskTimer()
	{
		AtomicInteger count = new AtomicInteger(0);
		Runnable callback = () -> count.incrementAndGet();
		BukkitTask task = scheduler.runTaskTimer(null, callback, 10L, 2L);
		assertNotNull(task);
		scheduler.performTicks(9L);
		assertEquals(0, count.get());
		scheduler.performOneTick();
		assertEquals(1, count.get());
		scheduler.performOneTick();
		assertEquals(1, count.get());
		scheduler.performOneTick();
		assertEquals(2, count.get());
		task.cancel();
		scheduler.performOneTick();
		assertEquals(2, count.get());
	}

	private BukkitTask testTask; /* This is needed because a lambda can't reach writable closures */

	@Test
	void runTaskTimer_SelfCancelling()
	{
		AtomicInteger count = new AtomicInteger(0);
		testTask = scheduler.runTaskTimer(null, () ->
		{
			if (count.incrementAndGet() == 2)
				testTask.cancel();
		}, 1, 1);
		assertEquals(0, count.get());
		scheduler.performOneTick();
		assertEquals(1, count.get());
		scheduler.performOneTick();
		assertEquals(2, count.get());
		scheduler.performOneTick();
		assertEquals(2, count.get());
	}

	@Test
	void runTaskTimer_ZeroDelay_DoesntExecuteTaskImmediately()
	{
		AtomicInteger count = new AtomicInteger(0);
		Runnable callback = () -> count.incrementAndGet();
		scheduler.runTaskTimer(null, callback, 0, 2L);
		assertEquals(0, count.get());
		scheduler.performTicks(1L);
		assertEquals(1, count.get());
	}

	@Test
	void runTaskAsynchronously_TaskExecutedOnSeperateThread() throws InterruptedException, BrokenBarrierException, TimeoutException
	{
		final Thread mainThread = Thread.currentThread();

		CyclicBarrier barrier = new CyclicBarrier(2);
		scheduler.runTaskAsynchronously(null, () ->
		{
			assertNotEquals(mainThread, Thread.currentThread());
			try
			{
				barrier.await(3L, TimeUnit.SECONDS);
			}
			catch (InterruptedException | BrokenBarrierException | TimeoutException e)
			{
				throw new RuntimeException(e);
			}
		});
		barrier.await(3L, TimeUnit.SECONDS);
	}

	@Test
	void runTaskTimerAsynchronously_TaskExecutedOnSeperateThread() throws InterruptedException, BrokenBarrierException, TimeoutException
	{
		final Thread mainThread = Thread.currentThread();

		CyclicBarrier barrier = new CyclicBarrier(2);
		AtomicInteger count = new AtomicInteger();

		testTask = scheduler.runTaskTimerAsynchronously(null, () ->
		{
			assertNotEquals(mainThread, Thread.currentThread());
			try
			{
				if (count.incrementAndGet() == 2)
					testTask.cancel();
				barrier.await(3L, TimeUnit.SECONDS);
			}
			catch (InterruptedException | BrokenBarrierException | TimeoutException e)
			{
				testTask.cancel();
				throw new RuntimeException(e);
			}
		}, 2L, 1L);

		assertEquals(0, count.get());
		assertTrue(scheduler.isQueued(testTask.getTaskId()));

		scheduler.performTicks(1L);
		assertTrue(scheduler.isQueued(testTask.getTaskId()));
		assertEquals(0, count.get());

		scheduler.performTicks(1L);
		barrier.await(3L, TimeUnit.SECONDS);
		assertTrue(scheduler.isQueued(testTask.getTaskId()));
		assertEquals(1, count.get());

		scheduler.performTicks(1L);
		barrier.await(3L, TimeUnit.SECONDS);
		assertFalse(scheduler.isQueued(testTask.getTaskId()));
		assertEquals(2, count.get());

		scheduler.performTicks(1L);
		assertFalse(scheduler.isQueued(testTask.getTaskId()));
		assertEquals(2, count.get());
	}

	@Test
	void cancellingAsyncTaskDecreasesNumberOfQueuedAsyncTasks()
	{
		assertEquals(0, scheduler.getNumberOfQueuedAsyncTasks());
		BukkitTask task = scheduler.runTaskLaterAsynchronously(null, () ->
		{
		}, 1);
		assertEquals(1, scheduler.getNumberOfQueuedAsyncTasks());
		task.cancel();
		assertEquals(0, scheduler.getNumberOfQueuedAsyncTasks());
	}

	@Test
	void cancellingAllTaskByPlugin()
	{
		ServerMock server = MockBukkit.mock();
		MockBukkit.load(TestPlugin.class);
		Plugin plugin = server.getPluginManager().getPlugin("MockBukkitTestPlugin");
		BukkitSchedulerMock scheduler1 = server.getScheduler();
		assertEquals(0, scheduler1.getNumberOfQueuedAsyncTasks());
		scheduler1.runTaskLaterAsynchronously(plugin, () ->
		{
		}, 5);
		scheduler1.runTaskLaterAsynchronously(plugin, () ->
		{
		}, 10);
		BukkitTask task = scheduler1.runTaskLaterAsynchronously(null, () ->
		{
		}, 5);
		assertEquals(3, scheduler1.getNumberOfQueuedAsyncTasks());
		scheduler1.cancelTasks(plugin);
		assertEquals(1, scheduler1.getNumberOfQueuedAsyncTasks());
		scheduler1.cancelTask(task.getTaskId());
		assertEquals(0, scheduler1.getNumberOfQueuedAsyncTasks());
		MockBukkit.unmock();
	}


	@Test
	void longScheduledRunningTask_Throws_RunTimeException()
	{
		assertEquals(0, scheduler.getNumberOfQueuedAsyncTasks());
		scheduler.runTaskAsynchronously(null, () ->
		{
			while (true)
			{
				try
				{
					Thread.sleep(SLEEP_TIME);
				}
				catch (InterruptedException e)
				{
					throw new RuntimeException(e);
				}
			}
		});
		scheduler.runTaskLaterAsynchronously(null, () ->
		{
			while (true)
			{
				try
				{
					Thread.sleep(SLEEP_TIME);
				}
				catch (InterruptedException e)
				{
					throw new RuntimeException(e);
				}
			}
		}, 2);
		assertEquals(1, scheduler.getActiveRunningCount());
		scheduler.performOneTick();
		assertEquals(1, scheduler.getActiveRunningCount());
		scheduler.performOneTick();
		assertEquals(2, scheduler.getActiveRunningCount());
		scheduler.performOneTick();
		assertEquals(2, scheduler.getActiveRunningCount());
		scheduler.setShutdownTimeout(300);
		assertThrows(RuntimeException.class, () ->
		{
			scheduler.shutdown();
		});
	}

	@Test
	void longRunningTask_Throws_RunTimeException()
	{
		assertEquals(0, scheduler.getNumberOfQueuedAsyncTasks());
		final AtomicBoolean alive = new AtomicBoolean(true);
		testTask = scheduler.runTaskAsynchronously(null, () ->
		{
			while (alive.get())
			{
				if (testTask.isCancelled())
				{
					alive.set(false);
				}
				try
				{
					Thread.sleep(SLEEP_TIME);
				}
				catch (InterruptedException e)
				{
					alive.set(false);
					String message = "Interrupted";
					throw new RuntimeException(message, e);
				}
			}
		});
		assertTrue(alive.get());
		assertEquals(1, scheduler.getActiveRunningCount());
		scheduler.performTicks(10);
		scheduler.setShutdownTimeout(10);
		assertThrows(RuntimeException.class, () -> scheduler.shutdown());
	}

	@Test
	void saveOverdueTasks_EmptyByDefault()
	{
		scheduler.saveOverdueTasks();
		assertTrue(scheduler.getOverdueTasks().isEmpty());
	}

	@Test
	void saveOverdueTasks_SavesOverdueTasks() throws InterruptedException
	{
		CountDownLatch tasksSaved = new CountDownLatch(1);
		CountDownLatch taskStarted = new CountDownLatch(1);
		scheduler.runTaskAsynchronously(null, () ->
		{
			try
			{
				taskStarted.countDown();
				tasksSaved.await();
			}
			catch (InterruptedException e)
			{
			}
		});
		taskStarted.await();
		scheduler.saveOverdueTasks();
		tasksSaved.countDown();
		assertFalse(scheduler.getOverdueTasks().isEmpty());
	}

	@Test
	void assertNoOverdueTasks()
	{
		scheduler.saveOverdueTasks();
		scheduler.assertNoOverdueTasks();
	}

	@Test
	void assertNoOverdueTasks_FailedWhenOverdue() throws InterruptedException
	{
		CountDownLatch tasksSaved = new CountDownLatch(1);
		CountDownLatch taskStarted = new CountDownLatch(1);
		scheduler.runTaskAsynchronously(null, () ->
		{
			try
			{
				taskStarted.countDown();
				tasksSaved.await();
			}
			catch (InterruptedException e)
			{
			}
		});
		taskStarted.await();
		scheduler.saveOverdueTasks();
		tasksSaved.countDown();
		assertThrowsExactly(AssertionFailedError.class, () -> scheduler.assertNoOverdueTasks());
	}

	@Test
	void waitAsyncEventsFinished()
	{
		MockBukkit.mock();
		AtomicBoolean done = new AtomicBoolean(false);
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onChat(AsyncChatEvent event) throws Exception
			{
				Thread.sleep(50);
				done.set(true);
			}
		}, MockBukkit.createMockPlugin());
		AsyncChatEvent event = new AsyncChatEvent(true, null, null, null, null, null, null);
		scheduler.executeAsyncEvent(event);
		assertFalse(done.get());

		scheduler.waitAsyncEventsFinished();

		assertTrue(done.get());
		MockBukkit.unmock();
	}

	@Test
	void shutdown_waitsForAsyncEvents()
	{
		MockBukkit.mock();
		AtomicBoolean done = new AtomicBoolean(false);
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onChat(AsyncChatEvent event) throws Exception
			{
				Thread.sleep(SLEEP_TIME);
				done.set(true);
			}
		}, MockBukkit.createMockPlugin());
		AsyncChatEvent event = new AsyncChatEvent(true, null, null, null, null, null, null);
		scheduler.executeAsyncEvent(event);
		assertFalse(done.get());

		scheduler.shutdown();

		assertTrue(done.get());
		MockBukkit.unmock();
	}

	@Test
	void runTask_Consumer()
	{
		AtomicBoolean executed = new AtomicBoolean(false);
		Consumer<BukkitTask> task = (t) -> executed.set(true);
		scheduler.runTask(null, task);
		assertFalse(executed.get());
		scheduler.performOneTick();
		assertTrue(executed.get());
	}

	@Test
	void runTaskLater_Consumer()
	{
		AtomicBoolean executed = new AtomicBoolean(false);
		Consumer<BukkitTask> callback = (t) -> executed.set(true);
		scheduler.runTaskLater(null, callback, 20L);
		assertFalse(executed.get());
		scheduler.performTicks(10L);
		assertFalse(executed.get());
		scheduler.performTicks(20L);
		assertTrue(executed.get());
	}

	@Test
	void runTaskTimer_Consumer()
	{
		AtomicInteger count = new AtomicInteger(0);
		Consumer<BukkitTask> callback = (t) -> count.incrementAndGet();
		scheduler.runTaskTimer(null, callback, 10L, 2L);
		scheduler.performTicks(9L);
		assertEquals(0, count.get());
		scheduler.performOneTick();
		assertEquals(1, count.get());
		scheduler.performOneTick();
		assertEquals(1, count.get());
		scheduler.performOneTick();
		assertEquals(2, count.get());
	}

	@Test
	void runTaskTimer_ZeroDelay_DoesntExecuteTaskImmediately_Consumer()
	{
		AtomicInteger count = new AtomicInteger(0);
		Consumer<BukkitTask> callback = (t) -> count.incrementAndGet();
		scheduler.runTaskTimer(null, callback, 0, 2L);
		assertEquals(0, count.get());
		scheduler.performTicks(1L);
		assertEquals(1, count.get());
	}

	@Test
	void getMainThreadExecutor_RunsOnMainThread()
	{
		MockBukkit.mock();
		AtomicBoolean b = new AtomicBoolean();

		Executor executor = scheduler.getMainThreadExecutor(MockBukkit.createMockPlugin());
		assertNotNull(executor);

		executor.execute(() -> b.set(Bukkit.isPrimaryThread()));
		scheduler.performOneTick();

		assertTrue(b.get());
		MockBukkit.unmock();
	}

	@Test
	void getMainThreadExecutor_NullPlugin_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> scheduler.getMainThreadExecutor(null));
	}

	@Test
	void getMainThreadExecutor_NullCommand_ThrowsException()
	{
		MockBukkit.mock();
		Executor executor = scheduler.getMainThreadExecutor(MockBukkit.createMockPlugin());

		assertThrowsExactly(NullPointerException.class, () -> executor.execute(null));

		MockBukkit.unmock();
	}

	@Test
	void repeatingTask_DoesntHang()
	{
		scheduler.runTaskTimer(null, () ->
		{
		}, 1L, 1L);
		scheduler.setShutdownTimeout(1000L);
		scheduler.shutdown();
	}

	@Test
	void runTaskLater_DoesntHang()
	{
		scheduler.runTaskLater(null, () ->
		{
		}, 1L);
		scheduler.performTicks(2);
		scheduler.setShutdownTimeout(1000L);
		scheduler.shutdown();
	}

	@Test
	void taskIsRunning()
	{
		BukkitTask bukkitTask = scheduler.runTaskTimer(null, () ->
		{
		}, 1L, 1L);
		scheduler.performOneTick();
		Assertions.assertTrue(scheduler.isCurrentlyRunning(bukkitTask.getTaskId()));
	}

	@Test
	void taskNotRunning()
	{
		Assertions.assertFalse(scheduler.isCurrentlyRunning(Integer.MAX_VALUE));
	}

	@Test
	void runTask_AsyncConsumer() throws Exception
	{
		CountDownLatch countDownLatch = new CountDownLatch(1);

		scheduler.runTaskAsynchronously(null, bukkitTask -> countDownLatch.countDown());
		assertTrue(countDownLatch.await(2, TimeUnit.SECONDS));
	}

}
