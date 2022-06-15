package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class MockPlayerListTest
{

	private ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testPlayerListDisconnectPlayer()
	{
		MockPlayerList playerList = server.getPlayerList();
		PlayerMock playerA = server.addPlayer();
		playerList.disconnectPlayer(playerA);

		assertFalse(playerList.getOnlinePlayers().contains(playerA));
	}

}
