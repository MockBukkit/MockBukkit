package org.mockbukkit.mockbukkit.entity;

import com.destroystokyo.paper.entity.Pathfinder;
import org.bukkit.Location;
import org.bukkit.entity.Zombie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class PathfinderMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private WorldMock world;
	private Zombie zombie;

	@BeforeEach
	void setUp()
	{
		world = server.addSimpleWorld("world");
		zombie = world.spawn(new Location(world, 0, 64, 0), Zombie.class);
	}

	@Test
	void testGetPathfinder()
	{
		Pathfinder pathfinder = zombie.getPathfinder();

		assertInstanceOf(PathfinderMock.class, pathfinder);
		assertSame(zombie, pathfinder.getEntity());
		assertSame(pathfinder, zombie.getPathfinder());
		assertFalse(pathfinder.hasPath());
	}

	@Test
	void testFindPath()
	{
		Pathfinder.PathResult path = zombie.getPathfinder().findPath(new Location(world, 10.5, 64, -3.2));

		assertNotNull(path);
		assertEquals(new Location(world, 10, 64, -4), path.getFinalPoint());
		assertEquals(path.getFinalPoint(), path.getNextPoint());
		assertTrue(path.canReachFinalPoint());
		assertFalse(zombie.getPathfinder().hasPath());
	}

	@Test
	void testFindPath_OtherWorld()
	{
		WorldMock other = server.addSimpleWorld("other");

		assertNull(zombie.getPathfinder().findPath(new Location(other, 10, 64, 0)));
	}

	@Test
	void testMoveTo()
	{
		Pathfinder pathfinder = zombie.getPathfinder();
		Location destination = new Location(world, 5, 64, 5);

		assertTrue(pathfinder.moveTo(destination, 1.5));

		assertTrue(pathfinder.hasPath());
		assertEquals(destination, pathfinder.getCurrentPath().getFinalPoint());
		assertEquals(1.5, ((PathfinderMock) pathfinder).getSpeed());
		assertEquals(new Location(world, 0, 64, 0), zombie.getLocation());
	}

	@Test
	void testMoveTo_Entity()
	{
		Zombie other = world.spawn(new Location(world, 3, 64, 8), Zombie.class);

		assertTrue(zombie.getPathfinder().moveTo(other));

		assertEquals(new Location(world, 3, 64, 8), zombie.getPathfinder().getCurrentPath().getFinalPoint());
	}

	@Test
	void testStopPathfinding()
	{
		PathfinderMock pathfinder = (PathfinderMock) zombie.getPathfinder();
		pathfinder.moveTo(new Location(world, 5, 64, 5));

		pathfinder.stopPathfinding();

		assertFalse(pathfinder.hasPath());
		assertNull(pathfinder.getCurrentPath());
		assertEquals(0, pathfinder.getSpeed());
	}

	@Test
	void testDoorsAndFloat()
	{
		Pathfinder pathfinder = zombie.getPathfinder();

		assertFalse(pathfinder.canOpenDoors());
		assertTrue(pathfinder.canPassDoors());
		assertFalse(pathfinder.canFloat());

		pathfinder.setCanOpenDoors(true);
		pathfinder.setCanPassDoors(false);
		pathfinder.setCanFloat(true);

		assertTrue(pathfinder.canOpenDoors());
		assertFalse(pathfinder.canPassDoors());
		assertTrue(pathfinder.canFloat());
	}

}
