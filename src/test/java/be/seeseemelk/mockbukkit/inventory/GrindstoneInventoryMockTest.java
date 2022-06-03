package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GrindstoneInventoryMockTest
{

	private GrindstoneInventoryMock inventory;

	@BeforeEach
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		inventory = new GrindstoneInventoryMock(null);
	}

	@AfterEach
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetSnapshot()
	{
		InventoryMock snapshot = inventory.getSnapshot();

		assertInstanceOf(GrindstoneInventoryMock.class, snapshot);
		assertNotEquals(inventory, snapshot);
	}

}
