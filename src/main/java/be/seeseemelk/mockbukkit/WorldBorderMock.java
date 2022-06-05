package be.seeseemelk.mockbukkit;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A mock world border object.
 */
public class WorldBorderMock implements WorldBorder
{

	private static final double DEFAULT_BORDER_SIZE = 6.0E7D;
	private static final double DEFAULT_DAMAGE_AMOUNT = 0.2D;
	private static final double DEFAULT_DAMAGE_BUFFER = 5.0D;
	private static final int DEFAULT_WARNING_DISTANCE = 5;
	private static final int DEFAULT_WARNING_TIME = 15;
	private static final double DEFAULT_CENTER_X = 0;
	private static final double DEFAULT_CENTER_Z = 0;
	private static final double MAX_CENTER_VALUE = 3.0E7D;

	private final World world;
	private final Server server;
	private double size;
	private double damageAmount;
	private double damageBuffer;
	private int warningDistance;
	private int warningTime;
	private double centerX;
	private double centerZ;

	/**
	 * Creates a new world border mock
	 *
	 * @param world  The world it is the border of
	 * @param server The server it is in
	 */
	public WorldBorderMock(@NotNull World world, @NotNull Server server)
	{
		this.world = world;
		this.server = server;

		reset();
	}

	@Override
	public @Nullable World getWorld()
	{
		return this.world;
	}

	@Override
	public void reset()
	{
		setSize(DEFAULT_BORDER_SIZE);
		setDamageAmount(DEFAULT_DAMAGE_AMOUNT);
		setDamageBuffer(DEFAULT_DAMAGE_BUFFER);
		setWarningDistance(DEFAULT_WARNING_DISTANCE);
		setWarningTime(DEFAULT_WARNING_TIME);
		setCenter(DEFAULT_CENTER_X, DEFAULT_CENTER_Z);
	}

	@Override
	public double getSize()
	{
		return size;
	}

	@Override
	public void setSize(double newSize)
	{
		this.size = newSize;
	}

	@Override
	public void setSize(double newSize, long seconds)
	{
		if (seconds > 0)
		{
			// Assumes server at perfect 20tps
			double distance = newSize - size;
			double ticksToTake = seconds * 20;

			double distancePerTick = distance / ticksToTake;

			new BukkitRunnable()
			{
				@Override
				public void run()
				{
					if ((size < newSize && distance > 0) || (size > newSize && distance < 0))
					{
						size += distancePerTick;
					}
					else
					{
						size = newSize;
						this.cancel();
					}
				}
			}.runTaskTimer(null, 1, 1);
		}
		else
		{
			setSize(newSize);
		}
	}

	@Override
	public @NotNull Location getCenter()
	{
		return new Location(this.world, centerX, 0, centerZ);
	}

	@Override
	public void setCenter(@NotNull Location location)
	{
		setCenter(location.getX(), location.getZ());
	}

	@Override
	public void setCenter(double x, double z)
	{
		x = Math.min(MAX_CENTER_VALUE, Math.max(-MAX_CENTER_VALUE, x));
		z = Math.min(MAX_CENTER_VALUE, Math.max(-MAX_CENTER_VALUE, z));
		this.centerX = x;
		this.centerZ = z;
	}

	@Override
	public double getDamageBuffer()
	{
		return damageBuffer;
	}

	@Override
	public void setDamageBuffer(double blocks)
	{
		this.damageBuffer = blocks;
	}

	@Override
	public double getDamageAmount()
	{
		return damageAmount;
	}

	@Override
	public void setDamageAmount(double damage)
	{
		this.damageAmount = damage;
	}

	@Override
	public int getWarningTime()
	{
		return warningTime;
	}

	@Override
	public void setWarningTime(int seconds)
	{
		this.warningTime = seconds;
	}

	@Override
	public int getWarningDistance()
	{
		return warningDistance;
	}

	@Override
	public void setWarningDistance(int distance)
	{
		this.warningDistance = distance;
	}

	@Override
	public boolean isInside(@NotNull Location location)
	{
		Validate.notNull(location, "Location cannot be null");

		BoundingBox worldBorderBoundingBox = new BoundingBox(centerX - size, Double.MAX_VALUE, centerZ - size,
				centerX + size, Double.MAX_VALUE * -1, centerZ + size);

		return worldBorderBoundingBox.contains(location.toVector()) && location.getWorld() == world;
	}

}
