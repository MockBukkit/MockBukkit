package be.seeseemelk.mockbukkit.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.TestPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.junit.Before;
import org.junit.Test;

public class BukkitSchedulerMockTest
{
	private BukkitSchedulerMock scheduler;

	@Before
	public void setUp()
	{
		scheduler = new BukkitSchedulerMock();
	}

	@Test
	public void getCurrentTick()
	{
		assertEquals(0, scheduler.getCurrentTick());
		scheduler.performOneTick();
		assertEquals(1, scheduler.getCurrentTick());
		scheduler.performTicks(2L);
		assertEquals(3, scheduler.getCurrentTick());
	}

	@Test
	public void runTask()
	{
		AtomicBoolean executed = new AtomicBoolean(false);
		Runnable task = () -> executed.set(true);
		scheduler.runTask(null, task);
		assertFalse(executed.get());
		scheduler.performOneTick();
		assertTrue(executed.get());
	}

	@Test
	public void runTaskLater()
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
	public void runTaskTimer()
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
	public void runTaskTimer_SelfCancelling()
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
	public void runTaskTimer_ZeroDelay_DoesntExecuteTaskImmediately()
	{
		AtomicInteger count = new AtomicInteger(0);
		Runnable callback = () -> count.incrementAndGet();
		scheduler.runTaskTimer(null, callback, 0, 2L);
		assertEquals(0, count.get());
		scheduler.performTicks(1L);
		assertEquals(1, count.get());
	}

	@Test
	public void runScheduledTask_WhileScheduler_ShutDown()
	{
		AtomicInteger count = new AtomicInteger(0);
		Runnable callback = () -> count.incrementAndGet();
		scheduler.shutdown();
		scheduler.runTaskTimer(null, callback, 0, 2L);
		scheduler.performTicks(1L);
		assertEquals(0, count.get());
	}

	@Test
	public void runTaskLaterAsynchronously_TaskExecutedOnSeperateThread() throws InterruptedException, BrokenBarrierException, TimeoutException
	{
		final Thread mainThread = Thread.currentThread();
		AtomicInteger count = new AtomicInteger();
		CyclicBarrier barrier = new CyclicBarrier(2);

		testTask = scheduler.runTaskLaterAsynchronously(null, ()->
		{

			assertNotEquals(mainThread, Thread.currentThread());
			count.incrementAndGet();
			try
			{
				barrier.await(3L, TimeUnit.SECONDS);
			}
			catch (InterruptedException | BrokenBarrierException | TimeoutException e)
			{
				throw new RuntimeException(e);
			}

		}, 2);
		assertTrue(scheduler.isQueued(testTask.getTaskId()));
		assertEquals(0, count.get());
		scheduler.performTicks(1L);
		assertTrue(scheduler.isQueued(testTask.getTaskId()));
		assertEquals(0, count.get());
		scheduler.performTicks(3);
		barrier.await(3L, TimeUnit.SECONDS);
		assertFalse(scheduler.isQueued(testTask.getTaskId()));
		assertEquals(1, count.get());

	}

	@Test
	public void runTaskAsynchronously_TaskExecutedOnSeperateThread() throws InterruptedException, BrokenBarrierException, TimeoutException
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
	public void runTaskTimerAsynchronously_TaskExecutedOnSeperateThread() throws InterruptedException, BrokenBarrierException, TimeoutException
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
	public void testPluginCancel()
	{
		MockBukkit.mock();
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		BukkitScheduler s = MockBukkit.getMock().getScheduler();
		BukkitTask task = s.runTaskLaterAsynchronously(plugin, ()-> {}, 10);
		BukkitTask task2 = s.runTaskLaterAsynchronously(plugin, ()-> {}, 10);
		BukkitTask task3 = s.runTaskLaterAsynchronously(null, ()-> {}, 10);

		assertTrue(s.isQueued(task.getTaskId()));
		assertTrue(s.isQueued(task2.getTaskId()));
		assertTrue(s.isQueued(task3.getTaskId()));

		s.cancelTasks(plugin);
		assertTrue(task.isCancelled());
		assertTrue(task2.isCancelled());
		assertTrue(s.isQueued(task3.getTaskId()));
		s.cancelTask(task3.getTaskId());
		assertFalse(s.isQueued(task3.getTaskId()));
		assertTrue(task3.isCancelled());
		MockBukkit.unmock();

	}
}















