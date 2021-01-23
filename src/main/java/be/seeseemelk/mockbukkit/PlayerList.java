package be.seeseemelk.mockbukkit;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.BanList;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import be.seeseemelk.mockbukkit.entity.PlayerMock;

/**
 * Replica of the Bukkit internal PlayerList and CraftPlayerList implementation
 * 
 * @author seeseemelk
 * @author TheBusyBiscuit
 * 
 */
public class PlayerList
{
	private int maxPlayers = Integer.MAX_VALUE;

	private final List<PlayerMock> onlinePlayers = new CopyOnWriteArrayList<>();
	private final Set<OfflinePlayer> offlinePlayers = Collections.synchronizedSet(new HashSet<>());

	private BanList ipBans = new MockBanList();
	private BanList profileBans = new MockBanList();

	public void setMaxPlayers(int maxPlayers)
	{
		// TODO: The maxPlayers setting is currently not enforced.
		this.maxPlayers = maxPlayers;
	}

	public int getMaxPlayers()
	{
		return this.maxPlayers;
	}

	@NotNull
	public BanList getIPBans()
	{
		return this.ipBans;
	}

	@NotNull
	public BanList getProfileBans()
	{
		return this.profileBans;
	}

	public void addPlayer(@NotNull PlayerMock player)
	{
		onlinePlayers.add(player);
		offlinePlayers.add(player);
	}

	public void addOfflinePlayer(@NotNull OfflinePlayer player)
	{
		offlinePlayers.add(player);
	}

	@NotNull
	public Set<OfflinePlayer> getOperators()
	{
		return Stream.concat(onlinePlayers.stream(), offlinePlayers.stream()).filter(OfflinePlayer::isOp)
				.collect(Collectors.toSet());
	}

	@NotNull
	public Collection<PlayerMock> getOnlinePlayers()
	{
		return Collections.unmodifiableList(onlinePlayers);
	}

	@NotNull
	public OfflinePlayer[] getOfflinePlayers()
	{
		return offlinePlayers.toArray(new OfflinePlayer[0]);
	}

	public boolean isSomeoneOnline()
	{
		return !onlinePlayers.isEmpty();
	}

	@NotNull
	public List<Player> matchPlayer(@NotNull String name)
	{
		return onlinePlayers.stream().filter(
				player -> player.getName().toLowerCase(Locale.ENGLISH).startsWith(name.toLowerCase(Locale.ENGLISH)))
				.collect(Collectors.toList());
	}

	@Nullable
	public Player getPlayerExact(@NotNull String name)
	{
		return onlinePlayers.stream()
				.filter(player -> player.getName().toLowerCase(Locale.ENGLISH).equals(name.toLowerCase(Locale.ENGLISH)))
				.findFirst().orElse(null);
	}

	@NotNull
	public PlayerMock getPlayer(int num)
	{
		if (num < 0 || num >= onlinePlayers.size())
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		else
		{
			return onlinePlayers.get(num);
		}
	}

	public void clearOnlinePlayers()
	{
		onlinePlayers.clear();
	}

	public void clearOfflinePlayers()
	{
		offlinePlayers.clear();
	}
}
