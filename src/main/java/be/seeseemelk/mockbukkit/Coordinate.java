package be.seeseemelk.mockbukkit;

/**
 * A simple class that contains an x, y and z coordinate as integers.
 */
public class Coordinate
{
	public int x;
	public int y;
	public int z;

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

	@Override
	public int hashCode()
	{
		return (x << 0) + (y << 8) + (z << 16);
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
