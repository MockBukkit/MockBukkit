package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.SpawnCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class MonsterMockTest
{

	@MockBukkitInject
	private ZombieMock monster;

	@Test
	void testGetSpawnCategory()
	{
		assertEquals(SpawnCategory.MONSTER, monster.getSpawnCategory());
	}

}
