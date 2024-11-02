package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MagmaCube;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class MagmaCubeMockTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	private MagmaCube magmaCubeMock;

	@BeforeEach
	void setUp()
	{
		magmaCubeMock = new MagmaCubeMock(serverMock, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.MAGMA_CUBE, magmaCubeMock.getType());
	}

}
