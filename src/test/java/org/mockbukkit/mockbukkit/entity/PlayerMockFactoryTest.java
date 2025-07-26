package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class PlayerMockFactoryTest
{

	@MockBukkitInject
	private ServerMock server;

	private PlayerMockFactory factory;

	@BeforeEach
	void setUp()
	{
		factory = new PlayerMockFactory(server);
	}

	@Test
	void createRandomPlayer_createsRandomPlayer()
	{
		Player player = factory.createRandomPlayer();
		assertNotNull(player.getName());
		assertNotNull(player.getUniqueId());
	}

	@Test
	void createRandomPlayer_TwoInvocations_DifferentPlayers()
	{
		Player player1 = factory.createRandomPlayer();
		Player player2 = factory.createRandomPlayer();
		assertNotEquals(player1, player2, "Two random players are the same");
	}

}
