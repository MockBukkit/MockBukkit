package be.seeseemelk.mockbukkit.scheduler;

import com.destroystokyo.paper.event.server.ServerExceptionEvent;
import com.destroystokyo.paper.exception.ServerSchedulerException;
import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Taken and heavily modified from <a href="https://github.com/PaperMC/Paper/blob/046466f3ba72d6bdaa0213cde64676dd0024ace0/patches/server/0191-Improved-Async-Task-Scheduler.patch">Paper's async scheduler</a>.
 */
public class AsyncSchedulerMock extends BukkitSchedulerMock
{

	private final ThreadPoolExecutor executor = new ThreadPoolExecutor(4, Integer.MAX_VALUE, 30L, TimeUnit.SECONDS, new SynchronousQueue<>());
	private final ExecutorService managerThread = Executors.newSingleThreadExecutor();
	private final List<Task> temp = new ArrayList<>();
	private final AtomicReference<Throwable> asyncException = new AtomicReference<>();

	protected AsyncSchedulerMock()
	{
		super(true);
		executor.allowCoreThreadTimeOut(true);
		executor.prestartAllCoreThreads();
	}

	@Override
	public void cancelTask(int taskId)
	{
		this.managerThread.execute(() -> this.removeTask(taskId));
	}

	private synchronized void removeTask(int taskId)
	{
		parsePending();
		this.pendingSync.removeIf((task) ->
		{
			if (task.getTaskId() != taskId)
				return false;
			task.cancel0();
			return true;
		});
	}

	@Override
	public void performOneTick()
	{
		this.managerThread.execute(this::runTasks);
		Throwable t = this.asyncException.get();
		if (t != null)
		{
			this.asyncException.set(null);
			if (t instanceof RuntimeException re)
			{
				throw re;
			}
			else
			{
				throw new RuntimeException(t);
			}
		}
	}

	private synchronized void runTasks()
	{
		parsePending();
		while (!this.pendingSync.isEmpty() && this.pendingSync.peek().getNextRun() <= super.currentTick.get())
		{
			Task task = this.pendingSync.remove();
			if (executeTask(task))
			{
				if (this.asyncException.get() != null)
				{
					break; // Don't loose exceptions
				}
				final long period = task.getPeriod();
				if (period > 0)
				{
					task.setNextRun(super.currentTick.get() + period);
					temp.add(task);
				}
			}
			parsePending();
		}
		this.pendingSync.addAll(temp);
		temp.clear();
	}

	/**
	 * Executes the given task.
	 *
	 * @param task The task to execute.
	 * @return True if the task ran, false if it was cancelled.
	 */
	private boolean executeTask(Task task)
	{
		Preconditions.checkState(this.isRunning, "Scheduler shutdown!");
		if (task.getPeriod() < Task.NO_REPEATING)
			return false;

		this.runners.put(task.getTaskId(), task);
		this.executor.execute(() ->
		{
			try
			{
				task.run();
			}
			catch (Exception t)
			{
				new ServerExceptionEvent(new ServerSchedulerException(t, task)).callEvent();
				asyncException.set(t);
			}
		});
		return true;
	}

	/**
	 * Executes a runnable on the async executor.
	 *
	 * @param r Runnable to execute
	 * @return A future representing the task.
	 */
	public Future<?> execute(Runnable r)
	{
		Preconditions.checkState(this.isRunning, "Scheduler shutdown!");
		return this.executor.submit(r);
	}

	@Override
	public synchronized void cancelTasks(@NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		parsePending();
		Iterator<Task> iterator = this.pendingSync.iterator();
		while (iterator.hasNext())
		{
			Task task = iterator.next();
			if (task.getTaskId() != -1 && task.getOwner().equals(plugin))
			{
				task.cancel0();
				iterator.remove();
			}
		}
	}

	@Override
	public int getNumberOfQueuedAsyncTasks()
	{
		return this.runners.size();
	}

	@Override
	public void shutdown()
	{
		super.shutdown();
		shutdownExecutor(executor);
		shutdownExecutor(managerThread);
	}

	private void shutdownExecutor(ExecutorService executor)
	{
		executor.shutdown();
		try
		{
			if (!executor.awaitTermination(this.shutdownTimeout, TimeUnit.MILLISECONDS))
			{
				executor.shutdownNow();
			}
		}
		catch (InterruptedException e)
		{
			executor.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

}
