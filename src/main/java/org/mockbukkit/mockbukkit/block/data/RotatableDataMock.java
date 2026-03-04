package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Rotatable;
import org.jetbrains.annotations.NotNull;

public class RotatableDataMock extends BlockDataMock implements Rotatable
{

	/**
	 * Constructs a new {@link RotatableDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public RotatableDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link RotatableDataMock} based on an existing {@link RotatableDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected RotatableDataMock(@NotNull RotatableDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull BlockFace getRotation()
	{
		int data = this.get(BlockDataKey.ROTATION);
		return getBlockFaceFromId(data);
	}

	@Override
	public void setRotation(@NotNull BlockFace rotation)
	{
		int val = getIdFromBlockFace(rotation);
		this.set(BlockDataKey.ROTATION, val);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull RotatableDataMock clone()
	{
		return new RotatableDataMock(this);
	}

	public static @NotNull BlockFace getBlockFaceFromId(int data)
	{
		return switch (data)
		{
			case 0x0 -> BlockFace.SOUTH;
			case 0x1 -> BlockFace.SOUTH_SOUTH_WEST;
			case 0x2 -> BlockFace.SOUTH_WEST;
			case 0x3 -> BlockFace.WEST_SOUTH_WEST;
			case 0x4 -> BlockFace.WEST;
			case 0x5 -> BlockFace.WEST_NORTH_WEST;
			case 0x6 -> BlockFace.NORTH_WEST;
			case 0x7 -> BlockFace.NORTH_NORTH_WEST;
			case 0x8 -> BlockFace.NORTH;
			case 0x9 -> BlockFace.NORTH_NORTH_EAST;
			case 0xA -> BlockFace.NORTH_EAST;
			case 0xB -> BlockFace.EAST_NORTH_EAST;
			case 0xC -> BlockFace.EAST;
			case 0xD -> BlockFace.EAST_SOUTH_EAST;
			case 0xE -> BlockFace.SOUTH_EAST;
			case 0xF -> BlockFace.SOUTH_SOUTH_EAST;
			default -> throw new IllegalArgumentException("Unknown rotation " + data);
		};
	}

	public static int getIdFromBlockFace(@NotNull BlockFace rotation)
	{
		return switch (rotation)
		{
			case SOUTH -> 0x0;
			case SOUTH_SOUTH_WEST -> 0x1;
			case SOUTH_WEST -> 0x2;
			case WEST_SOUTH_WEST -> 0x3;
			case WEST -> 0x4;
			case WEST_NORTH_WEST -> 0x5;
			case NORTH_WEST -> 0x6;
			case NORTH_NORTH_WEST -> 0x7;
			case NORTH -> 0x8;
			case NORTH_NORTH_EAST -> 0x9;
			case NORTH_EAST -> 0xA;
			case EAST_NORTH_EAST -> 0xB;
			case EAST -> 0xC;
			case EAST_SOUTH_EAST -> 0xD;
			case SOUTH_EAST -> 0xE;
			case SOUTH_SOUTH_EAST -> 0xF;
			default -> throw new IllegalArgumentException("Illegal rotation " + rotation);
		};
	}

}
