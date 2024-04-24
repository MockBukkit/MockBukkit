package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.DispenserInventoryMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.loot.LootTable;
import org.bukkit.projectiles.BlockProjectileSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Dispenser}.
 *
 * @see ContainerStateMock
 */
public class DispenserStateMock extends ContainerStateMock implements Dispenser
{

	/**
	 * Constructs a new {@link DispenserStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#DISPENSER}
	 *
	 * @param material The material this state is for.
	 */
	public DispenserStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.DISPENSER);
	}

	/**
	 * Constructs a new {@link DispenserStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#DISPENSER}
	 *
	 * @param block The block this state is for.
	 */
	protected DispenserStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.DISPENSER);
	}

	/**
	 * Constructs a new {@link DispenserStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected DispenserStateMock(@NotNull DispenserStateMock state)
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
	public void setLootTable(@Nullable LootTable lootTable, long l)
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
		return new DispenserInventoryMock(this);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new DispenserStateMock(this);
	}

	@Override
	public BlockProjectileSource getBlockProjectileSource()
	{
		if (isPlaced())
		{
			return new DispenserProjectileSourceMock(this);
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean dispense()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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