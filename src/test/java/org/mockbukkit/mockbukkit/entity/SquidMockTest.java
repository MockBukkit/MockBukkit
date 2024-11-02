package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Squid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class SquidMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private Squid squid;

	@BeforeEach
	void setUp()
	{
		squid = new SquidMock(server, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.SQUID, squid.getType());
	}

}
