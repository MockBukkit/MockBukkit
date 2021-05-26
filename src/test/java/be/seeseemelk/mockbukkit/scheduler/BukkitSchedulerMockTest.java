package be.seeseemelk.mockbukkit.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
	public void getPendingTasks_Sync()
	{
		assertTrue(scheduler.getPendingTasks().isEmpty());
		int amountTasks = 20;
		AtomicInteger count = new AtomicInteger(amountTasks);
		Runnable callback = count::decrementAndGet;
		for (int i = 0; i < amountTasks; i++)
		{
			scheduler.runTaskLater(null, callback, 2L+(i%5));
		}
		while (count.get()>0)
		{
			assertEquals(count.get(), scheduler.getPendingTasks().size());
			scheduler.performOneTick();
		}
		assertEquals(count.get(), scheduler.getPendingTasks().size());
	}

	@Test
	public void getPendingTasks_Async()
	{
		assertTrue(scheduler.getPendingTasks().isEmpty());
		int amountTasks = 20;
		AtomicInteger count = new AtomicInteger(amountTasks);
		Runnable callback = () ->
		{
			simulateWorkload();
			count.decrementAndGet();
		};
		for (int i = 0; i < amountTasks; i++)
		{
			scheduler.runTaskLaterAsynchronously(null, callback, 2L + (i % 5));
		}
		int pendingTasks;
		int oldPendingTasks = amountTasks;
		while ((pendingTasks = scheduler.getPendingTasks().size()) > 0)
		{
			int currentCount = count.get();
			assertTrue(currentCount <= pendingTasks);
			assertTrue(pendingTasks <= oldPendingTasks);
			scheduler.performOneTick();
			oldPendingTasks = pendingTasks;
		}
		assertTrue(scheduler.getPendingTasks().isEmpty());
		assertEquals(0, count.get());
	}

	@Test
	public void getPendingTasks_AsyncCanceledTasks()
	{
		assertTrue(scheduler.getPendingTasks().isEmpty());
		int amountTasks = 20;
		Runnable callback = this::simulateWorkload;
		for (int i = 0; i < amountTasks; i++)
		{
			scheduler.runTaskLaterAsynchronously(null, callback, 1L + (i % 5));
		}
		assertEquals(amountTasks, scheduler.getPendingTasks().size());
		scheduler.performOneTick();
		assertTrue(amountTasks >= scheduler.getPendingTasks().size());
		assertFalse(scheduler.getPendingTasks().isEmpty());
		for (BukkitTask task : scheduler.getPendingTasks())
		{
			task.cancel();
		}
		assertTrue(scheduler.getPendingTasks().isEmpty());
		scheduler.performOneTick();
		assertTrue(scheduler.getPendingTasks().isEmpty());
	}

	/**
	 * Simulates varying work load by waiting by a random amount of time (up to 20ms).
	 */
	private void simulateWorkload() {
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(2, 20));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
