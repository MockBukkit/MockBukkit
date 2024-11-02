package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnderCrystalMockTest
{

	private ServerMock server;
	private EnderCrystalMock crystal;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		crystal = new EnderCrystalMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void isShowingBottom_GivenDefaultValue()
	{
		assertFalse(crystal.isShowingBottom());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void isShowingBottom_GivenUpdateInValue(boolean expectedValue)
	{
		crystal.setShowingBottom(expectedValue);
		boolean actual = crystal.isShowingBottom();
		assertEquals(expectedValue, actual);
	}

	@Test
	void getBeamTarget_GivenDefaultValue()
	{
		assertNull(crystal.getBeamTarget());
	}

	@Test
	void getBeamTarget_GivenLocationInTheSameWorld()
	{

		World world = new WorldMock();
		Location entityLocation = new Location(world, 0, 0, 0);
		crystal.setLocation(entityLocation);

		Location target = new Location(world, 10.25, 5, 23.75);
		crystal.setBeamTarget(target);

		@Nullable Location actual = crystal.getBeamTarget();
		assertNotNull(actual);
		assertEquals(10, actual.getX());
		assertEquals(5, actual.getY());
		assertEquals(23, actual.getZ());

		crystal.setBeamTarget(null);
		assertNull(crystal.getBeamTarget());
	}

	@Test
	void setBeamTarget_GivenLocationInDifferentWorld()
	{

		World worldA = new WorldMock();
		Location entityLocation = new Location(worldA, 0, 0, 0);
		crystal.setLocation(entityLocation);

		World worldB = new WorldMock();
		Location target = new Location(worldB, 0, 0, 0);

		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> crystal.setBeamTarget(target));

		assertEquals("Cannot set beam target location to different world", e.getMessage());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.END_CRYSTAL, crystal.getType());
	}

	@Test
	void getBoundingBox_GivenDefaultLocation()
	{
		BoundingBox actual = crystal.getBoundingBox();
		assertNotNull(actual);

		assertEquals(-1, actual.getMinX());
		assertEquals(0, actual.getMinY());
		assertEquals(-1, actual.getMinZ());

		assertEquals(1, actual.getMaxX());
		assertEquals(2, actual.getMaxY());
		assertEquals(1, actual.getMaxZ());
	}

	@Test
	void getBoundingBox_GivenCustomLocation()
	{
		crystal.teleport(new Location(crystal.getWorld(), 10, 5, 10));

		BoundingBox actual = crystal.getBoundingBox();
		assertNotNull(actual);

		assertEquals(9, actual.getMinX());
		assertEquals(5, actual.getMinY());
		assertEquals(9, actual.getMinZ());

		assertEquals(11, actual.getMaxX());
		assertEquals(7, actual.getMaxY());
		assertEquals(11, actual.getMaxZ());
	}

}
