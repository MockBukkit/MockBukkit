package org.mockbukkit.mockbukkit.entity;

import com.destroystokyo.paper.entity.Pathfinder;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * Mock implementation of a {@link Pathfinder}. MockBukkit has no navigation: a path found is the straight one to its
 * destination, and a mob told to move keeps its path and speed without walking it, for the test to read.
 *
 * @see MobMock#getPathfinder()
 */
public class PathfinderMock implements Pathfinder
{

	private final @NotNull Mob entity;
	private @Nullable PathResult currentPath;
	private double speed;
	private boolean canOpenDoors;
	private boolean canPassDoors = true;
	private boolean canFloat;

	/**
	 * Constructs a new {@link PathfinderMock} for the provided mob.
	 *
	 * @param entity The mob this pathfinder moves.
	 */
	public PathfinderMock(@NotNull Mob entity)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		this.entity = entity;
	}

	@Override
	public @NotNull Mob getEntity()
	{
		return entity;
	}

	@Override
	public void stopPathfinding()
	{
		this.currentPath = null;
		this.speed = 0;
	}

	@Override
	public boolean hasPath()
	{
		return currentPath != null;
	}

	@Override
	public @Nullable PathResult getCurrentPath()
	{
		return currentPath;
	}

	/**
	 * The speed the mob was last told to move at; {@code 0} when it has no path.
	 *
	 * @return The speed of the current path.
	 */
	public double getSpeed()
	{
		return speed;
	}

	@Override
	public @Nullable PathResult findPath(@NotNull Location location, int radius)
	{
		Preconditions.checkNotNull(location, "Location cannot be null");
		if (!Objects.equals(location.getWorld(), entity.getWorld()))
		{
			return null;
		}
		return new PathResultMock(List.of(location.toBlockLocation()), 0, true);
	}

	@Override
	public @Nullable PathResult findPath(@NotNull Entity target, int radius)
	{
		Preconditions.checkNotNull(target, "Target cannot be null");
		return findPath(target.getLocation(), radius);
	}

	@Override
	public boolean moveTo(@NotNull PathResult path, double speed)
	{
		Preconditions.checkNotNull(path, "Path cannot be null");
		this.currentPath = path;
		this.speed = speed;
		return true;
	}

	@Override
	public boolean canOpenDoors()
	{
		return canOpenDoors;
	}

	@Override
	public void setCanOpenDoors(boolean canOpenDoors)
	{
		this.canOpenDoors = canOpenDoors;
	}

	@Override
	public boolean canPassDoors()
	{
		return canPassDoors;
	}

	@Override
	public void setCanPassDoors(boolean canPassDoors)
	{
		this.canPassDoors = canPassDoors;
	}

	@Override
	public boolean canFloat()
	{
		return canFloat;
	}

	@Override
	public void setCanFloat(boolean canFloat)
	{
		this.canFloat = canFloat;
	}

}
