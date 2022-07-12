package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
		assertTrue(animal.isBreedItem(new ItemStack(Material.WHEAT)));
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
		assertFalse(animal.isBreedItem(new ItemStack(Material.DIRT)));
	}

	@Test
	void testIsBreedingItemWithWrongMaterial()
	{
		assertFalse(animal.isBreedItem(Material.DIRT));
	}

}
