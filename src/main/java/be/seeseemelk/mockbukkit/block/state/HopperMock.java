package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.HopperInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;

/**
 * This {@link ContainerMock} represents a {@link Hopper}.
 *
 * @author TheBusyBiscuit
 *
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

}
