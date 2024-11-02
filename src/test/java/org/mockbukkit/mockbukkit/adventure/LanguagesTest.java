package org.mockbukkit.mockbukkit.adventure;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class LanguagesTest
{

	@Test
	void testConstructorIsPrivate() throws NoSuchMethodException
	{
		Constructor<Languages> constructor = Languages.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);

		Throwable cause = exception.getCause();
		assertInstanceOf(UnsupportedOperationException.class, cause);
		assertTrue(cause.getMessage().equalsIgnoreCase("Utility class"));
	}

}
