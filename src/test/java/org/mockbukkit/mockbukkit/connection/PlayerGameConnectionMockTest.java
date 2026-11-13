package org.mockbukkit.mockbukkit.connection;

import io.papermc.paper.connection.PlayerGameConnection;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class PlayerGameConnectionMockTest
{

	@MockBukkitInject
	private ServerMock server;

	@Test
	void getConnection_BelongsToThePlayer()
	{
		PlayerMock player = server.addPlayer();

		PlayerGameConnection connection = player.getConnection();

		assertSame(player, connection.getPlayer());
		assertSame(connection, player.getConnection());
		assertTrue(connection.isConnected());
		assertEquals(player.getAddress(), connection.getClientAddress());
	}

	@Test
	void disconnect_KicksThePlayer()
	{
		PlayerMock player = server.addPlayer();

		player.getConnection().disconnect(Component.text("Bye"));

		assertFalse(player.isOnline());
		assertFalse(player.getConnection().isConnected());
	}

}
