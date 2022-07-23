package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class LoomInventoryMockTest
{

	private LoomInventoryMock inventory;

	@BeforeEach
	void setUp() throws Exception
	{
		MockBukkit.mock();
		inventory = new LoomInventoryMock(null);
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

		assertInstanceOf(LoomInventoryMock.class, snapshot);
		assertNotSame(inventory, snapshot);
	}

}
