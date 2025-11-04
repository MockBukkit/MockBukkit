package org.mockbukkit.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Shelf;
import org.bukkit.inventory.ShelfInventory;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.inventory.ShelfInventoryMock;

/**
 * Mock implementation of a {@link Shelf}.
 *
 * @see TileStateMock
 */
public class ShelfStateMock extends TileStateMock implements Shelf
{
	private ShelfInventory inventory = new ShelfInventoryMock(this);

	/**
	 * Constructs a new {@link ShelfStateMock} for the provided {@link Material}.
	 *
	 * @param material The material this state is for.
	 */
	public ShelfStateMock(@NotNull Material material)
	{
		super(material);
		Preconditions.checkArgument(Tag.WOODEN_SHELVES.isTagged(material), "Material must be a shelf.");
	}

	protected ShelfStateMock(@NotNull Block block)
	{
		super(block);
	}

	protected ShelfStateMock(@NotNull ShelfStateMock state)
	{
		super(state);
	}

	@Override
	public ShelfInventory getInventory()
	{
		return isPlaced() ? inventory : getSnapshotInventory();
	}

	@Override
	public ShelfInventory getSnapshotInventory()
	{
		return new ShelfInventoryMock(this);
	}

	@Override
	public @NotNull ShelfStateMock getSnapshot()
	{
		return new ShelfStateMock(this);
	}

}
