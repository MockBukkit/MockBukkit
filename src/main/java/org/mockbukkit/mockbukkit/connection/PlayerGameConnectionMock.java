package org.mockbukkit.mockbukkit.connection;

import com.destroystokyo.paper.ClientOption;
import com.google.common.base.Preconditions;
import io.papermc.paper.connection.PlayerGameConnection;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.ServerLinks;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Mock implementation of a {@link PlayerGameConnection}: the connection of a player in the game, which answers for the
 * player it belongs to.
 *
 * @see PlayerMock#getConnection()
 */
public class PlayerGameConnectionMock implements PlayerGameConnection
{

	private final @NotNull PlayerMock player;

	/**
	 * Constructs a new {@link PlayerGameConnectionMock} for the provided player.
	 *
	 * @param player The player of this connection.
	 */
	public PlayerGameConnectionMock(@NotNull PlayerMock player)
	{
		this.player = Preconditions.checkNotNull(player, "Player cannot be null");
	}

	@Override
	public @NotNull PlayerMock getPlayer()
	{
		return player;
	}

	@Override
	public void reenterConfiguration()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendReportDetails(@NotNull Map<String, String> details)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendLinks(@NotNull ServerLinks links)
	{
		player.sendLinks(links);
	}

	@Override
	public void transfer(@NotNull String host, int port)
	{
		player.transfer(host, port);
	}

	@Override
	public <T> @NotNull T getClientOption(@NotNull ClientOption<T> type)
	{
		return player.getClientOption(type);
	}

	@Override
	public @Nullable String getClientBrandName()
	{
		return player.getClientBrandName();
	}

	@Override
	public void storeCookie(@NotNull NamespacedKey key, byte @NotNull [] value)
	{
		player.storeCookie(key, value);
	}

	@Override
	public @NotNull CompletableFuture<byte[]> retrieveCookie(@NotNull NamespacedKey key)
	{
		return player.retrieveCookie(key);
	}

	@Override
	public void disconnect(@NotNull Component component)
	{
		player.kick(component);
	}

	@Override
	public boolean isConnected()
	{
		return player.isOnline();
	}

	@Override
	public boolean isTransferred()
	{
		return player.isTransferred();
	}

	@Override
	public @Nullable SocketAddress getAddress()
	{
		return player.getAddress();
	}

	@Override
	public @Nullable InetSocketAddress getClientAddress()
	{
		return player.getAddress();
	}

	@Override
	public @Nullable InetSocketAddress getVirtualHost()
	{
		return player.getVirtualHost();
	}

	@Override
	public @Nullable InetSocketAddress getHAProxyAddress()
	{
		return player.getHAProxyAddress();
	}

	@Override
	public void sendPluginMessage(@NotNull Plugin source, @NotNull String channel, byte @NotNull [] message)
	{
		player.sendPluginMessage(source, channel, message);
	}

	@Override
	public @NotNull Set<String> getListeningPluginChannels()
	{
		return player.getListeningPluginChannels();
	}

}
