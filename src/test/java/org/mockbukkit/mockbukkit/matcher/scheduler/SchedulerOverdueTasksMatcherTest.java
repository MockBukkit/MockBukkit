package org.mockbukkit.mockbukkit.matcher.scheduler;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.scheduler.BukkitSchedulerMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import java.util.concurrent.CountDownLatch;

import static org.mockbukkit.mockbukkit.matcher.scheduler.SchedulerOverdueTasksMatcher.hasNoOverdueTasks;

@ExtendWith(MockBukkitExtension.class)
class SchedulerOverdueTasksMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private BukkitSchedulerMock scheduler;

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
