package be.seeseemelk.mockbukkit.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Taken and heavily modified from <a href="https://hub.spigotmc.org/stash/projects/SPIGOT/repos/craftbukkit/browse/src/main/java/org/bukkit/craftbukkit/scheduler/CraftFuture.java">CraftBukkit's CraftFuture</a>.
 */
class FutureTask<T> extends Task implements Future<T>
{

	private final Callable<T> callable;
	private T value;
	private Exception exception;

	protected FutureTask(BukkitScheduler scheduler, int id, Plugin plugin, Callable<T> callable)
	{
		super(scheduler, id, plugin, Task.NO_REPEATING, null);
		this.callable = callable;
	}

	@Override
	public synchronized boolean cancel(final boolean mayInterruptIfRunning)
	{
		if (getPeriod() != Task.NO_REPEATING)
			return false;

		setPeriod(Task.CANCELED);
		return true;
	}

	@Override
	public boolean isDone()
	{
		return this.getPeriod() != Task.NO_REPEATING && this.getPeriod() != Task.FUTURE_PROCESS;
	}

	@Override
	public T get() throws CancellationException, InterruptedException, ExecutionException
	{
		try
		{
			return this.get(0, TimeUnit.MILLISECONDS);
		}
		catch (final TimeoutException e)
		{
			throw new Error(e);
		}
	}

	@Override
	public synchronized T get(long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
	{
		timeout = unit.toMillis(timeout);
		long period = this.getPeriod();
		long time = timeout > 0 ? System.currentTimeMillis() : 0L;
		while (true)
		{
			if (period == Task.NO_REPEATING || period == Task.FUTURE_PROCESS)
			{
				this.wait(timeout);
				period = this.getPeriod();
				if (period == Task.NO_REPEATING || period == Task.FUTURE_PROCESS)
				{
					if (timeout == 0L)
						continue;

					timeout += time - (time = System.currentTimeMillis());
					if (timeout > 0)
						continue;

					throw new TimeoutException();
				}
			}
			if (period == Task.CANCELED)
				throw new CancellationException();

			if (period == Task.FUTURE_DONE)
			{
				if (this.exception == null)
					return this.value;
				throw new ExecutionException(this.exception);
			}
			throw new IllegalStateException("Expected a period of " + Task.NO_REPEATING + " to " + Task.FUTURE_DONE + ", but got " + period);
		}
	}

	@Override
	public void run()
	{
		synchronized (this)
		{
			if (getPeriod() == Task.CANCELED)
				return;
			setPeriod(Task.FUTURE_PROCESS);
		}
		try
		{
			this.value = this.callable.call();
		}
		catch (final Exception e)
		{
			this.exception = e;
		}
		finally
		{
			synchronized (this)
			{
				setPeriod(Task.FUTURE_DONE);
				this.notifyAll();
			}
		}
	}

	@Override
	synchronized boolean cancel0()
	{
		if (getPeriod() != Task.NO_REPEATING)
			return false;
		setPeriod(Task.CANCELED);
		notifyAll();
		return true;
	}

}
