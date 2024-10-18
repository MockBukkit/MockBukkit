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

class WitherSkullMockTest
{

	private WitherSkullMock witherSkull;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		witherSkull = new WitherSkullMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetChargedDefault()
	{
		assertFalse(witherSkull.isCharged());
	}

	@Test
	void testSetCharged()
	{
		witherSkull.setCharged(true);
		assertTrue(witherSkull.isCharged());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.WITHER_SKULL, witherSkull.getType());
	}

}
