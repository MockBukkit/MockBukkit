package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.block.state.LecternStateMock;
import org.bukkit.Material;
import org.bukkit.inventory.LecternInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class LecternInventoryMockTest
{

	private LecternInventoryMock inventory;
	private LecternStateMock block;

	@BeforeEach
	void setup()
	{
		new ServerMock();
		block = new LecternStateMock(Material.LECTERN);
		inventory = new LecternInventoryMock(block);
	}

	@Test
	void testGetSnapshot()
	{
		assertInstanceOf(LecternInventory.class, inventory.getSnapshot());
		assertNotSame(inventory, inventory.getSnapshot());
	}

	@Test
	void testGetHolder()
	{
		assertEquals(block, inventory.getHolder());
	}

}
