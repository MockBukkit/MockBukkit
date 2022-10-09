package be.seeseemelk.mockbukkit.block.data;

import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.FACING;
import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.OCCUPIED;
import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.PART;

public class BedMock extends BlockDataMock implements Bed
{

	public BedMock(@NotNull Material type)
	{
		super(type);
		checkType(type, MaterialTags.BEDS);
		this.setFacing(BlockFace.NORTH);
		super.set(OCCUPIED, false);
		this.setPart(Part.FOOT);
	}

	@Override
	public @NotNull Part getPart()
	{
		return super.get(PART);
	}

	@Override
	public void setPart(@NotNull Part part)
	{
		super.set(PART, part);
	}

	@Override
	public boolean isOccupied()
	{
		return super.get(OCCUPIED);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return super.get(FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace facing)
	{

		if (!getFaces().contains(facing))
		{
			throw new IllegalArgumentException("Invalid face: " + facing);
		}
		super.set(FACING, facing);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
	}

}
