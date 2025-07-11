package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.RedstoneWire;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class RedstoneWireDataMock extends BlockDataMock implements RedstoneWire
{

	/**
	 * Constructs a new {@link RedstoneWireDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public RedstoneWireDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link RedstoneWireDataMock} based on an existing {@link RedstoneWireDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected RedstoneWireDataMock(@NotNull RedstoneWireDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull Connection getFace(@NotNull BlockFace face)
	{
		return switch (face)
		{
			case NORTH -> this.get(BlockDataKey.REDSTONE_NORTH);
			case EAST -> this.get(BlockDataKey.REDSTONE_EAST);
			case SOUTH -> this.get(BlockDataKey.REDSTONE_SOUTH);
			case WEST -> this.get(BlockDataKey.REDSTONE_WEST);
			default -> throw new IllegalArgumentException("Invalid face " + face);
		};
	}

	@Override
	public void setFace(@NotNull BlockFace face, @NotNull Connection connection)
	{
		switch (face)
		{
		case NORTH -> this.set(BlockDataKey.REDSTONE_NORTH, connection);
		case EAST -> this.set(BlockDataKey.REDSTONE_EAST, connection);
		case SOUTH -> this.set(BlockDataKey.REDSTONE_SOUTH, connection);
		case WEST -> this.set(BlockDataKey.REDSTONE_WEST, connection);
		default -> throw new IllegalArgumentException("Cannot have face " + face);
		}
	}

	@Override
	public @NotNull Set<BlockFace> getAllowedFaces()
	{
		return Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
	}

	@Override
	public int getPower()
	{
		return this.get(BlockDataKey.POWER);
	}

	@Override
	public void setPower(int power)
	{
		Preconditions.checkArgument(power >= 0 && power <= this.getMaximumPower(), "Power must be between 0 and %s", this.getMaximumPower());
		this.set(BlockDataKey.POWER, power);
	}

	@Override
	public int getMaximumPower()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_POWER);
	}

	@Override
	@SuppressWarnings({"java:S2975", "java:S1182"})
	public @NotNull RedstoneWireDataMock clone()
	{
		return new RedstoneWireDataMock(this);
	}

}
