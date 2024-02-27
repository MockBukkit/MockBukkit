package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.ChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Chest}.
 *
 * @see TileStateMock
 */
public class ChestMock extends LootableContainerMock implements Chest
{

	private boolean isOpen = false;

	/**
	 * Constructs a new {@link ChestMock} for the provided {@link Material}.
	 * Only supports {@link Material#CHEST} and {@link Material#TRAPPED_CHEST}.
	 *
	 * @param material The material this state is for.
	 */
	public ChestMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.CHEST, Material.TRAPPED_CHEST);
	}

	/**
	 * Constructs a new {@link ChestMock} for the provided {@link Block}.
	 * Only supports {@link Material#CHEST} and {@link Material#TRAPPED_CHEST}.
	 *
	 * @param block The block this state is for.
	 */
	protected ChestMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.CHEST, Material.TRAPPED_CHEST);
	}

	/**
	 * Constructs a new {@link ChestMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected ChestMock(@NotNull ChestMock state)
	{
		super(state);
		this.isOpen = state.isOpen;
	}

	@Override
	public void open()
	{
		isOpen = true;
	}

	@Override
	public void close()
	{
		isOpen = false;
	}

	@Override
	public boolean isOpen()
	{
		return isOpen;
	}

	@Override
	public @NotNull Inventory getBlockInventory()
	{
		return getInventory();
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
		return new ChestInventoryMock(this, 27);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new ChestMock(this);
	}

}
