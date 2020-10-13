package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.inventory.meta.FireworkMetaMock;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class FireworkMockTest
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
    public void testEntitySpawning()
    {
        Location location = new Location(world, 100, 100, 100);
        Firework firework = (Firework) world.spawnEntity(location, EntityType.FIREWORK);

        // Does our Firework exist in the correct World?
        assertTrue(world.getEntities().contains(firework));

        // Does it have a default FireworkMeta?
        assertNotNull(firework.getFireworkMeta());

        // Is it at the right location?
        assertEquals(location, firework.getLocation());
    }

    @Test
    public void testEntityType()
    {
        Firework firework = new FireworkMock(server, UUID.randomUUID());
        assertEquals(EntityType.FIREWORK, firework.getType());
    }

    @Test
    public void testSecondConstructor()
    {
        FireworkMeta meta = new FireworkMetaMock();
        meta.addEffect(FireworkEffect.builder().flicker(true).with(Type.BALL_LARGE).withColor(Color.BLUE).build());

        Firework firework = new FireworkMock(server, UUID.randomUUID(), meta);
        assertEquals(meta, firework.getFireworkMeta());
    }

    @Test
    public void testShotAtAngle()
    {
        Firework firework = new FireworkMock(server, UUID.randomUUID());

        // Default should be false
        assertFalse(firework.isShotAtAngle());

        firework.setShotAtAngle(true);
        assertTrue(firework.isShotAtAngle());
    }

    @Test
    public void testSetFireworkMeta()
    {
        FireworkMeta meta = new FireworkMetaMock();
        meta.addEffect(FireworkEffect.builder().flicker(true).with(Type.BALL_LARGE).withColor(Color.BLUE).build());

        Firework firework = new FireworkMock(server, UUID.randomUUID());

        assertNotEquals(meta, firework.getFireworkMeta());

        firework.setFireworkMeta(meta);
        assertEquals(meta, firework.getFireworkMeta());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetFireworkMetaNotNull()
    {
        Firework firework = new FireworkMock(server, UUID.randomUUID());
        firework.setFireworkMeta(null);
    }
}
