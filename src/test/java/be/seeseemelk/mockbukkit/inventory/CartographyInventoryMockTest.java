package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.inventory.CartographyInventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
		assertNotEquals(inventory, snapshot);
	}

}
