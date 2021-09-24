package be.seeseemelk.mockbukkit.scheduler;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.logging.Logger;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;

import org.apache.commons.lang.Validate;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;
import org.jetbrains.annotations.NotNull;

public class BukkitSchedulerMock implements BukkitScheduler
{
	private static final String LOGGER_NAME = "BukkitSchedulerMock";
	private final ThreadPoolExecutor pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
	        60L, TimeUnit.SECONDS,
	        new SynchronousQueue<>());
	private final ExecutorService asyncEventExecutor = Executors.newCachedThreadPool();
	private final TaskList scheduledTasks = new TaskList();
	private final AtomicReference<Exception> asyncException = new AtomicReference<>();
	private long currentTick = 0;
	private int id = 0;
	private long executorTimeout = 60000;

	private static Runnable wrapTask(ScheduledTask task)
	{
		return () ->
		{
			task.setRunning(true);
			task.run();
			task.setRunning(false);
		};
	}


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
		pool.shutdown();
		if (asyncException.get() != null)
			throw new AsyncTaskException(asyncException.get());
		asyncEventExecutor.shutdownNow();
	}

	public @NotNull Future<?> executeAsyncEvent(Event event)
	{
		Validate.notNull(event, "Cannot schedule an Event that is null!");
		return asyncEventExecutor.submit(() -> MockBukkit.getMock().getPluginManager().callEvent(event));
	}


	/**
	 * Get the current tick of the server.
	 *
	 * @return The current tick of the server.
	 */
	public long getCurrentTick()
	{
		return currentTick;
	}

	/**
	 * Perform one tick on the server.
	 */
	public void performOneTick()
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
				}

				if (task instanceof RepeatingTask && !task.isCancelled())
				{
					((RepeatingTask) task).updateScheduledTick();
					scheduledTasks.addTask(task);
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
	 * indefinitely, this function will never return.
	 */
	public void waitAsyncTasksFinished()
	{
		// Make sure all tasks get to execute. (except for repeating asynchronous tasks, they only will fire once)
		while (scheduledTasks.getScheduledTaskCount() > 0)
		{
			performOneTick();
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
			if (System.currentTimeMillis() > (systemTime + executorTimeout))
			{
				// If a plugin has left a a runnable going and not cancelled it we could call this bad practice.
				// we should now force interrupt all these runnables forcing them to throw Interrupted Exceptions.
				// if they handle that
				for (ScheduledTask task : scheduledTasks.getCurrentTaskList())
				{
					if (task.isRunning())
					{
						task.cancel();
						cancelTask(task.getTaskId());
						throw new RuntimeException("Forced Cancellation of task owned by "
						                           + task.getOwner().getName());
					}
				}
				pool.shutdownNow();
			}
		}
	}

	@Override
	public @NotNull BukkitTask runTask(@NotNull Plugin plugin, @NotNull Runnable task)
	{
		return runTaskLater(plugin, task, 1L);
	}

	@Override
	public @NotNull BukkitTask runTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task)
	{
		return runTask(plugin, (Runnable) task);
	}

	@Override
	public @NotNull BukkitTask runTaskLater(@NotNull Plugin plugin, @NotNull Runnable task, long delay)
	{
		delay = Math.max(delay, 1);
		ScheduledTask scheduledTask = new ScheduledTask(id++, plugin, true, currentTick + delay, task);
		scheduledTasks.addTask(scheduledTask);
		return scheduledTask;
	}

	@Override
	public @NotNull BukkitTask runTaskTimer(@NotNull Plugin plugin, @NotNull Runnable task, long delay, long period)
	{
		delay = Math.max(delay, 1);
		RepeatingTask repeatingTask = new RepeatingTask(id++, plugin, true, currentTick + delay, period, task);
		scheduledTasks.addTask(repeatingTask);
		return repeatingTask;
	}

	@Override
	public @NotNull BukkitTask runTaskTimer(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay, long period)
	{
		return runTaskTimer(plugin, (Runnable) task, delay, period);
	}

	@Override
	public int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay)
	{
		Logger.getLogger(LOGGER_NAME).warning("Consider using runTaskLater instead of scheduleSyncDelayTask");
		return runTaskLater(plugin, task, delay).getTaskId();
	}

	@Override
	public int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay)
	{
		Logger.getLogger(LOGGER_NAME).warning("Consider using runTaskLater instead of scheduleSyncDelayTask");
		return runTaskLater(plugin, (Runnable) task, delay).getTaskId();
	}

	@Override
	public int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task)
	{
		Logger.getLogger(LOGGER_NAME).warning("Consider using runTask instead of scheduleSyncDelayTask");
		return runTask(plugin, task).getTaskId();
	}

	@Override
	public int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task)
	{
		Logger.getLogger(LOGGER_NAME).warning("Consider using runTask instead of scheduleSyncDelayTask");
		return runTask(plugin, (Runnable) task).getTaskId();
	}

	@Override
	public int scheduleSyncRepeatingTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay, long period)
	{
		Logger.getLogger(LOGGER_NAME).warning("Consider using runTaskTimer instead of scheduleSyncRepeatingTask");
		return runTaskTimer(plugin, task, delay, period).getTaskId();
	}

	@Override
	public int scheduleSyncRepeatingTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay, long period)
	{
		Logger.getLogger(LOGGER_NAME).warning("Consider using runTaskTimer instead of scheduleSyncRepeatingTask");
		return runTaskTimer(plugin, (Runnable) task, delay, period).getTaskId();
	}

	@Override
	public int scheduleAsyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay)
	{
		Logger.getLogger(LOGGER_NAME)
		.warning("Consider using runTaskLaterAsynchronously instead of scheduleAsyncDelayedTask");
		return runTaskLaterAsynchronously(plugin, task, delay).getTaskId();
	}

	@Override
	public int scheduleAsyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task)
	{
		Logger.getLogger(LOGGER_NAME)
		.warning("Consider using runTaskAsynchronously instead of scheduleAsyncDelayedTask");
		return runTaskAsynchronously(plugin, task).getTaskId();
	}

	@Override
	public int scheduleAsyncRepeatingTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay, long period)
	{
		Logger.getLogger(LOGGER_NAME)
		.warning("Consider using runTaskTimerAsynchronously instead of scheduleAsyncRepeatingTask");
		return runTaskTimerAsynchronously(plugin, task, delay, period).getTaskId();
	}

	@Override
	public <T> @NotNull Future<T> callSyncMethod(@NotNull Plugin plugin, @NotNull Callable<T> task)
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
			if (task.getOwner() != null)
			{
				if (task.getOwner().equals(plugin))
				{
					task.cancel();
				}
			}
		}
	}

	@Override
	public boolean isCurrentlyRunning(int taskId)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull List<BukkitTask> getPendingTasks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BukkitTask runTaskAsynchronously(@NotNull Plugin plugin, @NotNull Runnable task)
	{
		ScheduledTask scheduledTask = new ScheduledTask(id++, plugin, false, currentTick, new AsyncRunnable(task));
		pool.execute(wrapTask(scheduledTask));
		return scheduledTask;
	}

	@Override
	public @NotNull BukkitTask runTaskAsynchronously(@NotNull Plugin plugin, @NotNull BukkitRunnable task)
	{
		return runTaskAsynchronously(plugin, (Runnable) task);
	}

	@Override
	public @NotNull BukkitTask runTaskLater(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay)
	{
		return runTaskLater(plugin, (Runnable) task, delay);
	}

	@Override
	public @NotNull BukkitTask runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull Runnable task, long delay)
	{
		ScheduledTask scheduledTask = new ScheduledTask(id++, plugin, false, currentTick + delay,
		        new AsyncRunnable(task));
		scheduledTasks.addTask(scheduledTask);
		return scheduledTask;
	}

	@Override
	public @NotNull BukkitTask runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay)
	{
		return runTaskLaterAsynchronously(plugin, (Runnable) task, delay);
	}

	@Override
	public @NotNull BukkitTask runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull Runnable task, long delay, long period)
	{
		RepeatingTask scheduledTask = new RepeatingTask(id++, plugin, false, currentTick + delay, period,
		        new AsyncRunnable(task));
		scheduledTasks.addTask(scheduledTask);
		return scheduledTask;
	}

	@Override
	public @NotNull BukkitTask runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay, long period)
	{
		return runTaskTimerAsynchronously(plugin, (Runnable) task, delay, period);
	}

	class AsyncRunnable implements Runnable
	{
		private final Runnable task;

		private AsyncRunnable(Runnable runnable)
		{
			task = runnable;
		}

		@Override
		public void run()
		{
			try
			{
				task.run();
			}
			catch (Exception t)
			{
				asyncException.set(t);
			}
		}

	}

	@Override
	public void runTask(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void runTaskAsynchronously(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void runTaskLater(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void runTaskTimer(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay, long period)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay, long period)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	protected int getActiveRunningCount()
	{
		return pool.getActiveCount();
	}

	private static class TaskList
	{

		private final Map<Integer, ScheduledTask> tasks;

		private TaskList()
		{
			tasks = new ConcurrentHashMap<>();
		}

		/**
		 * Add a task but locks the Task list to other writes while adding it.
		 *
		 * @param task the task to remove.
		 * @return true on success.
		 */
		private boolean addTask(ScheduledTask task)
		{
			if (task == null)
			{
				return false;
			}
			tasks.put(task.getTaskId(), task);
			return true;
		}


		protected final List<ScheduledTask> getCurrentTaskList()
		{
			List<ScheduledTask> out = new ArrayList<>();
			if (tasks.size() != 0)
			{
				out.addAll(tasks.values());
			}
			return out;

		}

		protected int getScheduledTaskCount()
		{
			int scheduled = 0;
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
			return scheduled;
		}

		protected boolean cancelTask(int taskID)
		{
			if (tasks.containsKey(taskID))
			{
				ScheduledTask task = tasks.get(taskID);
				task.cancel();
				tasks.put(taskID, task);
				return true;
			}
			return false;
		}
	}
}
