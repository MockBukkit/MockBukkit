package be.seeseemelk.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Stairs;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class StairsMock extends BlockDataMock implements Stairs
{

	private static final String SHAPE = "shape";
	private static final String HALF = "half";
	private static final String FACING = "facing";
	private static final String WATERLOGGED = "waterlogged";

	public StairsMock(@NotNull Material type)
	{
		super(type);
		checkType(type, Tag.STAIRS);
		setShape(Shape.STRAIGHT);
		setWaterlogged(false);
		setFacing(BlockFace.NORTH);
		setHalf(Half.BOTTOM);
	}

	@Override
	public @NotNull Shape getShape()
	{
		return get(SHAPE);
	}

	@Override
	public void setShape(@NotNull Shape shape)
	{
		set(SHAPE, shape);
	}

	@Override
	public @NotNull Half getHalf()
	{
		return get(HALF);
	}

	@Override
	public void setHalf(@NotNull Half half)
	{
		set(HALF, half);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return get(FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace facing)
	{
		Preconditions.checkArgument(getFaces().contains(facing), "Invalid face. Must be one of " + getFaces());
		set(FACING, facing);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return Set.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST);
	}

	@Override
	public boolean isWaterlogged()
	{
		return get(WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		set(WATERLOGGED, waterlogged);
	}

}
