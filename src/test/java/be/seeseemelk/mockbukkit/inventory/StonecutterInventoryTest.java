package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class StonecutterInventoryTest
{

	private StonecutterInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		inventory = new StonecutterInventoryMock(null);
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

		assertInstanceOf(StonecutterInventoryMock.class, snapshot);
		assertNotSame(inventory, snapshot);
	}

}
