package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BeaconInventoryMockTest
{

	private ServerMock server;
	private BeaconInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		this.server = MockBukkit.mock();
		this.inventory = new BeaconInventoryMock(null);
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testSetItemDefaultNull()
	{
		assertNull(inventory.getItem());
	}

	@Test
	void testSetItem()
	{
		ItemStack item = new ItemStack(Material.EMERALD);
		inventory.setItem(item);
		assertNotNull(inventory.getItem());
		assertEquals(item, inventory.getItem());
	}

	@Test
	void testSetItemNull()
	{
		assertDoesNotThrow(() -> inventory.setItem(null));
		assertNull(inventory.getItem());
	}

	@Test
	void testGetSnapshot()
	{
		assertNotNull(inventory.getSnapshot());

		inventory.setItem(new ItemStack(Material.EMERALD));
		assertNotEquals(inventory, inventory.getSnapshot());
		assertEquals(inventory.getItem(), inventory.getSnapshot().getItem());
	}

}
