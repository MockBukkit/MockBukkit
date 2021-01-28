package be.seeseemelk.mockbukkit.scoreboard;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import be.seeseemelk.mockbukkit.MockBukkit;

/**
 * Created for the AddstarMC Project. Created by Narimm on 24/12/2018.
 */
@SuppressWarnings("deprecation")
public class TeamMock implements Team
{

	private final String name;
	private String displayName;
	private String prefix;
	private String suffic;
	private ChatColor color;
	private boolean allowFriendlyFire = false;
	private final HashSet<String> entries;
	private boolean canSeeFriendly = true;
	private EnumMap<Option, OptionStatus> options = new EnumMap<>(Option.class);
	private boolean registered;
	private Scoreboard board;

	public TeamMock(String name, Scoreboard board)
	{
		this.name = name;
		this.board = board;
		registered = true;
		entries = new HashSet<>();
		options.put(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
	}

	@Override
	public String getName() throws IllegalStateException
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		return name;
	}

	@Override
	public String getDisplayName() throws IllegalStateException
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		return displayName;
	}

	@Override
	public void setDisplayName(String s)
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		this.displayName = s;
	}

	@Override
	public String getPrefix() throws IllegalStateException
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		return prefix;
	}

	@Override
	public void setPrefix(String s)
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		this.prefix = s;
	}

	@Override
	public String getSuffix() throws IllegalStateException
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		return suffic;
	}

	@Override
	public void setSuffix(String s)
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		this.suffic = s;
	}

	@Override
	public ChatColor getColor() throws IllegalStateException
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		return color;
	}

	@Override
	public void setColor(ChatColor chatColor)
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		this.color = chatColor;
	}

	@Override
	public boolean allowFriendlyFire() throws IllegalStateException
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		return allowFriendlyFire;
	}

	@Override
	public void setAllowFriendlyFire(boolean b) throws IllegalStateException
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		this.allowFriendlyFire = b;
	}

	@Override
	public boolean canSeeFriendlyInvisibles() throws IllegalStateException
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		return canSeeFriendly;
	}

	@Override
	public void setCanSeeFriendlyInvisibles(boolean b) throws IllegalStateException
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		this.canSeeFriendly = b;
	}

	/** @deprecated  */
	@Override
	@Deprecated
	public NameTagVisibility getNameTagVisibility()
	{
		if (!registered)throw new IllegalStateException("Team not registered");

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
		if (!registered)throw new IllegalStateException("Team not registered");

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

		if (!registered)throw new IllegalStateException("Team not registered");
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
		if (!registered)throw new IllegalStateException("Team not registered");
		return entries.size();
	}

	@Override
	public Scoreboard getScoreboard()
	{
		return board;
	}

	/** @deprecated */
	@Override
	public void addPlayer(OfflinePlayer offlinePlayer)
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		entries.add(offlinePlayer.getName());
	}

	@Override
	public void addEntry(String s)
	{
		if (!registered)throw new IllegalStateException("Team not registered");
		entries.add(s);
	}

	/** @deprecated */
	@Override
	@Deprecated
	public boolean removePlayer(OfflinePlayer offlinePlayer)
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		return entries.remove(offlinePlayer.getName());
	}

	@Override
	public boolean removeEntry(String s)
	{
		if (!registered)throw new IllegalStateException("Team not registered");
		return entries.remove(s);

	}

	@Override
	public void unregister() throws IllegalStateException
	{
		if (!registered)
			throw new IllegalStateException("Team not registered");

		registered = false;
	}

	/** @deprecated */
	@Override
	@Deprecated
	public boolean hasPlayer(OfflinePlayer offlinePlayer)
	{
		if (!registered)throw new IllegalStateException("Team not registered");
		return entries.contains(offlinePlayer.getName());
	}

	@Override
	public boolean hasEntry(String s)
	{
		if (!registered)throw new IllegalStateException("Team not registered");
		return entries.contains(s);
	}

	@Override
	public OptionStatus getOption(Option option) throws IllegalStateException
	{
		if (!registered)throw new IllegalStateException("Team not registered");
		return options.get(option);
	}

	@Override
	public void setOption(Option option, OptionStatus optionStatus) throws IllegalStateException
	{
		if (!registered)throw new IllegalStateException("Team not registered");

		options.put(option, optionStatus);
	}
}
