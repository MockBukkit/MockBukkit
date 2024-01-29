package be.seeseemelk.mockbukkit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreadAccessExceptionTest
{

	@Test
	void testConstructor()
	{
		ThreadAccessException exception = new ThreadAccessException();

		assertEquals("be.seeseemelk.mockbukkit.ThreadAccessException", exception.getClass().getName());
	}

	@Test
	void testConstructorWithMessage()
	{
		ThreadAccessException exception = new ThreadAccessException("Hello, world!");

		assertEquals("Hello, world!", exception.getMessage());
	}
}
