package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GhastMockTest
{

	private GhastMock ghast;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		ghast = new GhastMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testIsChargingDefault()
	{
		assertFalse(ghast.isCharging());
	}

	@Test
	void testSetCharging()
	{
		ghast.setCharging(true);
		assertTrue(ghast.isCharging());
	}

	@Test
	void testGetExplosionPowerDefault()
	{
		assertEquals(1, ghast.getExplosionPower());
	}

	@Test
	void testSetExplosionPower()
	{
		ghast.setExplosionPower(10);
		assertEquals(10, ghast.getExplosionPower());
	}

	@Test
	void testSetExplosionPowerInvalidToSmall()
	{
		assertThrows(IllegalArgumentException.class, () -> ghast.setExplosionPower(-1));
	}

	@Test
	void testSetExplosionPowerInvalidToBig()
	{
		assertThrows(IllegalArgumentException.class, () -> ghast.setExplosionPower(128));
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.GHAST, ghast.getType());
	}

}
