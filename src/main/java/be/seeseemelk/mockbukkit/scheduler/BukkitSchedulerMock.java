package be.seeseemelk.mockbukkit.scheduler;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

/**
 * Taken and heavily modified from <a href="https://hub.spigotmc.org/stash/projects/SPIGOT/repos/craftbukkit/browse/src/main/java/org/bukkit/craftbukkit/scheduler/CraftScheduler.java">CraftBukkit's scheduler</a>.
 */
public class BukkitSchedulerMock implements BukkitScheduler
{

	private static final int START_ID = 1;

	/**
	 * Prevents the ID from overflowing.
	 */
	private static final IntUnaryOperator INCREMENT_IDS = previous -> (previous == Integer.MAX_VALUE) ? START_ID : previous + 1;

	/**
	 * The ID of the previous task. Should be updated before creating a new task.
	 */
	private final AtomicInteger taskId = new AtomicInteger(BukkitSchedulerMock.START_ID);

	/**
	 * A queue of all tasks that need to be run.
	 */
	private final LinkedList<Task> tasks = new LinkedList<>();

	/**
	 * Pending synchronous tasks. Sync only.
	 */
	protected final PriorityQueue<Task> pendingSync = new PriorityQueue<>(Comparator.comparingLong(Task::getNextRun).thenComparing(Task::getCreationTime));

	/**
	 * Sync only.
	 */
	private final List<Task> tempSync = new ArrayList<>();

	/**
	 * Currently running tasks. Only used to see what tasks are running.
	 */
	protected final Map<Integer, Task> runners = new ConcurrentHashMap<>();

	/**
	 * The sync task that's currently executing.
	 */
	private volatile Task currentTask;

	/**
	 * The current tick of the scheduler.
	 */
	protected volatile AtomicInteger currentTick = new AtomicInteger(-1);

	protected int shutdownTimeout = 5000;
	protected boolean shutdown = false;

	private final BukkitSchedulerMock asyncScheduler;
	private final boolean isAsyncScheduler;

	public BukkitSchedulerMock()
	{
		this(false);
	}

	public BukkitSchedulerMock(boolean isAsync)
	{
		this.isAsyncScheduler = isAsync;
		this.asyncScheduler = (isAsync) ? this : new AsyncSchedulerMock();
	}

