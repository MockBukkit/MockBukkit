package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith(MockBukkitExtension.class)
class LoomInventoryMockTest
{

	private LoomInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		inventory = new LoomInventoryMock(null);
	}

	@Test
	void testGetSnapshot()
	{
		InventoryMock snapshot = inventory.getSnapshot();

		assertInstanceOf(LoomInventoryMock.class, snapshot);
		assertNotSame(inventory, snapshot);
	}

}
