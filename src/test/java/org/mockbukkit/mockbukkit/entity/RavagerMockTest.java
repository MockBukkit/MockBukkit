package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.util.BoundingBox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RavagerMockTest
{

	private ServerMock server;
	private RavagerMock ravager;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		ravager = new RavagerMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getAttackTicks_GivenDefaultValue()
	{
		assertEquals(0, ravager.getAttackTicks());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100})
	void getAttackTicks_GivenPossibleValues(int ticks)
	{
		ravager.setAttackTicks(ticks);

		assertEquals(ticks, ravager.getAttackTicks());
	}

	@Test
	void getStunnedTicks_GivenDefaultValue()
	{
		assertEquals(0, ravager.getStunnedTicks());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100})
	void getStunnedTicks_GivenPossibleValues(int ticks)
	{
		ravager.setStunnedTicks(ticks);

		assertEquals(ticks, ravager.getStunnedTicks());
	}

	@Test
	void getRoarTicks_GivenDefaultValue()
	{
		assertEquals(0, ravager.getRoarTicks());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100})
	void getRoarTicks_GivenPossibleValues(int ticks)
	{
		ravager.setRoarTicks(ticks);

		assertEquals(ticks, ravager.getRoarTicks());
	}

	@Test
	void getCelebrationSound()
	{
		assertEquals(Sound.ENTITY_RAVAGER_CELEBRATE, ravager.getCelebrationSound());
	}

	@Test
	void getEyeHeight_GivenDefaultValue()
	{
		assertEquals(1.87D, ravager.getEyeHeight());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.RAVAGER, ravager.getType());
	}

	@Test
	void getBoundingBox_GivenDefaultLocation()
	{
		BoundingBox actual = ravager.getBoundingBox();
		assertNotNull(actual);

		assertEquals(-0.975, actual.getMinX());
		assertEquals(0, actual.getMinY());
		assertEquals(-0.975, actual.getMinZ());

		assertEquals(0.975, actual.getMaxX());
		assertEquals(2.2, actual.getMaxY());
		assertEquals(0.975, actual.getMaxZ());
	}

	@Test
	void getBoundingBox_GivenCustomLocation()
	{
		ravager.teleport(new Location(ravager.getWorld(), 10, 5, 10));

		BoundingBox actual = ravager.getBoundingBox();
		assertNotNull(actual);

		assertEquals(9.025, actual.getMinX());
		assertEquals(5, actual.getMinY());
		assertEquals(9.025, actual.getMinZ());

		assertEquals(10.975, actual.getMaxX());
		assertEquals(7.2, actual.getMaxY());
		assertEquals(10.975, actual.getMaxZ());
	}

}
