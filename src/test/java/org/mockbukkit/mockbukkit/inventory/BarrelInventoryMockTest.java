package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class BarrelInventoryMockTest
{

	private BarrelInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		this.inventory = new BarrelInventoryMock((InventoryHolder) null);
	}


	@Test
	void testGetSnapshot()
	{
		assertNotNull(inventory.getSnapshot());

		ItemStack item = new ItemStackMock(Material.EMERALD);
		inventory.addItem(item);
		assertNotSame(inventory, inventory.getSnapshot());
		assertTrue(Arrays.stream(inventory.getContents()).anyMatch(stack -> stack != null && stack.isSimilar(item)));
	}

}
