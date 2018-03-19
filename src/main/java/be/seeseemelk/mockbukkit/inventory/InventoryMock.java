package be.seeseemelk.mockbukkit.inventory;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public abstract class InventoryMock implements org.bukkit.inventory.Inventory
{
	private final ItemStack[] items;
	private final String name;
	private final InventoryHolder holder;
	private final InventoryType type;

	public InventoryMock(InventoryHolder holder, String name, int size, InventoryType type)
	{
		this.holder = holder;
		this.name = name;
		this.type = type;
		
		items = new ItemStack[size];
	}
	
	/**
	 * Asserts that a certain condition is true for all items, even {@code nulls},
	 * in this inventory.
	 * 
	 * @param condition
	 *            The condition to check for.
	 */
	public void assertTrueForAll(Predicate<ItemStack> condition)
	{
		for (ItemStack item : items)
		{
			assertTrue(condition.test(item));
		}
	}
	
	/**
	 * Assets that a certain condition is true for all items in this inventory that
	 * aren't null.
	 * 
	 * @param condition
	 *            The condition to check for.
	 */
	public void assertTrueForNonNulls(Predicate<ItemStack> condition)
	{
		assertTrueForAll(itemstack -> itemstack == null || condition.test(itemstack));
	}
	
	/**
	 * Asserts that a certain condition is true for at least one item in this
	 * inventory. It will skip any null items.
	 * 
	 * @param condition
	 *            The condition to check for.
	 */
	public void assertTrueForSome(Predicate<ItemStack> condition)
	{
		for (ItemStack item : items)
		{
			if (item != null && condition.test(item))
			{
				return;
			}
		}
		fail("Condition was not met for any items");
	}
	
	/**
	 * Asserts that the inventory contains at least one itemstack that is compatible
	 * with the given itemstack.
	 * 
	 * @param item The itemstack to compare everything to.
	 */
	public void assertContainsAny(ItemStack item)
	{
		assertTrueForSome(itemstack -> item.isSimilar(itemstack));
	}
	
	/**
	 * Asserts that the inventory contains at least a specific amount of items that are compatible
	 * with the given itemstack.
	 * @param item The itemstack to search for.
	 * @param amount The minimum amount of items that one should have.
	 */
	public void assertContainsAtLeast(ItemStack item, int amount)
	{
		int n = getNumberOfItems(item);
		String message = String.format("Inventory contains only <%d> but expected at least <%d>", n, amount);
		assertTrue(message, n >= amount);
	}
	
	/**
	 * Get the number of times a certain item is in the inventory.
	 * @param item The item to check for.
	 * @return The number of times the item is present in this inventory.
	 */
	public int getNumberOfItems(ItemStack item)
	{
		int amount = 0;
		for (ItemStack itemstack : items)
		{
			if (itemstack != null && item.isSimilar(itemstack))
			{
				amount += itemstack.getAmount();
			}
		}
		return amount;
	}
	
	@Override
	public int getSize()
	{
		return items.length;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public String getTitle()
	{
		return name;
	}

	@Override
	public ItemStack getItem(int index)
	{
		if (items[index] == null)
			items[index] = new ItemStack(Material.AIR);
		return items[index];
	}
	
	@Override
	public void setItem(int index, ItemStack item)
	{
		items[index] = item.clone();
	}
	
	/**
	 * Adds a single item to the inventory. Returns whatever item it couldn't add.
	 * 
	 * @param item
	 *            The item to add.
	 * @return The remaining stack that couldn't be added. If it's empty it just
	 *         returns {@code null}.
	 */
	public ItemStack addItem(ItemStack item)
	{
		item = item.clone();
		for (int i = 0; i < items.length; i++)
		{
			ItemStack oItem = items[i];
			if (oItem == null)
			{
				int toAdd = Math.min(item.getAmount(), item.getMaxStackSize());
				items[i] = item.clone();
				items[i].setAmount(toAdd);
				item.setAmount(item.getAmount() - toAdd);
			}
			else if (item.isSimilar(oItem) && oItem.getAmount() < oItem.getMaxStackSize())
			{
				int toAdd = Math.min(item.getAmount(), item.getMaxStackSize() - oItem.getAmount());
				oItem.setAmount(oItem.getAmount() + toAdd);
				item.setAmount(item.getAmount() - toAdd);
			}
			
			if (item.getAmount() == 0)
			{
				return null;
			}
		}
		
		return item;
	}
	
	@Override
	public HashMap<Integer, ItemStack> addItem(ItemStack... items) throws IllegalArgumentException
	{
		HashMap<Integer, ItemStack> notSaved = new HashMap<Integer, ItemStack>();
		for (int i = 0; i < items.length; i++)
		{
			ItemStack item = items[i];
			ItemStack left = addItem(item);
			if (left != null)
			{
				notSaved.put(i, left);
			}
		}
		return notSaved;
	}
	
	@Override
	public ItemStack[] getContents()
	{
		return items;
	}
	
	@Override
	public void setContents(ItemStack[] items)
	{
		for (int i = 0; i < getSize(); i++)
		{
			if (i < items.length && items[i] != null)
			{
				this.items[i] = items[i].clone();
			}
			else
			{
				this.items[i] = null;
			}
		}
	}
	
	@Override
	public InventoryHolder getHolder()
	{
		return holder;
	}
	
	@Override
	public ListIterator<ItemStack> iterator()
	{
		List<ItemStack> list = Arrays.asList(items).stream().filter(item -> item != null).collect(Collectors.toList());
		return list.listIterator();
	}
	
	@Override
	public InventoryType getType()
	{
		return type;
	}
	
	@Override
	public int getMaxStackSize()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setMaxStackSize(int size)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public HashMap<Integer, ItemStack> removeItem(ItemStack... items) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean contains(int materialId)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean contains(Material material) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean contains(ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean contains(int materialId, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean contains(Material material, int amount) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean contains(ItemStack item, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean containsAtLeast(ItemStack item, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public HashMap<Integer, ? extends ItemStack> all(int materialId)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public HashMap<Integer, ? extends ItemStack> all(Material material) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public HashMap<Integer, ? extends ItemStack> all(ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int first(int materialId)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int first(Material material) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int first(ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int firstEmpty()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void remove(int materialId)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public void remove(Material material) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public void remove(ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public void clear(int index)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public void clear()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public List<HumanEntity> getViewers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public ListIterator<ItemStack> iterator(int index)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
