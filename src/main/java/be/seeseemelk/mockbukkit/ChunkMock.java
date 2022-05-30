package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.persistence.PersistentDataContainerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.HashMap;
import java.util.Map;

public class ChunkMock implements Chunk
{

	private final World world;
	private final int x;
	private final int z;
	private boolean loaded = true;
	private final PersistentDataContainer persistentDataContainer = new PersistentDataContainerMock();

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
	public @NotNull BlockState[] getTileEntities(boolean useSnapshot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getChunkKey()
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
	public @NotNull ChunkSnapshot getChunkSnapshot(boolean includeMaxblocky, boolean includeBiome, boolean includeBiomeTempRain)
	{
		Map<Coordinate, BlockState> blockStates = new HashMap<>((15 * 15) * Math.abs((world.getMaxHeight() - world.getMinHeight())), 1.0f);
		for (int x = 0; x < 15; x++)
		{
			for (int y = world.getMinHeight(); y < world.getMaxHeight(); y++)
			{
				for (int z = 0; z < 15; z++)
				{
					blockStates.put(new Coordinate(x, y, z), getBlock(x, y, z).getState());
				}
			}
		}
		return new ChunkSnapshotMock(x, z, world.getMinHeight(), world.getMaxHeight(), world.getName(), world.getFullTime(), blockStates);
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		else if (obj instanceof ChunkMock)
		{
			ChunkMock chunk = (ChunkMock) obj;
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
	public @NotNull PersistentDataContainer getPersistentDataContainer()
	{
		return persistentDataContainer;
	}

}
