package be.seeseemelk.mockbukkit.scheduler.paper;

import be.seeseemelk.mockbukkit.scheduler.BukkitSchedulerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FoliaAsyncSchedulerTest
{

	private BukkitSchedulerMock bukkitScheduler;
	private FoliaAsyncScheduler scheduler;

	@BeforeEach
	void setUp()
	{
		bukkitScheduler = new BukkitSchedulerMock();
		scheduler = new FoliaAsyncScheduler(bukkitScheduler);
	}

	@Test
	void runNow_RunsTask() throws InterruptedException
	{
		CountDownLatch latch = new CountDownLatch(1);
		scheduler.runNow(null, task -> latch.countDown());
		assertTrue(latch.await(1, java.util.concurrent.TimeUnit.SECONDS));
	}

	@Test
	void runDelayed_RunsTaskLater() throws InterruptedException
	{
		CountDownLatch latch = new CountDownLatch(1);
		scheduler.runDelayed(null, task ->
				latch.countDown(), 100, TimeUnit.MILLISECONDS);
		assertNotEquals(0, latch.getCount());
		bukkitScheduler.performTicks(1);
		assertNotEquals(0, latch.getCount());
		bukkitScheduler.performTicks(1);
		assertTrue(latch.await(1, TimeUnit.SECONDS));
	}

}
