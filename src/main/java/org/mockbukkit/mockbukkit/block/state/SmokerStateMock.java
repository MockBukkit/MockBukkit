package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Smoker;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link Smoker}.
 *
 * @see AbstractFurnaceStateMock
 */
public class SmokerStateMock extends AbstractFurnaceStateMock implements Smoker
{

	/**
	 * Constructs a new {@link SmokerStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#SMOKER}
	 *
	 * @param material The material this state is for.
	 */
	public SmokerStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SMOKER);
	}

	/**
	 * Constructs a new {@link SmokerStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#SMOKER}
	 *
	 * @param block The block this state is for.
	 */
	protected SmokerStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SMOKER);
	}

	/**
	 * Constructs a new {@link SmokerStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected SmokerStateMock(@NotNull SmokerStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull SmokerStateMock getSnapshot()
	{
		return new SmokerStateMock(this);
	}

	@Override
	public @NotNull SmokerStateMock copy()
	{
		return new SmokerStateMock(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Smoker)) return false;
		return super.equals(o);
	}

}
