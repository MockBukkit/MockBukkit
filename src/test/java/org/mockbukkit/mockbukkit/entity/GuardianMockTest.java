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

class GuardianMockTest
{

	private GuardianMock guardian;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		guardian = new GuardianMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testHasLaserDefault()
	{
		assertFalse(guardian.hasLaser());
	}

	@Test
	void testSetLaserWithNullTargetThrows()
	{
		assertFalse(guardian.setLaser(true));
	}

	@Test
	void testSetLaser()
	{
		guardian.setTarget(new PlayerMock(MockBukkit.getMock(), "player"));
		assertTrue(guardian.setLaser(true));
	}

	@Test
	void testIsElderDefault()
	{
		assertFalse(guardian.isElder());
	}

	@Test
	void testSetElder()
	{
		assertThrows(UnsupportedOperationException.class, () -> guardian.setElder(true));
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.GUARDIAN, guardian.getType());
	}

}
