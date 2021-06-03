package be.seeseemelk.mockbukkit;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public class WorldBorderMock implements WorldBorder
{

	private final World world;
	private final Server server;
	private double size = 6.0E7;
	private double damageAmount = 0.2;
	private double damageBuffer = 5.0;
	private int warningDistance = 5;
	private int warningTime = 15;
	private double centerX = 0;
	private double centerZ = 0;

	public WorldBorderMock(World world, Server server)
	{
		this.world = world;
		this.server = server;
	}

	@Override
	public void reset()
	{
		setSize(6.0E7);
		setDamageAmount(0.2);
		setDamageBuffer(5.0);
		setWarningDistance(5);
		setWarningTime(15);
		setCenter(0, 0);
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
		if (seconds > 0) {
			// Assumes server at perfect 20tps
			double distance = newSize - size;
			double ticksToTake = seconds * 20;

			double distancePerTick = distance / ticksToTake;

			server.getScheduler().runTaskTimer(null, new BukkitRunnable()
			{
				@Override
				public void run()
				{
					if ((size < newSize && distance > 0) || (size > newSize && distance < 0)) {
						size += distancePerTick;
					} else {
						size = newSize;
						this.cancel();
					}
				}
			}, 1, 1);
		} else {
			setSize(newSize);
		}
	}

	@NotNull
	@Override
	public Location getCenter()
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
