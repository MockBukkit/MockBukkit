package be.seeseemelk.mockbukkit.scheduler;

import org.bukkit.plugin.Plugin;

public class RepeatingTask extends ScheduledTask
{
	private long period;

	public RepeatingTask(int id, Plugin plugin, boolean isSync, long scheduledTick, long period, Runnable runnable)
	{
		super(id, plugin, isSync, scheduledTick, runnable);
		this.period = period;
	}

	/**
	 * Gets the period of the timer.
	 * @return The period of the timer.
	 */
	public long getPeriod()
	{
		return period;
	}

	/**
	 * Updates the scheduled tick for the next run.
	 */
	public void updateScheduledTick()
	{
		setScheduledTick(getScheduledTick() + period);
	}

	@Override
	public void run()
	{
		super.run();
	}
}
