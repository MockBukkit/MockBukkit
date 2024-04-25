package org.mockbukkit.mockbukkit.matcher.entity;

import org.bukkit.Location;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.entity.EntityMock;

public class EntityLocationMatcher extends TypeSafeMatcher<EntityMock>
{

	private final Location location;
	private final double maximumDistance;

	public EntityLocationMatcher(Location location, double maximumDistance)
	{
		this.location = location;
		this.maximumDistance = maximumDistance;
	}

	@Override
	protected boolean matchesSafely(EntityMock item)
	{
		Location itemLocation = item.getLocation();
		double distance = itemLocation.distance(location);
		return itemLocation.getWorld() == location.getWorld() && distance <= maximumDistance;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to be within the specified location");
	}

	public static EntityLocationMatcher isInLocation(Location location, double maximumDistance)
	{
		return new EntityLocationMatcher(location, maximumDistance);
	}

}
