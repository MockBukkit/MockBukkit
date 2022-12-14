package be.seeseemelk.mockbukkit;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

public class ChunkMock implements Chunk
{

	private final World world;
	private final int x;
	private final int z;
	private boolean loaded = true;
	private boolean slimeChunk = false;

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
	public World getWorld()
	{
		return world;
	}

	@Override
	public Block getBlock(int x, int y, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ChunkSnapshot getChunkSnapshot()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ChunkSnapshot getChunkSnapshot(boolean includeMaxblocky, boolean includeBiome, boolean includeBiomeTempRain)
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

	public void setSlimeChunk(boolean slimeChunk)
	{
		this.slimeChunk = slimeChunk;
	}

	@Override
	public boolean isSlimeChunk()
	{
		return slimeChunk;
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
	public boolean addPluginChunkTicket(Plugin plugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removePluginChunkTicket(Plugin plugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Collection<Plugin> getPluginChunkTickets()
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
	public boolean contains(BlockData block)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
