package be.seeseemelk.mockbukkit.scheduler.paper;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.scheduler.BukkitSchedulerMock;
import com.google.common.base.Preconditions;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class FoliaAsyncScheduler implements AsyncScheduler
{

	private static final long NANOS_PER_TICK = 50_000_000;

	private final BukkitSchedulerMock scheduler;

	public FoliaAsyncScheduler(BukkitSchedulerMock scheduler)
	{
		this.scheduler = scheduler;
	}

	@Override
	public @NotNull ScheduledTask runNow(@NotNull Plugin plugin, @NotNull Consumer<ScheduledTask> task)
	{
		Preconditions.checkNotNull(task, "task cannot be null");
		PaperScheduledTask scheduledTask = new PaperScheduledTask(plugin, task);
		scheduler.runTaskAsynchronously(plugin, scheduledTask::run);
		return scheduledTask;
	}

	@Override
	public @NotNull ScheduledTask runDelayed(@NotNull Plugin plugin, @NotNull Consumer<ScheduledTask> task, long delay, @NotNull TimeUnit unit)
	{
		Preconditions.checkNotNull(task, "task cannot be null");
		Preconditions.checkNotNull(unit, "unit cannot be null");
		PaperScheduledTask scheduledTask = new PaperScheduledTask(plugin, task);
		scheduler.runTaskLaterAsynchronously(plugin, scheduledTask::run, toTicks(delay, unit));
		return scheduledTask;
	}

	@Override
	public @NotNull ScheduledTask runAtFixedRate(@NotNull Plugin plugin, @NotNull Consumer<ScheduledTask> task, long initialDelay, long period, @NotNull TimeUnit unit)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void cancelTasks(@NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(plugin, "plugin cannot be null");
		scheduler.cancelTasks(plugin);
	}

	private static long toTicks(long delay, TimeUnit timeUnit)
	{
		long nanoseconds = timeUnit.toNanos(delay);
		return nanoseconds / NANOS_PER_TICK;
	}

}
