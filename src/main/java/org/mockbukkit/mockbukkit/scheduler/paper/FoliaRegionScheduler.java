package org.mockbukkit.mockbukkit.scheduler.paper;

import com.google.common.base.Preconditions;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.scheduler.BukkitSchedulerMock;

import java.util.function.Consumer;

public final class FoliaRegionScheduler implements RegionScheduler
{

    private static final String PLUGIN_CANNOT_BE_NULL = "plugin cannot be null";
    private static final String TASK_CANNOT_BE_NULL = "task cannot be null";
    private static final String WORLD_CANNOT_BE_NULL = "world cannot be null";
    private static final String RUNNABLE_CANNOT_BE_NULL = "runnable cannot be null";

    private final BukkitSchedulerMock scheduler;

    public FoliaRegionScheduler(@NotNull BukkitSchedulerMock scheduler)
    {
        this.scheduler = scheduler;
    }

    @Override
    public @NotNull ScheduledTask run(
            @NotNull Plugin plugin,
            @NotNull World world,
            int chunkX,
            int chunkZ,
            @NotNull Consumer<ScheduledTask> task)
    {
        Preconditions.checkNotNull(plugin, PLUGIN_CANNOT_BE_NULL);
        Preconditions.checkNotNull(world, WORLD_CANNOT_BE_NULL);
        Preconditions.checkNotNull(task, TASK_CANNOT_BE_NULL);

        PaperScheduledTask scheduledTask = new PaperScheduledTask(plugin, task);
        scheduler.runTask(plugin, scheduledTask::run);
        return scheduledTask;
    }

    @Override
    public void execute(
            @NotNull Plugin plugin,
            @NotNull World world,
            int chunkX,
            int chunkZ,
            @NotNull Runnable run)
    {
        Preconditions.checkNotNull(plugin, PLUGIN_CANNOT_BE_NULL);
        Preconditions.checkNotNull(world, WORLD_CANNOT_BE_NULL);
        Preconditions.checkNotNull(run, RUNNABLE_CANNOT_BE_NULL);

        scheduler.runTask(plugin, run);
    }

    @Override
    public @NotNull ScheduledTask runDelayed(
            @NotNull Plugin plugin,
            @NotNull World world,
            int chunkX,
            int chunkZ,
            @NotNull Consumer<ScheduledTask> task,
            long delayTicks)
    {
        Preconditions.checkNotNull(plugin, PLUGIN_CANNOT_BE_NULL);
        Preconditions.checkNotNull(world, WORLD_CANNOT_BE_NULL);
        Preconditions.checkNotNull(task, TASK_CANNOT_BE_NULL);

        PaperScheduledTask scheduledTask = new PaperScheduledTask(plugin, task);
        scheduler.runTaskLater(plugin, scheduledTask::run, delayTicks);
        return scheduledTask;
    }

    @Override
    public @NotNull ScheduledTask runAtFixedRate(
            @NotNull Plugin plugin,
            @NotNull World world,
            int chunkX,
            int chunkZ,
            @NotNull Consumer<ScheduledTask> task,
            long initialDelayTicks,
            long periodTicks)
    {
        Preconditions.checkNotNull(plugin, PLUGIN_CANNOT_BE_NULL);
        Preconditions.checkNotNull(world, WORLD_CANNOT_BE_NULL);
        Preconditions.checkNotNull(task, TASK_CANNOT_BE_NULL);

        PaperScheduledTask scheduledTask = new PaperScheduledTask(plugin, task);
        scheduler.runTaskTimer(plugin, scheduledTask::run, initialDelayTicks, periodTicks);
        return scheduledTask;
    }
}