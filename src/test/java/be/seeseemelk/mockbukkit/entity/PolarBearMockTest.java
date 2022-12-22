package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
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
	void testIsStandingDefault() {
		assertFalse(polarBear.isStanding());
	}

	@Test
	void testSetStanding() {
		polarBear.setStanding(true);
		assertTrue(polarBear.isStanding());
	}

	@Test
	void testCanBreed() {
		assertFalse(polarBear.canBreed());
	}

	@Test
	void testGetType() {
		assertEquals(EntityType.POLAR_BEAR, polarBear.getType());
	}
}
