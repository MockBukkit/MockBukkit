package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public abstract class InventoryViewMock extends InventoryView
{
	private Inventory topInventory;
	private Inventory bottomInventory;
	private HumanEntity player;
	private InventoryType type;
	private String name;

	public InventoryViewMock(HumanEntity player, String name, Inventory top, Inventory bottom, InventoryType type)
	{
		this.player = player;
		this.type = type;
		this.name = name;
		topInventory = top;
		bottomInventory = bottom;
	}

	/**
	 * Sets the top inventory.
	 *
	 * @param inventory The top inventory.
	 */
	public void setTopInventory(Inventory inventory)
	{
		topInventory = inventory;
	}

	/**
	 * Sets the bottom inventory.
	 *
	 * @param inventory The bottom inventory.
	 */
	public void setBottomInventory(Inventory inventory)
	{
		bottomInventory = inventory;
	}

	/**
	 * Sets the player viewing.
	 *
	 * @param player The player viewing.
	 */
	public void setPlayer(HumanEntity player)
	{
		this.player = player;
	}

	/**
	 * Sets the type of inventory view.
	 *
	 * @param type The new type of inventory view.
	 */
	public void setType(InventoryType type)
	{
		this.type = type;
	}

	@Override
	public Inventory getTopInventory()
	{
		return topInventory;
	}

	@Override
	public Inventory getBottomInventory()
	{
		return bottomInventory;
	}

	@Override
	public HumanEntity getPlayer()
	{
		return player;
	}

	@Override
	public InventoryType getType()
	{
		return type;
	}

	@Override
	public String getTitle()
	{
		return name;
	}

}
