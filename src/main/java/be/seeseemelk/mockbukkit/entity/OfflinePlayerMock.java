package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
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
		return false;
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
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("UUID", this.uuid.toString());
		return result;
	}

	@Override
	public boolean isBanned()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isWhitelisted()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setWhitelisted(boolean value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Player getPlayer()
	{
		return MockBukkit.getMock().getPlayerExact(this.name);
	}

	@Override
	public long getFirstPlayed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public long getLastPlayed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPlayedBefore()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getLastSeen()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
