package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.containsAtLeast;

@ExtendWith(MockBukkitExtension.class)
class DropperInventoryMockTest
{

	private DropperInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		this.inventory = new DropperInventoryMock((InventoryHolder) null);
	}

	@Test
	void testGetSnapshot()
	{
		assertNotNull(inventory.getSnapshot());

		ItemStack item = new ItemStackMock(Material.EMERALD);
		inventory.addItem(item);
		assertNotSame(inventory, inventory.getSnapshot());
		assertThat(inventory, containsAtLeast(item, 1));
	}

}
