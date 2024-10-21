package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.bukkit.entity.EntityType.ELDER_GUARDIAN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElderGuardianMockTest
{

	private ElderGuardianMock guardian;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		guardian = new ElderGuardianMock(server, java.util.UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(ELDER_GUARDIAN, guardian.getType());
	}

	@Test
	void testIsElder()
	{
		assertTrue(guardian.isElder());
	}

}
