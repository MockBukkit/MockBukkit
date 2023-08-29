package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.persistence.PersistentDataContainerMock;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Mock implementation of a {@link Chunk}.
 */
public class ChunkMock implements Chunk
{

	private final World world;
	private final int x;
	private final int z;
	private boolean loaded = true;
	private final PersistentDataContainer persistentDataContainer = new PersistentDataContainerMock();

	/**
	 * Constructs a new {@link ChunkMock} for the provided world, at the specified coordinates.
	 *
	 * @param world The world the chunk is in.
	 * @param x     The X coordinate of the chunk.
	 * @param z     The Y coordinate of the chunk.
	 */
	protected ChunkMock(final World world, final int x, final int z)
	{
		this.world = world;
		this.x = x;
		this.z = z;
	}

	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getZ()
	{
		return z;
	}

	@Override
	public @NotNull Collection<BlockState> getTileEntities(@NotNull Predicate<Block> blockPredicate, boolean useSnapshot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isGenerated()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BlockState[] getTileEntities(boolean useSnapshot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull World getWorld()
	{
		return world;
	}

	@Override
	public @NotNull Block getBlock(int x, int y, int z)
	{
		Preconditions.checkArgument(0 <= x && x <= 15, "x out of range (expected 0-15, got %s)", x);
		Preconditions.checkArgument(world.getMinHeight() <= y && y <= world.getMaxHeight(), "y out of range (expected %s-%s, got %s)", world.getMinHeight(), world.getMaxHeight(), y);
		Preconditions.checkArgument(0 <= z && z <= 15, "z out of range (expected 0-15, got %s)", z);
		return world.getBlockAt((this.x << 4) + x, y, (this.z << 4) + z);
	}

	/**
	 * Gets a block at a {@link Coordinate}.
	 *
	 * @param coordinate The coordinate at which to get the block.
	 * @return The block at the provided coordinate.
	 */
	public @NotNull Block getBlock(@NotNull Coordinate coordinate)
	{
		return getBlock(coordinate.x, coordinate.y, coordinate.z);
	}

	@Override
	public @NotNull ChunkSnapshot getChunkSnapshot()
	{
		return getChunkSnapshot(true, false, false);
	}

	@Override
	@SuppressWarnings("UnstableApiUsage")
	public @NotNull ChunkSnapshot getChunkSnapshot(boolean includeMaxblocky, boolean includeBiome, boolean includeBiomeTempRain)
	{
		// Cubic size of the chunk (w * w * h).
		int size = (16 * 16) * Math.abs((world.getMaxHeight() - world.getMinHeight()));
		ImmutableMap.Builder<Coordinate, BlockData> blockData = ImmutableMap.builderWithExpectedSize(size);
		ImmutableMap.Builder<Coordinate, Biome> biomes = ImmutableMap.builderWithExpectedSize(size);
		for (int blockX = 0; blockX < 16; blockX++)
		{
			for (int blockY = world.getMinHeight(); blockY < world.getMaxHeight(); blockY++)
			{
				for (int blockZ = 0; blockZ < 16; blockZ++)
				{
					Coordinate coord = new Coordinate(blockX, blockY, blockZ);
					blockData.put(coord, getBlock(blockX, blockY, blockZ).getBlockData());
					if (includeBiome || includeBiomeTempRain)
					{
						biomes.put(coord, world.getBiome(blockX << 4, blockY, blockZ << 4));
					}
				}
			}
		}
		return new ChunkSnapshotMock(x, z, world.getMinHeight(), world.getMaxHeight(), world.getName(), world.getFullTime(), blockData.build(), (includeBiome || includeBiomeTempRain) ? biomes.build() : null);
	}


	@Override
	public boolean isEntitiesLoaded()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public Entity[] getEntities()
	{
		BoundingBox boundingBox = new BoundingBox(x << 4,
				world.getMinHeight(),
				z << 4,
				(x << 4) + 16,
				world.getMaxHeight(),
				(z << 4) + 16);
		return world.getNearbyEntities(boundingBox).toArray(new Entity[0]);
	}

	@Override
	public BlockState[] getTileEntities()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isLoaded()
	{
		return loaded;
	}

	@Override
	public boolean load(boolean generate)
	{
		return load();
	}

	@Override
	public boolean load()
	{
		loaded = true;
		return true;
	}

	@Override
	public boolean unload(boolean save)
	{
		return unload();
	}

	@Override
	public boolean unload()
	{
		loaded = false;
		return true;
	}

	@Override
	public boolean isSlimeChunk()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int hashCode()
	{
		return world.hashCode() + x + z;
	}

	@Override
	public boolean equals(@Nullable Object obj)
	{
		if (obj == null)
			return false;
		else if (obj instanceof ChunkMock chunk)
		{
			return x == chunk.x && z == chunk.z && world.equals(chunk.world);
		}
		else
			return false;
	}

	@Override
	public boolean isForceLoaded()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setForceLoaded(boolean forced)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addPluginChunkTicket(@NotNull Plugin plugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removePluginChunkTicket(@NotNull Plugin plugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<Plugin> getPluginChunkTickets()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getInhabitedTime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setInhabitedTime(long ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean contains(@NotNull BlockData block)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean contains(@NotNull Biome biome)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull LoadLevel getLoadLevel()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull PersistentDataContainer getPersistentDataContainer()
	{
		return persistentDataContainer;
	}

}
