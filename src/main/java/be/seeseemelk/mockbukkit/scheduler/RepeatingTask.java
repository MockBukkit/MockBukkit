package be.seeseemelk.mockbukkit.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A {@link ScheduledTask} that can be repeated.
 */
public class RepeatingTask extends ScheduledTask
{

	private final long period;

	/**
	 * Constructs a new {@link RepeatingTask} with the provided parameters.
	 *
	 * @param id            The task ID.
	 * @param plugin        The plugin owning the task.
	 * @param isSync        Whether the task is synchronous.
	 * @param scheduledTick The tick the task is scheduled to run at.
	 * @param period        How often the task should run.
	 * @param runnable      The runnable to run.
	 */
	public RepeatingTask(int id, Plugin plugin, boolean isSync, long scheduledTick, long period,
			@NotNull Runnable runnable)
	{
		super(id, plugin, isSync, scheduledTick, runnable);
		this.period = period;
	}

	/**
	 * Constructs a new {@link RepeatingTask} with the provided parameters.
	 *
	 * @param id            The task ID.
	 * @param plugin        The plugin owning the task.
	 * @param isSync        Whether the task is synchronous.
	 * @param scheduledTick The tick the task is scheduled to run at.
	 * @param period        How often the task should run.
	 * @param consumer      The consumer to run.
	 */
	public RepeatingTask(int id, Plugin plugin, boolean isSync, long scheduledTick, long period,
			@NotNull Consumer<? super BukkitTask> consumer)
	{
		super(id, plugin, isSync, scheduledTick, consumer);
		this.period = period;
	}

	/**
	 * Gets the period of the timer.
	 *
	 * @return The period of the timer.
	 */
	public long getPeriod()
	{
		return period;
	}

	/**
	 * Updates the scheduled tick for the next run.
	 */
	public void updateScheduledTick()
	{
		setScheduledTick(getScheduledTick() + period);
	}

}
