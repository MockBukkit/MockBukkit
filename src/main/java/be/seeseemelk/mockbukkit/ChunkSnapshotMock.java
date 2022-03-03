package be.seeseemelk.mockbukkit;

import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public class ChunkSnapshotMock implements ChunkSnapshot
{

	private final String worldName;
	private final int x;
	private final int minY;
	private final int z;
	private final long worldTime;
	private final BlockState[][][] blockStates;

	ChunkSnapshotMock(int x, int z, int minY, String worldName, long worldTime, BlockState[][][] blockStates)
	{
		this.x = x;
		this.z = z;
		this.minY = minY;
		this.worldName = worldName;
		this.worldTime = worldTime;
		this.blockStates = blockStates;
	}

	@Override
	public int getX()
	{
		return this.x;
	}

	@Override
	public int getZ()
	{
		return this.z;
	}

	@NotNull
	@Override
	public String getWorldName()
	{
		return this.worldName;
	}

	@NotNull
	@Override
	public Material getBlockType(int x, int y, int z)
	{
		return blockStates[x][y][z].getType();
	}

	@NotNull
	@Override
	public BlockData getBlockData(int x, int y, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getData(int x, int y, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getBlockSkyLight(int x, int y, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getBlockEmittedLight(int x, int y, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getHighestBlockYAt(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public Biome getBiome(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public Biome getBiome(int x, int y, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getRawBiomeTemperature(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getRawBiomeTemperature(int x, int y, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getCaptureFullTime()
	{
		return this.worldTime;
	}

	@Override
	public boolean isSectionEmpty(int sy)
	{
		for (int y = sy << 4; y < (sy << 4) + 16; y++)
		{
			for (int x = 0; x < 15; x++)
			{
				for (int z = 0; z < 15; z++)
				{
					if (!getBlockType(x, y, z).isAir())
					{
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean contains(@NotNull BlockData block)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
