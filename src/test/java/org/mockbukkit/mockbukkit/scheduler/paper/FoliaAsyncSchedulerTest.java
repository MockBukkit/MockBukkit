package org.mockbukkit.mockbukkit.scheduler.paper;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.plugin.PluginMock;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.scheduler.BukkitSchedulerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class FoliaAsyncSchedulerTest
{

	@MockBukkitInject
	ServerMock server;
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
		PluginMock pluginMock = MockBukkit.createMockPlugin();
		scheduler.runNow(pluginMock, task -> latch.countDown());
		assertTrue(latch.await(1, TimeUnit.SECONDS));
	}

	@Test
	void runNow_RunsOnDifferentThread()
	{
		CompletableFuture<Boolean> future = new CompletableFuture<>();
		future.completeOnTimeout(false, 2, TimeUnit.SECONDS);
		scheduler.runNow(MockBukkit.createMockPlugin(), (task) -> future.complete(!server.isPrimaryThread()));
		assertTrue(future.join());
	}

	@Test
	void runNow_Null_Plugin_ThrowsException()
	{
		assertThrows(NullPointerException.class, () -> scheduler.runNow(null, task ->
		{
		}));
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
		PluginMock pluginMock = MockBukkit.createMockPlugin();
		scheduler.runDelayed(pluginMock, task -> latch.countDown(), 100, TimeUnit.MILLISECONDS);
		assertNotEquals(0, latch.getCount());
		bukkitScheduler.performTicks(1);
		assertNotEquals(0, latch.getCount());
		bukkitScheduler.performTicks(1);
		assertTrue(latch.await(1, TimeUnit.SECONDS));
	}

	@Test
	void runDelayed_RunsOnDifferentThread()
	{
		CompletableFuture<Boolean> future = new CompletableFuture<>();
		future.completeOnTimeout(false, 2, TimeUnit.SECONDS);
		PluginMock pluginMock = MockBukkit.createMockPlugin();
		scheduler.runDelayed(pluginMock, task -> future.complete(!server.isPrimaryThread()), 100, TimeUnit.MILLISECONDS);
		bukkitScheduler.performTicks(2);
		assertTrue(future.join());
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
		PluginMock plugin = MockBukkit.createMockPlugin();
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
		PluginMock pluginMock = MockBukkit.createMockPlugin();
		assertThrows(NullPointerException.class, () -> scheduler.runAtFixedRate(pluginMock, (task) ->
		{
		}, 1, 1, null));
	}

	@Test
	void runAtFixedRate_NullTask_ThrowsExceptions()
	{
		PluginMock pluginMock = MockBukkit.createMockPlugin();
		assertThrows(NullPointerException.class, () -> scheduler.runAtFixedRate(pluginMock, null, 1, 1, TimeUnit.SECONDS));
	}

	@Test
	void runAtFixedRate_RunsRepeatedly() throws InterruptedException
	{
		CountDownLatch latch = new CountDownLatch(3);
		scheduler.runAtFixedRate(MockBukkit.createMockPlugin(), (task) -> latch.countDown(), 50, 50, TimeUnit.MILLISECONDS);
		bukkitScheduler.performTicks(3);
		assertTrue(latch.await(1, TimeUnit.SECONDS));
	}

	@Test
	void runAtFixedRate_RunsOnDifferentThread()
	{
		CompletableFuture<Boolean> future = new CompletableFuture<>();
		future.completeOnTimeout(false, 1, TimeUnit.SECONDS);
		scheduler.runAtFixedRate(MockBukkit.createMockPlugin(), (task) -> future.complete(!server.isPrimaryThread()), 50, 50, TimeUnit.MILLISECONDS);
		bukkitScheduler.performTicks(1);
		assertTrue(future.join());
	}

}
