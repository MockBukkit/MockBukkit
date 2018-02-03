package be.seeseemelk.mockbukkit.inventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;

public class InventoryMockTest
{
	private InventoryMock inventory;

	@Before
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		inventory = new InventoryMock(null, "Inventory", 9);
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unload();
	}

	@Test
	public void constructor_SetsSize()
	{
		assertEquals(9, new InventoryMock(null, "", 9).getSize());
		assertEquals(18, new InventoryMock(null, "", 18).getSize());
	}

	@Test
	public void getName_Default_CorrectName()
	{
		assertEquals("Inventory", inventory.getName());
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
	
	@Test
	public void assertTrueForAll_ChecksIfNullOnEmptyInventory_DoesNotAssert()
	{
		inventory.assertTrueForAll(itemstack -> itemstack == null);
	}
	
	@Test(expected = AssertionError.class)
	public void assertTrueForAll_ChecksIfNullOnNonEmptyInventory_Asserts()
	{
		inventory.addItem(new ItemStack(Material.DIRT, 1));
		inventory.assertTrueForAll(itemstack -> itemstack == null);
	}
	
	@Test
	public void assertTrueForNonNulls_NumberOfExecutionsOnInventoryOneItem_EqualToOne()
	{
		inventory.addItem(new ItemStack(Material.DIRT, 1));
		AtomicInteger calls = new AtomicInteger(0);
		inventory.assertTrueForNonNulls(itemstack -> {
			calls.incrementAndGet();
			return true;
		});
		assertEquals(1, calls.get());
	}
	
	@Test
	public void assertTrueForSome_OneItemMeetsCondition_DoesNotAssert()
	{
		inventory.addItem(new ItemStack(Material.DIRT, 1));
		inventory.assertTrueForSome(itemstack -> itemstack.getAmount() > 0);
	}
	
	@Test(expected = AssertionError.class)
	public void assertTrueForSome_NoItemsMeetCondition_Asserts()
	{
		inventory.addItem(new ItemStack(Material.DIRT, 1));
		inventory.assertTrueForSome(itemstack -> itemstack.getAmount() > 16);
	}
	
	@Test
	public void assertContainsAny_ContainsThem_DoesNotAssert()
	{
		inventory.addItem(new ItemStack(Material.DIRT, 16));
		inventory.assertContainsAny(new ItemStack(Material.DIRT));
	}
	
	@Test(expected = AssertionError.class)
	public void assertContainsAny_DoesNotContainThem_Asserts()
	{
		inventory.addItem(new ItemStack(Material.GRASS, 16));
		inventory.assertContainsAny(new ItemStack(Material.DIRT));
	}
	
	@Test
	public void assertContainsAtLeast_ContainsExactly_DoesNotAssert()
	{
		inventory.addItem(new ItemStack(Material.DIRT, 4));
		inventory.assertContainsAtLeast(new ItemStack(Material.DIRT), 4);
	}
	
	@Test
	public void assertContainsAtLeast_ContainsMore_DoesNotAssert()
	{
		inventory.addItem(new ItemStack(Material.DIRT, 8));
		inventory.assertContainsAtLeast(new ItemStack(Material.DIRT), 4);
	}
	
	@Test(expected = AssertionError.class)
	public void assertContainsAtLeast_DoesNotContainEnough_Asserts()
	{
		inventory.addItem(new ItemStack(Material.GRASS, 3));
		inventory.assertContainsAtLeast(new ItemStack(Material.DIRT), 4);
	}
}




























