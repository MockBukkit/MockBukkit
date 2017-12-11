package be.seeseemelk.mockbukkit.inventory;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;

public class PlayerInventoryMockTest
{
	private PlayerInventoryMock inventory;

	@Before
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		inventory = new PlayerInventoryMock("Inventory");
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unload();
	}

	@Test
	public void getName_Default_CorrectName()
	{
		assertEquals("Inventory", inventory.getName());
	}

	@Test
	public void getSize_Default_40()
	{
		assertEquals(40, inventory.getSize());
	}

	@Test
	public void getItem_Default_AllNull()
	{
		for (int i = 0; i < inventory.getSize(); i++)
		{
			assertNull(inventory.getItem(i));
		}
	}

	@Test
	public void addItem_EmptyInventoryAddsOneStack_OneStackUsed()
	{
		ItemStack stack = new ItemStack(Material.DIRT, 64);
		ItemStack remaining = inventory.addItem(stack);
		assertNull(remaining);
		ItemStack stored = inventory.getItem(0);
		assertEquals(stored.getAmount(), 64);
		ItemStack next = inventory.getItem(1);
		assertNull(next);
	}

	@Test
	public void addItem_FullInventoryAddsOneStack_NothingAdded()
	{
		ItemStack filler = new ItemStack(Material.COBBLESTONE, 1);
		for (int i = 0; i < inventory.getSize(); i++)
		{
			inventory.setItem(i, filler);
		}

		ItemStack stack = new ItemStack(Material.DIRT, 64);
		ItemStack remaining = inventory.addItem(stack);
		assertEquals(64, remaining.getAmount());

		for (ItemStack stored : inventory.getContents())
		{
			assertEquals(1, stored.getAmount());
			assertEquals(Material.COBBLESTONE, stored.getType());
		}
	}

	@Test
	public void addItem_PartiallyFilled_AddsOneStack_HalfAdded()
	{
		ItemStack filler = new ItemStack(Material.COBBLESTONE, 1);
		for (int i = 2; i < inventory.getSize(); i++)
		{
			inventory.setItem(i, filler);
		}
		ItemStack preset = new ItemStack(Material.DIRT, 48);
		inventory.setItem(0, preset);
		inventory.setItem(2, preset);
		preset.setAmount(64);
		inventory.setItem(1, preset);

		ItemStack store = new ItemStack(Material.DIRT, 64);
		ItemStack remaining = inventory.addItem(store);
		assertNotNull(remaining);
		assertEquals(32, remaining.getAmount());
	}

	@Test
	public void addItem_MultipleItems_ItemsAddedCorrectly()
	{
		ItemStack filler = new ItemStack(Material.COBBLESTONE, 1);
		for (int i = 1; i < inventory.getSize(); i++)
		{
			inventory.setItem(i, filler);
		}

		ItemStack preset = new ItemStack(Material.DIRT, 48);
		inventory.setItem(0, preset);

		ItemStack item1 = new ItemStack(Material.COBBLESTONE, 64);
		ItemStack item2 = new ItemStack(Material.DIRT, 64);
		HashMap<Integer, ItemStack> map = inventory.addItem(item1, item2);
		assertEquals(1, map.size());
		assertTrue(map.containsKey(1));
		assertEquals(48, map.get(1).getAmount());
	}

	@Test
	public void setContents_OneItem_SetAndRestCleared()
	{
		ItemStack filler = new ItemStack(Material.COBBLESTONE, 1);
		for (int i = 1; i < inventory.getSize(); i++)
		{
			inventory.setItem(i, filler);
		}

		ItemStack item = new ItemStack(Material.DIRT, 32);

		inventory.setContents(new ItemStack[] { item });
		
		assertTrue(item.isSimilar(inventory.getItem(0)));
		for (int i = 1; i < inventory.getSize(); i++)
		{
			assertNull(inventory.getItem(i));
		}
	}
}
