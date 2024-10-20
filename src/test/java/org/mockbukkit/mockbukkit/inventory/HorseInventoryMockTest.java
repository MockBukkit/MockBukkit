package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HorseInventoryMockTest
{

	private ServerMock server;
	private WorldMock world;
	private HorseInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		world = new WorldMock();
		world.setName("world");
		server.addWorld(world);

		inventory = new HorseInventoryMock(null);
	}

	@AfterEach
	void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void getSize_Default_2()
	{
		assertEquals(2, inventory.getSize());
	}

	@Test
	void setArmor()
	{
		assertNull(inventory.getArmor());
		ItemStack item = new ItemStackMock(Material.IRON_HORSE_ARMOR);

		inventory.setArmor(item);

		assertEquals(item, inventory.getArmor());
	}

	@Test
	void setArmor_SetsItemInSlot()
	{
		ItemStack item = new ItemStackMock(Material.IRON_HORSE_ARMOR);

		inventory.setArmor(item);

		assertEquals(item, inventory.getItem(1));
	}

}
