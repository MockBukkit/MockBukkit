package org.bukkit.command;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PluginCommandUtilsTest
{

	@Test
	void testConstructorIsPrivate() throws NoSuchMethodException
	{
		Constructor<PluginCommandUtils> constructor = PluginCommandUtils.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
		assertTrue(exception.getCause() instanceof UnsupportedOperationException);
		assertTrue(exception.getCause().getMessage().contains("This is a utility class and cannot be instantiated"));
	}

}
