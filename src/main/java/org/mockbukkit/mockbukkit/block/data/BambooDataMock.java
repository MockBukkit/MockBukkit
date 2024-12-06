package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.type.Bamboo;
import org.jetbrains.annotations.NotNull;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.AGE_KEY;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.LEAVES_KEY;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.STAGE_KEY;

/**
 * Mock implementation of {@link Bamboo}.
 */
public class BambooDataMock extends BlockDataMock implements Bamboo
{
	private static final int MAXIMUM_AGE_AMOUNT = 1;
	private static final int MAXIMUM_STAGE_AMOUNT = 1;

	/**
	 * Constructs a new {@link BambooDataMock} for the provided {@link Material}.
	 */
	public BambooDataMock()
	{
		super(Material.BAMBOO);

		this.setAge(0);
		this.setLeaves(Leaves.NONE);
		this.setStage(0);
	}

	@NotNull
	@Override
	public Leaves getLeaves()
	{
		return this.get(LEAVES_KEY);
	}

	@Override
	public void setLeaves(@NotNull Bamboo.Leaves leaves)
	{
		this.set(LEAVES_KEY, leaves);
	}

	@Override
	public int getAge()
	{
		return this.get(AGE_KEY);
	}

	@Override
	public void setAge(int age)
	{
		this.set(AGE_KEY, age);
	}

	@Override
	public int getMaximumAge()
	{
		return MAXIMUM_AGE_AMOUNT;
	}

	@Override
	public int getStage()
	{
		return this.get(STAGE_KEY);
	}

	@Override
	public void setStage(int stage)
	{
		this.set(STAGE_KEY, stage);
	}

	@Override
	public int getMaximumStage()
	{
		return MAXIMUM_STAGE_AMOUNT;
	}

}
