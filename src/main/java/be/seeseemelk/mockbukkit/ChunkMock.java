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
import org.bukkit.generator.structure.GeneratedStructure;
import org.bukkit.generator.structure.Structure;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Mock implementation of a {@link Chunk}.
 */
public class ChunkMock implements Chunk
{

	private final World world;
	private final int x;
	private final int z;
	private final PersistentDataContainer persistentDataContainer = new PersistentDataContainerMock();
	private boolean isSlimeChunk;
	private boolean isForceLoaded = false;

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
	public boolean isGenerated()
	{
		return true;
	}

	@Override
	public @NotNull BlockState[] getTileEntities(boolean useSnapshot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<BlockState> getTileEntities(@NotNull Predicate<? super Block> blockPredicate,
			boolean useSnapshot)
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
		Preconditions.checkArgument(world.getMinHeight() <= y && y <= world.getMaxHeight(),
				"y out of range (expected %s-%s, got %s)", world.getMinHeight(), world.getMaxHeight(), y);
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

	/**
	 * Gets all blocks in this chunk.
	 *
	 * @return A list of all blocks in this chunk.
	 */
	public @NotNull List<Block> getBlocks()
	{
		List<Block> blocks = new ArrayList<>(getCubicSize());
		for (int blockX = 0; blockX < 16; blockX++)
		{
			for (int blockY = world.getMinHeight(); blockY < world.getMaxHeight(); blockY++)
			{
				for (int blockZ = 0; blockZ < 16; blockZ++)
				{
					blocks.add(getBlock(blockX, blockY, blockZ));
				}
			}
		}
		return blocks;
	}

	@Override
	public @NotNull ChunkSnapshot getChunkSnapshot()
	{
		return getChunkSnapshot(true, false, false);
	}

	@Override
	@SuppressWarnings("UnstableApiUsage") // ImmutableMap#builderWithExpectedSize
	public @NotNull ChunkSnapshot getChunkSnapshot(boolean includeMaxblocky, boolean includeBiome,
			boolean includeBiomeTempRain)
	{
		ImmutableMap.Builder<Coordinate, BlockData> blockData = ImmutableMap.builderWithExpectedSize(getCubicSize());
		ImmutableMap.Builder<Coordinate, Biome> biomes = ImmutableMap.builderWithExpectedSize(getCubicSize());
		for (Block block : getBlocks())
		{
			Coordinate chunkLocalCoordinate = new Coordinate(block.getX() % 16, block.getY(), block.getZ() % 16);
			blockData.put(chunkLocalCoordinate, block.getBlockData());
			if (includeBiome || includeBiomeTempRain)
			{
				biomes.put(chunkLocalCoordinate, block.getBiome());
			}
		}
		return new ChunkSnapshotMock(x, z, world.getMinHeight(), world.getMaxHeight(), world.getName(),
				world.getFullTime(), blockData.build(), (includeBiome || includeBiomeTempRain) ? biomes.build() : null);
	}

	@Override
	public @NotNull ChunkSnapshot getChunkSnapshot(boolean b, boolean b1, boolean b2, boolean b3)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isEntitiesLoaded()
	{
		return isLoaded();
	}

	@Override
	public Entity[] getEntities()
	{
		BoundingBox boundingBox = new BoundingBox(x << 4, world.getMinHeight(), z << 4, (x << 4) + 16,
				world.getMaxHeight(), (z << 4) + 16);
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
		return world.isChunkLoaded(this);
	}

	@Override
	public boolean load(boolean generate)
	{
		return load();
	}

	@Override
	public boolean load()
	{
		world.loadChunk(this);
		return true;
	}

	@Override
	public boolean unload(boolean save)
	{
		return world.unloadChunk(x, z, save);
	}

	@Override
	public boolean unload()
	{
		return world.unloadChunk(this);
	}

	/**
	 * Sets the return value of {@link #isSlimeChunk()}.
	 *
	 * @param isSlimeChunk Whether this is a slime chunk.
	 */
	public void setSlimeChunk(boolean isSlimeChunk)
	{
		this.isSlimeChunk = isSlimeChunk;
	}

	@Override
	public boolean isSlimeChunk()
	{
		return this.isSlimeChunk;
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
		return isForceLoaded;
	}

	@Override
	public void setForceLoaded(boolean forced)
	{
		this.isForceLoaded = forced;
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
		return getBlocks().stream().anyMatch(b -> b.getBlockData().equals(block));
	}

	@Override
	public boolean contains(@NotNull Biome biome)
	{
		return getBlocks().stream().anyMatch(b -> b.getBiome() == biome);
	}

	@Override
	public @NotNull LoadLevel getLoadLevel()
	{
		return isLoaded() ? LoadLevel.ENTITY_TICKING : LoadLevel.UNLOADED;
	}

	@Override
	public @NotNull Collection<GeneratedStructure> getStructures()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<GeneratedStructure> getStructures(@NotNull Structure structure)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull PersistentDataContainer getPersistentDataContainer()
	{
		return persistentDataContainer;
	}

	private int getCubicSize()
	{
		return (16 * 16) * Math.abs(world.getMaxHeight() - world.getMinHeight()); // (w * w * h)
	}

}
