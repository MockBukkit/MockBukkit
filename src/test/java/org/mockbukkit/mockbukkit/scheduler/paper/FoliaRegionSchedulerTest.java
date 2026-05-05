package org.mockbukkit.mockbukkit.scheduler.paper;

import org.bukkit.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;
import org.mockbukkit.mockbukkit.scheduler.BukkitSchedulerMock;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockBukkitExtension.class)
class FoliaRegionSchedulerTest
{

    @MockBukkitInject
    private ServerMock server;

    @MockBukkitInject
    private BukkitSchedulerMock bukkitScheduler;

    private FoliaRegionScheduler scheduler;
    private PluginMock plugin;
    private World world;

    @BeforeEach
    void setUp()
    {
        scheduler = new FoliaRegionScheduler(bukkitScheduler);
        plugin = MockBukkit.createMockPlugin();
        world = server.addSimpleWorld("world");
    }

    @Test
    void run_RunsTask()
    {
        CountDownLatch latch = new CountDownLatch(1);
        scheduler.run(plugin, world, 0, 0, task -> latch.countDown());
        bukkitScheduler.performOneTick();
        assertEquals(0, latch.getCount());
    }

    @Test
    void execute_RunsTask()
    {
        CountDownLatch latch = new CountDownLatch(1);
        scheduler.execute(plugin, world, 0, 0, latch::countDown);
        bukkitScheduler.performOneTick();
        assertEquals(0, latch.getCount());
    }

    @Test
    void runDelayed_RunsLater()
    {
        CountDownLatch latch = new CountDownLatch(1);
        scheduler.runDelayed(plugin, world, 0, 0, task -> latch.countDown(), 2);
        bukkitScheduler.performOneTick();
        assertEquals(1, latch.getCount());
        bukkitScheduler.performOneTick();
        assertEquals(0, latch.getCount());
    }

    @Test
    void runAtFixedRate_RunsRepeatedly()
    {
        CountDownLatch latch = new CountDownLatch(3);
        scheduler.runAtFixedRate(plugin, world, 0, 0, task -> latch.countDown(), 1, 1);
        bukkitScheduler.performTicks(3);
        assertEquals(0, latch.getCount());
    }

    @Test
    void nullPlugin_Throws()
    {
        assertThrows(NullPointerException.class,
                () -> scheduler.run(null, world, 0, 0, task -> {}));
    }

    @Test
    void nullWorld_Throws()
    {
        assertThrows(NullPointerException.class,
                () -> scheduler.run(plugin, null, 0, 0, task -> {}));
    }

    @Test
    void nullTask_Throws()
    {
        assertThrows(NullPointerException.class,
                () -> scheduler.run(plugin, world, 0, 0, null));
    }
}
