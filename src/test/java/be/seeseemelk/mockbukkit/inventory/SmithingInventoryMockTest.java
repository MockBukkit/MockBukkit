package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class SmithingInventoryMockTest
{

	SmithingInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		inventory = new SmithingInventoryMock(null);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetResultDefault()
	{
		assertNull(inventory.getResult());
	}

	@Test
	void testSetResult()
	{
		ItemStack item = new ItemStack(Material.OAK_BOAT);
		inventory.setResult(item);
		Assertions.assertEquals(item, inventory.getResult());
	}

}
