package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.type.RedstoneRail;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class RedstoneRailDataMock extends RailDataMock implements RedstoneRail
{

	/**
	 * Constructs a new {@link RedstoneRailDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public RedstoneRailDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link RedstoneRailDataMock} based on an existing {@link RedstoneRailDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected RedstoneRailDataMock(@NotNull RedstoneRailDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isPowered()
	{
		return this.get(BlockDataKey.POWERED);
	}

	@Override
	public void setPowered(boolean powered)
	{
		this.set(BlockDataKey.POWERED, powered);
	}

	@Override
	public @NotNull Set<Shape> getShapes()
	{
		return Set.of(Shape.NORTH_SOUTH, Shape.EAST_WEST,
				Shape.ASCENDING_NORTH, Shape.ASCENDING_SOUTH,
				Shape.ASCENDING_EAST, Shape.ASCENDING_WEST
		);
	}

	@Override
	@SuppressWarnings({"java:S2975", "java:S1182"})
	public @NotNull RedstoneRailDataMock clone()
	{
		return new RedstoneRailDataMock(this);
	}

}
