package be.seeseemelk.mockbukkit.scheduler;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

public class BukkitSchedulerMockTest
{
	private BukkitSchedulerMock scheduler; 

	@Before
	public void setUp() throws Exception
	{
		scheduler = new BukkitSchedulerMock();
	}

	@Test
	public void runTask()
	{
		AtomicBoolean executed = new AtomicBoolean(false);
		Runnable task = () -> {
			executed.set(true);
		};
		scheduler.runTask(null, task);
		assertFalse(executed.get());
		scheduler.performOneTick();
		assertTrue(executed.get());
	}
	
	@Test
	public void runTaskLater()
	{
		AtomicBoolean executed = new AtomicBoolean(false);
		Runnable task = () -> {
			executed.set(true);
		};
		scheduler.runTaskLater(null, task, 20L);
		assertFalse(executed.get());
		scheduler.performTicks(10L);
		assertFalse(executed.get());
		scheduler.performTicks(20L);
		assertTrue(executed.get());
	}

}
