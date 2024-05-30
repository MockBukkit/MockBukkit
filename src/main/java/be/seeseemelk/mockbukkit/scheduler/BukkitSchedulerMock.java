package be.seeseemelk.mockbukkit.scheduler;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * Mock implementation of a {@link BukkitScheduler}.
 */
public class BukkitSchedulerMock implements BukkitScheduler
{

	private static final String LOGGER_NAME = "BukkitSchedulerMock";
	private final ThreadPoolExecutor pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
			new SynchronousQueue<>());
	private final ExecutorService asyncEventExecutor = Executors.newCachedThreadPool();
	private final List<Future<?>> queuedAsyncEvents = new ArrayList<>();
	private final TaskList scheduledTasks = new TaskList();
	private final List<BukkitWorker> activeWorkers = new ArrayList<>();
	private final AtomicReference<Exception> asyncException = new AtomicReference<>();

	// This variable must be accessed while synchronizing on
	// BukkitSchedulerMock.this to avoid data races and race conditions
	private long currentTick = 0;

	private final AtomicInteger id = new AtomicInteger();
	private long executorTimeout = 60000;
	private final List<BukkitWorker> overdueTasks = new ArrayList<>();

	private @NotNull Runnable wrapTask(@NotNull ScheduledTask task)
	{
		return () -> {
			task.setRunning(true);
			if (!task.isSync())
				this.activeWorkers.add(task);
			task.run();
			if (!task.isSync())
				this.activeWorkers.remove(task);
			task.setRunning(false);
		};
	}

	/**
	 * Sets the maximum time to wait for async tasks to finish before terminating them.
	 *
	 * @param timeout The timeout in milliseconds.
	 */
	public void setShutdownTimeout(long timeout)
	{
		this.executorTimeout = timeout;
	}

	/**
	 * Shuts the scheduler down. Note that this function will throw exception that where thrown by old asynchronous
	 * tasks.
	 */
	public void shutdown()
	{
		waitAsyncTasksFinished();
		shutdownPool(pool);

		if (asyncException.get() != null)
			throw new AsyncTaskException(asyncException.get());

		waitAsyncEventsFinished();
		shutdownPool(asyncEventExecutor);
	}

	/**
	 * Shuts down the given executor service, waiting up to the shutdown timeout for all tasks to finish.
	 *
	 * @param pool The pool to shut down.
	 * @see #setShutdownTimeout(long)
	 */
	private void shutdownPool(@NotNull ExecutorService pool)
	{
		pool.shutdown();
		try
		{
			if (!pool.awaitTermination(this.executorTimeout, TimeUnit.MILLISECONDS))
			{
				pool.shutdownNow();
			}
		}
		catch (InterruptedException e)
		{
			pool.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Executes an asynchronous event.
	 *
	 * @param event The event to execute.
	 * @return A future representing the task.
	 */
	public @NotNull Future<?> executeAsyncEvent(@NotNull Event event)
	{
		return executeAsyncEvent(event, null);
	}

	/**
	 * Executes an asynchronous event.
	 *
	 * @param event The event to execute.
	 * @param func  A consumer to call after the event is invoked.
	 * @param <T>   The event type.
	 * @return A future representing the task.
	 */
	public <T extends Event> @NotNull Future<?> executeAsyncEvent(@NotNull T event, @Nullable Consumer<T> func)
	{
		MockBukkit.ensureMocking();
		Preconditions.checkNotNull(event, "Cannot call a null event!");
		Future<?> future = asyncEventExecutor.submit(() -> {
			MockBukkit.getMock().getPluginManager().callEvent(event);
			if (func != null)
			{
				func.accept(event);
			}
		});
		queuedAsyncEvents.add(future);
		return future;
	}

	/**
	 * Get the current tick of the server.
	 *
	 * @return The current tick of the server.
	 */
	public synchronized long getCurrentTick()
	{
		return currentTick;
	}

	/**
	 * Perform one tick on the server.
	 */
	public synchronized void performOneTick()
	{
		currentTick++;
		List<ScheduledTask> oldTasks = scheduledTasks.getCurrentTaskList();

		for (ScheduledTask task : oldTasks)
		{
			if (task.getScheduledTick() == currentTick && !task.isCancelled())
			{
				if (task.isSync())
				{
					wrapTask(task).run();
				}
				else
				{
					pool.submit(wrapTask(task));
					task.submitted();
				}

				if (task instanceof RepeatingTask repeatingTask)
				{
					if (!task.isCancelled())
					{
						repeatingTask.updateScheduledTick();
						scheduledTasks.addTask(task);
					}
				}
				else
				{
					task.cancel();
				}
			}
		}
	}

	/**
	 * Perform a number of ticks on the server.
	 *
	 * @param ticks The number of ticks to executed.
	 */
	public void performTicks(long ticks)
	{
		for (long i = 0; i < ticks; i++)
		{
			performOneTick();
		}
	}

	/**
	 * Gets the number of async tasks which are awaiting execution.
	 *
	 * @return The number of async tasks which are pending execution.
	 */
	public int getNumberOfQueuedAsyncTasks()
	{
		int queuedAsync = 0;
		for (ScheduledTask task : scheduledTasks.getCurrentTaskList())
		{
			if (task.isSync() || task.isCancelled() || task.isRunning())
			{
				continue;
			}
			queuedAsync++;
		}
		return queuedAsync;
	}

	/**
	 * Waits until all asynchronous tasks have finished executing. If you have an asynchronous task that runs
	 * indefinitely, this function will never return. Note that this will not wait for async events to finish.
	 */
	public void waitAsyncTasksFinished()
	{
		// Cancel repeating tasks so they don't run forever.
		synchronized (scheduledTasks.tasks)
		{
			scheduledTasks.tasks.entrySet().stream().filter(entry -> entry.getValue() instanceof RepeatingTask)
					.forEach(entry -> scheduledTasks.cancelTask(entry.getKey()));

			// Make sure all tasks get to execute. (except for repeating asynchronous tasks,
			// they only will fire once)
			while (scheduledTasks.getScheduledTaskCount() > 0)
			{
				performOneTick();
			}
		}

		// Wait for all tasks to finish executing.
		long systemTime = System.currentTimeMillis();
		while (pool.getActiveCount() > 0)
		{
			try
			{
				Thread.sleep(10L);
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
				return;
			}

			if (System.currentTimeMillis() <= (systemTime + executorTimeout))
				continue;

			// If a plugin has left a runnable going and not cancelled it we could call this
			// bad practice.
			// We should force interrupt all these runnables, forcing them to throw
			// Interrupted Exceptions
			// if they handle that.
			for (ScheduledTask task : scheduledTasks.getCurrentTaskList())
			{
				if (!task.isRunning())
					continue;
				task.cancel();
				cancelTask(task.getTaskId());
				throw new RuntimeException("Forced Cancellation of task owned by " + task.getOwner().getName());
			}
			pool.shutdownNow();
		}
	}

	/**
	 * Blocks until all asynchronous event invocations are finished.
	 */
	public void waitAsyncEventsFinished()
	{
		for (Future<?> futureEvent : List.copyOf(queuedAsyncEvents))
		{
			if (futureEvent.isDone())
			{
				queuedAsyncEvents.remove(futureEvent);
			}
			else
			{
				try
				{
					queuedAsyncEvents.remove(futureEvent);
					futureEvent.get();
				}
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}
				catch (ExecutionException e)
				{
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public synchronized @NotNull BukkitTask runTask(@NotNull Plugin plugin, @NotNull Runnable task)
	{
		return runTaskLater(plugin, task, 1L);
	}

	@Override
	@Deprecated(since = "1.7.10")
	public synchronized @NotNull BukkitTask runTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task)
	{
		return runTask(plugin, (Runnable) task);
	}

	@Override
	public synchronized @NotNull BukkitTask runTaskLater(@NotNull Plugin plugin, @NotNull Runnable task, long delay)
	{
		delay = Math.max(delay, 1);
		ScheduledTask scheduledTask = new ScheduledTask(id.getAndIncrement(), plugin, true, currentTick + delay, task);
		scheduledTasks.addTask(scheduledTask);
		return scheduledTask;
	}

	@Override
	public synchronized @NotNull BukkitTask runTaskTimer(@NotNull Plugin plugin, @NotNull Runnable task, long delay,
			long period)
	{
		delay = Math.max(delay, 1);
		RepeatingTask repeatingTask = new RepeatingTask(id.getAndIncrement(), plugin, true, currentTick + delay, period,
				task);
		scheduledTasks.addTask(repeatingTask);
		return repeatingTask;
	}

	@Override
	@Deprecated(since = "1.7.10")
	public synchronized @NotNull BukkitTask runTaskTimer(@NotNull Plugin plugin, @NotNull BukkitRunnable task,
			long delay, long period)
	{
		return runTaskTimer(plugin, (Runnable) task, delay, period);
	}

	@Override
	public synchronized int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay)
	{
		Logger.getLogger(LOGGER_NAME).warning("Consider using runTaskLater instead of scheduleSyncDelayTask");
		return runTaskLater(plugin, task, delay).getTaskId();
	}

	@Override
	@Deprecated(since = "1.7.10")
	public synchronized int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay)
	{
		Logger.getLogger(LOGGER_NAME).warning("Consider using runTaskLater instead of scheduleSyncDelayTask");
		return runTaskLater(plugin, (Runnable) task, delay).getTaskId();
	}

	@Override
	public synchronized int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task)
	{
		Logger.getLogger(LOGGER_NAME).warning("Consider using runTask instead of scheduleSyncDelayTask");
		return runTask(plugin, task).getTaskId();
	}

	@Override
	@Deprecated(since = "1.7.10")
	public synchronized int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task)
	{
		Logger.getLogger(LOGGER_NAME).warning("Consider using runTask instead of scheduleSyncDelayTask");
		return runTask(plugin, (Runnable) task).getTaskId();
	}

	@Override
	public synchronized int scheduleSyncRepeatingTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay,
			long period)
	{
		Logger.getLogger(LOGGER_NAME).warning("Consider using runTaskTimer instead of scheduleSyncRepeatingTask");
		return runTaskTimer(plugin, task, delay, period).getTaskId();
	}

	@Override
	@Deprecated(since = "1.7.10")
	public synchronized int scheduleSyncRepeatingTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay,
			long period)
	{
		Logger.getLogger(LOGGER_NAME).warning("Consider using runTaskTimer instead of scheduleSyncRepeatingTask");
		return runTaskTimer(plugin, (Runnable) task, delay, period).getTaskId();
	}

	@Override
	@Deprecated(since = "1.5")
	public synchronized int scheduleAsyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay)
	{
		Logger.getLogger(LOGGER_NAME)
				.warning("Consider using runTaskLaterAsynchronously instead of scheduleAsyncDelayedTask");
		return runTaskLaterAsynchronously(plugin, task, delay).getTaskId();
	}

	@Override
	@Deprecated(since = "1.5")
	public synchronized int scheduleAsyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task)
	{
		Logger.getLogger(LOGGER_NAME)
				.warning("Consider using runTaskAsynchronously instead of scheduleAsyncDelayedTask");
		return runTaskAsynchronously(plugin, task).getTaskId();
	}

	@Override
	@Deprecated(since = "1.5")
	public synchronized int scheduleAsyncRepeatingTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay,
			long period)
	{
		Logger.getLogger(LOGGER_NAME)
				.warning("Consider using runTaskTimerAsynchronously instead of scheduleAsyncRepeatingTask");
		return runTaskTimerAsynchronously(plugin, task, delay, period).getTaskId();
	}

	@Override
	public synchronized <T> @NotNull Future<T> callSyncMethod(@NotNull Plugin plugin, @NotNull Callable<T> task)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void cancelTask(int taskId)
	{
		scheduledTasks.cancelTask(taskId);
	}

	@Override
	public void cancelTasks(@NotNull Plugin plugin)
	{
		for (ScheduledTask task : scheduledTasks.getCurrentTaskList())
		{
			if (Objects.equals(task.getOwner(), plugin)) // Implicit null check
			{
				task.cancel();
			}
		}
	}

	@Override
	public boolean isCurrentlyRunning(int taskId)
	{
		return scheduledTasks.tasks.containsKey(taskId);
	}

	@Override
	public boolean isQueued(int taskId)
	{
		for (ScheduledTask task : scheduledTasks.getCurrentTaskList())
		{
			if (task.getTaskId() == taskId)
				return !task.isCancelled();
		}
		return false;
	}

	@Override
	public @NotNull List<BukkitWorker> getActiveWorkers()
	{
		return this.activeWorkers;
	}

	@Override
	public @NotNull List<BukkitTask> getPendingTasks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public synchronized @NotNull BukkitTask runTaskAsynchronously(@NotNull Plugin plugin, @NotNull Runnable task)
	{
		ScheduledTask scheduledTask = new ScheduledTask(id.getAndIncrement(), plugin, false, currentTick,
				new AsyncRunnable(task));
		pool.execute(wrapTask(scheduledTask));
		return scheduledTask;
	}

	@Override
	public synchronized @NotNull BukkitTask runTaskAsynchronously(@NotNull Plugin plugin, @NotNull BukkitRunnable task)
	{
		return runTaskAsynchronously(plugin, (Runnable) task);
	}

	@Override
	public synchronized @NotNull BukkitTask runTaskLater(@NotNull Plugin plugin, @NotNull BukkitRunnable task,
			long delay)
	{
		return runTaskLater(plugin, (Runnable) task, delay);
	}

	@Override
	public synchronized @NotNull BukkitTask runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull Runnable task,
			long delay)
	{
		delay = Math.max(delay, 1);
		ScheduledTask scheduledTask = new ScheduledTask(id.getAndIncrement(), plugin, false, currentTick + delay,
				new AsyncRunnable(task));
		scheduledTasks.addTask(scheduledTask);
		return scheduledTask;
	}

	@Override
	public synchronized @NotNull BukkitTask runTaskLaterAsynchronously(@NotNull Plugin plugin,
			@NotNull BukkitRunnable task, long delay)
	{
		return runTaskLaterAsynchronously(plugin, (Runnable) task, delay);
	}

	@Override
	public synchronized @NotNull BukkitTask runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull Runnable task,
			long delay, long period)
	{
		delay = Math.max(delay, 1);
		RepeatingTask scheduledTask = new RepeatingTask(id.getAndIncrement(), plugin, false, currentTick + delay,
				period, new AsyncRunnable(task));
		scheduledTasks.addTask(scheduledTask);
		return scheduledTask;
	}

	@Override
	public synchronized @NotNull BukkitTask runTaskTimerAsynchronously(@NotNull Plugin plugin,
			@NotNull BukkitRunnable task, long delay, long period)
	{
		return runTaskTimerAsynchronously(plugin, (Runnable) task, delay, period);
	}

	@Override
	public synchronized void runTask(@NotNull Plugin plugin, @NotNull Consumer<? super BukkitTask> task)
	{
		runTaskLater(plugin, task, 0L);
	}

	@Override
	public synchronized void runTaskAsynchronously(@NotNull Plugin plugin, @NotNull Consumer<? super BukkitTask> task)
	{
		ScheduledTask scheduledTask = new ScheduledTask(this.id.getAndIncrement(), plugin, false, this.currentTick,
				task);
		pool.execute(wrapTask(scheduledTask));
	}

	@Override
	public synchronized void runTaskLater(@NotNull Plugin plugin, @NotNull Consumer<? super BukkitTask> task,
			long delay)
	{
		delay = Math.max(delay, 1);
		ScheduledTask scheduledTask = new ScheduledTask(id.getAndIncrement(), plugin, true, currentTick + delay, task);
		scheduledTasks.addTask(scheduledTask);
	}

	@Override
	public synchronized void runTaskLaterAsynchronously(@NotNull Plugin plugin,
			@NotNull Consumer<? super BukkitTask> task, long delay)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public synchronized void runTaskTimer(@NotNull Plugin plugin, @NotNull Consumer<? super BukkitTask> task,
			long delay, long period)
	{
		delay = Math.max(delay, 1);
		RepeatingTask repeatingTask = new RepeatingTask(id.getAndIncrement(), plugin, true, currentTick + delay, period,
				task);
		scheduledTasks.addTask(repeatingTask);
	}

	@Override
	public synchronized void runTaskTimerAsynchronously(@NotNull Plugin plugin,
			@NotNull Consumer<? super BukkitTask> task, long delay, long period)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Executor getMainThreadExecutor(@NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		return command -> {
			Preconditions.checkNotNull(command, "Command cannot be null");
			this.runTask(plugin, command);
		};
	}

	/**
	 * Gets the amount of thread currently executing asynchronous tasks.
	 *
	 * @return The amount of active task threads.
	 */
	protected int getActiveRunningCount()
	{
		return pool.getActiveCount();
	}

	/**
	 * Adds any active workers to the overdue tasks list.
	 */
	public void saveOverdueTasks()
	{
		this.overdueTasks.clear();
		this.overdueTasks.addAll(getActiveWorkers());
	}

	/**
	 * @return A list of overdue tasks saved by {@link #saveOverdueTasks()}.
	 */
	public @NotNull @UnmodifiableView List<BukkitWorker> getOverdueTasks()
	{
		return Collections.unmodifiableList(this.overdueTasks);
	}

	/**
	 * Asserts that there were no overdue tasks from {@link #saveOverdueTasks()}.
	 */
	public void assertNoOverdueTasks()
	{
		if (!overdueTasks.isEmpty())
			throw new AssertionFailedError("There are overdue tasks: " + overdueTasks);
	}

	private static class TaskList
	{

		private final @NotNull Map<Integer, ScheduledTask> tasks;

		private TaskList()
		{
			tasks = Collections.synchronizedMap(new HashMap<>());
		}

		/**
		 * Add a task but locks the Task list to other writes while adding it.
		 *
		 * @param task the task to remove.
		 * @return true on success.
		 */
		private boolean addTask(@Nullable ScheduledTask task)
		{
			if (task == null)
			{
				return false;
			}
			tasks.put(task.getTaskId(), task);
			return true;
		}

		protected final @NotNull List<ScheduledTask> getCurrentTaskList()
		{
			List<ScheduledTask> out = new ArrayList<>();
			synchronized (tasks)
			{
				if (tasks.size() != 0)
				{
					out.addAll(tasks.values());
				}
			}
			return out;

		}

		protected int getScheduledTaskCount()
		{
			int scheduled = 0;
			synchronized (tasks)
			{
				if (tasks.size() == 0)
				{
					return 0;
				}

				for (ScheduledTask task : tasks.values())
				{
					if (task.isCancelled() || task.isRunning())
						continue;
					scheduled++;
				}
			}
			return scheduled;
		}

		protected boolean cancelTask(int taskID)
		{
			synchronized (tasks)
			{
				ScheduledTask task = tasks.get(taskID);
				if (task != null)
				{
					task.cancel();
					return true;
				}
			}
			return false;
		}

	}

	private final class AsyncRunnable implements Runnable
	{

		private final Runnable task;

		private AsyncRunnable(Runnable task)
		{
			this.task = task;
		}

		@Override
		public void run()
		{
			try
			{
				this.task.run();
			}
			catch (Exception t)
			{
				asyncException.set(t);
			}
		}

	}

}
