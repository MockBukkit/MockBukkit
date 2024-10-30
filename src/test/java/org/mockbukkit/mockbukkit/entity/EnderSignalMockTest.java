package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderSignal;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockBukkitExtension.class)
class EnderSignalMockTest
{

    @MockBukkitInject
    private ServerMock server;
    private EnderSignal enderSignal;
    private WorldMock world;

    @BeforeEach
    void setUp()
    {
        world = server.addSimpleWorld("world");
        enderSignal = new EnderSignalMock(server, UUID.randomUUID());
        enderSignal.teleport(new Location(world, 0, 1, 0));
    }

    @Test
    void testGetTargetLocationDefault()
    {
        assertThrows(IllegalStateException.class, () -> enderSignal.getTargetLocation());
    }

    @Test
    void testSetTargetLocation()
    {
        Location location = new Location(world, 0, 0, 0);
        enderSignal.setTargetLocation(location);
        assertEquals(location, enderSignal.getTargetLocation());
    }

    @Test
    void testSetTargetLocationAcrossWorlds()
    {
        Location location = new Location(server.addSimpleWorld("world2"), 0, 0, 0);
        assertThrows(IllegalArgumentException.class, () -> enderSignal.setTargetLocation(location));
    }

    @Test
    void testSetTargetLocationNull()
    {
        assertThrows(IllegalArgumentException.class, () -> enderSignal.setTargetLocation(null));
    }

    @Test
    void testGetDropItemDefault()
    {
        assertTrue(enderSignal.getDropItem());
    }

    @Test
    void testSetDropItem()
    {
        enderSignal.setDropItem(false);
        assertFalse(enderSignal.getDropItem());
    }

    @Test
    void testGetItemDefault()
    {
        assertEquals(Material.ENDER_EYE, enderSignal.getItem().getType());
    }

    @Test
    void testSetItem()
    {
		var item = new ItemStack(Material.DIAMOND);
        enderSignal.setItem(item);
        assertEquals(Material.DIAMOND, enderSignal.getItem().getType());
		assertNotSame(item, enderSignal.getItem());
    }

    @Test
    void testSetItemNull()
    {
        enderSignal.setItem(new ItemStack(Material.DIAMOND));
        assertEquals(Material.DIAMOND, enderSignal.getItem().getType());
        enderSignal.setItem(null);
        assertEquals(Material.ENDER_EYE, enderSignal.getItem().getType());
    }

    @Test
    void testGetDespawnTimer()
    {
        assertEquals(0,enderSignal.getDespawnTimer());
    }

    @Test
    void testSetDespawnTimer()
    {
        enderSignal.setDespawnTimer(5);
        assertEquals(5,enderSignal.getDespawnTimer());
    }

}
