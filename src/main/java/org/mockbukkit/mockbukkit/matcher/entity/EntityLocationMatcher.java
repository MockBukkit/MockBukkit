package org.mockbukkit.mockbukkit.matcher.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.entity.EntityMock;

import static org.hamcrest.Matchers.not;

public class EntityLocationMatcher extends TypeSafeMatcher<EntityMock>
{

	private final Location location;
	private final double radius;

	public EntityLocationMatcher(Location location, double radius)
	{
		this.location = location;
		this.radius = radius;
	}

	@Override
	protected boolean matchesSafely(EntityMock entityMock)
	{
		Location itemLocation = entityMock.getLocation();
		double distance = itemLocation.distance(location);
		return itemLocation.getWorld() == location.getWorld() && distance <= radius;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to be within the following location ").appendValue(location);
	}

	@Override
	protected void describeMismatchSafely(EntityMock entityMock, Description description)
	{
		description.appendText("was at location ").appendValue(entityMock.getLocation());
	}

	/**
	 *
	 * @param location The location required for a match
	 * @param maxDistance The radius away from the location which gives a match
	 * @return A matcher which matches with any entity within a radius of specified location
	 */
	public static @NotNull EntityLocationMatcher isInLocation(@NotNull Location location, double maxDistance)
	{
		Preconditions.checkNotNull(location);
		return new EntityLocationMatcher(location, maxDistance);
	}

	/**
	 *
	 * @param location The location required for no match
	 * @param maxDistance The radius away from the location which withdraws a match
	 * @return A matcher which matches with any entity outside a radius of specified location
	 */
	public static @NotNull Matcher<EntityMock> isNotInLocation(@NotNull Location location, double maxDistance)
	{
		return not(isInLocation(location, maxDistance));
	}

}
