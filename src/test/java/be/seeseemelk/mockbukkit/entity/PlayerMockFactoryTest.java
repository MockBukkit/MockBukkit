package be.seeseemelk.mockbukkit.entity;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

class PlayerMockFactoryTest
{
	private ServerMock server;
	private PlayerMockFactory factory;

	@BeforeEach
	public void setUp()
	{
		server = MockBukkit.mock();
		factory = new PlayerMockFactory(server);
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
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
