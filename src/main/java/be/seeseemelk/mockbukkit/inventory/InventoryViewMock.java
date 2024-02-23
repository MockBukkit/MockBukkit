package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of an {@link InventoryView}.
 */
public abstract class InventoryViewMock extends InventoryView
{

	private Inventory topInventory;
	private Inventory bottomInventory;
	private HumanEntity player;
	private InventoryType type;
	private String name;
	private String originalTitle;
	private boolean titleChanged = false;

	/**
	 * Constructs a new {@link InventoryViewMock} with the provided parameters.
	 *
	 * @param player The player this view is for.
	 * @param name   The name of the view (title).
	 * @param top    The top inventory.
	 * @param bottom The bottom inventory.
	 * @param type   The type of the inventory.
	 */
	protected InventoryViewMock(HumanEntity player, String name, Inventory top, Inventory bottom, InventoryType type)
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
	public @NotNull Inventory getTopInventory()
	{
		return topInventory;
	}

	@Override
	public @NotNull Inventory getBottomInventory()
	{
		return bottomInventory;
	}

	@Override
	public @NotNull HumanEntity getPlayer()
	{
		return player;
	}

	@Override
	public @NotNull InventoryType getType()
	{
		return type;
	}

	@Override
	public @NotNull String getTitle()
	{
		return name;
	}

	@Override
	public @NotNull String getOriginalTitle()
	{
		return this.originalTitle;
	}

	@Override
	public void setTitle(@NotNull String title)
	{
		if (!this.titleChanged)
		{
			this.originalTitle = this.name;
			this.titleChanged = true;
		}
		this.name = title;
	}

}
