package be.seeseemelk.mockbukkit.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.TestPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BukkitSchedulerMockTest
{
	private BukkitSchedulerMock scheduler;

	@BeforeEach
	public void setUp()
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
	public void cancellingAsyncTaskDecreasesNumberOfQueuedAsyncTasks()
	{
		assertEquals(0, scheduler.getNumberOfQueuedAsyncTasks());
		BukkitTask task = scheduler.runTaskLaterAsynchronously(null, () -> {}, 1);
		assertEquals(1, scheduler.getNumberOfQueuedAsyncTasks());
		task.cancel();
		assertEquals(0, scheduler.getNumberOfQueuedAsyncTasks());
	}

	@Test
	public void cancellingAllTaskByPlugin()
	{
		MockBukkit.mock();
		MockBukkit.load(TestPlugin.class);
		Plugin plugin = MockBukkit.getMock().getPluginManager().getPlugin("MockBukkitTestPlugin");
		BukkitSchedulerMock scheduler1 = MockBukkit.getMock().getScheduler();
		assertEquals(0, scheduler1.getNumberOfQueuedAsyncTasks());
		scheduler1.runTaskLaterAsynchronously(plugin, () -> {}, 5);
		scheduler1.runTaskLaterAsynchronously(plugin, () -> {}, 10);
		BukkitTask task = scheduler1.runTaskLaterAsynchronously(null, () -> {}, 5);
		assertEquals(3, scheduler1.getNumberOfQueuedAsyncTasks());
		scheduler1.cancelTasks(plugin);
		assertEquals(1, scheduler1.getNumberOfQueuedAsyncTasks());
		scheduler1.cancelTask(task.getTaskId());
		assertEquals(0, scheduler1.getNumberOfQueuedAsyncTasks());
		MockBukkit.unmock();
	}


	@Test
	public void longScheduledRunningTask_Throws_RunTimeException()
	{
		assertEquals(0, scheduler.getNumberOfQueuedAsyncTasks());
		scheduler.runTaskAsynchronously(null, () ->
		{
			while (true)
			{
				try
				{
					Thread.sleep(10L);
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
					Thread.sleep(10L);
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
		assertThrows(RuntimeException.class, ()->
		{
			scheduler.shutdown();
		});
	}

	@Test
	public void longRunningTask_Throws_RunTimeException()
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
					Thread.sleep(10L);
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
		assertThrows(RuntimeException.class,
		             () ->
		{
			scheduler.shutdown();
		});
	}
}
