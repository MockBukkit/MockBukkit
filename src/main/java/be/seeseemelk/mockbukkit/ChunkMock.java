package be.seeseemelk.mockbukkit;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

public class ChunkMock implements Chunk
{
	private final World world;
	private final int x;
	private final int z;
	private boolean loaded = true;

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
	public boolean unload(boolean save, boolean safe)
	{
		return unload();
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

}


















