package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.inventory.BarrelInventoryMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

import java.util.Objects;

/**
 * Mock implementation of a {@link Barrel}.
 *
 * @see LootableContainerStateMock
 */
public class BarrelStateMock extends LootableContainerStateMock implements Barrel
{

	private boolean isOpen = false;

	/**
	 * Constructs a new {@link BarrelStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#BARREL}
	 *
	 * @param material The material this state is for.
	 */
	public BarrelStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.BARREL);
	}

	/**
	 * Constructs a new {@link BarrelStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#BARREL}
	 *
	 * @param block The block this state is for.
	 */
	protected BarrelStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.BARREL);
	}

	/**
	 * Constructs a new {@link BarrelStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected BarrelStateMock(@NotNull BarrelStateMock state)
	{
		super(state);
		this.isOpen = state.isOpen;
	}

	@Override
	public void open()
	{
		isOpen = true;
	}

	@Override
	public void close()
	{
		isOpen = false;
	}

	@Override
	public boolean isOpen()
	{
		return isOpen;
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
		return new BarrelInventoryMock(this);
	}

	@Override
	public @NotNull BarrelStateMock getSnapshot()
	{
		return new BarrelStateMock(this);
	}

	@Override
	public @NotNull BarrelStateMock copy()
	{
		return new BarrelStateMock(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof BarrelStateMock that))
		{
			return false;
		}
		if (!super.equals(o))
		{
			return false;
		}
		return isOpen == that.isOpen;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), isOpen);
	}

	@Override
	protected String toStringInternal()
	{
		return super.toStringInternal() + ", isOpen=" + isOpen;
	}

}
