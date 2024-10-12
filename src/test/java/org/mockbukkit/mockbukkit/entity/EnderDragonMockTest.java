package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class EnderDragonMockTest
{
	@MockBukkitInject
	private ServerMock server;
	private EnderDragonMock enderDragon;

	@BeforeEach
	void setUp()
	{
		enderDragon = new EnderDragonMock(server, UUID.randomUUID());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.ENDER_DRAGON, enderDragon.getType());
	}

	@Test
	void getHeight()
	{
		assertEquals(8.0D, enderDragon.getHeight());
	}

	@Test
	void getWidth()
	{
		assertEquals(16.0D, enderDragon.getWidth());
	}

	@Test
	void getEyeHeight()
	{
		assertEquals(6.8D, enderDragon.getEyeHeight());
	}
}
