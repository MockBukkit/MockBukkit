package org.mockbukkit.mockbukkit.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class MobMockTest
{

	@MockBukkitInject
	private MobMock mob;
	@MockBukkitInject
	private ServerMock server;

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
