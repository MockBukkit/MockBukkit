package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.inventory.InventoryHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class CartographyInventoryMockTest
{

	private CartographyInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		inventory = new CartographyInventoryMock((InventoryHolder) null);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetSnapshot()
	{
		InventoryMock snapshot = inventory.getSnapshot();

		assertInstanceOf(CartographyInventoryMock.class, snapshot);
		assertNotSame(inventory, snapshot);
	}

}
