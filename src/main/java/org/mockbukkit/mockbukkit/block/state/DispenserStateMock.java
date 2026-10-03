package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.projectiles.BlockProjectileSource;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.DispenserInventoryMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

/**
 * Mock implementation of a {@link Dispenser}.
 *
 * @see LootableContainerStateMock
 */
public class DispenserStateMock extends LootableContainerStateMock implements Dispenser
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
	protected @NotNull InventoryMock createInventory()
	{
		return new DispenserInventoryMock(this);
	}

	@Override
	public @NotNull DispenserStateMock getSnapshot()
	{
		return new DispenserStateMock(this);
	}

	@Override
	public @NotNull DispenserStateMock copy()
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
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof DispenserStateMock))
		{
			return false;
		}
		return super.equals(o);
	}

}
