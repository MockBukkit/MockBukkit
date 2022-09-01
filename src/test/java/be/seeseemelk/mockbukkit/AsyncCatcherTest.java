package be.seeseemelk.mockbukkit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	void catchOp_NotMainThread_ThrowsException() throws InterruptedException
	{
		AtomicBoolean exceptionThrown = new AtomicBoolean();

		Thread thread = new Thread(() ->
		{
			try
			{
				AsyncCatcher.catchOp("test");
			}
			catch (IllegalStateException e)
			{
				exceptionThrown.set(true);
			}
		});
		thread.start();
		thread.join();

		assertTrue(exceptionThrown.get(), "No exception thrown");
	}

}
