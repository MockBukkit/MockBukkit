package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class InventoryViewMock extends InventoryView
{
	private Inventory topInventory;

	public InventoryViewMock()
	{
		
	}
	
	/**
	 * Sets the top inventory.
	 * @param inventory The top inventory.
	 */
	public void setTopInventory(Inventory inventory)
	{
		topInventory = inventory;
	}

	@Override
	public Inventory getTopInventory()
	{
		return topInventory;
	}

	@Override
	public Inventory getBottomInventory()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public HumanEntity getPlayer()
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

}
