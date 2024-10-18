package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class AbstractHorseInventoryMockTest
{

	private AbstractHorseInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		this.inventory = new AbstractHorseInventoryMock(null);
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
