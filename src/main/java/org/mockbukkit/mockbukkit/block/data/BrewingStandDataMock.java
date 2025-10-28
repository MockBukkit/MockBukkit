package org.mockbukkit.mockbukkit.block.data;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.block.data.type.BrewingStand;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Mock implementation of {@link BrewingStand}.
 *
 * @see BlockDataMock
 */
public class BrewingStandDataMock extends BlockDataMock implements BrewingStand
{

	private final BlockDataKey[] bottleSlots = new BlockDataKey[] {
			BlockDataKey.HAS_BOTTLE_0, BlockDataKey.HAS_BOTTLE_1, BlockDataKey.HAS_BOTTLE_2
	};

	public BrewingStandDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected BrewingStandDataMock(@NotNull BlockDataMock other)
	{
		super(other);
	}

	@Override
	public boolean hasBottle(int index)
	{
		return this.get(bottleSlots[index]);
	}

	@Override
	public void setBottle(int index, boolean has)
	{
		this.set(bottleSlots[index], has);
	}

	@Override
	public @NotNull Set<Integer> getBottles()
	{
		ImmutableSet.Builder<Integer> bottles = ImmutableSet.builder();
		for (int index = 0, len = bottleSlots.length; index < len; index++)
		{
			if (this.get(bottleSlots[index]))
			{
				bottles.add(index);
			}
		}
		return bottles.build();
	}

	@Override
	public int getMaximumBottles()
	{
		return bottleSlots.length;
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull BrewingStandDataMock clone()
	{
		return new BrewingStandDataMock(this);
	}

}
