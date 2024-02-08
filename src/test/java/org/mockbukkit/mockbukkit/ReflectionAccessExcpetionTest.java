package org.mockbukkit.mockbukkit;

import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.excpetion.ReflectionAccessException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReflectionAccessExcpetionTest
{

	@Test
	void testConstructorWithMessage()
	{
		ReflectionAccessException exception = new ReflectionAccessException("Hello, world!");
		assertEquals("Hello, world!", exception.getMessage());
		assertEquals("org.mockbukkit.mockbukkit.excpetion.ReflectionAccessException", exception.getClass().getName());
	}
}
