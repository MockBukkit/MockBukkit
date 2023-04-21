package org.mockbukkit.mockbukkit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.AsyncCatcher;
import org.mockbukkit.mockbukkit.MockBukkit;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AsyncCatcherTest
{

	private ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void catchOp_MainThread_Succeeds()
	{
		assertDoesNotThrow(() -> AsyncCatcher.catchOp("test"));
	}

	@Test
	void catchOp_NotMainThread_ThrowsException()
	{
		AtomicReference<Exception> exceptionThrown = new AtomicReference<>();

		server.getScheduler().runTaskAsynchronously(null, () ->
		{
			try
			{
				AsyncCatcher.catchOp("test");
			}
			catch (IllegalStateException e)
			{
				exceptionThrown.set(e);
			}
		});

		server.getScheduler().waitAsyncTasksFinished();

		assertNotNull(exceptionThrown.get());
	}

}
