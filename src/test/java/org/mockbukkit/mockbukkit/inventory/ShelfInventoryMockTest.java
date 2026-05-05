package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.Material;
import org.bukkit.block.Shelf;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ShelfInventoryMockTest
{

	private final ShelfInventoryMock shelfInventory = new ShelfInventoryMock((Shelf) null);

	@Test
	void getSize()
	{
		assertEquals(3, shelfInventory.getSize());
	}

	@Test
	void getSnapshot()
	{
		ShelfInventoryMock actual = shelfInventory.getSnapshot();
		assertTrue(actual.isIdentical(shelfInventory));
		assertTrue(shelfInventory.isIdentical(actual));

		shelfInventory.addItem(ItemStack.of(Material.DIAMOND));
		assertFalse(actual.isIdentical(shelfInventory));
		assertFalse(shelfInventory.isIdentical(actual));
	}

}
