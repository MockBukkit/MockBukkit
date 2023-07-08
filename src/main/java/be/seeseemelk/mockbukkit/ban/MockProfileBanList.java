package be.seeseemelk.mockbukkit.ban;

import be.seeseemelk.mockbukkit.profile.PlayerProfileMock;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import org.bukkit.ban.ProfileBanList;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MockProfileBanList implements ProfileBanList
{

	private final Map<String, BanEntry<PlayerProfile>> bans = new HashMap<>();
	private static final String TARGET_CANNOT_BE_NULL = "Target cannot be null";

	@Override
	@Deprecated
	public @Nullable BanEntry<PlayerProfile> getBanEntry(@NotNull String target)
	{
		Preconditions.checkNotNull(target, TARGET_CANNOT_BE_NULL);
		return bans.getOrDefault(target, null);
	}

	@Override
	public @Nullable BanEntry<PlayerProfile> getBanEntry(@NotNull PlayerProfile target)
	{
		Preconditions.checkNotNull(target, TARGET_CANNOT_BE_NULL);
		return bans.getOrDefault(target.getName(), null);
	}

	@Override
	@Deprecated
	public @Nullable BanEntry<PlayerProfile> addBan(@NotNull String target, @Nullable String reason, @Nullable Date expires, @Nullable String source)
	{
		PlayerProfileMock profile = new PlayerProfileMock(target, Bukkit.getOfflinePlayer(target).getUniqueId());
		return addBan(profile, reason, expires, source);
	}

	@Override
	public @Nullable BanEntry<PlayerProfile> addBan(@NotNull PlayerProfile target, @Nullable String reason, @Nullable Date expires, @Nullable String source)
	{
		Preconditions.checkNotNull(target, TARGET_CANNOT_BE_NULL);

		final BanEntry<PlayerProfile> entry = new MockProfileBanEntry(
				target,
				(source == null || source.isBlank()) ? null : source,
				expires,
				(reason == null || reason.isBlank()) ? null : reason
		);

		this.bans.put(target.getName(), entry);
		return entry;
	}

	@Override
	@Deprecated
	public @NotNull Set<BanEntry> getBanEntries()
	{
		ImmutableSet.Builder<BanEntry> builder = ImmutableSet.builder();
		for (BanEntry<PlayerProfile> ban : bans.values())
		{
			builder.add(ban);
		}
		return builder.build();
	}

	@Override
	public @NotNull Set<BanEntry<PlayerProfile>> getEntries()
	{
		ImmutableSet.Builder<BanEntry<PlayerProfile>> builder = ImmutableSet.builder();
		for (BanEntry<PlayerProfile> ban : bans.values())
		{
			builder.add(ban);
		}
		return builder.build();
	}

	@Override
	public boolean isBanned(@NotNull PlayerProfile target)
	{
		return this.bans.values().stream().anyMatch(banEntry -> banEntry.getBanTarget().equals(target));
	}

	@Override
	public boolean isBanned(@NotNull String target)
	{
		return this.bans.values().stream().anyMatch(banEntry -> Objects.equals(banEntry.getBanTarget().getName(), target));
	}

	@Override
	public void pardon(@NotNull PlayerProfile target)
	{
		Preconditions.checkNotNull(target, TARGET_CANNOT_BE_NULL);
		this.bans.remove(target.getName());
	}

	@Override
	public void pardon(@NotNull String target)
	{
		Preconditions.checkNotNull(target, TARGET_CANNOT_BE_NULL);
		this.bans.remove(target);
	}

}
