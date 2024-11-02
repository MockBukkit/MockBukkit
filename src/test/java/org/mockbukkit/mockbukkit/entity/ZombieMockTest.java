package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.data.EntitySubType;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.util.BoundingBox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ZombieMockTest
{

	private ServerMock server;
	private ZombieMock zombie;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		zombie = new ZombieMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.ZOMBIE, zombie.getType());
	}

	@Test
	void getSubType_GivenDefaultZombie()
	{
		assertEquals(EntitySubType.DEFAULT, zombie.getSubType());
	}

	@Test
	void getSubType_GivenBabyZombie()
	{
		zombie.setBaby();
		assertEquals(EntitySubType.BABY, zombie.getSubType());
	}

	@Test
	void getEyeHeight_GivenDefaultZombie()
	{
		assertEquals(1.74D, zombie.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyZombie()
	{
		zombie.setBaby();
		assertEquals(0.87D, zombie.getEyeHeight());
	}

	@Test
	void getBoundingBox_GivenDefaultLocation()
	{
		BoundingBox actual = zombie.getBoundingBox();
		assertNotNull(actual);

		assertEquals(-0.3, actual.getMinX());
		assertEquals(0, actual.getMinY());
		assertEquals(-0.3, actual.getMinZ());

		assertEquals(0.3, actual.getMaxX());
		assertEquals(1.95, actual.getMaxY());
		assertEquals(0.3, actual.getMaxZ());
	}

	@Test
	void getBoundingBox_GivenCustomLocation()
	{
		zombie.teleport(new Location(zombie.getWorld(), 10, 5, 10));

		BoundingBox actual = zombie.getBoundingBox();
		assertNotNull(actual);

		assertEquals(9.7, actual.getMinX());
		assertEquals(5, actual.getMinY());
		assertEquals(9.7, actual.getMinZ());

		assertEquals(10.3, actual.getMaxX());
		assertEquals(6.95, actual.getMaxY());
		assertEquals(10.3, actual.getMaxZ());
	}

}
