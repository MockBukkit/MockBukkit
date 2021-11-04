package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.HopperInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * This {@link ContainerMock} represents a {@link Hopper}.
 *
 * @author TheBusyBiscuit
 */
public class HopperMock extends ContainerMock implements Hopper
{

	public HopperMock(@NotNull Material material)
	{
		super(material);
	}

	protected HopperMock(@NotNull Block block)
	{
		super(block);
	}

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
	protected InventoryMock createInventory()
	{
		return new HopperInventoryMock(this);
	}

	@Override
	public BlockState getSnapshot()
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

	@Override
	public @Nullable Component customName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void customName(@Nullable Component customName)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
