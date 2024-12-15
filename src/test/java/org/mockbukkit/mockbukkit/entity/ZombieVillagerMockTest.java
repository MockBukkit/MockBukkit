package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.data.EntitySubType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ZombieVillagerMockTest
{

	private ZombieVillagerMock zombie;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		zombie = new ZombieVillagerMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.ZOMBIE_VILLAGER, zombie.getType());
	}

	@Test
	void getSubType_GivenDefaultZombie()
	{
		assertEquals(EntitySubType.DEFAULT, zombie.getSubType());
	}

	@Test
	void getVillagerType()
	{
		assertEquals(Villager.Type.PLAINS, zombie.getVillagerType());
	}

	@Test
	void setVillagerType()
	{
		zombie.setVillagerType(Villager.Type.JUNGLE);
		assertEquals(Villager.Type.JUNGLE, zombie.getVillagerType());
	}

	@Test
	void getConversionPlayer()
	{
		assertNull(zombie.getConversionPlayer());
	}

	@Test
	void getVillagerProfession()
	{
		assertEquals(Villager.Profession.NONE, zombie.getVillagerProfession());
	}

	@Test
	void setVillagerProfessions()
	{
		zombie.setVillagerProfession(Villager.Profession.BUTCHER);
		assertEquals(Villager.Profession.BUTCHER, zombie.getVillagerProfession());
	}
}
