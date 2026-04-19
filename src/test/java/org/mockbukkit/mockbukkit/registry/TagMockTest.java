package org.mockbukkit.mockbukkit.registry;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.tag.TagKey;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
@SuppressWarnings("UnstableApiUsage")
class TagMockTest
{

	private TagMock<ItemType> tag;
	private TagKey<ItemType> tagKey;

	@BeforeEach
	void setUp()
	{
		tagKey = TagKey.create(RegistryKey.ITEM, NamespacedKey.minecraft("test_tag"));
		tag = new TagMock<>(tagKey, List.of(ItemType.STONE, ItemType.DIRT));
	}

	@Test
	void tagKey()
	{
		assertEquals(tagKey, tag.tagKey());
	}

	@Test
	void getKey()
	{
		assertEquals(NamespacedKey.minecraft("test_tag"), tag.getKey());
	}

	@Test
	void isTagged()
	{
		assertTrue(tag.isTagged(ItemType.STONE));
		assertTrue(tag.isTagged(ItemType.DIRT));
		assertFalse(tag.isTagged(ItemType.GRASS_BLOCK));
	}

	@Test
	void getValues()
	{
		Set<ItemType> values = tag.getValues();
		assertEquals(2, values.size());
		assertTrue(values.contains(ItemType.STONE));
		assertTrue(values.contains(ItemType.DIRT));
	}

	@Test
	void registryKey()
	{
		assertEquals(RegistryKey.ITEM, tag.registryKey());
	}

	@Test
	void contains()
	{
		assertTrue(tag.contains(TypedKey.create(RegistryKey.ITEM, NamespacedKey.minecraft("stone"))));
		assertFalse(tag.contains(TypedKey.create(RegistryKey.ITEM, NamespacedKey.minecraft("grass_block"))));
	}

	@Test
	void size()
	{
		assertEquals(2, tag.size());
	}

}
