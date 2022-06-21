package be.seeseemelk.mockbukkit.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CancellationException;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;
import org.jetbrains.annotations.NotNull;

public class ScheduledTask implements BukkitTask, BukkitWorker
{

	private final int id;
	private final Plugin plugin;
	private final boolean isSync;
	private boolean isCancelled = false;
	private long scheduledTick;
	private boolean running;
	private final Runnable runnable;
	private final List<Runnable> cancelListeners = new LinkedList<>();
	private Thread thread;

	public ScheduledTask(int id, Plugin plugin, boolean isSync, long scheduledTick, Runnable runnable)
	{
		this.id = id;
		this.plugin = plugin;
		this.isSync = isSync;
		this.scheduledTick = scheduledTick;
		this.runnable = runnable;
		this.running = false;
	}


	public boolean isRunning()
	{
		return running;
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
		return scheduledTick;
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
	 * Get the task itself that will be ran.
	 *
	 * @return The task that will be ran.
	 */
	public Runnable getRunnable()
	{
		return runnable;
	}

	/**
	 * Runs the task if it has not been cancelled.
	 */
	public void run()
	{
		thread = Thread.currentThread();
		if (!isCancelled())
			runnable.run();
		else
			throw new CancellationException("Task is cancelled");
	}

	@Override
	public int getTaskId()
	{
		return id;
	}

	@Override
	public @NotNull Plugin getOwner()
	{
		return plugin;
	}

	@Override
	public @NotNull Thread getThread()
	{
		return thread;
	}

	@Override
	public boolean isSync()
	{
		return isSync;
	}

	@Override
	public boolean isCancelled()
	{
		return isCancelled;
	}

	@Override
	public void cancel()
	{
		isCancelled = true;
		cancelListeners.forEach(Runnable::run);
	}

	/**
	 * Adds a callback which is executed when the task is cancelled.
	 *
	 * @param callback The callback which gets executed when the task is cancelled.
	 */
	public void addOnCancelled(Runnable callback)
	{
		cancelListeners.add(callback);
	}

}
