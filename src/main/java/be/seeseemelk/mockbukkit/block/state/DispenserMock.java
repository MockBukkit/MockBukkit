package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.loot.LootTable;
import org.bukkit.projectiles.BlockProjectileSource;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.DispenserInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;

/**
 * This {@link ContainerMock} represents a {@link Dispenser}.
 *
 * @author TheBusyBiscuit
 *
 */
public class DispenserMock extends ContainerMock implements Dispenser
{

	public DispenserMock(@NotNull Material material)
	{
		super(material);
	}

	protected DispenserMock(@NotNull Block block)
	{
		super(block);
	}

	protected DispenserMock(@NotNull DispenserMock state)
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
		return new DispenserInventoryMock(this);
	}

	@Override
	public BlockState getSnapshot()
	{
		return new DispenserMock(this);
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

}
