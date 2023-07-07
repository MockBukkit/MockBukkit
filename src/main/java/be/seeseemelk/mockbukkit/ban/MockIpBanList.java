package be.seeseemelk.mockbukkit.ban;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.net.InetAddresses;
import org.bukkit.BanEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MockIpBanList implements org.bukkit.ban.IpBanList
{

	private final Map<String, BanEntry<InetAddress>> bans = new HashMap<>();

	@Override
	public @Nullable BanEntry<InetAddress> getBanEntry(@NotNull String target)
	{
		Preconditions.checkArgument(target != null, "Target cannot be null");
		return bans.getOrDefault(target, null);
	}

	@Override
	public @Nullable BanEntry<InetAddress> getBanEntry(@NotNull InetAddress target)
	{
		Preconditions.checkArgument(target != null, "Target cannot be null");
		return bans.getOrDefault(InetAddresses.toAddrString(target), null);
	}

	@Override

	public @Nullable BanEntry<InetAddress> addBan(@NotNull String target, @Nullable String reason, @Nullable Date expires, @Nullable String source)
	{
		Preconditions.checkArgument(target != null, "Ban target cannot be null");
		BanEntry<InetAddress> entry = new MockIpBanEntry(
				target,
				(reason == null || reason.isBlank()) ? null : reason,
				expires,
				(source == null || source.isBlank()) ? null : source
		);

		bans.put(target, entry);
		return entry;
	}

	@Override
	public @Nullable BanEntry<InetAddress> addBan(@NotNull InetAddress target, @Nullable String reason, @Nullable Date expires, @Nullable String source)
	{
		return addBan(InetAddresses.toAddrString(target), reason, expires, source);
	}

	@Override
	@Deprecated
	@SuppressWarnings("rawtypes")
	public @NotNull Set<BanEntry> getBanEntries()
	{
		ImmutableSet.Builder<BanEntry> builder = ImmutableSet.builder();
		for (String target : bans.keySet())
		{
			BanEntry<InetAddress> banEntry = bans.get(target);
			if (banEntry != null)
			{
				builder.add(banEntry);
			}
		}
		return builder.build();
	}

	@Override
	public @NotNull Set<BanEntry<InetAddress>> getEntries()
	{
		ImmutableSet.Builder<BanEntry<InetAddress>> builder = ImmutableSet.builder();
		for (String target : bans.keySet())
		{
			BanEntry<InetAddress> banEntry = bans.get(target);
			if (banEntry != null)
			{
				builder.add(banEntry);
			}
		}
		return builder.build();
	}

	@Override
	public boolean isBanned(@NotNull InetAddress target)
	{
		return this.bans.values()
				.stream()
				.filter(banEntry -> banEntry.getBanTarget().equals(target))
				.findFirst()
				.orElse(null)
				!= null;
	}

	@Override
	public boolean isBanned(@NotNull String target)
	{
		return this.bans.values()
				.stream()
				.filter(banEntry -> InetAddresses.toAddrString(banEntry.getBanTarget()).equals(target))
				.findFirst()
				.orElse(null)
				!= null;
	}

	@Override
	public void pardon(@NotNull InetAddress target)
	{
		this.pardon(InetAddresses.toAddrString(target));
	}

	@Override
	public void pardon(@NotNull String target)
	{
		Preconditions.checkArgument(target != null, "Target cannot be null");
		this.bans.remove(target);
	}

}
