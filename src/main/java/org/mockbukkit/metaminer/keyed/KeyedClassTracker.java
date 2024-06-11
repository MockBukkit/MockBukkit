package org.mockbukkit.metaminer.keyed;

import io.leangen.geantyref.GenericTypeReflector;
import io.papermc.paper.registry.RegistryKey;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyedClassTracker
{

	public static Map<RegistryKey<?>, Class<?>> CLASS_REGISTRY_KEY_RELATION = loadClassRegistryKeyRelation();

	private static Map<RegistryKey<?>, Class<?>> loadClassRegistryKeyRelation()
	{
		Map<RegistryKey<?>, Class<?>> output = new HashMap<>();
		for (final Field field : RegistryKey.class.getFields())
		{
			if (field.getType() == RegistryKey.class)
			{
				// get the legacy type from the RegistryKey generic parameter on the field
				final Class<?> legacyType = GenericTypeReflector.erase(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]);
				try
				{
					RegistryKey<?> registryKey = (RegistryKey<?>) field.get(null);
					output.put(registryKey, legacyType);
				}
				catch (IllegalAccessException e)
				{
					throw new RuntimeException(e);
				}
			}
		}
		return output;
	}

}
