package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.entity.OfflinePlayerMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SkullMetaMockTest
{

    @Before
    public void setUp()
    {
        MockBukkit.mock();
    }

    @After
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    public void testDefaultNoOwner()
    {
        SkullMetaMock meta = new SkullMetaMock();

        assertFalse(meta.hasOwner());
        assertNull(meta.getOwner());
        assertNull(meta.getOwningPlayer());
    }

    @Test
    public void testSetOwner()
    {
        SkullMetaMock meta = new SkullMetaMock();

        assertTrue(meta.setOwner("TheBusyBiscuit"));
        assertTrue(meta.hasOwner());
        assertEquals("TheBusyBiscuit", meta.getOwner());
        assertEquals("TheBusyBiscuit", meta.getOwningPlayer().getName());
    }

    @Test
    public void testSetOwningPlayer()
    {
        SkullMetaMock meta = new SkullMetaMock();

        assertTrue(meta.setOwningPlayer(new OfflinePlayerMock("TheBusyBiscuit")));
        assertTrue(meta.hasOwner());
        assertEquals("TheBusyBiscuit", meta.getOwner());
        assertEquals("TheBusyBiscuit", meta.getOwningPlayer().getName());
    }

    @Test
    public void testClone()
    {
        SkullMetaMock meta = new SkullMetaMock();

        assertTrue(meta.setOwner("TheBusyBiscuit"));

        SkullMetaMock clone = meta.clone();
        assertEquals(meta, clone);
        assertEquals("TheBusyBiscuit", clone.getOwner());
    }

}
