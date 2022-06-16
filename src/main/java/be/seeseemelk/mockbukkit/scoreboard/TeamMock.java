package be.seeseemelk.mockbukkit.scoreboard;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class TeamMock implements Team
{

	private final String name;
	private String displayName;
	private String prefix;
	private String suffix;
	private ChatColor color;
	private boolean allowFriendlyFire = false;
	private final HashSet<String> entries;
	private boolean canSeeFriendly = true;
	private EnumMap<Option, OptionStatus> options = new EnumMap<>(Option.class);
	private ScoreboardMock board;

	public TeamMock(String name, ScoreboardMock board)
	{
		this.name = name;
		this.board = board;
		entries = new HashSet<>();
		options.put(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
	}

	@Override
	public String getName() throws IllegalStateException
	{
		checkRegistered();

		return name;
	}

	@Override
	public @NotNull Component displayName() throws IllegalStateException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void displayName(@Nullable Component displayName) throws IllegalStateException, IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Component prefix() throws IllegalStateException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void prefix(@Nullable Component prefix) throws IllegalStateException, IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Component suffix() throws IllegalStateException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void suffix(@Nullable Component suffix) throws IllegalStateException, IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasColor()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull TextColor color() throws IllegalStateException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void color(@Nullable NamedTextColor color)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getDisplayName() throws IllegalStateException
	{
		checkRegistered();

		return displayName;
	}

	@Override
	public void setDisplayName(String s)
	{
		checkRegistered();

		this.displayName = s;
	}

	@Override
	public String getPrefix() throws IllegalStateException
	{
		checkRegistered();

		return prefix;
	}

	@Override
	public void setPrefix(String s)
	{
		checkRegistered();

		this.prefix = s;
	}

	@Override
	public String getSuffix() throws IllegalStateException
	{
		checkRegistered();

		return suffix;
	}

	@Override
	public void setSuffix(String s)
	{
		checkRegistered();

		this.suffix = s;
	}

	@Override
	public ChatColor getColor() throws IllegalStateException
	{
		checkRegistered();

		return color;
	}

	@Override
	public void setColor(ChatColor chatColor)
	{
		checkRegistered();

		this.color = chatColor;
	}

	@Override
	public boolean allowFriendlyFire() throws IllegalStateException
	{
		checkRegistered();

		return allowFriendlyFire;
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

		return canSeeFriendly;
	}

	@Override
	public void setCanSeeFriendlyInvisibles(boolean b) throws IllegalStateException
	{
		checkRegistered();

		this.canSeeFriendly = b;
	}

	/** @deprecated  */
	@Override
	@Deprecated
	public NameTagVisibility getNameTagVisibility()
	{
		checkRegistered();

		OptionStatus s = options.get(Option.NAME_TAG_VISIBILITY);
		switch (s)
		{
		case NEVER:
			return NameTagVisibility.NEVER;
		case ALWAYS:
			return NameTagVisibility.ALWAYS;
		case FOR_OTHER_TEAMS:
			return NameTagVisibility.HIDE_FOR_OTHER_TEAMS;
		case FOR_OWN_TEAM:
			return NameTagVisibility.HIDE_FOR_OWN_TEAM;
		default:
			throw new IllegalArgumentException("Option not compatible");
		}
	}

	/** @deprecated */
	@Override
	@Deprecated
	public void setNameTagVisibility(NameTagVisibility nameTagVisibility)
	{
		MockBukkit.getMock().getLogger().log(Level.WARNING, "Consider USE setOption() DEPRECATED");
		checkRegistered();

		switch (nameTagVisibility)
		{
		case ALWAYS:
			setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
			return;
		case NEVER:
			setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.NEVER);
			return;
		case HIDE_FOR_OTHER_TEAMS:
			setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.FOR_OTHER_TEAMS);
			return;
		case HIDE_FOR_OWN_TEAM:
			setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.FOR_OWN_TEAM);
			return;
		default:
			throw new IllegalArgumentException("Option not compatible");
		}
	}

	/** @deprecated  */
	@Override
	@Deprecated
	public Set<OfflinePlayer> getPlayers() throws IllegalStateException
	{

		checkRegistered();
		Set<OfflinePlayer> players = new HashSet<>();
		for (String s : entries)
		{
			if (s != null)
			{
				OfflinePlayer player = MockBukkit.getMock().getOfflinePlayer(s);
				if (player != null) players.add(player);
			}
		}
		return players;
	}

	@Override
	public Set<String> getEntries() throws IllegalStateException
	{
		return entries;
	}

	@Override
	public int getSize() throws IllegalStateException
	{
		checkRegistered();
		return entries.size();
	}

	@Override
	public Scoreboard getScoreboard()
	{
		return board;
	}

	@Override
	@Deprecated
	public void addPlayer(OfflinePlayer offlinePlayer)
	{
		checkRegistered();

		entries.add(offlinePlayer.getName());
	}

	@Override
	public void addEntry(String s)
	{
		checkRegistered();
		entries.add(s);
	}

	@Override
	public void addEntities(@NotNull Collection<Entity> entities) throws IllegalStateException, IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void addEntries(@NotNull Collection<String> entries) throws IllegalStateException, IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/** @deprecated */
	@Override
	@Deprecated
	public boolean removePlayer(OfflinePlayer offlinePlayer)
	{
		checkRegistered();

		return entries.remove(offlinePlayer.getName());
	}

	@Override
	public boolean removeEntry(String s)
	{
		checkRegistered();
		return entries.remove(s);
	}

	@Override
	public boolean removeEntities(@NotNull Collection<Entity> entities) throws IllegalStateException, IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removeEntries(@NotNull Collection<String> entries) throws IllegalStateException, IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void unregister() throws IllegalStateException
	{
		checkRegistered();

		board.unregister(this);
		board = null;
	}

	/** @deprecated */
	@Override
	@Deprecated
	public boolean hasPlayer(OfflinePlayer offlinePlayer)
	{
		checkRegistered();
		return entries.contains(offlinePlayer.getName());
	}

	@Override
	public boolean hasEntry(String s)
	{
		checkRegistered();
		return entries.contains(s);
	}

	@Override
	public OptionStatus getOption(Option option) throws IllegalStateException
	{
		checkRegistered();
		return options.get(option);
	}

	@Override
	public void setOption(Option option, OptionStatus optionStatus) throws IllegalStateException
	{
		checkRegistered();

		options.put(option, optionStatus);
	}

	@Override
	public void addEntity(@NotNull Entity entity) throws IllegalStateException, IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removeEntity(@NotNull Entity entity) throws IllegalStateException, IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasEntity(@NotNull Entity entity) throws IllegalStateException, IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * Throws an exception if the team is not registered.
	 */
	public void checkRegistered()
	{
		if (board == null)
		{
			throw new IllegalStateException("Team not registered");
		}
	}
}
