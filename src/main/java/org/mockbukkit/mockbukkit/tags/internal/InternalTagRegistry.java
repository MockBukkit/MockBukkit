package org.mockbukkit.mockbukkit.tags.internal;

import org.mockbukkit.mockbukkit.tags.TagRegistry;

import java.util.ArrayList;
import java.util.List;

public enum InternalTagRegistry
{
	BLOCKS(TagRegistry.BLOCKS),
	ITEMS(TagRegistry.ITEMS),
	FLUIDS(TagRegistry.FLUIDS),
	GAME_EVENTS(TagRegistry.GAME_EVENTS),
	ENTITY_TYPES(TagRegistry.ENTITY_TYPES),
	DAMAGE_TYPES(TagRegistry.DAMAGE_TYPES);

	private final TagRegistry tagRegistry;
	private final ArrayList<InternalTag<?>> relatedTags;

	InternalTagRegistry(TagRegistry tagRegistry)
	{
		this.tagRegistry = tagRegistry;
		this.relatedTags = new ArrayList<>();
	}

	public List<InternalTag<?>> getRelatedTags()
	{
		return this.relatedTags;
	}

	public TagRegistry getTagRegistryEquivalent()
	{
		return this.tagRegistry;
	}
}
