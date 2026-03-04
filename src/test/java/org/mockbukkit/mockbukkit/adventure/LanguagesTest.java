package org.mockbukkit.mockbukkit.adventure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
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
