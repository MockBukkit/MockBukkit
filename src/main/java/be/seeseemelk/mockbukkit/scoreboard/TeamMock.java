package be.seeseemelk.mockbukkit.scoreboard;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.entity.EntityMock;
import com.google.common.base.Preconditions;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;

/**
 * Mock implementation of a {@link Team}.
 */
public class TeamMock implements Team
{

	private final String name;
	private Component displayName;
	private @NotNull Component prefix = Component.empty();
	private @NotNull Component suffix = Component.empty();
	private @NotNull ChatColor color = ChatColor.RESET;
	private boolean allowFriendlyFire = false;
	private final @NotNull HashSet<String> entries;
	private boolean canSeeFriendly = true;
	private final EnumMap<Option, OptionStatus> options = new EnumMap<>(Option.class);
	private @Nullable ScoreboardMock board;

	/**
	 * Constructs a new {@link TeamMock} for the provided {@link ScoreboardMock} with the specified name.
	 *
	 * @param name  The name of the team.
	 * @param board The scoreboard the team is for.
	 */
	public TeamMock(@NotNull String name, ScoreboardMock board)
	{
		this.name = name;
		this.displayName = Component.text(name);
		this.board = board;
		this.entries = new HashSet<>();
		this.options.put(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
	}

	@Override
	public @NotNull String getName() throws IllegalStateException
	{
		checkRegistered();
		return this.name;
	}

	@Override
	public @NotNull Component displayName() throws IllegalStateException
	{
		checkRegistered();
		return this.displayName;
	}

	@Override
	public void displayName(@Nullable Component displayName) throws IllegalStateException, IllegalArgumentException
	{
		checkRegistered();
		this.displayName = displayName == null ? Component.empty() : displayName;
	}

	@Override
	public @NotNull Component prefix() throws IllegalStateException
	{
		checkRegistered();
		return this.prefix;
	}

	@Override
	public void prefix(@Nullable Component prefix) throws IllegalStateException, IllegalArgumentException
	{
		checkRegistered();
		this.prefix = prefix == null ? Component.empty() : prefix;
	}

	@Override
	public @NotNull Component suffix() throws IllegalStateException
	{
		checkRegistered();
		return this.suffix;
	}

	@Override
	public void suffix(@Nullable Component suffix) throws IllegalStateException, IllegalArgumentException
	{
		checkRegistered();
		this.suffix = suffix == null ? Component.empty() : suffix;
	}

	@Override
	public boolean hasColor()
	{
		checkRegistered();
		return this.color.isColor();
	}

	@Override
	public @NotNull TextColor color() throws IllegalStateException
	{
		Preconditions.checkState(hasColor(), "Team colors must have hex values");
		return TextColor.color(this.color.asBungee().getColor().getRGB());
	}

	@Override
	public void color(@Nullable NamedTextColor color)
	{
		checkRegistered();
		this.color = color == null ? ChatColor.RESET : ChatColor.valueOf(color.toString().toUpperCase(Locale.ROOT));
	}

	@Override
	public @NotNull String getDisplayName() throws IllegalStateException
	{
		checkRegistered();
		return LegacyComponentSerializer.legacySection().serialize(this.displayName);
	}

	@Override
	public void setDisplayName(@NotNull String displayName)
	{
		Preconditions.checkNotNull(displayName, "Display name cannot be null");
		Preconditions.checkArgument(ChatColor.stripColor(displayName).length() <= 128,
				"Display name is longer than the limit of 128 characters");
		checkRegistered();
		this.displayName = LegacyComponentSerializer.legacySection().deserialize(displayName);
	}

	@Override
	public @NotNull String getPrefix() throws IllegalStateException
	{
		checkRegistered();
		return LegacyComponentSerializer.legacySection().serialize(this.prefix);
	}

	@Override
	public void setPrefix(@NotNull String prefix)
	{
		Preconditions.checkNotNull(prefix, "Prefix cannot be null");
		Preconditions.checkArgument(ChatColor.stripColor(prefix).length() <= 64,
				"Prefix is longer than the limit of 64 characters");
		checkRegistered();
		this.prefix = LegacyComponentSerializer.legacySection().deserialize(prefix);
	}

	@Override
	public @NotNull String getSuffix() throws IllegalStateException
	{
		checkRegistered();
		return LegacyComponentSerializer.legacySection().serialize(this.suffix);
	}

	@Override
	public void setSuffix(@NotNull String suffix)
	{
		Preconditions.checkNotNull(suffix, "Suffix cannot be null");
		Preconditions.checkArgument(ChatColor.stripColor(suffix).length() <= 64,
				"Suffix is longer than the limit of 64 characters");
		checkRegistered();
		this.suffix = LegacyComponentSerializer.legacySection().deserialize(suffix);
	}

	@Override
	public @NotNull ChatColor getColor() throws IllegalStateException
	{
		checkRegistered();
		return this.color;
	}

	@Override
	public void setColor(@NotNull ChatColor chatColor)
	{
		Preconditions.checkNotNull(chatColor, "Color cannot be null");
		checkRegistered();
		this.color = chatColor;
	}

	@Override
	public boolean allowFriendlyFire() throws IllegalStateException
	{
		checkRegistered();
		return this.allowFriendlyFire;
	}

	@Override
	public void setAllowFriendlyFire(boolean b) throws IllegalStateException
	{
		checkRegistered();
		this.allowFriendlyFire = b;
	}

	@Override
	public boolean canSeeFriendlyInvisibles() throws IllegalStateException
	{
		checkRegistered();
		return this.canSeeFriendly;
	}

	@Override
	public void setCanSeeFriendlyInvisibles(boolean b) throws IllegalStateException
	{
		checkRegistered();
		this.canSeeFriendly = b;
	}

	/**
	 * @deprecated
	 */
	@Override
	@Deprecated(since = "1.9")
	public @NotNull NameTagVisibility getNameTagVisibility()
	{
		checkRegistered();

		OptionStatus s = options.get(Option.NAME_TAG_VISIBILITY);
		return switch (s)
		{
		case NEVER -> NameTagVisibility.NEVER;
		case ALWAYS -> NameTagVisibility.ALWAYS;
		case FOR_OTHER_TEAMS -> NameTagVisibility.HIDE_FOR_OTHER_TEAMS;
		case FOR_OWN_TEAM -> NameTagVisibility.HIDE_FOR_OWN_TEAM;
		default -> throw new IllegalArgumentException("Option not compatible");
		};
	}

	/**
	 * @deprecated
	 */
	@Override
	@Deprecated(since = "1.9")
	public void setNameTagVisibility(@NotNull NameTagVisibility nameTagVisibility)
	{
		MockBukkit.getMock().getLogger().log(Level.WARNING, "Consider USE setOption() DEPRECATED");
		checkRegistered();

		switch (nameTagVisibility)
		{
		case ALWAYS -> setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
		case NEVER -> setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.NEVER);
		case HIDE_FOR_OTHER_TEAMS -> setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.FOR_OTHER_TEAMS);
		case HIDE_FOR_OWN_TEAM -> setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.FOR_OWN_TEAM);
		default -> throw new IllegalArgumentException("Option not compatible");
		}
	}

	/**
	 * @deprecated
	 */
	@Override
	@Deprecated(since = "1.8.7")
	public @NotNull Set<OfflinePlayer> getPlayers() throws IllegalStateException
	{
		checkRegistered();
		Set<OfflinePlayer> players = new HashSet<>();
		for (String s : entries)
		{
			if (s != null)
			{
				OfflinePlayer player = MockBukkit.getMock().getOfflinePlayer(s);
				if (player != null)
					players.add(player);
			}
		}
		return players;
	}

	@Override
	public @NotNull Set<String> getEntries() throws IllegalStateException
	{
		checkRegistered();
		return this.entries;
	}

	@Override
	public int getSize() throws IllegalStateException
	{
		checkRegistered();
		return this.entries.size();
	}

	@Override
	public Scoreboard getScoreboard()
	{
		return this.board;
	}

	@Override
	@Deprecated(since = "1.8.7")
	public void addPlayer(@NotNull OfflinePlayer offlinePlayer)
	{
		Preconditions.checkNotNull(offlinePlayer, "OfflinePlayer cannot be null");
		checkRegistered();
		this.entries.add(offlinePlayer.getName());
	}

	@Override
	public void addEntry(@NotNull String entry)
	{
		Preconditions.checkNotNull(entry, "Entry cannot be null");
		checkRegistered();
		this.entries.add(entry);
	}

	@Override
	public void addEntities(@NotNull Collection<Entity> entities) throws IllegalStateException, IllegalArgumentException
	{
		Preconditions.checkNotNull(entities, "Entities cannot be null");
		addEntries(entities.stream().map(entity -> ((EntityMock) entity).getScoreboardEntry()).toList());
	}

	@Override
	public void addEntries(@NotNull Collection<String> entries) throws IllegalStateException, IllegalArgumentException
	{
		Preconditions.checkNotNull(entries, "Entries cannot be null");
		checkRegistered();
		this.entries.addAll(entries);
	}

	/**
	 * @deprecated
	 */
	@Override
	@Deprecated(since = "1.8.7")
	public boolean removePlayer(@NotNull OfflinePlayer offlinePlayer)
	{
		Preconditions.checkNotNull(offlinePlayer, "OfflinePlayer cannot be null");
		checkRegistered();
		return this.entries.remove(offlinePlayer.getName());
	}

	@Override
	public boolean removeEntry(@NotNull String entry)
	{
		Preconditions.checkNotNull(entry, "Entry cannot be null");
		checkRegistered();
		return this.entries.remove(entry);
	}

	@Override
	public boolean removeEntities(@NotNull Collection<Entity> entities)
			throws IllegalStateException, IllegalArgumentException
	{
		Preconditions.checkNotNull(entities, "Entities cannot be null");
		return removeEntries(entities.stream().map(entity -> ((EntityMock) entity).getScoreboardEntry()).toList());
	}

	@Override
	public boolean removeEntries(@NotNull Collection<String> entries)
			throws IllegalStateException, IllegalArgumentException
	{
		Preconditions.checkNotNull(entries, "Entries cannot be null");
		checkRegistered();
		return this.entries.removeAll(entries);
	}

	@Override
	public void unregister() throws IllegalStateException
	{
		checkRegistered();
		this.board.unregister(this);
		this.board = null;
	}

	/**
	 * @deprecated
	 */
	@Override
	@Deprecated(since = "1.8.7")
	public boolean hasPlayer(@NotNull OfflinePlayer offlinePlayer)
	{
		Preconditions.checkNotNull(offlinePlayer, "OfflinePlayer cannot be null");
		checkRegistered();
		return this.entries.contains(offlinePlayer.getName());
	}

	@Override
	public boolean hasEntry(@NotNull String entry)
	{
		Preconditions.checkNotNull(entry, "Entry cannot be null");
		checkRegistered();
		return this.entries.contains(entry);
	}

	@Override
	public @NotNull OptionStatus getOption(@NotNull Option option) throws IllegalStateException
	{
		Preconditions.checkNotNull(option, "Option cannot be null");
		checkRegistered();
		return this.options.get(option);
	}

	@Override
	public void setOption(@NotNull Option option, @NotNull OptionStatus optionStatus) throws IllegalStateException
	{
		Preconditions.checkNotNull(option, "Option cannot be null");
		Preconditions.checkNotNull(optionStatus, "OptionStatus cannot be null");
		checkRegistered();
		this.options.put(option, optionStatus);
	}

	@Override
	public void addEntity(@NotNull Entity entity) throws IllegalStateException, IllegalArgumentException
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		addEntry(((EntityMock) entity).getScoreboardEntry());
	}

	@Override
	public boolean removeEntity(@NotNull Entity entity) throws IllegalStateException, IllegalArgumentException
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		return removeEntry(((EntityMock) entity).getScoreboardEntry());
	}

	@Override
	public boolean hasEntity(@NotNull Entity entity) throws IllegalStateException, IllegalArgumentException
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		checkRegistered();
		return this.entries.contains(((EntityMock) entity).getScoreboardEntry());
	}

	/**
	 * Throws an exception if the team is not registered.
	 */
	public void checkRegistered()
	{
		if (this.board != null)
			return;
		throw new IllegalStateException("Team not registered");
	}

	@Override
	public @NotNull Iterable<? extends Audience> audiences()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
