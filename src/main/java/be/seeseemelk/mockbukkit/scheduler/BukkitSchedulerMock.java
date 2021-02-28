package be.seeseemelk.mockbukkit.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.jetbrains.annotations.NotNull;

public class BukkitSchedulerMock implements BukkitScheduler
{
	private static final String LOGGER_NAME = "BukkitSchedulerMock";
	private long currentTick = 0;
	private int id = 0;
	private List<ScheduledTask> tasks = new LinkedList<>();
	private final ExecutorService pool = Executors.newCachedThreadPool();
	private final AtomicInteger asyncTasksRunning = new AtomicInteger();
	private final AtomicReference<Exception> asyncException = new AtomicReference<>();
	private int asyncTasksQueued = 0;
	private boolean shuttingDown = false;

	/**
	 * Shuts the scheduler down. Note that this function will throw exception that where thrown by old asynchronous
	 * tasks.
	 */
	public void shutdown()
	{
		shuttingDown = true;
		waitAsyncTasksFinished();
		pool.shutdown();

		if (asyncException.get() != null)
			throw new AsyncTaskException(asyncException.get());
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
		List<ScheduledTask> oldTasks = tasks;
		tasks = new LinkedList<>();

		for (ScheduledTask task : oldTasks)
		{
			if (task.getScheduledTick() == currentTick && !task.isCancelled())
			{
				if (task.isSync())
				{
					task.run();
				}
				else
				{
					asyncTasksRunning.incrementAndGet();
					pool.execute(task.getRunnable());
					asyncTasksQueued--;
				}

				if (task instanceof RepeatingTask && !task.isCancelled())
				{
					((RepeatingTask) task).updateScheduledTick();
					tasks.add(task);
				}
			}
			else if (!task.isCancelled())
			{
				tasks.add(task);
			}
		}
		if (oldTasks.size() == 0 && asyncTasksQueued > 0)
		{
			Logger.getLogger(LOGGER_NAME).warning("Asynchronous Tasks may have been lost -> " + asyncTasksQueued);
			asyncTasksQueued = 0;
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
	 * Waits until all asynchronous tasks have finished executing. If you have an asynchronous task that runs
	 * indefinitely, this function will never return.
	 */
	public void waitAsyncTasksFinished()
	{
		// Make sure all tasks get to execute. (except for repeating asynchronous tasks, they only will fire once)
		while (asyncTasksQueued > 0)
			performOneTick();

		// Wait for all tasks to finish executing.
		while (asyncTasksRunning.get() > 0)
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
		checkForShutDown(scheduledTask);
		tasks.add(scheduledTask);
		return scheduledTask;
	}

	@Override
	public @NotNull BukkitTask runTaskTimer(@NotNull Plugin plugin, @NotNull Runnable task, long delay, long period)
	{
		delay = Math.max(delay, 1);
		RepeatingTask repeatingTask = new RepeatingTask(id++, plugin, true, currentTick + delay, period, task);
		checkForShutDown(repeatingTask);
		tasks.add(repeatingTask);
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
		for (ScheduledTask task : tasks)
		{
			if (task.getTaskId() == taskId)
			{
				cancelTask(task);
				return;
			}
		}
	}

	private void cancelTask(@NotNull ScheduledTask task)
	{

		if (!task.isCancelled())
		{
			task.cancel();
			if (asyncTasksQueued > 0)
			{
				asyncTasksQueued--;
			}
		}
	}

	@Override
	public void cancelTasks(@NotNull Plugin plugin)
	{
		for (ScheduledTask task : tasks)
		{
			if (plugin.equals(task.getOwner()))
			{
				cancelTask(task);
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
		for (ScheduledTask task : tasks)
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
		checkForShutDown(scheduledTask);
		asyncTasksRunning.incrementAndGet();
		pool.execute(scheduledTask.getRunnable());
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
		checkForShutDown(scheduledTask);
		asyncTasksQueued++;
		tasks.add(scheduledTask);
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
		checkForShutDown(scheduledTask);
		tasks.add(scheduledTask);
		return scheduledTask;
	}

	private void checkForShutDown(ScheduledTask task)
	{
		if (shuttingDown)
		{
			String name;
			if (task.getOwner() != null)
			{
				name = task.getOwner().getName();
			}
			else
			{
				name = "";
			}
			Logger.getLogger(LOGGER_NAME).warning(
			    name + "(" + task.getRunnable().getClass().getSimpleName()
			    +  "): Scheduler is shutting down - why are you scheduling tasks - CANCELLED");
			task.cancel();
		}
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
			asyncTasksRunning.decrementAndGet();
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
}
