package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BatMockTest
{

	private BatMock bat;

	@BeforeEach
	void setup()
	{
		ServerMock server = MockBukkit.mock();
		bat = new BatMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testIsAwakeDefault()
	{
		assertTrue(bat.isAwake());
	}

	@Test
	void testSetAwake()
	{
		bat.setAwake(false);
		assertFalse(bat.isAwake());
	}

	@Test
	void testEntityType()
	{
		assertEquals(EntityType.BAT, bat.getType());
	}

	@Test
	void testTargetLocationDefault()
	{
		assertNull(bat.getTargetLocation());
	}

	@Test
	void testTargetLocation()
	{
		bat.setTargetLocation(bat.getLocation());
		assertEquals(bat.getLocation(), bat.getTargetLocation());
	}

	@Test
	void testSetTargetLocationNull()
	{
		bat.setTargetLocation(new Location(null, 0, 0, 0));
		assertNotNull(bat.getTargetLocation());
		bat.setTargetLocation(null);
		assertNull(bat.getTargetLocation());
	}

}
