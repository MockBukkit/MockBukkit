package be.seeseemelk.mockbukkit.boss;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BossBarMockTest
{
	private ServerMock server;
	private BossBar bar;

	@Before
	public void setUp()
	{
		server = MockBukkit.mock();
		bar = server.createBossBar("Test bossbar", BarColor.BLUE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC,
		                           BarFlag.CREATE_FOG);
	}

	@After
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	public void testBossBarNotNull()
	{
		assertNotNull(bar);
	}


	@Test
	public void testFlags()
	{
		assertTrue(bar.hasFlag(BarFlag.PLAY_BOSS_MUSIC));
		assertTrue(bar.hasFlag(BarFlag.CREATE_FOG));
		assertFalse(bar.hasFlag(BarFlag.DARKEN_SKY));

		bar.removeFlag(BarFlag.CREATE_FOG);

		assertFalse(bar.hasFlag(BarFlag.CREATE_FOG));
	}

	@Test
	public void testSetProgress()
	{
		bar.setProgress(0.5);
		assertEquals(0.5, bar.getProgress(), 0);
	}

	@Test
	public void testSetName()
	{
		assertEquals("Test bossbar", bar.getTitle());

		bar.setTitle("Hello world");
		assertEquals("Hello world", bar.getTitle());
	}

	@Test
	public void testSetColor()
	{
		assertEquals(BarColor.BLUE, bar.getColor());

		bar.setColor(BarColor.PURPLE);
		assertEquals(BarColor.PURPLE, bar.getColor());
	}

	@Test
	public void testBarStyle()
	{
		assertEquals(BarStyle.SOLID, bar.getStyle());

		bar.setStyle(BarStyle.SEGMENTED_10);
		assertEquals(BarStyle.SEGMENTED_10, bar.getStyle());
	}


	@Test
	public void testBarPlayers()
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

	@Test(expected = IllegalArgumentException.class)
	public void testAddingPlayerNull()
	{
		bar.addPlayer(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemovingNullPlayer()
	{
		bar.removePlayer(null);
	}

}
