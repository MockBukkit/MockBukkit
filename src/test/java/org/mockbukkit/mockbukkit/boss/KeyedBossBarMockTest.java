package org.mockbukkit.mockbukkit.boss;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.collect.Iterators;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class KeyedBossBarMockTest
{
	@MockBukkitInject
	private ServerMock server;

	@Test
	void testKeyedBossBar()
	{
		KeyedBossBar bar = server.createBossBar(new NamespacedKey("mockbukkittest", "bossbar1"), "Boss bar 1",
				BarColor.WHITE, BarStyle.SEGMENTED_10);
		assertNotNull(bar);

		// Make sure it initalized correctly
		assertEquals("Boss bar 1", bar.getTitle());
		assertEquals(BarColor.WHITE, bar.getColor());
		assertEquals(BarStyle.SEGMENTED_10, bar.getStyle());
		assertEquals(1.0, bar.getProgress(), 0);

		assertEquals(1, Iterators.size(server.getBossBars()));

		assertEquals(bar, server.getBossBar(new NamespacedKey("mockbukkittest", "bossbar1")));
		assertEquals(bar, server.getBossBars().next());

		assertTrue(server.removeBossBar(new NamespacedKey("mockbukkittest", "bossbar1")));
		assertEquals(0, Iterators.size(server.getBossBars()));

		assertFalse(server.removeBossBar(new NamespacedKey("mockbukkittest", "nonexistantbossbar")));
	}

	@Test
	void testNullKey()
	{
		assertThrows(NullPointerException.class,
				() -> server.createBossBar(null, "Boss bar 1", BarColor.WHITE, BarStyle.SEGMENTED_10));
	}

	@Test
	void testGetKey()
	{
		KeyedBossBar bar = server.createBossBar(new NamespacedKey("mockbukkittest", "bossbar1"), "Boss bar 1",
				BarColor.WHITE, BarStyle.SEGMENTED_10);
		assertEquals(new NamespacedKey("mockbukkittest", "bossbar1"), bar.getKey());
	}

}
