package be.seeseemelk.mockbukkit.scheduler;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.function.Consumer;

/**
 * Taken and heavily modified from <a href="https://hub.spigotmc.org/stash/projects/SPIGOT/repos/craftbukkit/browse/src/main/java/org/bukkit/craftbukkit/scheduler/CraftTask.java">CraftBukkit's CraftTask</a>.
 */
public class Task implements BukkitTask, Runnable
{

	public static final int NO_REPEATING = -1;
	public static final int CANCELED = -2;
	// FutureTask magic ids defined here to avoid conflicts.
	public static final int FUTURE_PROCESS = -3;
	public static final int FUTURE_DONE = -4;

	private final BukkitScheduler scheduler;
	private final int taskId;
	private final Plugin owner;
	private final Instant creationTime;
	private final Runnable rTask;
	private final Consumer<BukkitTask> cTask;
	private long period;
	private long nextRun;

	protected Task(@NotNull BukkitScheduler scheduler, @NotNull Runnable runnable)
	{
		this(scheduler, -1, null, Task.NO_REPEATING, runnable);
	}

	@SuppressWarnings("unchecked")
	protected Task(@NotNull BukkitScheduler scheduler, int id, Plugin plugin, long period, @NotNull Object task)
	{
		Preconditions.checkNotNull(scheduler, "Scheduler cannot be null");
		Preconditions.checkNotNull(task, "Task cannot be null");
		this.scheduler = scheduler;
		this.taskId = id;
		this.owner = plugin;
		this.period = period;
		this.rTask = task instanceof Runnable r ? r : null;
		this.cTask = task instanceof Consumer<?> c ? (Consumer<BukkitTask>) c : null;
		this.creationTime = Instant.now();
	}

	@Override
	public void run()
	{
		if (this.rTask != null)
		{
			this.rTask.run();
		}
		else
		{
			this.cTask.accept(this);
		}
	}

	@Override
	public int getTaskId()
	{
		return this.taskId;
	}

	@Override
	public @NotNull Plugin getOwner()
	{
		return this.owner;
	}

	@Override
	public boolean isSync()
	{
		return !(this instanceof AsyncTask);
	}

	@Override
	public boolean isCancelled()
	{
		return getPeriod() == CANCELED;
	}

	@Override
	public void cancel()
	{
		MockBukkit.ensureMocking();
		this.scheduler.cancelTask(getTaskId());
	}

	/**
	 * Sets the status of this task to canceled.
	 *
	 * @return false if it is a {@link FutureTask} that has already begun execution, true otherwise
	 */
	boolean cancel0()
	{
		this.setPeriod(Task.CANCELED);
		return true;
	}

	/**
	 * @return The {@link Instant} this task was created.
	 */
	protected Instant getCreationTime()
	{
		return this.creationTime;
	}

	/**
	 * @return The period for this task.
	 */
	protected long getPeriod()
	{
		return this.period;
	}

	/**
	 * @param period The new period for this task.
	 */
	protected void setPeriod(long period)
	{
		this.period = period;
	}

	/**
	 * @return The tick this task is going to run on next.
	 */
	protected long getNextRun()
	{
		return this.nextRun;
	}

	/**
	 * @param nextRun The tick this task should run on next.
	 */
	protected void setNextRun(long nextRun)
	{
		this.nextRun = nextRun;
	}

}
