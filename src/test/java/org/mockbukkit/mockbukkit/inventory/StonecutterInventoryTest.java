package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.inventory.InventoryHolder;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith(MockBukkitExtension.class)
class StonecutterInventoryTest
{

	private StonecutterInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		inventory = new StonecutterInventoryMock((InventoryHolder) null);
	}

	@Test
	void testGetSnapshot()
	{
		InventoryMock snapshot = inventory.getSnapshot();

		assertInstanceOf(StonecutterInventoryMock.class, snapshot);
		assertNotSame(inventory, snapshot);
	}

}
