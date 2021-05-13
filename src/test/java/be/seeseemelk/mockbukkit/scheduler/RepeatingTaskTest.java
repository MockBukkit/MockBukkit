package be.seeseemelk.mockbukkit.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RepeatingTaskTest
{

	@Test
	public void getScheduledTick_Start_IsEqualToDelay()
	{
		RepeatingTask task = new RepeatingTask(0, null, true, 10, 20, null);
		assertEquals(10, task.getScheduledTick());
	}

	@Test
	public void getScheduledTick_AfterUpdateScheduledTick_Changed()
	{
		RepeatingTask task = new RepeatingTask(0, null, true, 10, 20, () -> {});
		task.updateScheduledTick();
		assertEquals(30, task.getScheduledTick());
	}

	@Test
	public void getPeriod_SomePeriod_ExactPeriod()
	{
		RepeatingTask task = new RepeatingTask(0, null, true, 10, 20, null);
		assertEquals(20, task.getPeriod());
	}

}
