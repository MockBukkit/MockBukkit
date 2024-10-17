package org.mockbukkit.mockbukkit.matcher.scheduler;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.scheduler.BukkitSchedulerMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import java.util.concurrent.CountDownLatch;

import static org.mockbukkit.mockbukkit.matcher.scheduler.SchedulerOverdueTasksMatcher.hasNoOverdueTasks;

class SchedulerOverdueTasksMatcherTest extends AbstractMatcherTest
{

	private BukkitSchedulerMock scheduler;

	@BeforeEach
	void setUp()
	{
		this.scheduler = new BukkitSchedulerMock();
	}

	@Test
	void hasNoOverdueTasks_noTasks()
	{
		scheduler.saveOverdueTasks();
		assertMatches(hasNoOverdueTasks(), scheduler);
	}

	@Test
	void hasNoOverdueTasks_tasks() throws InterruptedException
	{
		CountDownLatch tasksSaved = new CountDownLatch(1);
		CountDownLatch taskStarted = new CountDownLatch(1);
		scheduler.runTaskAsynchronously(null, () ->
		{
			try
			{
				taskStarted.countDown();
				tasksSaved.await();
			}
			catch (InterruptedException ignored)
			{
				// Code will end after reaching this point, therefore no-op
			}
		});
		taskStarted.await();
		scheduler.saveOverdueTasks();
		tasksSaved.countDown();
		assertDoesNotMatch(hasNoOverdueTasks(), scheduler);
	}

	@Test
	void nullSafe()
	{
		testIsNullSafe();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasNoOverdueTasks();
	}

}
