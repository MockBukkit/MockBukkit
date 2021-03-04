package be.seeseemelk.mockbukkit;

import java.util.Collection;

import be.seeseemelk.mockbukkit.persistence.PersistentDataContainerMock;
import org.apache.commons.lang.Validate;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

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
	public World getWorld()
	{
		return world;
	}

	@Override
	public Block getBlock(int x, int y, int z)
	{
		Validate.isTrue(x >= 0 && x <= 15, "x is out of range (expected 0-15)");
		Validate.isTrue(y >= 0 && y <= 255, "y is out of range (expected 0-255)");
		Validate.isTrue(z >= 0 && z <= 15, "z is out of range (expected 0-15)");
		return world.getBlockAt((this.x << 4) + x, y, (this.z << 4) + z);
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

	@Override
	public PersistentDataContainer getPersistentDataContainer()
	{
		return persistentDataContainer;
	}
}