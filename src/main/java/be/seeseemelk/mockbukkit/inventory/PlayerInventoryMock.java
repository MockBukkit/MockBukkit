package be.seeseemelk.mockbukkit.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class PlayerInventoryMock implements PlayerInventory
{
	public static final int HOTBAR = 0;
	public static final int MAIN_INVENTORY = 9;
	public static final int BOOTS = 36;
	public static final int LEGGINGS = 37;
	public static final int CHESTPLATE = 38;
	public static final int HELMET = 39;
	
	private final ItemStack[] items = new ItemStack[getSize()];
	private final String name;
	
	public PlayerInventoryMock(String name)
	{
		this.name = name;
	}
	
	@Override
	public int getSize()
	{
		return 40;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public ItemStack getItem(int index)
	{
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
	public void setContents(ItemStack[] items) throws IllegalArgumentException
	{
		for (int i = 0; i < getSize(); i++)
		{
			if (i < items.length)
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setStorageContents(ItemStack[] items) throws IllegalArgumentException
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
	public String getTitle()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public InventoryType getType()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public ListIterator<ItemStack> iterator()
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
	public ItemStack[] getArmorContents()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public ItemStack[] getExtraContents()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public ItemStack getHelmet()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public ItemStack getChestplate()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public ItemStack getLeggings()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public ItemStack getBoots()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setArmorContents(ItemStack[] items)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public void setExtraContents(ItemStack[] items)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public void setHelmet(ItemStack helmet)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public void setChestplate(ItemStack chestplate)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public void setLeggings(ItemStack leggings)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public void setBoots(ItemStack boots)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public ItemStack getItemInMainHand()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setItemInMainHand(ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public ItemStack getItemInOffHand()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setItemInOffHand(ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public ItemStack getItemInHand()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setItemInHand(ItemStack stack)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public int getHeldItemSlot()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setHeldItemSlot(int slot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public int clear(int id, int data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public HumanEntity getHolder()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
}
