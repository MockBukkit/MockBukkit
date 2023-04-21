package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MobMockTest
{

	private MobMock mob;
	ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		mob = new SimpleMobMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetTargetDefault()
	{
		assertNull(mob.getTarget());
	}

	@Test
	void testSetTarget()
	{
		PlayerMock player = server.addPlayer();
		mob.setTarget(player);
		assertEquals(player, mob.getTarget());
	}

}
