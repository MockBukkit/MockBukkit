package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.entity.OfflinePlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.base.Preconditions;
import org.bukkit.BanList;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Replica of the Bukkit internal PlayerList and CraftPlayerList implementation
 *
 * @author seeseemelk
 * @author TheBusyBiscuit
 */
public class MockPlayerList
{

	private int maxPlayers = Integer.MAX_VALUE;

	private final Set<PlayerMock> onlinePlayers = Collections.synchronizedSet(new LinkedHashSet<>());
	private final Set<OfflinePlayer> offlinePlayers = Collections.synchronizedSet(new HashSet<>());
	private final Map<UUID, Long> lastLogins = Collections.synchronizedMap(new HashMap<>());
	private final Map<UUID, Long> lastSeen = Collections.synchronizedMap(new HashMap<>());
	private final Map<UUID, Long> firstPlayed = Collections.synchronizedMap(new HashMap<>());
	private final Map<UUID, Boolean> hasPlayedBefore = Collections.synchronizedMap(new HashMap<>());

	private final @NotNull BanList ipBans = new MockBanList();
	private final @NotNull BanList profileBans = new MockBanList();

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
		this.firstPlayed.putIfAbsent(player.getUniqueId(), System.currentTimeMillis());
		this.lastLogins.put(player.getUniqueId(), System.currentTimeMillis());
		this.onlinePlayers.add(player);
		this.offlinePlayers.add(player);
		this.hasPlayedBefore.put(player.getUniqueId(), this.hasPlayedBefore.containsKey(player.getUniqueId()));
	}

	public void disconnectPlayer(@NotNull PlayerMock player)
	{
		this.lastSeen.put(player.getUniqueId(), System.currentTimeMillis());
		this.onlinePlayers.remove(player);
	}

	/**
	 * Checks if a player has played before.
	 *
	 * @param uuid The UUID of the player.
	 * @return Whether the player has played before.
	 * @see Player#hasPlayedBefore()
	 */
	public boolean hasPlayedBefore(@NotNull UUID uuid)
	{
		Preconditions.checkNotNull(uuid, "UUID cannot be null");
		return this.hasPlayedBefore.getOrDefault(uuid, false);
	}

	public void addOfflinePlayer(@NotNull OfflinePlayer player)
	{
		this.offlinePlayers.add(player);
	}

	/**
	 * Gets the first time that this player was seen on the server.
	 *
	 * @param uuid The UUID of the player.
	 * @return The time of first log-in, or 0.
	 * @see OfflinePlayer#getFirstPlayed()
	 */
	public long getFirstPlayed(UUID uuid)
	{
		return this.firstPlayed.getOrDefault(uuid, 0L);
	}

	/**
	 * Sets the return value of {@link #getFirstPlayed(UUID)}.
	 *
	 * @param uuid        UUID of the player to set first played time for.
	 * @param firstPlayed The first played time. Must be non-negative.
	 */
	public void setFirstPlayed(UUID uuid, long firstPlayed)
	{
		Preconditions.checkArgument(firstPlayed > 0, "First played time must be non-negative");
		this.firstPlayed.put(uuid, firstPlayed);
	}

	/**
	 * Gets the last time a player was seen online.
	 *
	 * @param uuid The UUID of the player.
	 * @return The last time the player was seen online.
	 * @see OfflinePlayer#getLastSeen()
	 */
	public long getLastSeen(UUID uuid)
	{
		OfflinePlayer player = getOfflinePlayer(uuid);
		if (player != null && player.isOnline())
		{
			return System.currentTimeMillis();
		}
		return this.lastSeen.getOrDefault(uuid, 0L);
	}

	/**
	 * Sets the return value of {@link #getLastLogin(UUID)} <i>while the player is offline</i>.
	 * If the player is online, this will not have an effect.
	 *
	 * @param uuid     UUID of the player to set last seen time for.
	 * @param lastSeen The last seen time. Must be non-negative.
	 */
	public void setLastSeen(UUID uuid, long lastSeen)
	{
		Preconditions.checkArgument(lastSeen > 0, "Last seen time must be non-negative");
		this.lastSeen.put(uuid, lastSeen);
	}

	/**
	 * Gets the last time a player was seen online.
	 *
	 * @param uuid The UUID of the player.
	 * @return The last time the player was seen online.
	 * @see OfflinePlayer#getLastLogin()
	 */
	public long getLastLogin(UUID uuid)
	{
		return this.lastLogins.getOrDefault(uuid, 0L);
	}

	/**
	 * Sets the return value of {@link #getLastLogin(UUID)}.
	 *
	 * @param uuid      UUID of the player to set last login time for.
	 * @param lastLogin The last login time. Must be non-negative.
	 */
	public void setLastLogin(UUID uuid, long lastLogin)
	{
		Preconditions.checkArgument(lastLogin > 0, "Last login time must be non-negative");
		this.lastLogins.put(uuid, lastLogin);
	}

	@NotNull
	public Set<OfflinePlayer> getOperators()
	{
		return Stream.concat(this.onlinePlayers.stream(), this.offlinePlayers.stream())
				.filter(OfflinePlayer::isOp)
				.collect(Collectors.toSet());
	}

	@NotNull
	public Collection<PlayerMock> getOnlinePlayers()
	{
		return Collections.unmodifiableSet(this.onlinePlayers);
	}

	@NotNull
	public OfflinePlayer @NotNull [] getOfflinePlayers()
	{
		return this.offlinePlayers.toArray(new OfflinePlayer[0]);
	}

	public boolean isSomeoneOnline()
	{
		return !this.onlinePlayers.isEmpty();
	}

	@NotNull
	public List<Player> matchPlayer(@NotNull String name)
	{
		String nameLower = name.toLowerCase(Locale.ENGLISH);
		return this.onlinePlayers.stream()
				.filter(player -> player.getName().toLowerCase(Locale.ENGLISH).startsWith(nameLower))
				.collect(Collectors.toList());
	}

	@Nullable
	public Player getPlayerExact(@NotNull String name)
	{
		String nameLower = name.toLowerCase(Locale.ENGLISH);
		return this.onlinePlayers.stream()
				.filter(player -> player.getName().toLowerCase(Locale.ENGLISH).equals(nameLower))
				.findFirst().orElse(null);
	}

	@Nullable
	public Player getPlayer(@NotNull String name)
	{
		Player player = getPlayerExact(name);

		if (player != null)
		{
			return player;
		}

		final String lowercase = name.toLowerCase(Locale.ENGLISH);
		int delta = Integer.MAX_VALUE;

		for (Player namedPlayer : this.onlinePlayers)
		{
			if (namedPlayer.getName().toLowerCase(Locale.ENGLISH).startsWith(lowercase))
			{
				int currentDelta = Math.abs(namedPlayer.getName().length() - lowercase.length());

				if (currentDelta < delta)
				{
					delta = currentDelta;
					player = namedPlayer;
				}
			}
		}

		return player;
	}

	@Nullable
	public Player getPlayer(@NotNull UUID id)
	{
		for (Player player : this.onlinePlayers)
		{
			if (id.equals(player.getUniqueId()))
			{
				return player;
			}
		}

		return null;
	}

	@NotNull
	public PlayerMock getPlayer(int num)
	{
		return List.copyOf(this.onlinePlayers).get(num);
	}

	@NotNull
	public OfflinePlayer getOfflinePlayer(@NotNull String name)
	{
		Player player = getPlayer(name);

		if (player != null)
		{
			return player;
		}

		for (OfflinePlayer offlinePlayer : this.offlinePlayers)
		{
			if (name.equals(offlinePlayer.getName()))
			{
				return offlinePlayer;
			}
		}

		return new OfflinePlayerMock(name);
	}

	@Nullable
	public OfflinePlayer getOfflinePlayer(@NotNull UUID id)
	{
		Player player = getPlayer(id);

		if (player != null)
		{
			return player;
		}

		for (OfflinePlayer offlinePlayer : getOfflinePlayers())
		{
			if (offlinePlayer.getUniqueId().equals(id))
			{
				return offlinePlayer;
			}
		}

		return null;
	}

	public void clearOnlinePlayers()
	{
		this.onlinePlayers.clear();
	}

	public void clearOfflinePlayers()
	{
		this.offlinePlayers.clear();
	}

}
