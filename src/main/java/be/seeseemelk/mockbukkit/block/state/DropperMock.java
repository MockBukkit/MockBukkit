package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.DropperInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dropper;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Dropper}.
 *
 * @see ContainerMock
 */
public class DropperMock extends LootableContainerMock implements Dropper
{

	/**
	 * Constructs a new {@link DispenserMock} for the provided {@link Material}.
	 * Only supports {@link Material#DISPENSER}
	 *
	 * @param material The material this state is for.
	 */
	public DropperMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.DROPPER);
	}

	/**
	 * Constructs a new {@link DispenserMock} for the provided {@link Block}.
	 * Only supports {@link Material#DISPENSER}
	 *
	 * @param block The block this state is for.
	 */
	protected DropperMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.DROPPER);
	}

	/**
	 * Constructs a new {@link DispenserMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected DropperMock(@NotNull DropperMock state)
	{
		super(state);
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
		return new DropperInventoryMock(this);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new DropperMock(this);
	}

	@Override
	public void drop()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
