package org.mockbukkit.mockbukkit.boss;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class BossBarImplementationMockTest
{

	@MockBukkitInject
	ServerMock server;

	@Test
	void updatesBossBar()
	{
		PlayerMock player = server.addPlayer();
		BossBar bossBar = BossBar.bossBar(Component.text("hello world"), 1, BossBar.Color.RED, BossBar.Overlay.PROGRESS);

		bossBar.addViewer(player);

		assertTrue(player.getBossBars().contains(bossBar)); //Passed :D

		bossBar.viewers().forEach(viewer ->
		{
			if (viewer instanceof Player p)
			{
				bossBar.removeViewer(p);
			}
		});

		assertFalse(player.getBossBars().contains(bossBar)); //Failed :(
	}

}
