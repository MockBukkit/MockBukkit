package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class EggMockTest
{

	@MockBukkitInject
	private EggMock egg;

	@Test
	void testGetType()
	{
		assertEquals(EntityType.EGG, egg.getType());
	}

	@Test
	void testGetItemDefault()
	{
		assertEquals(new ItemStackMock(Material.EGG), egg.getItem());
	}

	@Test
	void testSetItem()
	{
		ItemStack item = new ItemStackMock(Material.DIAMOND);
		egg.setItem(item);
		assertEquals(Material.DIAMOND, egg.getItem().getType());
	}

	@Test
	void testSetItemNull()
	{
		assertThrows(NullPointerException.class, () -> egg.setItem(null));
	}

}
