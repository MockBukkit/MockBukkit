package be.seeseemelk.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.AmethystCluster;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class AmethystClusterMock extends BlockDataMock implements AmethystCluster, Directional, Waterlogged
{

	private static final String FACING = "facing";
	private static final String WATERLOGGED = "waterlogged";

	public AmethystClusterMock(Material type)
	{
		super(type);
		if (type != Material.AMETHYST_CLUSTER)
		{
			throw new IllegalArgumentException("Cannot create an AmethystClusterMock for " + type.name());
		}
		setFacing(BlockFace.NORTH);
		setWaterlogged(false);
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
		return Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN);
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
