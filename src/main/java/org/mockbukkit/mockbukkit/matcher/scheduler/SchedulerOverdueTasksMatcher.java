package org.mockbukkit.mockbukkit.matcher.scheduler;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.scheduler.BukkitSchedulerMock;

import static org.hamcrest.Matchers.not;

public class SchedulerOverdueTasksMatcher extends TypeSafeMatcher<BukkitSchedulerMock>
{

	@Override
	protected boolean matchesSafely(BukkitSchedulerMock schedulerMock)
	{
		return schedulerMock.getOverdueTasks().isEmpty();
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

	/**
	 * @return A matcher which matches with any scheduler which has no overdue tasks
	 */
	public static @NotNull SchedulerOverdueTasksMatcher hasNoOverdueTasks()
	{
		return new SchedulerOverdueTasksMatcher();
	}

	public static Matcher<BukkitSchedulerMock> hasOverdueTasks()
	{
		return not(hasNoOverdueTasks());
	}

}
