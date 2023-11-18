package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.SpawnCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

@ExtendWith(MockBukkitExtension.class)
class MonsterMockTest
{

	@MockBukkitInject
	ServerMock server;
	ZombieMock monster;

	@BeforeEach
	void setUp() throws Exception
	{
		monster = new ZombieMock(server, UUID.randomUUID());
	}

	@Test
	void testGetSpawnCategory()
	{
		Assertions.assertEquals(SpawnCategory.MONSTER, monster.getSpawnCategory());
	}
}
