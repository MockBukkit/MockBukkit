package org.mockbukkit.mockbukkit.matcher.entity;

import org.bukkit.Location;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.entity.EntityLocationMatcher.isInLocation;

@ExtendWith(MockBukkitExtension.class)
class EntityLocationMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	private PlayerMock entity;
	private Location location;

	@BeforeEach
	void setUp()
	{
		this.entity = serverMock.addPlayer();
		this.location = entity.getLocation().clone();
	}

	@Test
	void inLocation()
	{
		assertMatches(isInLocation(location, 0), entity);
	}

	@Test
	void notInLocation()
	{
		assertDoesNotMatch(isInLocation(location.add(1, 0, 0), 0), entity);
	}

	@Test
	void nullSafe()
	{
		testIsNullSafe();
	}

	@Test
	void unknownTypeSafe()
	{
		testCopesWithUnknownTypes();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return isInLocation(location, 0);
	}

}
