package be.seeseemelk.mockbukkit.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;

class ExperienceOrbMockTest
{

	private ServerMock server;
	private World world;

	@BeforeEach
	public void setUp()
	{
		server = MockBukkit.mock();
		world = new WorldMock();
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

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
