package org.mockbukkit.mockbukkit.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReflectionAccessExcpetionTest
{

	@Test
	void testConstructorWithMessage()
	{
		ReflectionAccessException exception = new ReflectionAccessException("Hello, world!");
		assertEquals("Hello, world!", exception.getMessage());
		assertEquals("org.mockbukkit.mockbukkit.exception.ReflectionAccessException", exception.getClass().getName());
	}
}
