package org.mockbukkit.mockbukkit.tags;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Fluid;
import org.bukkit.GameEvent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

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
	BLOCKS(Tag.REGISTRY_BLOCKS, Material.class),

	/**
	 * The items registry
	 */
	ITEMS(Tag.REGISTRY_ITEMS, Material.class),

	/**
	 * The fluids;
	 */
	FLUIDS(Tag.REGISTRY_FLUIDS, Fluid.class),

	/**
	 * The game events;
	 */
	GAME_EVENTS(Tag.REGISTRY_GAME_EVENTS, GameEvent.class),

	/**
	 * The entity types;
	 */
	ENTITY_TYPES(Tag.REGISTRY_ENTITY_TYPES, EntityType.class);

	private final Map<NamespacedKey, Tag<?>> tags = new HashMap<>();
	private final String registryName;
	private final Class<?> tagType;

	TagRegistry(@NotNull String registryName, @NotNull Class<?> tagType)
	{
		this.registryName = registryName;
		this.tagType = tagType;
	}

	/**
	 * @return The name of the registry.
	 */
	@NotNull
	public final String getRegistry()
	{
		return this.registryName;
	}

	/**
	 * @return The tag tpe.
	 */
	@NotNull
	public Class<?> getTagType()
	{
		return tagType;
	}

	/**
	 * @return A map of all tags.
	 */
	@NotNull
	public final Map<NamespacedKey, Tag<?>> getTags()
	{
		return this.tags;
	}

	/**
	 * @return Whether the tags are empty.
	 */
	public boolean isEmpty()
	{
		return this.tags.isEmpty();
	}

}
