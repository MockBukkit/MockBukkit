package be.seeseemelk.mockbukkit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadAccessExceptionTest
{

	@Test
	public void testConstructor()
	{
		ThreadAccessException exception = new ThreadAccessException();

		assertEquals("be.seeseemelk.mockbukkit.ThreadAccessException", exception.getClass().getName());
	}

	@Test
	public void testConstructorWithMessage()
	{
		ThreadAccessException exception = new ThreadAccessException("Hello, world!");

		assertEquals("Hello, world!", exception.getMessage());
	}
}
