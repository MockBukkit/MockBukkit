package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.ban.MockProfileBanList;
import be.seeseemelk.mockbukkit.profile.PlayerProfileMock;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.base.Preconditions;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.ban.ProfileBanList;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Mock implementation of an {@link OfflinePlayer}.
 * Not interchangeable with {@link PlayerMock}.
 *
 * @see PlayerMock
 */
public class OfflinePlayerMock implements OfflinePlayer
{

	private final @NotNull UUID uuid;
	private final @Nullable String name;
	private @Nullable Location respawnLocation = null;

	/**
	 * Constructs a new {@link OfflinePlayerMock} on the provided {@link ServerMock} with a specified {@link UUID} and name.
	 *
	 * @param uuid The UUID of the player.
	 * @param name The name of the player.
	 */
	public OfflinePlayerMock(@NotNull UUID uuid, @Nullable String name)
	{
		Preconditions.checkNotNull(uuid, "UUID cannot be null");
		this.uuid = uuid;
		this.name = name;
	}

	/**
	 * Constructs a new {@link OfflinePlayerMock} on the provided {@link ServerMock} with a random {@link UUID} and specified name.
	 *
	 * @param name The name of the player.
	 */
	public OfflinePlayerMock(@Nullable String name)
	{
		this(UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes()), name);
	}

	/**
	 * Makes this offline player join the server.
	 * A new PlayerMock will be constructed, and added to the server.
	 * Will throw an {@link IllegalStateException} if the player is already online.
	 *
	 * @param server The server to join.
	 * @return The created PlayerMock.
	 */
	public @NotNull PlayerMock join(@NotNull ServerMock server)
	{
		Preconditions.checkNotNull(server, "Server cannot be null");
		Preconditions.checkState(!isOnline(), "Player already online");
		PlayerMock player = new PlayerMock(server, this.name, this.uuid);
		server.addPlayer(player);
		return player;
	}

	@Override
	public boolean isOnline()
	{
		return getPlayer() != null;
	}

	@Override
	public boolean isConnected()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable String getName()
	{
		return this.name;
	}

	@Override
	public @NotNull UUID getUniqueId()
	{
		return this.uuid;
	}

	@Override
	public boolean isOp()
	{
		return MockBukkit.getMock().getPlayerList().getOperators().contains(this);
	}

	@Override
	public void setOp(boolean value)
	{
		if (value)
		{
			MockBukkit.getMock().getPlayerList().addOperator(this.uuid);
		}
		else
		{
			MockBukkit.getMock().getPlayerList().removeOperator(this.uuid);
		}
	}

	@Override
	public @NotNull Map<String, Object> serialize()
	{
		return Map.of("UUID", this.uuid.toString());
	}

	@Override
	public boolean isBanned()
	{
		MockBukkit.ensureMocking();
		return ((MockProfileBanList) Bukkit.getBanList(BanList.Type.PROFILE)).isBanned(getPlayerProfile());
	}

	@Override
	@SuppressWarnings("unchecked") // Paper does it too ¯\_(ツ)_/¯
	public @Nullable BanEntry<PlayerProfile> ban(@Nullable String reason, @Nullable Date expires,
			@Nullable String source)
	{
		MockBukkit.ensureMocking();
		return ((ProfileBanList) Bukkit.getBanList(BanList.Type.PROFILE)).addBan(this.getPlayerProfile(), reason,
				expires, source);
	}

	@Override
	@SuppressWarnings("unchecked") // Paper does it too ¯\_(ツ)_/¯
	public @Nullable BanEntry<PlayerProfile> ban(@Nullable String reason, @Nullable Instant expires,
			@Nullable String source)
	{
		MockBukkit.ensureMocking();
		return ((ProfileBanList) Bukkit.getBanList(BanList.Type.PROFILE)).addBan(this.getPlayerProfile(), reason,
				expires, source);
	}

	@Override
	@SuppressWarnings("unchecked") // Paper does it too ¯\_(ツ)_/¯
	public @Nullable BanEntry<PlayerProfile> ban(@Nullable String reason, @Nullable Duration duration,
			@Nullable String source)
	{
		MockBukkit.ensureMocking();
		return ((ProfileBanList) Bukkit.getBanList(BanList.Type.PROFILE)).addBan(this.getPlayerProfile(), reason,
				duration, source);
	}

	@Override
	public boolean isWhitelisted()
	{
		MockBukkit.ensureMocking();
		return Bukkit.getWhitelistedPlayers().contains(this);
	}

	@Override
	public void setWhitelisted(boolean value)
	{
		MockBukkit.ensureMocking();
		Bukkit.getWhitelistedPlayers().add(this);
	}

	@Override
	public @Nullable Player getPlayer()
	{
		MockBukkit.ensureMocking();
		return Bukkit.getPlayer(this.getUniqueId());
	}

	@Override
	public long getFirstPlayed()
	{
		MockBukkit.ensureMocking();
		return MockBukkit.getMock().getPlayerList().getFirstPlayed(getUniqueId());
	}

	@Override
	@Deprecated(since = "1.13")
	public long getLastPlayed()
	{
		return getLastSeen();
	}

	@Override
	public boolean hasPlayedBefore()
	{
		MockBukkit.ensureMocking();
		return MockBukkit.getMock().getPlayerList().hasPlayedBefore(getUniqueId());
	}

	@Override
	public @Nullable Location getRespawnLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Location getBedSpawnLocation()
	{
		return getRespawnLocation();
	}

	@Override
	public long getLastLogin()
	{
		MockBukkit.ensureMocking();
		return MockBukkit.getMock().getPlayerList().getLastLogin(getUniqueId());
	}

	@Override
	public long getLastSeen()
	{
		MockBukkit.ensureMocking();
		return MockBukkit.getMock().getPlayerList().getLastSeen(getUniqueId());
	}

	@Override
	public void incrementStatistic(@NotNull Statistic statistic)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(@NotNull Statistic statistic)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(@NotNull Statistic statistic, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(@NotNull Statistic statistic, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setStatistic(@NotNull Statistic statistic, int newValue)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getStatistic(@NotNull Statistic statistic)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getStatistic(@NotNull Statistic statistic, @NotNull Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setStatistic(@NotNull Statistic statistic, @NotNull Material material, int newValue)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int newValue)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Location getLastDeathLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Location getLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull PlayerProfile getPlayerProfile()
	{
		return new PlayerProfileMock(this); // Paper does not cache this.
	}

}
