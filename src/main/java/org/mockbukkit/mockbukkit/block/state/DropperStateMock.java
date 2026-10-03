package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.DropperInventoryMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

/**
 * Mock implementation of a {@link Dropper}.
 *
 * @see LootableContainerStateMock
 */
public class DropperStateMock extends LootableContainerStateMock implements Dropper
{

	/**
	 * Constructs a new {@link DispenserStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#DISPENSER}
	 *
	 * @param material The material this state is for.
	 */
	public DropperStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.DROPPER);
	}

	/**
	 * Constructs a new {@link DispenserStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#DISPENSER}
	 *
	 * @param block The block this state is for.
	 */
	protected DropperStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.DROPPER);
	}

	/**
	 * Constructs a new {@link DispenserStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected DropperStateMock(@NotNull DropperStateMock state)
	{
		super(state);
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
		return new DropperInventoryMock(this);
	}

	@Override
	public @NotNull DropperStateMock getSnapshot()
	{
		return new DropperStateMock(this);
	}

	@Override
	public @NotNull DropperStateMock copy()
	{
		return new DropperStateMock(this);
	}

	@Override
	public void drop()
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
		if (!(o instanceof DropperStateMock))
		{
			return false;
		}
		return super.equals(o);
	}

}
