package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnimalsMockTest
{

	private ServerMock server;
	private AnimalsMock animal;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		animal = new AnimalsMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testIsBreedingItemDefault()
	{
		assertTrue(animal.isBreedItem(new ItemStackMock(Material.WHEAT)));
	}

	@Test
	void assertIsBreedingItemThrowsWithNull()
	{
		assertThrows(NullPointerException.class, () -> animal.isBreedItem((ItemStack) null));
	}

	@Test
	void testIsBreedingItemWithMaterial()
	{
		assertTrue(animal.isBreedItem(Material.WHEAT));
	}

	@Test
	void testIsBreedingItemWithNullMaterialThrows()
	{
		assertThrows(NullPointerException.class, () -> animal.isBreedItem((Material) null));
	}

	@Test
	void testIsBreedingItemWithWrongItem()
	{
		assertFalse(animal.isBreedItem(new ItemStackMock(Material.DIRT)));
	}

	@Test
	void testIsBreedingItemWithWrongMaterial()
	{
		assertFalse(animal.isBreedItem(Material.DIRT));
	}

	@Test
	void testGetBreedCauseDefault()
	{
		assertNull(animal.getBreedCause());
	}

	@Test
	void testSetBreedCause()
	{
		UUID uuid = UUID.randomUUID();
		animal.setBreedCause(uuid);
		assertEquals(uuid, animal.getBreedCause());
	}

	@Test
	void testNullBreedCauseDoesNotThrow()
	{
		assertDoesNotThrow(() -> animal.setBreedCause(null));
	}

	@Test
	void testGetLoveModeTicks()
	{
		assertEquals(0, animal.getLoveModeTicks());
	}

	@Test
	void testIsLoveMode()
	{
		animal.setLoveModeTicks(1);
		assertTrue(animal.isLoveMode());
	}

	@Test
	void testIsLoveModeDefault()
	{
		assertFalse(animal.isLoveMode());
	}

	@Test
	void testSetLoveModeTicks()
	{
		int ticks = 10;
		animal.setLoveModeTicks(ticks);
		assertEquals(ticks, animal.getLoveModeTicks());
	}

	@Test
	void testSetLoveModeTicksNegative()
	{
		assertThrows(IllegalArgumentException.class, () -> animal.setLoveModeTicks(-1));
	}

	@Test
	void testGetSpawnCategory()
	{
		assertEquals(SpawnCategory.ANIMAL, animal.getSpawnCategory());
	}

	@Test
	void testToString()
	{
		assertEquals("AnimalsMock", animal.toString());
	}

}
