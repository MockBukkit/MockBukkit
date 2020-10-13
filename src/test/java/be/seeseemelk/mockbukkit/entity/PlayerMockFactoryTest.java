package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class PlayerMockFactoryTest
{
    private ServerMock server;
    private PlayerMockFactory factory;

    @Before
    public void setUp()
    {
        server = MockBukkit.mock();
        factory = new PlayerMockFactory(server);
    }

    @After
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    public void createRandomPlayer_createsRandomPlayer()
    {
        Player player = factory.createRandomPlayer();
        assertNotNull(player.getName());
        assertNotNull(player.getUniqueId());
    }

    @Test
    public void createRandomPlayer_TwoInvocations_DifferentPlayers()
    {
        Player player1 = factory.createRandomPlayer();
        Player player2 = factory.createRandomPlayer();
        assertNotEquals("Two random players are the same", player1, player2);
    }

}
