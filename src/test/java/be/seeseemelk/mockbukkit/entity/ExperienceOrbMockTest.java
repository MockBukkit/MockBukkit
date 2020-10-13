package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExperienceOrbMockTest
{

    private ServerMock server;
    private World world;

    @Before
    public void setUp()
    {
        server = MockBukkit.mock();
        world = new WorldMock();
    }

    @After
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    public void testEntityType()
    {
        ExperienceOrb orb = new ExperienceOrbMock(server, UUID.randomUUID());
        assertEquals(EntityType.EXPERIENCE_ORB, orb.getType());
    }

    @Test
    public void testEntitySpawning()
    {
        Location location = new Location(world, 100, 100, 100);
        ExperienceOrb orb = (ExperienceOrb) world.spawnEntity(location, EntityType.EXPERIENCE_ORB);

        // Does our Firework exist in the correct World?
        assertTrue(world.getEntities().contains(orb));

        // 0 experience by default?
        assertEquals(0, orb.getExperience());

        // Is it at the right location?
        assertEquals(location, orb.getLocation());
    }

    @Test
    public void testSecondConstructor()
    {
        ExperienceOrb orb = new ExperienceOrbMock(server, UUID.randomUUID(), 10);
        assertEquals(10, orb.getExperience());
    }

    @Test
    public void testSetExperience()
    {
        ExperienceOrb orb = new ExperienceOrbMock(server, UUID.randomUUID(), 0);

        assertEquals(0, orb.getExperience());
        orb.setExperience(10);
        assertEquals(10, orb.getExperience());
    }
}
