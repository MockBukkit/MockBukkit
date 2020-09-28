package be.seeseemelk.mockbukkit.boss;

import org.bukkit.boss.BossBar;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BossBarMockTest {
    private ServerMock server;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testCreateBossBar() {
        BossBar bar = server.createBossBar("Test bossbar", BarColor.BLUE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC,
                BarFlag.CREATE_FOG);
        assertNotNull(bar);

        assertEquals("Test bossbar", bar.getTitle());
        assertEquals(BarColor.BLUE, bar.getColor());
        assertEquals(BarStyle.SOLID, bar.getStyle());

        assertTrue(bar.hasFlag(BarFlag.PLAY_BOSS_MUSIC));
        assertTrue(bar.hasFlag(BarFlag.CREATE_FOG));
        assertFalse(bar.hasFlag(BarFlag.DARKEN_SKY));

    }
}
