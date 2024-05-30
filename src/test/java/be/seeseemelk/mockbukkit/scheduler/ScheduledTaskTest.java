package be.seeseemelk.mockbukkit.scheduler;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScheduledTaskTest
{

	@Test
	void getScheduledTick_GetsScheduledTick()
	{
		ScheduledTask task = new ScheduledTask(0, null, true, 5, () -> {
		});
		assertEquals(5, task.getScheduledTick());
	}

	@Test
	void getRunnable_GetsRunnable()
	{
		Runnable runnable = () -> {
		};
		ScheduledTask task = new ScheduledTask(0, null, true, 0, runnable);
		assertSame(runnable, task.getRunnable());
	}

	@Test
	void getTaskId_GetsTaskId()
	{
		ScheduledTask task = new ScheduledTask(5, null, true, 0, () -> {
		});
		assertEquals(5, task.getTaskId());
	}

	@Test
	void isSync()
	{
		ScheduledTask task = new ScheduledTask(0, null, true, 0, () -> {
		});
		assertTrue(task.isSync());
		task = new ScheduledTask(0, null, false, 0, () -> {
		});
		assertFalse(task.isSync());
	}

	@Test
	void setScheduledTick_OtherTick_TickSetExactly()
	{
		ScheduledTask task = new ScheduledTask(0, null, true, 5, () -> {
		});
		assertEquals(5, task.getScheduledTick());
		task.setScheduledTick(20);
		assertEquals(20, task.getScheduledTick());
	}

	@Test
	void cancel()
	{
		ScheduledTask task = new ScheduledTask(0, null, true, 0, () -> {
		});
		assertFalse(task.isCancelled());
		task.cancel();
		assertTrue(task.isCancelled());
	}

	@Test
	void run_NotCancelled_Executed()
	{
		AtomicBoolean executed = new AtomicBoolean(false);
		ScheduledTask task = new ScheduledTask(0, null, true, 0, () -> {
			executed.set(true);
		});
		task.run();
		assertTrue(executed.get());
	}

	@Test
	void run_Cancelled_ThrowsException()
	{
		AtomicBoolean executed = new AtomicBoolean(false);
		ScheduledTask task = new ScheduledTask(0, null, true, 0, () -> {
			executed.set(true);
		});
		task.cancel();

		assertThrows(CancellationException.class, task::run);
	}

}
