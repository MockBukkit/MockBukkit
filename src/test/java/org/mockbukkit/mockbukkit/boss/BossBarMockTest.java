package org.mockbukkit.mockbukkit.boss;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BossBarMockTest
{

	private ServerMock server;
	private BossBar bar;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		bar = server.createBossBar("Test bossbar", BarColor.BLUE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC,
				BarFlag.CREATE_FOG);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testBossBarNotNull()
	{
		assertNotNull(bar);
	}

	@Test
	void testFlags()
	{
		assertTrue(bar.hasFlag(BarFlag.PLAY_BOSS_MUSIC));
		assertTrue(bar.hasFlag(BarFlag.CREATE_FOG));
		assertFalse(bar.hasFlag(BarFlag.DARKEN_SKY));

		bar.removeFlag(BarFlag.CREATE_FOG);

		assertFalse(bar.hasFlag(BarFlag.CREATE_FOG));
	}

	@Test
	void testSetProgress()
	{
		bar.setProgress(0.5);
		assertEquals(0.5, bar.getProgress(), 0);
	}

	@Test
	void testSetProgressNegative()
	{
		assertThrows(IllegalArgumentException.class, () -> bar.setProgress(-0.5));
	}

	@Test
	void testSetProgressOverOne()
	{
		assertThrows(IllegalArgumentException.class, () -> bar.setProgress(1.5));
	}

	@Test
	void testSetName()
	{
		assertEquals("Test bossbar", bar.getTitle());

		bar.setTitle("Hello world");
		assertEquals("Hello world", bar.getTitle());
	}

	@Test
	void testSetNameNull()
	{
		assertEquals("Test bossbar", bar.getTitle());
		bar.setTitle(null);
		assertEquals("", bar.getTitle());
	}

	@Test
	void testSetColor()
	{
		assertEquals(BarColor.BLUE, bar.getColor());

		bar.setColor(BarColor.PURPLE);
		assertEquals(BarColor.PURPLE, bar.getColor());
	}

	@Test
	void testBarStyle()
	{
		assertEquals(BarStyle.SOLID, bar.getStyle());

		bar.setStyle(BarStyle.SEGMENTED_10);
		assertEquals(BarStyle.SEGMENTED_10, bar.getStyle());
	}

	@Test
	void testBarPlayers()
	{
		assertEquals(0, bar.getPlayers().size());

		Player player1 = new PlayerMock(server, "TestPlayer");
		bar.addPlayer(player1);
		assertEquals(1, bar.getPlayers().size());
		assertEquals(player1, bar.getPlayers().get(0));

		bar.getPlayers().clear();
		assertEquals(1, bar.getPlayers().size());

		Player player2 = new PlayerMock(server, "TestPlayer2");
		bar.addPlayer(player2);
		assertEquals(2, bar.getPlayers().size());

		bar.removePlayer(player2);
		assertEquals(1, bar.getPlayers().size());

		bar.removeAll();
		assertEquals(0, bar.getPlayers().size());

	}

	@Test
	void testAddingPlayerNull()
	{
		assertThrows(NullPointerException.class, () -> bar.addPlayer(null));
	}

	@Test
	void testRemovingNullPlayer()
	{
		assertThrows(NullPointerException.class, () -> bar.removePlayer(null));
	}

	@Test
	void testIsVisible()
	{
		assertTrue(bar.isVisible());

		bar.setVisible(false);
		assertFalse(bar.isVisible());
	}

	@Test
	void testShow()
	{
		bar.setVisible(false);
		assertFalse(bar.isVisible());
		bar.show();
		assertTrue(bar.isVisible());
	}

	@Test
	void testHide()
	{
		bar.setVisible(true);
		assertTrue(bar.isVisible());
		bar.hide();
		assertFalse(bar.isVisible());
	}

}
