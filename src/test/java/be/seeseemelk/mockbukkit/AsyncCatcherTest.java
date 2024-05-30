package be.seeseemelk.mockbukkit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
	void catchOp_NotMainThread_ThrowsException()
	{
		AtomicReference<Exception> exceptionThrown = new AtomicReference<>();

		server.getScheduler().runTaskAsynchronously(null, () -> {
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

	@Test
	void testConstructorIsPrivate() throws NoSuchMethodException
	{
		Constructor<AsyncCatcher> constructor = AsyncCatcher.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
		assertTrue(exception.getCause() instanceof UnsupportedOperationException);
		assertTrue(exception.getCause().getMessage().contains("Utility class"));
	}
}
