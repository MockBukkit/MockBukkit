package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dropper;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.DropperInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;

/**
 * This {@link ContainerMock} represents a {@link Dropper}.
 *
 * @author TheBusyBiscuit
 *
 */
public class DropperMock extends ContainerMock implements Dropper
{

	public DropperMock(@NotNull Material material)
	{
		super(material);
	}

	protected DropperMock(@NotNull Block block)
	{
		super(block);
	}

	protected DropperMock(@NotNull DropperMock state)
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
		return new DropperInventoryMock(this);
	}

	@Override
	public BlockState getSnapshot()
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
