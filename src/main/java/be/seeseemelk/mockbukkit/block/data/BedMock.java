package be.seeseemelk.mockbukkit.block.data;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BedMock extends BlockDataMock implements Bed
{

	private static final String PART = "part";
	private static final String OCCUPIED = "occupied";
	private static final String FACING = "facing";

	public BedMock(Material type)
	{
		super(type);
		if (!Tag.BEDS.isTagged(type))
		{
			throw new IllegalArgumentException("Cannot create a BedMock from " + type);
		}
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
		return ImmutableSet.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
	}

}
