package be.seeseemelk.mockbukkit;

import java.util.Objects;

/**
 * A simple class that contains an x and z coordinate of a chunk.
 */
public class ChunkCoordinate
{
	public final int x;
	public final int z;

	public ChunkCoordinate(int x, int z)
	{
		this.x = x;
		this.z = z;
	}

	public int getX()
	{
		return x;
	}

	public int getZ()
	{
		return z;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(x, z);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChunkCoordinate other = (ChunkCoordinate) obj;
		return x == other.x && z == other.z;
	}
}
