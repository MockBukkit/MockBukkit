package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class CrafterInventoryMockTest
{

	private CrafterInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		this.inventory = new CrafterInventoryMock((InventoryHolder) null);
	}

	@Test
	void getType() {
		assertEquals(InventoryType.CRAFTER, inventory.getType());
	}

	@Test
	void getSnapshot() {
		assertTrue(inventory.isIdentical(inventory.getSnapshot()));
	}

}
