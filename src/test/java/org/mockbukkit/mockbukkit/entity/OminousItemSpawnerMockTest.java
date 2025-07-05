package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith(MockBukkitExtension.class)
class OminousItemSpawnerMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private OminousItemSpawnerMock ominousSpawner;

	@BeforeEach
	void setUp()
	{
		ominousSpawner = new OminousItemSpawnerMock(server, UUID.randomUUID());
	}

	@Nested
	class SetItem
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(ItemStack.empty(), ominousSpawner.getItem());
		}

		@Test
		void givenItemChange()
		{
			ItemStack item = ItemStack.of(Material.DIAMOND);
			ominousSpawner.setItem(item);

			assertEquals(item, ominousSpawner.getItem());
			assertNotSame(item, ominousSpawner.getItem());

			ominousSpawner.setItem(null);
			assertEquals(ItemStack.empty(), ominousSpawner.getItem());
		}

	}

	@Nested
	class SetSpawnItemAfterTicks
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(60, ominousSpawner.getSpawnItemAfterTicks());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 })
		void givenValueChange(int value)
		{
			ominousSpawner.setSpawnItemAfterTicks(value);
			assertEquals(value, ominousSpawner.getSpawnItemAfterTicks());
		}

	}

}
