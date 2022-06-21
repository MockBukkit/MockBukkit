package be.seeseemelk.mockbukkit.scheduler;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.function.Consumer;

public class ScheduledTask implements BukkitTask
{

	private final int id;
	private final Plugin plugin;
	private final boolean isSync;
	private boolean isCancelled = false;
	private long scheduledTick;
	private boolean running;
	private final Runnable runnable;
	private final Consumer<BukkitTask> consumer;
	private final List<Runnable> cancelListeners = new LinkedList<>();

	public ScheduledTask(int id, Plugin plugin, boolean isSync, long scheduledTick, @NotNull Runnable runnable)
	{
		this(id, plugin, isSync, scheduledTick, runnable, null);
		Preconditions.checkNotNull(runnable, "Runnable cannot be null");
	}

	public ScheduledTask(int id, Plugin plugin, boolean isSync, long scheduledTick, @NotNull Consumer<BukkitTask> consumer)
	{
		this(id, plugin, isSync, scheduledTick, null, consumer);
		Preconditions.checkNotNull(consumer, "Consumer cannot be null");
	}

	private ScheduledTask(int id, Plugin plugin, boolean isSync, long scheduledTick, @Nullable Runnable runnable, @Nullable Consumer<BukkitTask> consumer)
	{
		this.id = id;
		this.plugin = plugin;
		this.isSync = isSync;
		this.scheduledTick = scheduledTick;
		this.runnable = runnable;
		this.consumer = consumer;
		this.running = false;
	}


	public boolean isRunning()
	{
		return this.running;
	}

	public void setRunning(boolean running)
	{
		this.running = running;
	}


	/**
	 * Get the tick at which the task is scheduled to run at.
	 *
	 * @return The tick the task is scheduled to run at.
	 */
	public long getScheduledTick()
	{
		return this.scheduledTick;
	}

	/**
	 * Sets the tick at which the task is scheduled to run at.
	 *
	 * @param scheduledTick The tick at which the task is scheduled to run at.
	 */
	protected void setScheduledTick(long scheduledTick)
	{
		this.scheduledTick = scheduledTick;
	}

	/**
	 * Get the task itself that will be run.
	 *
	 * @return The task that will be run.
	 */
	public Runnable getRunnable()
	{
		return this.runnable;
	}

	/**
	 * Get the Consumer that will be run.
	 *
	 * @return The consumer that will be run.
	 */
	public Consumer<BukkitTask> getConsumer()
	{
		return this.consumer;
	}

	/**
	 * Runs the task if it has not been cancelled.
	 */
	public void run()
	{
		if (!isCancelled())
		{
			if (this.runnable != null)
				this.runnable.run();
			if (this.consumer != null)
				this.consumer.accept(this);
		}
		else
		{
			throw new CancellationException("Task is cancelled");
		}
	}

	@Override
	public int getTaskId()
	{
		return this.id;
	}

	@Override
	public @NotNull Plugin getOwner()
	{
		return this.plugin;
	}

	@Override
	public boolean isSync()
	{
		return this.isSync;
	}

	@Override
	public boolean isCancelled()
	{
		return this.isCancelled;
	}

	@Override
	public void cancel()
	{
		this.isCancelled = true;
		this.cancelListeners.forEach(Runnable::run);
	}

	/**
	 * Adds a callback which is executed when the task is cancelled.
	 *
	 * @param callback The callback which gets executed when the task is cancelled.
	 */
	public void addOnCancelled(Runnable callback)
	{
		this.cancelListeners.add(callback);
	}

}
