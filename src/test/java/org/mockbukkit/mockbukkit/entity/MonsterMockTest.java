package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.SpawnCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class MonsterMockTest
{

	@MockBukkitInject
	ServerMock server;
	ZombieMock monster;

	@BeforeEach
	void setUp()
	{
		monster = new ZombieMock(server, UUID.randomUUID());
	}

	@Test
	void testGetSpawnCategory()
	{
		assertEquals(SpawnCategory.MONSTER, monster.getSpawnCategory());
	}

}
