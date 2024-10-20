package org.mockbukkit.mockbukkit.world;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * A simple class that contains an x and z coordinate of a chunk.
 */
public class ChunkCoordinate
{

	/**
	 * The X coordinate.
	 */
	public final int x;

	/**
	 * The Z coordinate.
	 */
	public final int z;

	/**
	 * Constructs a new {@link ChunkCoordinate}.
	 *
	 * @param x The X coordinate.
	 * @param z The Z coordinate.
	 */
	public ChunkCoordinate(int x, int z)
	{
		this.x = x;
		this.z = z;
	}

	/**
	 * @return The X coordinate.
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @return The Z coordinate.
	 */
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
	public boolean equals(@Nullable Object obj)
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
