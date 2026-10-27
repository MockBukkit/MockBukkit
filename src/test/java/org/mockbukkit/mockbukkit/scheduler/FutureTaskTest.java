package org.mockbukkit.mockbukkit.scheduler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class FutureTaskTest
{

	@MockBukkitInject
	private BukkitSchedulerMock scheduler;

	@Test
	void callSyncMethod_runsOnMainThread() throws ExecutionException, InterruptedException, TimeoutException
	{
		final Thread mainThread = Thread.currentThread();

		Future<Integer> future = scheduler.callSyncMethod(null, () ->
		{
			assertEquals(mainThread, Thread.currentThread());

			return 5;
		});

		assertFalse(future.isDone());
		assertFalse(future.isCancelled());
		for (var task : scheduler.getPendingTasks())
		{
			assertTrue(task.isSync());
			assertFalse(task.isCancelled());
		}
		scheduler.performOneTick();
		assertTrue(future.isDone());
		assertFalse(future.isCancelled());
		assertEquals(5, future.get(0, TimeUnit.SECONDS));
	}

	@Test
	void callSyncMethod_runsOnMainThread_fromAsync() throws BrokenBarrierException, InterruptedException, TimeoutException
	{
		final Thread mainThread = Thread.currentThread();
		CyclicBarrier barrier = new CyclicBarrier(2);

		scheduler.runTaskAsynchronously(null, () ->
		{
			assertNotEquals(mainThread, Thread.currentThread());

			Future<Integer> future = scheduler.callSyncMethod(null, () ->
			{
				assertEquals(mainThread, Thread.currentThread());

				return 5;
			});

			try
			{
				barrier.await(5L, TimeUnit.SECONDS);
				assertFalse(future.isCancelled());
				assertEquals(5, future.get());
				assertFalse(future.isCancelled());
				barrier.await(5L, TimeUnit.SECONDS);
			}
			catch (InterruptedException | ExecutionException | TimeoutException | BrokenBarrierException e)
			{
				throw new RuntimeException(e);
			}
		});

		scheduler.performOneTick();
		barrier.await(5L, TimeUnit.SECONDS);
		scheduler.performOneTick();
		barrier.await(5L, TimeUnit.SECONDS);
	}

	@Test
	void callSyncMethod_throws()
	{
		Future<Integer> future = scheduler.callSyncMethod(null, () ->
		{
			throw new RuntimeException("Expected");
		});

		assertFalse(future.isDone());
		scheduler.performOneTick();
		assertTrue(future.isDone());
		ExecutionException exception = assertThrows(ExecutionException.class, () -> future.get(0, TimeUnit.SECONDS));
		RuntimeException runtimeException = assertInstanceOf(RuntimeException.class, exception.getCause());
		assertEquals("Expected", runtimeException.getMessage());
	}

	@Test
	void callSyncMethod_cancel()
	{
		AtomicBoolean called = new AtomicBoolean();
		Future<Void> future = scheduler.callSyncMethod(null, () ->
		{
			called.set(true);
			return null;
		});

		assertFalse(future.isDone());
		assertFalse(future.isCancelled());
		for (var task : scheduler.getPendingTasks())
		{
			assertFalse(task.isCancelled());
		}
		assertFalse(called.get());
		future.cancel(false);
		assertTrue(future.isDone());
		assertTrue(future.isCancelled());
		for (var task : scheduler.getPendingTasks())
		{
			assertTrue(task.isCancelled());
		}
		assertThrows(CancellationException.class, () -> future.get(0, TimeUnit.SECONDS));
		assertFalse(called.get());
		scheduler.performTicks(10);
		assertFalse(called.get());
	}

	@Test
	void callSyncMethod_cancelTask()
	{
		AtomicBoolean called = new AtomicBoolean();
		Future<Void> future = scheduler.callSyncMethod(null, () ->
		{
			called.set(true);
			return null;
		});

		assertFalse(future.isDone());
		assertFalse(future.isCancelled());
		assertFalse(called.get());
		for (var task : scheduler.getPendingTasks())
		{
			assertFalse(task.isCancelled());
			task.cancel();
		}
		assertTrue(future.isDone());
		assertTrue(future.isCancelled());
		for (var task : scheduler.getPendingTasks())
		{
			assertTrue(task.isCancelled());
		}
		assertThrows(CancellationException.class, () -> future.get(0, TimeUnit.SECONDS));
		assertFalse(called.get());
		scheduler.performTicks(10);
		assertFalse(called.get());
	}

	@Test
	void callSyncMethod_cannotCancelWhileRunning() throws Exception
	{
		AtomicReference<Future<Integer>> reference = new AtomicReference<>();
		Future<Integer> future = scheduler.callSyncMethod(null, () ->
		{
			assertFalse(reference.get().cancel(false));
			return 42;
		});
		reference.set(future);

		scheduler.performOneTick();

		assertFalse(future.isCancelled());
		assertEquals(42, future.get());
	}

	@Test
	void callSyncMethod_cancelTaskWhileRunningPreservesResult() throws Exception
	{
		AtomicInteger taskId = new AtomicInteger();
		Future<Integer> future = scheduler.callSyncMethod(null, () ->
		{
			scheduler.cancelTask(taskId.get());
			return 42;
		});
		var pending = scheduler.getPendingTasks();
		assertEquals(1, pending.size()); // Just to be sure
		taskId.set(pending.getFirst().getTaskId());

		scheduler.performOneTick();

		assertFalse(future.isCancelled());
		assertEquals(42, future.get());
	}

	@Test
	void callSyncMethod_cancelTwiceReturnsFalseSecondTime()
	{
		Future<Integer> future = scheduler.callSyncMethod(null, () -> 42);

		assertTrue(future.cancel(false));
		assertFalse(future.cancel(false));
	}

}
