package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HorseInventoryMockTest
{

	private ServerMock server;
	private WorldMock world;
	private HorseInventoryMock inventory;

	@BeforeEach
	void setUp() throws Exception
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
	void setSaddle()
	{
		assertEquals(null, inventory.getSaddle());
		ItemStack item = new ItemStack(Material.SADDLE);
		item.setAmount(1);
		inventory.setSaddle(item);
		assertEquals(item, inventory.getSaddle());
	}

	@Test
	void setArmor()
	{
		assertEquals(null, inventory.getArmor());
		ItemStack item = new ItemStack(Material.IRON_HORSE_ARMOR);
		item.setAmount(1);
		inventory.setArmor(item);
		assertEquals(item, inventory.getArmor());
	}
}
