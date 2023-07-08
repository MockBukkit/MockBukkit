package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
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
