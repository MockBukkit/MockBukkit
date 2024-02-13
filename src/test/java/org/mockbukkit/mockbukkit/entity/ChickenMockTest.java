package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.entity.ChickenMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChickenMockTest
{

	private ServerMock server;
	private ChickenMock chicken;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		chicken = new ChickenMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.CHICKEN, chicken.getType());
	}

	@Test
	void testIsBreedItemItemStack()
	{
		assertTrue(chicken.isBreedItem(new ItemStack(Material.WHEAT_SEEDS)));
	}

	@Test
	void testIsBreedItemMaterial()
	{
		assertTrue(chicken.isBreedItem(Material.WHEAT_SEEDS));
	}

	@Test
	void testIsBreedItemItemStackFalse()
	{
		assertFalse(chicken.isBreedItem(new ItemStack(Material.STONE)));
	}

	@Test
	void testIsBreedItemMaterialFalse()
	{
		assertFalse(chicken.isBreedItem(Material.STONE));
	}

	@Test
	void testIsBreedItemNull()
	{
		assertThrows(NullPointerException.class, () -> chicken.isBreedItem((ItemStack) null));
	}

	@Test
	void testIsBreedItemNullWithMaterial()
	{
		assertThrows(NullPointerException.class, () -> chicken.isBreedItem((Material) null));
	}

	@Test
	void testIsChickenJockeyDefault()
	{
		assertFalse(chicken.isChickenJockey());
	}

	@Test
	void testSetIsChickenJockey()
	{
		chicken.setIsChickenJockey(true);
		assertTrue(chicken.isChickenJockey());
	}

	@Test
	void testGetEggLayTimeDefault()
	{
		assertTrue(chicken.getEggLayTime() >= 6000);
	}

	@Test
	void testSetEggLayTime()
	{
		chicken.setEggLayTime(100);
		assertEquals(100, chicken.getEggLayTime());
	}
	
}
