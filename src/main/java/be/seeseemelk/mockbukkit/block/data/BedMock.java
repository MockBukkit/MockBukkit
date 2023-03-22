package be.seeseemelk.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.FACING;
import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.OCCUPIED;
import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.PART;

/**
 * Mock implementation of {@link Bed}.
 */
public class BedMock extends BlockDataMock implements Bed
{

	/**
	 * Constructs a new {@link BedMock} for the provided {@link Material}.
	 * Only supports materials in {@link Tag#BEDS}
	 *
	 * @param type The material this data is for.
	 */
	public BedMock(@NotNull Material type)
	{
		super(type);
		checkType(type, Tag.BEDS);
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
	public void setOccupied(boolean occupied)
	{
		super.set(OCCUPIED, occupied);
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
