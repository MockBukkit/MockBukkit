package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnchantingInventoryMockTest
{

	private EnchantingInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		this.inventory = new EnchantingInventoryMock(null);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void setItem()
	{
		inventory.setItem(new ItemStackMock(Material.DIAMOND_SWORD));

		assertNotNull(inventory.getItem());
		assertEquals(Material.DIAMOND_SWORD, inventory.getItem().getType());
	}

	@Test
	void setItem_SetsItemInSlot()
	{
		inventory.setItem(new ItemStackMock(Material.DIAMOND_SWORD));

		assertNotNull(inventory.getItem(0));
		assertEquals(Material.DIAMOND_SWORD, inventory.getItem(0).getType());
	}

	@Test
	void setSecondary()
	{
		inventory.setSecondary(new ItemStackMock(Material.LAPIS_LAZULI));

		assertNotNull(inventory.getSecondary());
		assertEquals(Material.LAPIS_LAZULI, inventory.getSecondary().getType());
	}

	@Test
	void setSecondary_SetsItemInSlot()
	{
		inventory.setSecondary(new ItemStackMock(Material.LAPIS_LAZULI));

		assertNotNull(inventory.getItem(1));
		assertEquals(Material.LAPIS_LAZULI, inventory.getItem(1).getType());
	}

}
