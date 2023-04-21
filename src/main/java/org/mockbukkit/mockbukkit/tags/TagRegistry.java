package org.mockbukkit.mockbukkit.tags;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * An enum for all the different {@link Material} {@link Tag} registries.
 *
 * @author TheBusyBiscuit
 */
public enum TagRegistry
{

	/**
	 * The blocks registry
	 */
	BLOCKS,

	/**
	 * The items registry
	 */
	ITEMS;

	private final Map<NamespacedKey, TagWrapperMock> tags = new HashMap<>();

	/**
	 * @return The name of the registry.
	 */
	@NotNull
	public final String getRegistry()
	{
		return name().toLowerCase(Locale.ROOT);
	}

	/**
	 * @return A map of all tags.
	 */
	@NotNull
	public final Map<NamespacedKey, TagWrapperMock> getTags()
	{
		return tags;
	}

	/**
	 * @return Whether the tags are empty.
	 */
	public boolean isEmpty()
	{
		return tags.isEmpty();
	}

}
