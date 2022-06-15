package be.seeseemelk.mockbukkit;

import org.jetbrains.annotations.NotNull;

/**
 * A simple class that contains an x, y and z coordinate as integers.
 */
public class Coordinate
{
	public final int x;
	public final int y;
	public final int z;

	/**
	 * Creates a new coordinate object with a specified (x, y, z).
	 *
	 * @param x The x coordinate to set.
	 * @param y The y coordinate to set.
	 * @param z The z coordinate to set.
	 */
	public Coordinate(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Creates a coordinate object with a (x, y, z) of (0, 0, 0).
	 */
	public Coordinate()
	{
		this(0, 0, 0);
	}

	/**
	 * Gets the chunk coordinate this coordinate is in.
	 *
	 * @return The chunk coordinate of this coordinate.
	 */
	@NotNull
	public ChunkCoordinate toChunkCoordinate()
	{
		return new ChunkCoordinate(x >> 4, z >> 4);
	}

	/**
	 * Gets the coordinate relative to the chunk.
	 *
	 * @return The local coordinate.
	 */
	@NotNull
	public Coordinate toLocalCoordinate()
	{
		return new Coordinate(x & 0xf, y, z & 0xf);
	}

	@Override
	public int hashCode()
	{
		return x + (y << 8) + (z << 16);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Coordinate)
		{
			Coordinate c = (Coordinate) obj;
			return x == c.x && y == c.y && z == c.z;
		}
		else
		{
			return false;
		}
	}
}
