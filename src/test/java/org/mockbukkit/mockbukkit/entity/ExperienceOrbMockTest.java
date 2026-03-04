package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ExperienceOrbMockTest
{

	@MockBukkitInject
	private ServerMock server;
	@MockBukkitInject
	private World world;

	@Test
	void testEntityType()
	{
		ExperienceOrb orb = new ExperienceOrbMock(server, UUID.randomUUID());
		assertEquals(EntityType.EXPERIENCE_ORB, orb.getType());
	}

	@Test
	void testEntitySpawning()
	{
		Location location = new Location(world, 100, 100, 100);
		ExperienceOrb orb = (ExperienceOrb) world.spawnEntity(location, EntityType.EXPERIENCE_ORB);

		// Does our orb exist in the correct World?
		assertTrue(world.getEntities().contains(orb));

		// 0 experience by default?
		assertEquals(0, orb.getExperience());

		// Is it at the right location?
		assertEquals(location, orb.getLocation());
	}

	@Test
	void testSecondConstructor()
	{
		ExperienceOrb orb = new ExperienceOrbMock(server, UUID.randomUUID(), 10);
		assertEquals(10, orb.getExperience());
	}

	@Test
	void testSetExperience()
	{
		ExperienceOrb orb = new ExperienceOrbMock(server, UUID.randomUUID(), 0);

		assertEquals(0, orb.getExperience());
		orb.setExperience(10);
		assertEquals(10, orb.getExperience());
	}

}
