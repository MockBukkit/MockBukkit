package org.mockbukkit.mockbukkit.matcher.scheduler;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.scheduler.BukkitSchedulerMock;

public class SchedulerOverdueTasksMatcher extends TypeSafeMatcher<BukkitSchedulerMock>
{

	@Override
	protected boolean matchesSafely(BukkitSchedulerMock item)
	{
		return item.getOverdueTasks().isEmpty();
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have no overdue tasks");
	}

	@Override
	protected void describeMismatchSafely(BukkitSchedulerMock schedulerMock, Description description)
	{
		description.appendText("was with the following overdue tasks ").appendValueList("[", ",", "]", schedulerMock.getOverdueTasks());
	}

	public static SchedulerOverdueTasksMatcher hasNoOverdueTasks()
	{
		return new SchedulerOverdueTasksMatcher();
	}

}
