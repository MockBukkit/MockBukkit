package org.mockbukkit.mockbukkit.scheduler.paper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.EntityMock;
import org.mockbukkit.mockbukkit.entity.ZombieMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;
import org.mockbukkit.mockbukkit.scheduler.BukkitSchedulerMock;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockBukkitExtension.class)
class FoliaEntitySchedulerTest
{

    @MockBukkitInject
    private ServerMock server;

    @MockBukkitInject
    private BukkitSchedulerMock bukkitScheduler;

    private FoliaEntityScheduler scheduler;
    private PluginMock plugin;
    private EntityMock entity;

    @BeforeEach
    void setUp()
    {
        entity = new ZombieMock(server, UUID.randomUUID());
        scheduler = new FoliaEntityScheduler(bukkitScheduler, entity);
        plugin = MockBukkit.createMockPlugin();
    }

    @Test
    void execute_RunsTask()
    {
        CountDownLatch latch = new CountDownLatch(1);
        assertTrue(scheduler.execute(plugin, latch::countDown, null, 0));
        bukkitScheduler.performOneTick();
        assertEquals(0, latch.getCount());
    }

    @Test
    void run_RunsTask()
    {
        CountDownLatch latch = new CountDownLatch(1);
        scheduler.run(plugin, task -> latch.countDown(), null);
        bukkitScheduler.performOneTick();
        assertEquals(0, latch.getCount());
    }

    @Test
    void runDelayed_RunsLater()
    {
        CountDownLatch latch = new CountDownLatch(1);
        scheduler.runDelayed(plugin, task -> latch.countDown(), null, 2);
        bukkitScheduler.performOneTick();
        assertEquals(1, latch.getCount());
        bukkitScheduler.performOneTick();
        assertEquals(0, latch.getCount());
    }

    @Test
    void runAtFixedRate_RunsRepeatedly()
    {
        CountDownLatch latch = new CountDownLatch(3);
        scheduler.runAtFixedRate(plugin, task -> latch.countDown(), null, 1, 1);
        bukkitScheduler.performTicks(3);
        assertEquals(0, latch.getCount());
    }

    @Test
    void nullPlugin_Throws()
    {
        assertThrows(NullPointerException.class,
                () -> scheduler.run(null, task -> {}, null));
    }

    @Test
    void nullTask_Throws()
    {
        assertThrows(NullPointerException.class,
                () -> scheduler.run(plugin, null, null));
    }

    @Test
    void entityRemoved_RunsRetireCallbackInsteadOfTask()
    {
        CountDownLatch taskLatch = new CountDownLatch(1);
        CountDownLatch retireLatch = new CountDownLatch(1);

        scheduler.runDelayed(
                plugin,
                task -> taskLatch.countDown(),
                retireLatch::countDown,
                1
        );

        // Simulate entity retirement
        entity.remove();

        bukkitScheduler.performTicks(2);

        // Task must NOT run
        assertEquals(1, taskLatch.getCount());

        // Retire callback MUST run
        assertEquals(0, retireLatch.getCount());
    }
}
