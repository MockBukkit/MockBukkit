package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class EnderPearlMockTest
{

	@MockBukkitInject
	private EnderPearl pearl;

	@Test
	void getItem_DefaultIsEnderPearl()
	{
		assertEquals(Material.ENDER_PEARL, pearl.getItem().getType());
	}

	@Test
	void testSetItem()
	{
		ItemStack item = new ItemStackMock(Material.APPLE);
		pearl.setItem(item);
		assertEquals(Material.APPLE, pearl.getItem().getType());
		assertEquals(1, pearl.getItem().getAmount());
		assertNotSame(item, pearl.getItem());
		assertEquals(item, pearl.getItem());
	}

	@Test
	void testSetItem_AlwaysOne()
	{
		ItemStack item = new ItemStackMock(Material.APPLE, 5);
		pearl.setItem(item);
		assertEquals(1, pearl.getItem().getAmount());
	}

	@Test
	void testSetItem_Null()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () ->
				pearl.setItem(null));

		assertEquals("Item cannot be null", nullPointerException.getMessage());
	}

	@Test
	void testGetItem_ReturnsCopy()
	{
		ItemStack item = new ItemStackMock(Material.APPLE);
		pearl.setItem(item);
		assertNotSame(item, pearl.getItem());
		assertEquals(item, pearl.getItem());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.ENDER_PEARL, pearl.getType());
	}

}
