package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class PlayerInventoryMock extends InventoryMock implements PlayerInventory
{
	public static final int HOTBAR = 0;
	public static final int MAIN_INVENTORY = 9;
	public static final int BOOTS = 36;
	public static final int LEGGINGS = 37;
	public static final int CHESTPLATE = 38;
	public static final int HELMET = 39;
	
	public PlayerInventoryMock(HumanEntity holder, String name)
	{
		super(holder, name, 40);
	}
	
	@Override
	public HumanEntity getHolder()
	{
		return (HumanEntity) super.getHolder();
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
	
}
