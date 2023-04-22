package org.plugin.java;

import org.bukkit.command.PluginCommandUtils;
import org.bukkit.plugin.java.JavaPluginUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaPluginUtilsTest
{
	@Test
	void testConstructorIsPrivate() throws NoSuchMethodException
	{
		Constructor<JavaPluginUtils> constructor = JavaPluginUtils.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
		assertTrue(exception.getCause() instanceof UnsupportedOperationException);
		assertTrue(exception.getCause().getMessage().contains("This is a utility class and cannot be instantiated"));
	}
}
