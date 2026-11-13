package org.mockbukkit.mockbukkit.entity;

import com.destroystokyo.paper.entity.Pathfinder;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Mock implementation of a {@link Pathfinder.PathResult}: the points a {@link PathfinderMock} found, which MockBukkit
 * does not walk.
 *
 * @param points         The points of the path, the last one being its destination.
 * @param nextPointIndex The index of the next point to reach.
 * @param reachable      Whether the destination can be reached.
 */
public record PathResultMock(@NotNull List<Location> points, int nextPointIndex, boolean reachable) implements Pathfinder.PathResult
{

	/**
	 * Constructs a new {@link PathResultMock}.
	 *
	 * @param points         The points of the path, the last one being its destination.
	 * @param nextPointIndex The index of the next point to reach.
	 * @param reachable      Whether the destination can be reached.
	 */
	public PathResultMock
	{
		Preconditions.checkNotNull(points, "Points cannot be null");
		Preconditions.checkArgument(!points.isEmpty(), "A path has at least one point");
		points = points.stream().map(Location::clone).toList();
	}

	@Override
	public @NotNull List<Location> getPoints()
	{
		return points.stream().map(Location::clone).toList();
	}

	@Override
	public int getNextPointIndex()
	{
		return nextPointIndex;
	}

	@Override
	public @Nullable Location getNextPoint()
	{
		return nextPointIndex < points.size() ? points.get(nextPointIndex).clone() : null;
	}

	@Override
	public @NotNull Location getFinalPoint()
	{
		return points.getLast().clone();
	}

	@Override
	public boolean canReachFinalPoint()
	{
		return reachable;
	}

}
