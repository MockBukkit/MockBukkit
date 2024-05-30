package be.seeseemelk.mockbukkit;

import com.google.common.base.Preconditions;
import io.papermc.paper.event.world.border.WorldBorderBoundsChangeEvent;
import io.papermc.paper.event.world.border.WorldBorderBoundsChangeFinishEvent;
import io.papermc.paper.event.world.border.WorldBorderCenterChangeEvent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * Mock implementation of a {@link WorldBorder}.
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
	private static final long MAX_MOVEMENT_TIME = 9223372036854775L;
	private static final double MAX_BORDER_SIZE = 6.0E7D;
	private static final double MIN_BORDER_SIZE = 1.0D;

	private final @NotNull World world;
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
	 * @param world The world it is the border of
	 */
	public WorldBorderMock(@NotNull World world)
	{
		Preconditions.checkNotNull(world, "World cannot be null");

		this.world = world;

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
		return this.size;
	}

	@Override
	public void setSize(double newSize)
	{
		this.setSize(newSize, 0L);
	}

	@Override
	public void setSize(double newSize, long seconds)
	{
		newSize = Math.min(MAX_BORDER_SIZE, Math.max(MIN_BORDER_SIZE, newSize));
		seconds = Math.min(MAX_MOVEMENT_TIME, Math.max(0L, seconds));

		WorldBorderBoundsChangeEvent.Type moveType = seconds <= 0 ? WorldBorderBoundsChangeEvent.Type.INSTANT_MOVE
				: WorldBorderBoundsChangeEvent.Type.STARTED_MOVE;
		WorldBorderBoundsChangeEvent event = new WorldBorderBoundsChangeEvent(this.world, this, moveType, this.size,
				newSize, seconds * 1000L);
		if (!event.callEvent())
			return;

		double millis = event.getDuration();
		newSize = event.getNewSize();

		if (millis <= 0)
		{
			this.size = newSize;
			return;
		}

		double distance = newSize - this.size;
		moveBorderOverTime(distance, millis, newSize);
	}

	@Override
	public void setSize(double newSize, @NotNull TimeUnit unit, long time)
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	private void moveBorderOverTime(double distance, double millis, double newSize)
	{
		double distancePerTick = distance / ((millis / 1000) * 20);
		final double oldSize = this.size;
		WorldBorderMock thisBorder = this; // We can't use 'this' in the anonymous class below, so we need to store it
											// in a variable.
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				if ((size < newSize && distance > 0.001) || (size > newSize && distance < -0.001))
				{
					size += distancePerTick;
				}
				else
				{
					size = newSize;
					new WorldBorderBoundsChangeFinishEvent(world, thisBorder, oldSize, newSize, millis).callEvent();
					this.cancel();
				}
			}
		}.runTaskTimer(null, 1, 1);
	}

	@Override
	public @NotNull Location getCenter()
	{
		return new Location(this.world, this.centerX, 0, this.centerZ);
	}

	@Override
	public void setCenter(@NotNull Location location)
	{
		Preconditions.checkNotNull(location, "Location cannot be null");

		setCenter(location.getX(), location.getZ());
	}

	@Override
	public void setCenter(double x, double z)
	{
		x = Math.min(MAX_CENTER_VALUE, Math.max(-MAX_CENTER_VALUE, x));
		z = Math.min(MAX_CENTER_VALUE, Math.max(-MAX_CENTER_VALUE, z));

		WorldBorderCenterChangeEvent event = new WorldBorderCenterChangeEvent(this.world, this,
				new Location(this.world, this.centerX, 0, this.centerZ), new Location(this.world, x, 0, z));
		if (!event.callEvent())
			return;

		this.centerX = event.getNewCenter().getX();
		this.centerZ = event.getNewCenter().getZ();
	}

	@Override
	public double getDamageBuffer()
	{
		return this.damageBuffer;
	}

	@Override
	public void setDamageBuffer(double blocks)
	{
		this.damageBuffer = blocks;
	}

	@Override
	public double getDamageAmount()
	{
		return this.damageAmount;
	}

	@Override
	public void setDamageAmount(double damage)
	{
		this.damageAmount = damage;
	}

	@Override
	public int getWarningTime()
	{
		return this.warningTime;
	}

	@Override
	public void setWarningTime(int seconds)
	{
		this.warningTime = seconds;
	}

	@Override
	public int getWarningDistance()
	{
		return this.warningDistance;
	}

	@Override
	public void setWarningDistance(int distance)
	{
		this.warningDistance = distance;
	}

	@Override
	public boolean isInside(@NotNull Location location)
	{
		Preconditions.checkNotNull(location, "Location cannot be null");

		BoundingBox worldBorderBoundingBox = new BoundingBox(this.centerX - this.size, Double.MAX_VALUE,
				this.centerZ - this.size, this.centerX + this.size, Double.MAX_VALUE * -1, this.centerZ + size);

		return worldBorderBoundingBox.contains(location.toVector()) && location.getWorld() == this.world;
	}

	@Override
	public double getMaxSize()
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getMaxCenterCoordinate()
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
