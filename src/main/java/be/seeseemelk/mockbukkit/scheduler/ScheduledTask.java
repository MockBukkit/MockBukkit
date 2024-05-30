package be.seeseemelk.mockbukkit.scheduler;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.function.Consumer;

/**
 * Mock implementation of a {@link BukkitTask}.
 */
public class ScheduledTask implements BukkitTask, BukkitWorker
{

	private final int id;
	private final Plugin plugin;
	private final boolean isSync;
	private boolean isCancelled = false;
	private long scheduledTick;
	private boolean running;
	private final @Nullable Runnable runnable;
	private final @Nullable Consumer<? super BukkitTask> consumer;
	private final List<Runnable> cancelListeners = new LinkedList<>();
	private Thread thread;
	private boolean submitted = false;

	/**
	 * Constructs a new {@link ScheduledTask} with the provided parameters.
	 *
	 * @param id            The task ID.
	 * @param plugin        The plugin owning the task.
	 * @param isSync        Whether the task is synchronous.
	 * @param scheduledTick The tick the task is scheduled to run at.
	 * @param runnable      The runnable to run.
	 */
	public ScheduledTask(int id, Plugin plugin, boolean isSync, long scheduledTick, @NotNull Runnable runnable)
	{
		this(id, plugin, isSync, scheduledTick, runnable, null);
		Preconditions.checkNotNull(runnable, "Runnable cannot be null");
	}

	/**
	 * Constructs a new {@link ScheduledTask} with the provided parameters.
	 *
	 * @param id            The task ID.
	 * @param plugin        The plugin owning the task.
	 * @param isSync        Whether the task is synchronous.
	 * @param scheduledTick The tick the task is scheduled to run at.
	 * @param consumer      The consumer to run.
	 */
	public ScheduledTask(int id, Plugin plugin, boolean isSync, long scheduledTick,
			@NotNull Consumer<? super BukkitTask> consumer)
	{
		this(id, plugin, isSync, scheduledTick, null, consumer);
		Preconditions.checkNotNull(consumer, "Consumer cannot be null");
	}

	/**
	 * Constructs a new {@link ScheduledTask} with the provided parameters.
	 *
	 * @param id            The task ID.
	 * @param plugin        The plugin owning the task.
	 * @param isSync        Whether the task is synchronous.
	 * @param scheduledTick The tick the task is scheduled to run at.
	 * @param runnable      The runnable to run.
	 * @param consumer      The consumer to run.
	 */
	private ScheduledTask(int id, Plugin plugin, boolean isSync, long scheduledTick, @Nullable Runnable runnable,
			@Nullable Consumer<? super BukkitTask> consumer)
	{
		this.id = id;
		this.plugin = plugin;
		this.isSync = isSync;
		this.scheduledTick = scheduledTick;
		this.runnable = runnable;
		this.consumer = consumer;
		this.running = false;
	}

	/**
	 * @return Whether the task is running.
	 */
	public boolean isRunning()
	{
		return this.running;
	}

	/**
	 * Sets whether the task is running.
	 * Should not be used outside of {@link BukkitSchedulerMock}.
	 *
	 * @param running Whether the task is running.
	 */
	@ApiStatus.Internal
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
	public @Nullable Runnable getRunnable()
	{
		return this.runnable;
	}

	/**
	 * Get the Consumer that will be run.
	 *
	 * @return The consumer that will be run.
	 */
	public @Nullable Consumer<? super BukkitTask> getConsumer()
	{
		return this.consumer;
	}

	/**
	 * Marks the task as being submitted to the async thread pool.
	 * This is used to bypass the #isCancelled check if it gets updated before the task is run.
	 */
	@ApiStatus.Internal
	protected void submitted()
	{
		submitted = true;
	}

	/**
	 * Runs the task if it has not been cancelled.
	 */
	public void run()
	{
		thread = Thread.currentThread();
		if (!isSync && submitted || !isCancelled())
		{
			submitted = false;
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
	public @NotNull Thread getThread()
	{
		return thread;
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