	@Override
	public int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task)
	{
		return this.scheduleSyncDelayedTask(plugin, task, 0L);
	}

	@Override
	public @NotNull BukkitTask runTask(@NotNull Plugin plugin, @NotNull Runnable runnable)
	{
		return this.runTaskLater(plugin, runnable, 0L);
	}

	@Override
	public void runTask(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task) throws IllegalArgumentException
	{
		this.runTaskLater(plugin, task, 0L);
	}

	@Override
	public @NotNull BukkitTask runTaskAsynchronously(@NotNull Plugin plugin, @NotNull Runnable runnable)
	{
		return this.runTaskLaterAsynchronously(plugin, runnable, 0L);
	}

	@Override
	public void runTaskAsynchronously(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task) throws IllegalArgumentException
	{
		this.runTaskLaterAsynchronously(plugin, task, 0L);
	}

	@Override
	public int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay)
	{
		return this.scheduleSyncRepeatingTask(plugin, task, delay, Task.NO_REPEATING);
	}

	@Override
	public @NotNull BukkitTask runTaskLater(@NotNull Plugin plugin, @NotNull Runnable runnable, long delay)
	{
		return this.runTaskTimer(plugin, runnable, delay, Task.NO_REPEATING);
	}

	@Override
	public void runTaskLater(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay) throws IllegalArgumentException
	{
		this.runTaskTimer(plugin, task, delay, Task.NO_REPEATING);
	}

	@Override
	public @NotNull BukkitTask runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull Runnable runnable, long delay)
	{
		return this.runTaskTimerAsynchronously(plugin, runnable, delay, Task.NO_REPEATING);
	}

	@Override
	public void runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay) throws IllegalArgumentException
	{
		this.runTaskTimerAsynchronously(plugin, task, delay, Task.NO_REPEATING);
	}

	@Override
	public void runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay, long period) throws IllegalArgumentException
	{
		this.runTaskTimerAsynchronously(plugin, (Object) task, delay, period);
	}

	@Override
	public int scheduleSyncRepeatingTask(@NotNull Plugin plugin, @NotNull Runnable runnable, long delay, long period)
	{
		return this.runTaskTimer(plugin, runnable, delay, period).getTaskId();
	}

	@Override
	public @NotNull BukkitTask runTaskTimer(@NotNull Plugin plugin, @NotNull Runnable runnable, long delay, long period)
	{
		return this.runTaskTimer(plugin, (Object) runnable, delay, period);
	}

	@Override
	public void runTaskTimer(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay, long period) throws IllegalArgumentException
	{
		this.runTaskTimer(plugin, (Object) task, delay, period);
	}

	public BukkitTask runTaskTimer(Plugin plugin, Object runnable, long delay, long period)
	{
		BukkitSchedulerMock.validateTaskObj(plugin, runnable);
		delay = Math.max(delay, 0L);
		period = (period < Task.NO_REPEATING) ? Task.NO_REPEATING : Math.min(period, 1L);
		return this.handleTask(new Task(this, this.nextId(), plugin, period, runnable), delay);
	}

	@Override
	public @NotNull BukkitTask runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull Runnable runnable, long delay, long period)
	{
		return this.runTaskTimerAsynchronously(plugin, (Object) runnable, delay, period);
	}

	public BukkitTask runTaskTimerAsynchronously(Plugin plugin, Object runnable, long delay, long period)
	{
		BukkitSchedulerMock.validateTaskObj(plugin, runnable);
		delay = Math.max(delay, 0L);
		period = (period < Task.NO_REPEATING) ? Task.NO_REPEATING : Math.min(period, 1L);
		return this.handleTask(new AsyncTask(this, this.asyncScheduler.runners, this.nextId(), plugin, period, runnable), delay);
	}

	@Override
	public <T> @NotNull FutureTask<T> callSyncMethod(@NotNull Plugin plugin, @NotNull Callable<T> task)
	{
		BukkitSchedulerMock.validateTaskObj(plugin, task);
		FutureTask<T> future = new FutureTask<>(this, this.nextId(), plugin, task);
		this.handleTask(future, 0L);
		return future;
	}

	@Override
	public void cancelTask(int taskId)
	{
		if (taskId <= 0)
			return;
		if (!this.isAsyncScheduler)
			this.asyncScheduler.cancelTask(taskId);

		Task task = this.runners.get(taskId);
		if (task != null)
			task.cancel0();

		task = new Task(this, () ->
		{
			if (!tryCancel(BukkitSchedulerMock.this.tempSync, t -> t.getTaskId() == taskId))
			{
				tryCancel(BukkitSchedulerMock.this.pendingSync, t -> t.getTaskId() == taskId);
			}
		});

		this.handleTask(task, 0L);

		Task pendingTask;
		synchronized (this.tasks)
		{
			while ((pendingTask = tasks.poll()) != null)
			{
				if (pendingTask == task)
					return;
				if (pendingTask.getTaskId() == taskId)
					pendingTask.cancel0();
			}
		}
	}

	@Override
	public void cancelTasks(@NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(plugin, "Cannot cancel tasks of null plugin");
		if (!this.isAsyncScheduler)
		{
			this.asyncScheduler.cancelTasks(plugin);
		}
		Task task = new Task(this, () ->
		{
			tryCancel(BukkitSchedulerMock.this.pendingSync, t -> t.getOwner().equals(plugin));
			tryCancel(BukkitSchedulerMock.this.tempSync, t -> t.getOwner().equals(plugin));
		});
		this.handleTask(task, 0L);

		synchronized (this.tasks)
		{
			Task pendingTask;
			while ((pendingTask = this.tasks.poll()) != null)
			{
				if (pendingTask == task)
					break;
				if (pendingTask.getTaskId() != -1 && pendingTask.getOwner().equals(plugin))
				{
					pendingTask.cancel0();
				}
			}
		}

		for (Task runner : this.runners.values())
		{
			if (runner.getOwner().equals(plugin))
			{
				runner.cancel0();
			}
		}
	}

	/**
	 * Utility method for the cancelTask methods.
	 *
	 * @param collection What tasks to filter through.
	 * @param filter     A function for how tasks should be filtered. The first task to meet this filter is canceled.
	 * @return True if a task was canceled, false otherwise.
	 */
	private boolean tryCancel(Collection<Task> collection, Predicate<Task> filter)
	{
		Iterator<Task> iter = collection.iterator();
		while (iter.hasNext())
		{
			Task task = iter.next();
			if (!filter.test(task))
				continue;
			task.cancel0();
			iter.remove();
			if (!task.isSync())
				continue;
			BukkitSchedulerMock.this.runners.remove(task.getTaskId());
			return true;
		}
		return false;
	}

	@Override
	public boolean isCurrentlyRunning(int taskId)
	{
		if (!this.isAsyncScheduler && this.asyncScheduler.isCurrentlyRunning(taskId))
			return true;

		Task task = this.runners.get(taskId);
		if (task == null)
			return false;

		if (task.isSync())
			return (task == this.currentTask);

		AsyncTask asyncTask = (AsyncTask) task;
		synchronized (asyncTask.getWorkers())
		{
			return !asyncTask.getWorkers().isEmpty();
		}
	}

	@Override
	public boolean isQueued(int taskId)
	{
		if (taskId <= 0)
			return false;
		if (!this.isAsyncScheduler && this.asyncScheduler.isQueued(taskId))
			return true;

		synchronized (this.tasks)
		{
			Task pendingTask;
			while ((pendingTask = tasks.poll()) != null)
			{
				if (pendingTask.getTaskId() != taskId)
					continue;
				return pendingTask.getPeriod() >= Task.NO_REPEATING;
			}
		}

		Task task = this.runners.get(taskId);
		return task != null && task.getPeriod() >= Task.NO_REPEATING;
	}

	@Override
	public @NotNull List<BukkitWorker> getActiveWorkers()
	{
		if (!isAsyncScheduler)
		{
			return this.asyncScheduler.getActiveWorkers();
		}
		List<BukkitWorker> workers = new ArrayList<>();
		for (Task taskObj : this.runners.values())
		{
			if (taskObj.isSync())
				continue;

			AsyncTask task = (AsyncTask) taskObj;
			synchronized (task.getWorkers())
			{
				workers.addAll(task.getWorkers());
			}
		}
		return workers;
	}

	@Override
	public @NotNull List<BukkitTask> getPendingTasks()
	{
		List<Task> truePending = new ArrayList<>();

		synchronized (this.tasks)
		{
			Task pendingTask;
			while ((pendingTask = this.tasks.poll()) != null)
			{
				if (pendingTask.getTaskId() != Task.NO_REPEATING)
				{
					truePending.add(pendingTask);
				}
			}
		}

		List<BukkitTask> pending = new ArrayList<>();
		for (Task task : this.runners.values())
		{
			if (task.getPeriod() >= Task.NO_REPEATING)
			{
				pending.add(task);
			}
		}

		for (Task task : truePending)
		{
			if (task.getPeriod() >= Task.NO_REPEATING && !pending.contains(task))
			{
				pending.add(task);
			}
		}
		if (!this.isAsyncScheduler)
		{
			pending.addAll(this.asyncScheduler.getPendingTasks());
		}
		return pending;
	}

	/**
	 * Preforms one scheduler tick.
	 */
	public void performOneTick()
	{
		if (!this.isAsyncScheduler)
		{
			this.asyncScheduler.performOneTick();
		}

		this.currentTick.incrementAndGet();
		this.parsePending();

		while (!this.pendingSync.isEmpty() && this.pendingSync.peek().getNextRun() <= this.currentTick.get())
		{
			Task task = this.pendingSync.remove();
			if (task.getPeriod() < Task.NO_REPEATING)
			{
				if (task.isSync())
				{
					this.runners.remove(task.getTaskId(), task);
				}
				this.parsePending();
				continue;
			}
			this.currentTask = task;
			try
			{
				task.run();
			}
			finally
			{
				this.currentTask = null;
			}
			this.parsePending();
			long period = task.getPeriod();
			if (period > 0)
			{
				task.setNextRun(this.currentTick.get() + period);
				this.tempSync.add(task);
			}
			else if (task.isSync())
			{
				this.runners.remove(task.getTaskId());
			}
		}
		this.pendingSync.addAll(this.tempSync);
		this.tempSync.clear();
	}

	protected void addTask(Task task)
	{
		synchronized (this.tasks)
		{
			this.tasks.addLast(task);
		}
	}

	protected Task handleTask(Task task, long delay)
	{
		Preconditions.checkState(this.shutdown, "Scheduler shutdown!");
		if (!this.isAsyncScheduler && !task.isSync())
		{
			this.asyncScheduler.handleTask(task, delay);
			return task;
		}
		task.setNextRun(this.currentTick.get() + delay);
		this.addTask(task);
		return task;
	}

	private static void validateTaskObj(Plugin plugin, Object task)
	{
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		Preconditions.checkNotNull(task, "Task cannot be null");
		Preconditions.checkArgument(task instanceof Runnable || task instanceof Consumer || task instanceof Callable, "Task must be Runnable, Consumer, or Callable");
		checkPluginEnabled(plugin);
	}

	private static void checkPluginEnabled(Plugin plugin)
	{
		if (plugin.isEnabled())
			return;
		throw new IllegalPluginAccessException("Plugin attempted to register task while disabled");
	}

	/**
	 * Increments {@link BukkitSchedulerMock#taskId} and returns it's value. Will continue to increment if tasks with the selected ID are still running.
	 *
	 * @return The next task ID that should be used.
	 */
	private int nextId()
	{
		Preconditions.checkArgument(this.runners.size() < Integer.MAX_VALUE, "There are already " + Integer.MAX_VALUE + " tasks scheduled! Cannot schedule more.");
		int nextId;
		do
		{
			nextId = this.taskId.updateAndGet(INCREMENT_IDS);
		} while (this.runners.containsKey(nextId)); // Prevent duplicate ID's after the ID counter resets.
		return nextId;
	}

	protected void parsePending()
	{
		synchronized (this.tasks)
		{
			Task task;
			while ((task = this.tasks.poll()) != null)
			{
				if (task.getTaskId() == Task.NO_REPEATING)
				{
					task.run();
				}
				else if (task.getPeriod() >= Task.NO_REPEATING)
				{
					this.pendingSync.add(task);
					this.runners.put(task.getTaskId(), task);
				}
			}
		}
	}

	// region Deprecated Bukkit Runnables
	@Deprecated
	@Override
	public int scheduleAsyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task)
	{
		throw new UnsupportedOperationException("Deprecated. Use #runTaskAsynchronously instead");
	}

	@Deprecated
	@Override
	public int scheduleAsyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay)
	{
		throw new UnsupportedOperationException("Deprecated. Use #runTaskTimerAsynchronously instead");
	}

	@Deprecated
	@Override
	public int scheduleAsyncRepeatingTask(@NotNull Plugin plugin, @NotNull Runnable runnable, long delay, long period)
	{
		throw new UnsupportedOperationException("Deprecated. Use #runTaskTimerAsynchronously instead");
	}

	@Deprecated
	@Override
	public int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay)
	{
		throw new UnsupportedOperationException("Use BukkitRunnable#runTaskLater(Plugin, long)");
	}

	@Deprecated
	@Override
	public int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task)
	{
		throw new UnsupportedOperationException("Use BukkitRunnable#runTask(Plugin)");
	}

	@Deprecated
	@Override
	public int scheduleSyncRepeatingTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay, long period)
	{
		throw new UnsupportedOperationException("Use BukkitRunnable#runTaskTimer(Plugin, long, long)");
	}

	@Deprecated
	@Override
	public @NotNull BukkitTask runTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task) throws IllegalArgumentException
	{
		throw new UnsupportedOperationException("Use BukkitRunnable#runTask(Plugin)");
	}

	@Deprecated
	@Override
	public @NotNull BukkitTask runTaskAsynchronously(@NotNull Plugin plugin, @NotNull BukkitRunnable task) throws IllegalArgumentException
	{
		throw new UnsupportedOperationException("Use BukkitRunnable#runTaskAsynchronously(Plugin)");
	}

	@Deprecated
	@Override
	public @NotNull BukkitTask runTaskLater(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay) throws IllegalArgumentException
	{
		throw new UnsupportedOperationException("Use BukkitRunnable#runTaskLater(Plugin, long)");
	}

	@Deprecated
	@Override
	public @NotNull BukkitTask runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay) throws IllegalArgumentException
	{
		throw new UnsupportedOperationException("Use BukkitRunnable#runTaskLaterAsynchronously(Plugin, long)");
	}

	@Deprecated
	@Override
	public @NotNull BukkitTask runTaskTimer(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay, long period) throws IllegalArgumentException
	{
		throw new UnsupportedOperationException("Use BukkitRunnable#runTaskTimer(Plugin, long, long)");
	}

	@Deprecated
	@Override
	public @NotNull BukkitTask runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay, long period) throws IllegalArgumentException
	{
		throw new UnsupportedOperationException("Use BukkitRunnable#runTaskTimerAsynchronously(Plugin, long, long)");
	}
	// endregion

	@Override
	public @NotNull Executor getMainThreadExecutor(@NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		checkPluginEnabled(plugin);
		return command ->
		{
			Preconditions.checkNotNull(command, "Command cannot be null");
			this.runTask(plugin, command);
		};
	}

	// region Custom Methods

	/**
	 * Gets the current tick of the scheduler.
	 *
	 * @return The current scheduler tick.
	 */
	public int getCurrentTick()
	{
		return this.currentTick.get();
	}

	/**
	 * Perform a number of ticks on the server.
	 *
	 * @param ticks The amount of ticks to perform.
	 */
	public void performTicks(int ticks)
	{
		for (int i = 0; i < ticks; i++)
		{
			performOneTick();
		}
	}

	/**
	 * Gets the number of async tasks which are awaiting execution.
	 *
	 * @return The number of queued async tasks.
	 */
	public int getNumberOfQueuedAsyncTasks()
	{
		return asyncScheduler.getNumberOfQueuedAsyncTasks();
	}

	public @NotNull Future<Void> executeAsyncEvent(@NotNull Event event)
	{
		return executeAsyncEvent(event, null);
	}

	@SuppressWarnings("unchecked")
	public <T extends Event> @NotNull Future<Void> executeAsyncEvent(@NotNull T event, @Nullable Consumer<T> func)
	{
		Preconditions.checkNotNull(event, "Cannot call a null event!");
		Preconditions.checkState(event.isAsynchronous(), "Cannot invoke a non-asynchronous event asynchronously!");
		return (Future<Void>) ((AsyncSchedulerMock) this.asyncScheduler).execute(() ->
		{
			Bukkit.getPluginManager().callEvent(event);
			if (func != null)
			{
				func.accept(event);
			}
		});
	}

	/**
	 * Sets the timeout for waiting for asynchronous tasks to finish.
	 *
	 * @param shutdownTimeout The timeout, in milliseconds.
	 */
	public void setShutdownTimeout(int shutdownTimeout)
	{
		this.shutdownTimeout = shutdownTimeout;
	}

	public void shutdown()
	{
		this.shutdown = true;
		for (Plugin plugin : Bukkit.getPluginManager().getPlugins())
		{
			cancelTasks(plugin);
		}
		waitAsyncTasksFinished();
	}

	/**
	 * Waits up to shutdownTimeout milliseconds for asynchronous tasks to finish.
	 * This does NOT prevent new async tasks from being added.
	 *
	 * @see #setShutdownTimeout(int)
	 */
	public void waitAsyncTasksFinished()
	{
		long startTime = System.currentTimeMillis();

		while (System.currentTimeMillis() - startTime > shutdownTimeout && !getActiveWorkers().isEmpty())
		{
			try
			{
				Thread.sleep(50L);
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}

		for (BukkitWorker overdueWorker : getActiveWorkers())
		{
			if (false) continue; // Get rid of "make exception conditional" warning
			throw new IllegalStateException("Plugin " + overdueWorker.getOwner() + " is not properly shutting down its async tasks!");
		}
	}

	// endregion

}
