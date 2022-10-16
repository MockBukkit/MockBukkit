package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

/**
 * A Mock specifically for {@link OfflinePlayer}. Not interchangeable with {@link PlayerMock}.
 *
 * @author TheBusyBiscuit
 * @see PlayerMock
 */
public class OfflinePlayerMock implements OfflinePlayer
{

	private final @NotNull UUID uuid;
	private final @Nullable String name;

	public OfflinePlayerMock(@NotNull UUID uuid, @Nullable String name)
	{
		Preconditions.checkNotNull(uuid, "UUID cannot be null");
		this.uuid = uuid;
		this.name = name;
	}

	public OfflinePlayerMock(@Nullable String name)
	{
		this(UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes()), name);
	}

	public @NotNull PlayerMock join(@NotNull ServerMock server)
	{
		Preconditions.checkNotNull(server, "Server cannot be null");
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setOp(boolean value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		return Bukkit.getBanList(BanList.Type.NAME).isBanned(getName());
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
	@Deprecated
	public long getLastPlayed()
	{
		return getLastSeen();
	}

	@Override
	public boolean hasPlayedBefore()
	{
		MockBukkit.ensureMocking();
		return Arrays.stream(MockBukkit.getMock().getPlayerList().getOfflinePlayers()).anyMatch(p -> p.getUniqueId().equals(getUniqueId()));
	}

	@Override
	public @Nullable Location getBedSpawnLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public @NotNull PlayerProfile getPlayerProfile()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
