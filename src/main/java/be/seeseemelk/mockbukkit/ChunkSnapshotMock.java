package be.seeseemelk.mockbukkit;

import com.google.common.base.Preconditions;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Mock implementation of a {@link ChunkSnapshot}.
 */
public class ChunkSnapshotMock implements ChunkSnapshot
{

	private final String worldName;
	private final int x;
	private final int z;
	private final int minY;
	private final int maxY;
	private final long worldTime;
	private final Map<Coordinate, BlockData> blockData;
	private final Map<Coordinate, Biome> biomes;

	/**
	 * Constructs a new {@link ChunkSnapshotMock} with the provided parameters.
	 *
	 * @param x         The X coordinate of the chunk.
	 * @param z         The Y coordinate of the chunk.
	 * @param minY      The minimum Y level of the world.
	 * @param maxY      The maximum Y level of the world.
	 * @param worldName The name of the world.
	 * @param worldTime The time of the world at snapshot creation.
	 * @param blockData A map of all {@link BlockData} in this chunk.
	 * @param biomes    A map of all {@link Biome}s in this chunk.
	 */
	ChunkSnapshotMock(int x, int z, int minY, int maxY, String worldName, long worldTime,
			Map<Coordinate, BlockData> blockData, Map<Coordinate, Biome> biomes)
	{
		this.x = x;
		this.z = z;
		this.minY = minY;
		this.maxY = maxY;
		this.worldName = worldName;
		this.worldTime = worldTime;
		this.blockData = blockData;
		this.biomes = biomes;
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
		validateChunkCoordinates(x, y, z);

		return this.blockData.get(new Coordinate(x, y, z)).getMaterial();
	}

	@NotNull
	@Override
	public BlockData getBlockData(int x, int y, int z)
	{
		validateChunkCoordinates(x, y, z);

		return this.blockData.get(new Coordinate(x, y, z));
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
		return getBiome(z, 0, z);
	}

	@NotNull
	@Override
	public Biome getBiome(int x, int y, int z)
	{
		Preconditions.checkState(this.biomes != null && !this.biomes.isEmpty(),
				"ChunkSnapshot created without biome. Please call getSnapshot with includeBiome=true");
		validateChunkCoordinates(x, y, z);
		return this.biomes.get(new Coordinate(z, y, z));
	}

	@Override
	public double getRawBiomeTemperature(int x, int z)
	{
		return getRawBiomeTemperature(x, 0, z);
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
		int totalSections = (int) Math.ceil(Math.abs((minY - maxY)) / 16.0);
		if (sy < 0 || sy >= totalSections)
		{ // Bukkit just gets the value from an array, so if it's invalid it'll throw
			// this.
			throw new ArrayIndexOutOfBoundsException(
					"Index %d out of bounds for length %d".formatted(sy, totalSections));
		}

		for (int blockY = minY + (sy << 4); blockY < (minY + (sy << 4)) + 16; blockY++)
		{
			if (blockY < minY || blockY >= maxY)
			{
				// We round up when checking if section is too big, so if the height
				// isn't divisible by 16 we could get an error.
				break;
			}
			for (int blockX = 0; blockX < 16; blockX++)
			{
				for (int blockZ = 0; blockZ < 16; blockZ++)
				{
					if (!getBlockType(blockX, blockY, blockZ).isAir())
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
		Preconditions.checkNotNull(block, "BlockData cannot be null");
		return this.blockData.containsValue(block);
	}

	@Override
	public boolean contains(@NotNull Biome biome)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * Validates that the chunk coordinates are within the proper range.
	 * <p>
	 * X: 0-15 (inclusive)<p>
	 * Y: {@link #minY}-{@link #maxY} (inclusive)<p>
	 * Z: 0-15 (inclusive)
	 *
	 * @param x The X coordinate.
	 * @param y The Y coordinate.
	 * @param z The Z coordinate.
	 */
	private void validateChunkCoordinates(int x, int y, int z)
	{
		Preconditions.checkArgument(0 <= x && x <= 15, "x out of range (expected 0-15, got %s)", x);
		Preconditions.checkArgument(minY <= y && y <= maxY, "y out of range (expected %s-%s, got %s)", minY, maxY, y);
		Preconditions.checkArgument(0 <= z && z <= 15, "z out of range (expected 0-15, got %s)", z);
	}

}
