package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.DispenserInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
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
 * @see ContainerMock
 */
public class DispenserMock extends LootableContainerMock implements Dispenser
{

	/**
	 * Constructs a new {@link DispenserMock} for the provided {@link Material}.
	 * Only supports {@link Material#DISPENSER}
	 *
	 * @param material The material this state is for.
	 */
	public DispenserMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.DISPENSER);
	}

	/**
	 * Constructs a new {@link DispenserMock} for the provided {@link Block}.
	 * Only supports {@link Material#DISPENSER}
	 *
	 * @param block The block this state is for.
	 */
	protected DispenserMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.DISPENSER);
	}

	/**
	 * Constructs a new {@link DispenserMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected DispenserMock(@NotNull DispenserMock state)
	{
		super(state);
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
		return new DispenserInventoryMock(this);
	}

	@Override
	public @NotNull BlockState getSnapshot()
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
