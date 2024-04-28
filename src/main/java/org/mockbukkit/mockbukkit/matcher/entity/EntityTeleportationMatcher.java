package org.mockbukkit.mockbukkit.matcher.entity;

import org.bukkit.Location;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.entity.EntityMock;

public class EntityTeleportationMatcher extends TypeSafeMatcher<EntityMock>
{

	private final double maximumDistance;
	private final Location location;
	private boolean hasTeleported;

	public EntityTeleportationMatcher(Location location, double maximumDistance)
	{
		this.location = location;
		this.maximumDistance = maximumDistance;
	}

	@Override
	protected boolean matchesSafely(EntityMock item)
	{
		if (location != null)
		{
			Location itemLocation = item.getLocation();
			double distance = itemLocation.distance(location);
			if (itemLocation.getWorld() != location.getWorld() || distance > maximumDistance)
			{
				return false;
			}
		}
		this.hasTeleported = item.hasTeleported();
		item.clearTeleported();
		return hasTeleported;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have teleported to following location ").appendValue(location);
	}

	@Override
	public void describeMismatchSafely(EntityMock entityMock, Description description)
	{
		description.appendText("was at location ").appendValue(entityMock.getLocation());
		if (hasTeleported)
		{
			description.appendText(" and has teleported");
		}
		else
		{
			description.appendText(" and has not teleported");
		}
	}

	public static EntityTeleportationMatcher hasTeleported()
	{
		return hasTeleported(null);
	}

	public static EntityTeleportationMatcher hasTeleported(Location location)
	{
		return hasTeleported(location, 0.0);
	}

	public static EntityTeleportationMatcher hasTeleported(Location location, double maximumDistance)
	{
		return new EntityTeleportationMatcher(location, maximumDistance);
	}

}
