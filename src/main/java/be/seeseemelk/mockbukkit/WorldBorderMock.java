package be.seeseemelk.mockbukkit;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public class WorldBorderMock implements WorldBorder
{

	World world;
	double size;
	double damageAmount = 0.2;
	double damageBuffer = 5.0;
	int warningDistance = 5;
	int warningTime = 15;
	double centerX = 0;
	double centerZ = 0;

	public WorldBorderMock(World world)
	{
		this.world = world;
		reset();
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
		setSize(newSize, 0);
	}

	@Override
	public void setSize(double newSize, long seconds)
	{
		// I dont think that implementing seconds here is very important
		this.size = newSize;
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
