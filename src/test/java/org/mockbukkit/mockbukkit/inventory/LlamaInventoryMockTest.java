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

class LlamaInventoryMockTest
{

	private ServerMock server;
	private WorldMock world;
	private LlamaInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		world = new WorldMock();
		world.setName("world");
		server.addWorld(world);

		inventory = new LlamaInventoryMock(null);
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
	void setDecor()
	{
		assertNull(inventory.getDecor());
		ItemStack item = new ItemStackMock(Material.IRON_HORSE_ARMOR);

		inventory.setDecor(item);

		assertEquals(item, inventory.getDecor());
	}

	@Test
	void setDecor_SetsItemInSlot()
	{
		ItemStack item = new ItemStackMock(Material.IRON_HORSE_ARMOR);

		inventory.setDecor(item);

		assertEquals(item, inventory.getItem(1));
	}

}
