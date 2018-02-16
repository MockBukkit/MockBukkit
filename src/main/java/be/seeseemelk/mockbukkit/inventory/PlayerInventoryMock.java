package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class PlayerInventoryMock extends InventoryMock implements PlayerInventory
{
	public static final int HOTBAR = 0;
	public static final int SLOT_BAR = 9;
	public static final int BOOTS = 36;
	public static final int LEGGINGS = 37;
	public static final int CHESTPLATE = 38;
	public static final int HELMET = 39;
	public static final int OFF_HAND = 40;
	private int mainHandSlot = 0;
	private ItemStack[] armorContents = new ItemStack[4];
	private ItemStack[] extraContents = new ItemStack[1];
	
	public PlayerInventoryMock(HumanEntity holder, String name)
	{
		super(holder, name, 36, InventoryType.PLAYER);
	}
	
	@Override
	public int getSize()
	{
		return super.getSize() + armorContents.length + extraContents.length;
	}
	
	@Override
	public ItemStack getItem(int index)
	{
		int size = super.getSize();
		if (index < size)
			return super.getItem(index);
		else if (index < size + armorContents.length)
			return armorContents[index - size];
		else
			return extraContents[index - size - armorContents.length];
	}
	
	@Override
	public void setItem(int index, ItemStack item)
	{
		int size = super.getSize();
		if (index < size)
			super.setItem(index, item);
		else if (index < size + armorContents.length)
			armorContents[index - size] = item;
		else
			extraContents[index - size - armorContents.length] = item;
	}
	
	@Override
	public HumanEntity getHolder()
	{
		return (HumanEntity) super.getHolder();
	}
	
	@Override
	public ItemStack[] getArmorContents()
	{
		return armorContents;
	}
	
	@Override
	public ItemStack[] getExtraContents()
	{
		return extraContents;
	}
	
	@Override
	public ItemStack getHelmet()
	{
		return armorContents[3];
	}
	
	@Override
	public ItemStack getChestplate()
	{
		return armorContents[2];
	}
	
	@Override
	public ItemStack getLeggings()
	{
		return armorContents[1];
	}
	
	@Override
	public ItemStack getBoots()
	{
		return armorContents[0];
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
		armorContents[3] = helmet;
	}
	
	@Override
	public void setChestplate(ItemStack chestplate)
	{
		armorContents[2] = chestplate;
	}
	
	@Override
	public void setLeggings(ItemStack leggings)
	{
		armorContents[1] = leggings;
	}
	
	@Override
	public void setBoots(ItemStack boots)
	{
		armorContents[0] = boots;
	}
	
	@Override
	public ItemStack getItemInMainHand()
	{
		return getContents()[SLOT_BAR + mainHandSlot];
	}
	
	@Override
	public void setItemInMainHand(ItemStack item)
	{
		getContents()[SLOT_BAR + mainHandSlot] = item;
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
	
	@Deprecated
	@Override
	public ItemStack getItemInHand()
	{
		return getItemInMainHand();
	}
	
	@Deprecated
	@Override
	public void setItemInHand(ItemStack stack)
	{
		setItemInMainHand(stack);
		
	}
	
	@Override
	public int getHeldItemSlot()
	{
		return mainHandSlot;
	}
	
	@Override
	public void setHeldItemSlot(int slot)
	{
		if (slot < 0 || slot > 8)
			throw new ArrayIndexOutOfBoundsException("Slot should be within [0-8] (was: " + slot + ")");
		mainHandSlot = slot;
	}
	
	@Override
	public int clear(int id, int data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
}
