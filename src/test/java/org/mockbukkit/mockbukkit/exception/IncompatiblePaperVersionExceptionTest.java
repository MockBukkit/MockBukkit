package org.mockbukkit.mockbukkit.exception;

import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.BuildParameters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IncompatiblePaperVersionExceptionTest
{

	@Test
	void testExceptionMessageAndCause()
	{
		Throwable cause = new RuntimeException("Simulated failure");

		IncompatiblePaperVersionException exception = new IncompatiblePaperVersionException(cause);

		// Verify cause
		assertEquals(cause, exception.getCause());

		// Verify message contains expected version info
		String message = exception.getMessage();
		assertNotNull(message);
		assertTrue(message.contains(BuildParameters.MOCK_BUKKIT_VERSION));
		assertTrue(message.contains(BuildParameters.BUILD_NUMBER_STRING));
		assertTrue(message.contains(BuildParameters.PAPER_API_FULL_VERSION));
	}

}
