package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.BarrelInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Barrel}.
 *
 * @see ContainerMock
 */
public class BarrelMock extends LootableContainerMock implements Barrel
{

	private boolean isOpen = false;

	/**
	 * Constructs a new {@link BarrelMock} for the provided {@link Material}.
	 * Only supports {@link Material#BARREL}
	 *
	 * @param material The material this state is for.
	 */
	public BarrelMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.BARREL);
	}

	/**
	 * Constructs a new {@link BarrelMock} for the provided {@link Block}.
	 * Only supports {@link Material#BARREL}
	 *
	 * @param block The block this state is for.
	 */
	protected BarrelMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.BARREL);
	}

	/**
	 * Constructs a new {@link BarrelMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected BarrelMock(@NotNull BarrelMock state)
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
	protected @NotNull InventoryMock createInventory()
	{
		return new BarrelInventoryMock(this);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new BarrelMock(this);
	}

}
