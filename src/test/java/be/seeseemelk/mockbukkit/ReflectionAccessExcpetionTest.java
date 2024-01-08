package be.seeseemelk.mockbukkit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReflectionAccessExcpetionTest
{

	@Test
	void testConstructorWithMessage()
	{
		ReflectionAccessException exception = new ReflectionAccessException("Hello, world!");
		assertEquals("Hello, world!", exception.getMessage());
		assertEquals("be.seeseemelk.mockbukkit.ReflectionAccessException", exception.getClass().getName());
	}
}
