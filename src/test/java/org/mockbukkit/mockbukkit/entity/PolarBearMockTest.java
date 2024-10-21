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
import static org.junit.jupiter.api.Assertions.assertTrue;

class PolarBearMockTest
{

	private ServerMock server;
	private PolarBearMock polarBear;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		polarBear = new PolarBearMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testIsStandingDefault()
	{
		assertFalse(polarBear.isStanding());
	}

	@Test
	void testSetStanding()
	{
		polarBear.setStanding(true);
		assertTrue(polarBear.isStanding());
	}

	@Test
	void testCanBreed()
	{
		assertFalse(polarBear.canBreed());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.POLAR_BEAR, polarBear.getType());
	}

	@Test
	void getEyeHeight_GivenDefaultPolarBear()
	{
		assertEquals(1.19D, polarBear.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyPolarBear()
	{
		polarBear.setBaby();
		assertEquals(0.595D, polarBear.getEyeHeight());
	}

}
