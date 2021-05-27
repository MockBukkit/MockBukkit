package be.seeseemelk.mockbukkit.inventory;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class InventoryMock implements Inventory
{
	private final ItemStack[] items;
	private final InventoryHolder holder;
	private final InventoryType type;

	public InventoryMock(@Nullable InventoryHolder holder, int size, @NotNull InventoryType type)
	{
		Validate.isTrue(9 <= size && size <= 54 && size % 9 == 0,
		                "Size for custom inventory must be a multiple of 9 between 9 and 54 slots (got " + size + ")");
		Validate.notNull(type, "The InventoryType must not be null!");

		this.holder = holder;
		this.type = type;

		items = new ItemStack[size];
	}

	public InventoryMock(@Nullable InventoryHolder holder, @NotNull InventoryType type)
	{
		Validate.notNull(type, "The InventoryType must not be null!");

		this.holder = holder;
		this.type = type;

		items = new ItemStack[type.getDefaultSize()];
	}

	/**
	 * Asserts that a certain condition is true for all items, even {@code nulls}, in this inventory.
	 *
	 * @param condition The condition to check for.
	 */
	public void assertTrueForAll(@NotNull Predicate<ItemStack> condition)
	{
		for (ItemStack item : items)
		{
			assertTrue(condition.test(item));
		}
	}

	/**
	 * Assets that a certain condition is true for all items in this inventory that aren't null.
	 *
	 * @param condition The condition to check for.
	 */
	public void assertTrueForNonNulls(@NotNull Predicate<ItemStack> condition)
	{
		assertTrueForAll(itemstack -> itemstack == null || condition.test(itemstack));
	}

	/**
	 * Asserts that a certain condition is true for at least one item in this inventory. It will skip any null items.
	 *
	 * @param condition The condition to check for.
	 */
	public void assertTrueForSome(@NotNull Predicate<ItemStack> condition)
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
	 * Asserts that the inventory contains at least one itemstack that is compatible with the given itemstack.
	 *
	 * @param item The itemstack to compare everything to.
	 */
	public void assertContainsAny(@NotNull ItemStack item)
	{
		assertTrueForSome(item::isSimilar);
	}

	/**
	 * Asserts that the inventory contains at least a specific amount of items that are compatible with the given
	 * itemstack.
	 *
	 * @param item   The itemstack to search for.
	 * @param amount The minimum amount of items that one should have.
	 */
	public void assertContainsAtLeast(@NotNull ItemStack item, int amount)
	{
		int n = getNumberOfItems(item);
		String message = String.format("Inventory contains only <%d> but expected at least <%d>", n, amount);
		assertTrue(n >= amount, message);
	}

	/**
	 * Get the number of times a certain item is in the inventory.
	 *
	 * @param item The item to check for.
	 * @return The number of times the item is present in this inventory.
	 */
	public int getNumberOfItems(@NotNull ItemStack item)
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
	public ItemStack getItem(int index)
	{
		return items[index];
	}

	@Override
	public void setItem(int index, ItemStack item)
	{
		items[index] = item == null ? null : item.clone();
	}

	/**
	 * Adds a single item to the inventory. Returns whatever item it couldn't add.
	 *
	 * @param item The item to add.
	 * @return The remaining stack that couldn't be added. If it's empty it just returns {@code null}.
	 */
	@Nullable
	public ItemStack addItem(@NotNull ItemStack item)
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
		HashMap<Integer, ItemStack> notSaved = new HashMap<>();
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
		List<ItemStack> list = Arrays.asList(items).stream().filter(Objects::nonNull).collect(Collectors.toList());
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
	public ItemStack[] getStorageContents()
	{
		return getContents();
	}

	@Override
	public void setStorageContents(ItemStack[] items) throws IllegalArgumentException
	{
		setContents(items);
	}

	@Override
	public boolean contains(Material material) throws IllegalArgumentException
	{
		if (material == null)
		{
			throw new IllegalArgumentException("Material cannot be null.");
		}
		for (ItemStack itemStack : this.getContents())
		{
			if (itemStack != null && itemStack.getType() == material)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean contains(ItemStack item)
	{
		return contains(Objects.requireNonNull(item).getType());
	}

	@Override
	public boolean contains(Material material, int amount) throws IllegalArgumentException
	{
		if (material == null)
		{
			throw new IllegalArgumentException("Material cannot be null.");
		}
		return amount < 1 || getNumberOfItems(new ItemStack(material)) == amount;
	}

	@Override
	public boolean contains(ItemStack item, int amount)
	{
		return getNumberOfItems(item) == amount;
	}

	@Override
	public boolean containsAtLeast(ItemStack item, int amount)
	{
		return getNumberOfItems(item) >= amount;
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
		for (int i = 0; i < getSize(); i++)
		{
			if (items[i] == null || items[i].getType() == Material.AIR)
			{
				return i;
			}
		}

		return -1;
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
		items[index] = null;
	}

	@Override
	public void clear()
	{
		Arrays.fill(items, null);
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

	@Override
	public Location getLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isEmpty()
	{
		for (int i = 0; i < getSize(); i++)
		{
			if (items[i] != null && items[i].getType() != Material.AIR)
			{
				return false;
			}
		}

		return true;
	}

	@NotNull
	public Inventory getSnapshot()
	{
		Inventory inventory = new InventoryMock(holder, getSize(), type);
		inventory.setContents(getContents());
		return inventory;
	}

}
