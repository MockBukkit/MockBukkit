package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class CartographyInventoryMockTest
{

	private CartographyInventoryMock inventory;

	@BeforeEach
	void setUp() throws Exception
	{
		MockBukkit.mock();
		inventory = new CartographyInventoryMock(null);
	}

	@AfterEach
	void tearDown() throws Exception
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
