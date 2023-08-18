package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.HopperInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Hopper}.
 *
 * @see ContainerMock
 */
public class HopperMock extends ContainerMock implements Hopper
{

	/**
	 * Constructs a new {@link HopperMock} for the provided {@link Material}.
	 * Only supports {@link Material#HOPPER}
	 *
	 * @param material The material this state is for.
	 */
	public HopperMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.HOPPER);
	}

	/**
	 * Constructs a new {@link HopperMock} for the provided {@link Block}.
	 * Only supports {@link Material#HOPPER}
	 *
	 * @param block The block this state is for.
	 */
	protected HopperMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.HOPPER);
	}

	/**
	 * Constructs a new {@link HopperMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected HopperMock(@NotNull HopperMock state)
	{
		super(state);
	}

	@Override
	public void setLootTable(LootTable table)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public LootTable getLootTable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSeed(long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getSeed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
		return new HopperInventoryMock(this);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new HopperMock(this);
	}

	@Override
	public boolean isRefillEnabled()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasBeenFilled()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canPlayerLoot(@NotNull UUID player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPlayerLooted(@NotNull UUID player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Long getLastLooted(@NotNull UUID player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setHasPlayerLooted(@NotNull UUID player, boolean looted)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPendingRefill()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getLastFilled()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getNextRefill()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long setNextRefill(long refillAt)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
