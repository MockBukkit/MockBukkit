package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.Rail;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class RailDataMock extends BlockDataMock implements Rail
{

	/**
	 * Constructs a new {@link RailDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	protected RailDataMock(@NotNull Material material)
	{
		super(material);
	}

	@Override
	public @NotNull Shape getShape()
	{
		return this.get(BlockDataKey.RAIL_SHAPE);
	}

	@Override
	public void setShape(@NotNull Shape shape)
	{
		Preconditions.checkArgument(this.getShapes().contains(shape), "Invalid shape. Allowed values are: %s", this.getShapes().stream().sorted().toList());
		this.set(BlockDataKey.RAIL_SHAPE, shape);
	}

	@Override
	public @NotNull Set<Shape> getShapes()
	{
		return Arrays.stream(Shape.values()).collect(Collectors.toSet());
	}

	@Override
	public boolean isWaterlogged()
	{
		return this.get(BlockDataKey.WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		this.set(BlockDataKey.WATERLOGGED, waterlogged);
	}

}
