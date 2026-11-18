package org.mockbukkit.mockbukkit.connection;

import com.destroystokyo.paper.ClientOption;
import io.papermc.paper.connection.PlayerGameConnection;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.ServerLinks;
import org.bukkit.plugin.messaging.ChannelNotRegisteredException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.plugin.PluginMock;

import java.net.InetSocketAddress;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
	void addresses_AreThePlayers()
	{
		PlayerMock player = server.addPlayer();
		InetSocketAddress proxy = InetSocketAddress.createUnresolved("localhost", 8888);
		player.setHaProxyAddress(proxy);

		PlayerGameConnection connection = player.getConnection();

		assertEquals(player.getAddress(), connection.getAddress());
		assertEquals(proxy, connection.getHAProxyAddress());
		assertEquals(player.getListeningPluginChannels(), connection.getListeningPluginChannels());
	}

	@Test
	void sendPluginMessage_IsValidatedAsThePlayers()
	{
		PlayerMock player = server.addPlayer();
		PluginMock plugin = MockBukkit.createMockPlugin();
		server.getMessenger().registerOutgoingPluginChannel(plugin, "test:channel");
		PlayerGameConnection connection = player.getConnection();
		byte[] message = { 1 };

		assertDoesNotThrow(() -> connection.sendPluginMessage(plugin, "test:channel", message));
		assertThrows(ChannelNotRegisteredException.class, () -> connection.sendPluginMessage(plugin, "test:unregistered", message));
	}

	@Test
	void unimplementedByThePlayer_Throws()
	{
		PlayerGameConnection connection = server.addPlayer().getConnection();
		NamespacedKey key = new NamespacedKey("test", "cookie");
		ServerLinks links = Bukkit.getServerLinks();

		assertThrows(UnimplementedOperationException.class, connection::reenterConfiguration);
		assertThrows(UnimplementedOperationException.class, () -> connection.sendReportDetails(Map.of()));
		assertThrows(UnimplementedOperationException.class, () -> connection.sendLinks(links));
		assertThrows(UnimplementedOperationException.class, () -> connection.transfer("localhost", 25565));
		assertThrows(UnimplementedOperationException.class, () -> connection.getClientOption(ClientOption.LOCALE));
		assertThrows(UnimplementedOperationException.class, connection::getClientBrandName);
		assertThrows(UnimplementedOperationException.class, () -> connection.storeCookie(key, new byte[0]));
		assertThrows(UnimplementedOperationException.class, () -> connection.retrieveCookie(key));
		assertThrows(UnimplementedOperationException.class, connection::isTransferred);
		assertThrows(UnimplementedOperationException.class, connection::getVirtualHost);
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
