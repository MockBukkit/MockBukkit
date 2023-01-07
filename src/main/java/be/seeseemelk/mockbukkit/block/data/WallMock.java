package be.seeseemelk.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Wall;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.EAST;
import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.WEST;
import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.NORTH;
import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.SOUTH;
import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.UP;
import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.WATERLOGGED;

/**
 * Mock implementation of {@link Wall}.
 */
public class WallMock extends BlockDataMock implements Wall
{
	/**
	 * Constructs a new {@link WallMock} for the provided {@link Material}.
	 * Only supports materials in {@link Tag#WALLS}
	 *
	 * @param type The material this data is for.
	 */
	public WallMock(@NotNull Material type)
	{
		super(type);
		checkType(type, Tag.WALLS);
		setHeight(BlockFace.EAST, Height.NONE);
		setHeight(BlockFace.WEST, Height.NONE);
		setHeight(BlockFace.NORTH, Height.NONE);
		setHeight(BlockFace.SOUTH, Height.NONE);
		setUp(false);
		setWaterlogged(false);
	}

	/**
	 * @return the set of possible block faces for {@link Wall}
	 */
	public @NotNull Set<BlockFace> getFaces()
	{
		return Set.of(BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH);
	}

	@Override
	public @NotNull Height getHeight(@NotNull BlockFace face)
	{
		Preconditions.checkArgument(getFaces().contains(face), "Invalid face. Must be one of " + getFaces());
		if(face == BlockFace.EAST) { return get(EAST); }
	 	else if(face == BlockFace.WEST) { return get(WEST); }
	 	else if(face == BlockFace.NORTH) { return get(NORTH); }
	 	else { return get(SOUTH); }

	}

	@Override
	public void setHeight(@NotNull BlockFace face, @NotNull Height height)
	{
		Preconditions.checkNotNull(height, "Height cannot be null");
		Preconditions.checkArgument(getFaces().contains(face), "Invalid face. Must be one of " + getFaces());
		if(face == BlockFace.EAST) { set(EAST, height); }
		else if(face == BlockFace.WEST) { set(WEST, height); }
		else if(face == BlockFace.NORTH) { set(NORTH, height); }
		else  { set(SOUTH, height); }
	}

	@Override
	public boolean isUp()
	{
		return super.get(UP);
	}

	@Override
	public void setUp(boolean up)
	{
		super.set(UP, up);
	}

	@Override
	public boolean isWaterlogged()
	{
		return super.get(WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		super.set(WATERLOGGED, waterlogged);
	}

}
