package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.inventory.InventoryHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith(MockBukkitExtension.class)
class GrindstoneInventoryMockTest
{

	private GrindstoneInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		inventory = new GrindstoneInventoryMock((InventoryHolder) null);
	}

	@Test
	void testGetSnapshot()
	{
		InventoryMock snapshot = inventory.getSnapshot();

		assertInstanceOf(GrindstoneInventoryMock.class, snapshot);
		assertNotSame(inventory, snapshot);
	}

}
