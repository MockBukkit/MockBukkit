package org.mockbukkit.mockbukkit.tags;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
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

	}

}
