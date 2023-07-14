package be.seeseemelk.mockbukkit.scheduler.paper;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.scheduler.BukkitSchedulerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FoliaAsyncSchedulerTest
{

	private BukkitSchedulerMock bukkitScheduler;
	private FoliaAsyncScheduler scheduler;

	@BeforeEach
	void setUp()
	{
		MockBukkit.getOrCreateMock();
		bukkitScheduler = new BukkitSchedulerMock();
		scheduler = new FoliaAsyncScheduler(bukkitScheduler);
	}

	@AfterEach
	void tearDown()
	{
		if (MockBukkit.isMocked())
		{
			MockBukkit.unmock();
		}
	}
	@Test
	void runNow_RunsTask() throws InterruptedException
	{
		CountDownLatch latch = new CountDownLatch(1);
		scheduler.runNow(null, task -> latch.countDown());
		assertTrue(latch.await(1, java.util.concurrent.TimeUnit.SECONDS));
	}

	@Test
	void runNow_NullTask_ThrowsExceptions()
	{
		assertThrows(NullPointerException.class, () -> scheduler.runNow(null, null));
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

	@Test
	void runDelayed_NullTask_ThrowsExceptions()
	{
		assertThrows(NullPointerException.class, () -> scheduler.runDelayed(null, null, 1, TimeUnit.SECONDS));
	}

	@Test
	void runDelayed_NullTimeUnit_ThrowsExceptions()
	{
		assertThrows(NullPointerException.class, () -> scheduler.runDelayed(null, (task) ->
		{
		}, 1, null));
	}

	@Test
	void cancelTasks() throws InterruptedException
	{
		CountDownLatch latch = new CountDownLatch(3);
		MockPlugin plugin = MockBukkit.createMockPlugin();
		MockBukkit.unmock();
		scheduler.runDelayed(plugin, task -> latch.countDown(), 1, TimeUnit.NANOSECONDS);
		scheduler.runDelayed(plugin, task -> latch.countDown(), 1, TimeUnit.NANOSECONDS);
		scheduler.runDelayed(plugin, task -> latch.countDown(), 1, TimeUnit.NANOSECONDS);
		scheduler.cancelTasks(plugin);
		bukkitScheduler.performOneTick();
		assertFalse(latch.await(500, TimeUnit.MILLISECONDS));
	}

	@Test
	void cancelTasks_NullPlugin_ThrowsException()
	{
		assertThrows(NullPointerException.class, () -> scheduler.cancelTasks(null));
	}

	@Test
	void runAtFixedRate_NullPlugin_ThrowsException()
	{
		assertThrows(NullPointerException.class, () -> scheduler.runAtFixedRate(null, (task) ->
		{
		}, 1, 1, TimeUnit.SECONDS));
	}

	@Test
	void runAtFixedRate_NullTimeUnit_ThrowsExceptions()
	{
		MockPlugin mockPlugin = MockBukkit.createMockPlugin();
		assertThrows(NullPointerException.class, () -> scheduler.runAtFixedRate(mockPlugin, (task) ->
		{
		}, 1, 1, null));
	}

	@Test
	void runAtFixedRate_ok() throws Exception
	{
		ServerMock mock = MockBukkit.getOrCreateMock();
		CompletableFuture<Boolean> future = new CompletableFuture<>();
		future.completeOnTimeout(false, 2, TimeUnit.SECONDS);
		String threadName = Thread.currentThread().getName();
		scheduler.runAtFixedRate(MockBukkit.createMockPlugin(), (task) ->
		{
			future.complete(!Thread.currentThread().getName().equals(threadName));
		}, 50, 1, TimeUnit.MILLISECONDS);

		bukkitScheduler.performTicks(1L);
		Assertions.assertTrue(future.join());
	}
}
