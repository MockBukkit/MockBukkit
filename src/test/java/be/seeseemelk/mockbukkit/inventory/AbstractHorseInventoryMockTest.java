package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AbstractHorseInventoryMockTest
{

	private AbstractHorseInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		this.inventory = new AbstractHorseInventoryMock(null);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void setSaddle_SetsSaddle()
	{
		inventory.setSaddle(new ItemStackMock(Material.SADDLE));

		assertNotNull(inventory.getSaddle());
		assertEquals(Material.SADDLE, inventory.getSaddle().getType());
	}

	@Test
	void setSaddle_SetsItemInSlot()
	{
		inventory.setSaddle(new ItemStackMock(Material.SADDLE));

		assertNotNull(inventory.getItem(0));
		assertEquals(Material.SADDLE, inventory.getItem(0).getType());
	}

}
