package org.mockbukkit.mockbukkit.util;

import io.papermc.paper.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class for {@link io.papermc.paper.registry.Registry} related operations.
 */
public final class RegistryUtils
{

	private RegistryUtils()
	{
	}

	/**
	 * Gets the plural name of a registry key.
	 *
	 * @param key The registry key.
	 * @return The plural name, or {@code null} if the key is null.
	 */
	public static @Nullable String getPlural(RegistryKey<?> key)
	{
		if (key == null)
		{
			return null;
		}
		String value = key.key().value();
		if (value.equals("entity_type"))
		{
			return "entity_types";
		}
		if (value.equals("damage_type"))
		{
			return "damage_types";
		}
		if (value.endsWith("y"))
		{
			return value.substring(0, value.length() - 1) + "ies";
		}
		return value + "s";
	}

}
