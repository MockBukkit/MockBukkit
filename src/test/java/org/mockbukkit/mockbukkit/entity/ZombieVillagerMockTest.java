package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.entity.data.EntitySubType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class ZombieVillagerMockTest
{

	@MockBukkitInject
	private ZombieVillagerMock zombie;

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
