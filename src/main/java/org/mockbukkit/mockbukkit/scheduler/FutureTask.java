package org.mockbukkit.mockbukkit.scheduler;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * The {@link Future} returned by {@link BukkitScheduler#callSyncMethod(Plugin, Callable)}.
 */
class FutureTask<T> implements Future<T>
{

	// This variable must be accessed while synchronizing on FutureTask.this to avoid data races and race conditions
	private boolean hasRun = false;

	private final ScheduledTask task;
	private final CompletableFuture<T> future = new CompletableFuture<>();

	/**
	 * Constructs and schedules a new {@link FutureTask} with the provided parameters.
	 *
	 * @param scheduler The {@link BukkitSchedulerMock}.
	 * @param plugin    The plugin owning the task.
	 * @param callable  The callable to run.
	 */
	public FutureTask(@NotNull BukkitSchedulerMock scheduler, @NotNull Plugin plugin, @NotNull Callable<T> callable)
	{
		Preconditions.checkNotNull(callable, "Callable cannot be null");

		this.task = (ScheduledTask) scheduler.runTask(plugin, () ->
		{
			synchronized (FutureTask.this)
			{
				hasRun = true;
				if (future.isCancelled())
				{
					return;
				}
			}

			try
			{
				future.complete(callable.call());
			}
			catch (Throwable t)
			{
				future.completeExceptionally(t);
			}
		});
		this.task.addOnCancelled(() ->
		{
			synchronized (FutureTask.this)
			{
				if (!hasRun)
				{
					future.cancel(false);
				}
			}
		});

		// Handle race condition when the task is cancelled before addOnCancelled is called
		if (this.task.isCancelled())
		{
			future.cancel(false);
		}
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning)
	{
		boolean cancelled;
		synchronized (FutureTask.this)
		{
			if (hasRun)
			{
				return false;
			}

			// future.cancel(...) returns true if the task was already cancelled,
			// but Bukkit returns true only if the task is being cancelled
			cancelled = future.isCancelled();
			future.cancel(false);
		}

		task.cancel();
		return !cancelled;
	}

	@Override
	public boolean isCancelled()
	{
		return future.isCancelled();
	}

	@Override
	public boolean isDone()
	{
		return future.isDone();
	}

	@Override
	public T get() throws InterruptedException, ExecutionException
	{
		return future.get();
	}

	@Override
	public T get(long timeout, @NonNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
	{
		return future.get(timeout, unit);
	}

}
