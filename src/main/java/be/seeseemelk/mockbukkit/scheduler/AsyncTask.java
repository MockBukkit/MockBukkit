package be.seeseemelk.mockbukkit.scheduler;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitWorker;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Taken and heavily modified from <a href="https://hub.spigotmc.org/stash/projects/SPIGOT/repos/craftbukkit/browse/src/main/java/org/bukkit/craftbukkit/scheduler/CraftAsyncTask.java">CraftBukkit's CraftAsyncTask</a>.
 */
class AsyncTask extends Task
{

	private final List<BukkitWorker> workers = new LinkedList<>();
	private final Map<Integer, Task> runners;

	protected AsyncTask(@NotNull BukkitScheduler scheduler, @NotNull Map<Integer, Task> runners, int id, Plugin plugin, long delay, Object task)
	{
		super(scheduler, id, plugin, delay, task);
		Preconditions.checkNotNull(runners, "Runners cannot be null");
		this.runners = runners;
	}

	@Override
	public boolean isSync()
	{
		return false;
	}

	@Override
	public void run()
	{
		Thread thread = Thread.currentThread();

		synchronized (this.workers)
		{
			if (getPeriod() == Task.CANCELED)
				return;

			this.workers.add(new BukkitWorker()
			{
				@Override
				public @NotNull Thread getThread()
				{
					return thread;
				}

				@Override
				public int getTaskId()
				{
					return AsyncTask.this.getTaskId();
				}

				@Override
				public @NotNull Plugin getOwner()
				{
					return AsyncTask.this.getOwner();
				}
			});
		}

		Throwable thrown = null;
		try
		{
			super.run();
		}
		catch (Throwable t)
		{
			thrown = t;
		}
		finally
		{
			synchronized (this.workers)
			{
				try
				{
					Iterator<BukkitWorker> iter = this.workers.iterator();
					boolean removed = false;
					while (iter.hasNext())
					{
						if (iter.next().getThread() != thread)
							continue;
						iter.remove();
						removed = true;
						break;
					}

					if (!removed)
					{
						//noinspection ThrowFromFinallyBlock
						throw new IllegalStateException(String.format("Unable to remove worker %s on task %s for %s", thread.getName(), getTaskId(), getOwner().getDescription().getFullName()), thrown);
					}
				}
				finally
				{
					if (getPeriod() < 0 && this.workers.isEmpty())
					{ // Don't invert so above exception doesn't get lost.
						this.runners.remove(getTaskId());
					}
				}
			}
		}
	}

	protected List<BukkitWorker> getWorkers()
	{
		return this.workers;
	}

	@Override
	boolean cancel0()
	{
		synchronized (this.workers) // Prevent race condition for a completing task
		{
			setPeriod(Task.CANCELED);
			if (this.workers.isEmpty())
			{
				this.runners.remove(getTaskId());
			}
		}
		return true;
	}

}
