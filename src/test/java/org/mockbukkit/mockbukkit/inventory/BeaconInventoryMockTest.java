package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class BeaconInventoryMockTest
{

	private BeaconInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		this.inventory = new BeaconInventoryMock(null);
	}

	@Test
	void testSetItemDefaultNull()
	{
		assertNull(inventory.getItem());
	}

	@Test
	void testSetItem()
	{
		ItemStack item = new ItemStackMock(Material.EMERALD);
		inventory.setItem(item);
		assertNotNull(inventory.getItem());
		assertEquals(item, inventory.getItem());
	}

	@Test
	void testSetItem_SetsSlot()
	{
		ItemStack item = new ItemStackMock(Material.EMERALD);

		inventory.setItem(item);

		assertNotNull(inventory.getItem(0));
		assertEquals(item, inventory.getItem(0));
	}

	@Test
	void testSetItemNull()
	{
		assertDoesNotThrow(() -> inventory.setItem(null));
		assertNull(inventory.getItem());
	}

	@Test
	void testGetSnapshot()
	{
		assertNotNull(inventory.getSnapshot());

		inventory.setItem(new ItemStackMock(Material.EMERALD));
		assertNotEquals(inventory, inventory.getSnapshot());
		assertEquals(inventory.getItem(), inventory.getSnapshot().getItem());
	}

}
