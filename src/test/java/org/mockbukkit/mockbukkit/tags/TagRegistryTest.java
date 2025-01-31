package org.mockbukkit.mockbukkit.tags;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.bukkit.Fluid;
import org.bukkit.GameEvent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

@ExtendWith(MockBukkitExtension.class)
class TagRegistryTest
{

	@Nested
	class Blocks
	{

		@Test
		void givenEntityTypes()
		{
			@NotNull Map<NamespacedKey, Tag<?>> actual = TagRegistry.BLOCKS.getTags();
			assertNotNull(actual);
			assertEquals(182, actual.size());
		}

		@Test
		void getRegistryIsCorrect()
		{
			assertEquals("blocks", TagRegistry.BLOCKS.getRegistry());
		}

		@Test
		void getTypeIsCorrect()
		{
			assertEquals(Material.class, TagRegistry.BLOCKS.getTagType());
		}

	}

	@Nested
	class EntityTypes
	{

		@Test
		void givenEntityTypes()
		{
			@NotNull Map<NamespacedKey, Tag<?>> actual = TagRegistry.ENTITY_TYPES.getTags();
			assertNotNull(actual);
			assertEquals(35, actual.size());
		}

		@Test
		void getRegistryIsCorrect()
		{
			assertEquals("entity_types", TagRegistry.ENTITY_TYPES.getRegistry());
		}

		@Test
		void getTypeIsCorrect()
		{
			assertEquals(EntityType.class, TagRegistry.ENTITY_TYPES.getTagType());
		}

	}

	@Nested
	class Items
	{

		@Test
		void givenItems()
		{
			@NotNull Map<NamespacedKey, Tag<?>> actual = TagRegistry.ITEMS.getTags();
			assertNotNull(actual);
			assertEquals(154, actual.size());
		}

		@Test
		void getRegistryIsCorrect()
		{
			assertEquals("items", TagRegistry.ITEMS.getRegistry());
		}

		@Test
		void getTypeIsCorrect()
		{
			assertEquals(Material.class, TagRegistry.ITEMS.getTagType());
		}

	}

	@Nested
	class Fluids
	{

		@Test
		void givenFluids()
		{
			@NotNull Map<NamespacedKey, Tag<?>> actual = TagRegistry.FLUIDS.getTags();
			assertNotNull(actual);
			assertEquals(2, actual.size());
		}

		@Test
		void getRegistryIsCorrect()
		{
			assertEquals("fluids", TagRegistry.FLUIDS.getRegistry());
		}

		@Test
		void getTypeIsCorrect()
		{
			assertEquals(Fluid.class, TagRegistry.FLUIDS.getTagType());
		}

	}

	@Nested
	class GameEvents
	{

		@Test
		void givenGameEvents()
		{
			@NotNull Map<NamespacedKey, Tag<?>> actual = TagRegistry.GAME_EVENTS.getTags();
			assertNotNull(actual);
			assertEquals(5, actual.size());
		}

		@Test
		void getRegistryIsCorrect()
		{
			assertEquals("game_events", TagRegistry.GAME_EVENTS.getRegistry());
		}

		@Test
		void getTypeIsCorrect()
		{
			assertEquals(GameEvent.class, TagRegistry.GAME_EVENTS.getTagType());
		}

	}

}
