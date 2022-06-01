package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToObject;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;

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

		assertThat(snapshot, instanceOf(GrindstoneInventoryMock.class));
		assertThat(snapshot, not(equalToObject(inventory)));
	}

}
