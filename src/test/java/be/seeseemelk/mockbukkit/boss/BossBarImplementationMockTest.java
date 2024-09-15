package be.seeseemelk.mockbukkit.boss;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
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
