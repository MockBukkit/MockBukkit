package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class DecoratedPotInventoryMockTest
{

	private DecoratedPotInventoryMock potInventory;

	@BeforeEach
	void setUp()
	{
		this.potInventory = new DecoratedPotInventoryMock((InventoryHolder) null);
	}

	@Test
	void setAndGetItem()
	{
		ItemStack item = new ItemStack(Material.STRING);
		potInventory.setItem(item);
		assertEquals(item, potInventory.getItem());
		assertEquals(item, potInventory.getItem(0));
		assertEquals(1, potInventory.getSize());
	}

	@Test
	void setAndGetItem_null()
	{
		potInventory.setItem(null);
		assertNull(potInventory.getItem());
		assertNull(potInventory.getItem(0));
		assertEquals(1, potInventory.getSize());
	}

}
