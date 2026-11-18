package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class PathResultMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private WorldMock world;

	@BeforeEach
	void setUp()
	{
		world = server.addSimpleWorld("world");
	}

	@Test
	void testGetPoints()
	{
		Location first = new Location(world, 0, 64, 0);
		Location last = new Location(world, 5, 64, 0);
		List<Location> points = new ArrayList<>(List.of(first, last));
		PathResultMock path = new PathResultMock(points, 1, true);

		points.clear();
		first.setX(42);

		assertEquals(List.of(new Location(world, 0, 64, 0), last), path.getPoints());
		assertNotSame(path.getPoints().getFirst(), path.getPoints().getFirst());
		assertEquals(1, path.getNextPointIndex());
		assertEquals(last, path.getNextPoint());
	}

	@Test
	void testGetNextPoint_PastTheEnd()
	{
		Location point = new Location(world, 0, 64, 0);
		PathResultMock path = new PathResultMock(List.of(point), 1, false);

		assertNull(path.getNextPoint());
		assertEquals(point, path.getFinalPoint());
		assertFalse(path.canReachFinalPoint());
	}

	@Test
	void testNoPoints()
	{
		List<Location> points = List.of();

		assertThrows(IllegalArgumentException.class, () -> new PathResultMock(points, 0, true));
	}

}
