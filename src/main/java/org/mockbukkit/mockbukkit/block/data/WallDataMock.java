package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Wall;
import org.jetbrains.annotations.NotNull;

public class WallDataMock extends BlockDataMock implements Wall
{

	/**
	 * Constructs a new {@link WallDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public WallDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link WallDataMock} based on an existing {@link WallDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected WallDataMock(WallDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isUp()
	{
		return this.get(BlockDataKey.UP);
	}

	@Override
	public void setUp(boolean up)
	{
		this.set(BlockDataKey.UP, up);
	}

	@Override
	public @NotNull Height getHeight(@NotNull BlockFace blockFace)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void setHeight(@NotNull BlockFace blockFace, @NotNull Height height)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Gets the value of the 'waterlogged' property.
	 *
	 * @return the 'waterlogged' value
	 */
	@Override
	public boolean isWaterlogged()
	{
		return this.get(BlockDataKey.WATERLOGGED);
	}

	/**
	 * Sets the value of the 'waterlogged' property.
	 *
	 * @param waterlogged the new 'waterlogged' value
	 */
	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		this.set(BlockDataKey.WATERLOGGED, waterlogged);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull WallDataMock clone()
	{
		return new WallDataMock(this);
	}

}
