package be.seeseemelk.mockbukkit.block.data;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
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
		if (!type.name().endsWith("_BED"))
		{
			throw new IllegalArgumentException("Cannot create a BedMock from " + type);
		}
		setFacing(BlockFace.NORTH);
		set(OCCUPIED, false);
		setPart(Part.FOOT);
	}

	@Override
	public @NotNull Part getPart()
	{
		return get(PART);
	}

	@Override
	public void setPart(@NotNull Part part)
	{
		set(PART, part);
	}

	@Override
	public boolean isOccupied()
	{
		return get(OCCUPIED);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return get(FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace facing)
	{
		set(FACING, facing);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return ImmutableSet.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
	}

}
