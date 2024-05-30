package be.seeseemelk.mockbukkit.ban;

import be.seeseemelk.mockbukkit.profile.PlayerProfileMock;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import org.bukkit.ban.ProfileBanList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings(
{ "rawtypes", "unchecked" })
public class MockProfileBanList implements ProfileBanList
{

	private final Map<String, BanEntry<PlayerProfile>> bans = new HashMap<>();
	private static final String TARGET_CANNOT_BE_NULL = "Target cannot be null";

	@Override
	@Deprecated(since = "1.20")
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
	@Deprecated(since = "1.20")
	public @Nullable BanEntry<PlayerProfile> addBan(@NotNull String target, @Nullable String reason,
			@Nullable Date expires, @Nullable String source)
	{
		PlayerProfileMock profile = new PlayerProfileMock(target, Bukkit.getOfflinePlayer(target).getUniqueId());
		return addBan(profile, reason, expires, source);
	}

	@Override
	@Deprecated(since = "1.20")
	public <E extends BanEntry<? super PlayerProfile>> @Nullable E addBan(
			org.bukkit.profile.@NotNull PlayerProfile target, @Nullable String reason, @Nullable Date expires,
			@Nullable String source)
	{
		Preconditions.checkNotNull(target, TARGET_CANNOT_BE_NULL);
		PlayerProfile profile = Bukkit.createProfile(target.getUniqueId(), target.getName());
		BanEntry<PlayerProfile> entry = addBan(profile, reason, expires, source);

		return (E) new MockBukkitProfileBanEntry(Bukkit.createPlayerProfile(profile.getUniqueId(), profile.getName()),
				entry.getSource(), entry.getExpiration(), entry.getReason());
	}

	@Override
	public @Nullable BanEntry<PlayerProfile> addBan(@NotNull PlayerProfile target, @Nullable String reason,
			@Nullable Date expires, @Nullable String source)
	{
		Preconditions.checkNotNull(target, TARGET_CANNOT_BE_NULL);

		final BanEntry<PlayerProfile> entry = new MockPaperProfileBanEntry(target,
				(source == null || source.isBlank()) ? null : source, expires,
				(reason == null || reason.isBlank()) ? null : reason);

		this.bans.put(target.getName(), entry);
		return entry;
	}

	@Override
	public @Nullable BanEntry<PlayerProfile> addBan(@NotNull PlayerProfile target, @Nullable String reason,
			@Nullable Instant expires, @Nullable String source)
	{
		Date date = expires != null ? Date.from(expires) : null;
		return this.addBan(target, reason, date, source);
	}

	@Override
	public @Nullable BanEntry<PlayerProfile> addBan(@NotNull PlayerProfile target, @Nullable String reason,
			@Nullable Duration duration, @Nullable String source)
	{
		Instant instant = duration != null ? Instant.now().plus(duration) : null;
		return this.addBan(target, reason, instant, source);
	}

	@Override
	@Deprecated(since = "1.20")
	public <E extends BanEntry<? super PlayerProfile>> @Nullable E getBanEntry(
			org.bukkit.profile.@NotNull PlayerProfile target)
	{
		Preconditions.checkNotNull(target, TARGET_CANNOT_BE_NULL);
		PlayerProfile profile = Bukkit.createProfile(target.getUniqueId(), target.getName());
		return (E) getBanEntry(profile);
	}

	@Override
	@Deprecated(since = "1.20")
	public boolean isBanned(org.bukkit.profile.@NotNull PlayerProfile target)
	{
		Preconditions.checkNotNull(target, TARGET_CANNOT_BE_NULL);
		PlayerProfile profile = Bukkit.createProfile(target.getUniqueId(), target.getName());
		return isBanned(profile);
	}

	@Override
	@Deprecated(since = "1.20")
	public void pardon(org.bukkit.profile.@NotNull PlayerProfile target)
	{
		Preconditions.checkNotNull(target, TARGET_CANNOT_BE_NULL);
		PlayerProfile profile = Bukkit.createProfile(target.getUniqueId(), target.getName());
		pardon(profile);
	}

	@Override
	public <E extends BanEntry<? super PlayerProfile>> @Nullable E addBan(
			org.bukkit.profile.@NotNull PlayerProfile target, @Nullable String reason, @Nullable Instant expires,
			@Nullable String source)
	{
		Date date = expires != null ? Date.from(expires) : null;
		return this.addBan(target, reason, date, source);
	}

	@Override
	public <E extends BanEntry<? super PlayerProfile>> @Nullable E addBan(
			org.bukkit.profile.@NotNull PlayerProfile target, @Nullable String reason, @Nullable Duration duration,
			@Nullable String source)
	{
		Instant instant = duration != null ? Instant.now().plus(duration) : null;
		return this.addBan(target, reason, instant, source);
	}

	@Override
	@Deprecated(since = "1.20")
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
		return this.bans.values().stream()
				.anyMatch(banEntry -> Objects.equals(banEntry.getBanTarget().getName(), target));
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
