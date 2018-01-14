package be.seeseemelk.mockbukkit.scheduler;

import static org.junit.Assert.*;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

public class ScheduledTaskTest
{
	@Test
	public void getScheduledTick_GetsScheduledTick()
	{
		ScheduledTask task = new ScheduledTask(0, null, true, 5, null);
		assertEquals(5, task.getScheduledTick());
	}
	
	@Test
	public void getRunnable_GetsRunnable()
	{
		Runnable runnable = () -> {};
		ScheduledTask task = new ScheduledTask(0, null, true, 0, runnable);
		assertSame(runnable, task.getRunnable());
	}
	
	@Test
	public void getTaskId_GetsTaskId()
	{
		ScheduledTask task = new ScheduledTask(5, null, true, 0, null);
		assertEquals(5, task.getTaskId());
	}

	@Test
	public void isSync()
	{
		ScheduledTask task = new ScheduledTask(0, null, true, 0, null);
		assertTrue(task.isSync());
		task = new ScheduledTask(0, null, false, 0, null);
		assertFalse(task.isSync());
	}
	
	@Test
	public void setScheduledTick_OtherTick_TickSetExactly()
	{
		ScheduledTask task = new ScheduledTask(0, null, true, 5, null);
		assertEquals(5, task.getScheduledTick());
		task.setScheduledtick(20);
		assertEquals(20, task.getScheduledTick());
	}
	
	@Test
	public void cancel()
	{
		ScheduledTask task = new ScheduledTask(0, null, true, 0, null);
		assertEquals(false, task.isCancelled());
		task.cancel();
		assertEquals(true, task.isCancelled());
	}
	
	@Test
	public void run_NotCancelled_Executed()
	{
		AtomicBoolean executed = new AtomicBoolean(false);
		ScheduledTask task = new ScheduledTask(0, null, true, 0, () -> {
			executed.set(true);
		});
		task.run();
		assertTrue(executed.get());
	}
	
	@Test(expected = CancellationException.class)
	public void run_Cancelled_ThrowsException()
	{
		AtomicBoolean executed = new AtomicBoolean(false);
		ScheduledTask task = new ScheduledTask(0, null, true, 0, () -> {
			executed.set(true);
		});
		task.cancel();
		task.run();
	}
	
}














