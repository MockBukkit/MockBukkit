package be.seeseemelk.mockbukkit.scoreboard;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class ScoreboardMock implements Scoreboard
{
	private Map<String, ObjectiveMock> objectives = new HashMap<>();
	private Map<DisplaySlot, ObjectiveMock> objectivesByDisplaySlot = new EnumMap<>(DisplaySlot.class);
	private Map<String, Team> teams = new HashMap<>();

	@Override
	public @NotNull ObjectiveMock registerNewObjective(@NotNull String name, @NotNull String criteria) throws IllegalArgumentException
	{
		return registerNewObjective(name, criteria, name, RenderType.INTEGER);
	}

	@Override
	public @NotNull ObjectiveMock registerNewObjective(@NotNull String name, @NotNull String criteria, @NotNull String displayName)
			throws IllegalArgumentException
	{
		return registerNewObjective(name, criteria, displayName, RenderType.INTEGER);
	}

	@Override
	public @NotNull ObjectiveMock registerNewObjective(@NotNull String name, @NotNull String criteria, @NotNull String displayName, @NotNull RenderType renderType)
			throws IllegalArgumentException
	{
		ObjectiveMock objective = new ObjectiveMock(this, name, displayName, criteria, renderType);
		objectives.put(name, objective);
		return objective;
	}

	@Override
	public ObjectiveMock getObjective(@NotNull String name) throws IllegalArgumentException
	{
		return objectives.get(name);
	}

	@Override
	public @NotNull Set<Objective> getObjectivesByCriteria(@NotNull String criteria) throws IllegalArgumentException
	{
		return objectives.values().stream().filter(objective -> objective.getCriteria().equals(criteria))
				.collect(Collectors.toSet());
	}

	@Override
	public @NotNull Set<Objective> getObjectives()
	{
		return Collections.unmodifiableSet(new HashSet<>(objectives.values()));
	}

	@Override
	public ObjectiveMock getObjective(@NotNull DisplaySlot slot) throws IllegalArgumentException
	{
		return objectivesByDisplaySlot.get(slot);
	}

	@Override
	public @NotNull Set<Score> getScores(OfflinePlayer player) throws IllegalArgumentException
	{
		return getScores(player.getName());
	}

	@Override
	public @NotNull Set<Score> getScores(@NotNull String entry) throws IllegalArgumentException
	{
		Set<Score> scores = new HashSet<>();

		for (Objective o : objectives.values())
		{
			scores.add(o.getScore(entry));
		}

		return scores;
	}

	@Override
	public void resetScores(OfflinePlayer player) throws IllegalArgumentException
	{
		resetScores(player.getName());
	}

	@Override
	public void resetScores(@NotNull String entry) throws IllegalArgumentException
	{
		for (Objective o : objectives.values())
		{
			// Since Scores are always non-null, we don't need a null-check here.
			o.getScore(entry).setScore(0);
		}
	}

	@Override
	public Team getPlayerTeam(OfflinePlayer player) throws IllegalArgumentException
	{
		return getEntryTeam(player.getName());
	}

	@Override
	public Team getEntryTeam(@NotNull String entry) throws IllegalArgumentException
	{
		for (Team t : teams.values())
		{
			if (t.hasEntry(entry))
			{
				return t;
			}
		}

		return null;
	}

	@Override
	public Team getTeam(@NotNull String teamName) throws IllegalArgumentException
	{
		return teams.get(teamName);
	}

	@Override
	public @NotNull Set<Team> getTeams()
	{
		return Collections.unmodifiableSet(new HashSet<>(teams.values()));
	}

	@Override
	public @NotNull Team registerNewTeam(@NotNull String name) throws IllegalArgumentException
	{
		Team team = new TeamMock(name, this);
		teams.put(name, team);
		return team;
	}

	@Override
	public @NotNull Set<OfflinePlayer> getPlayers()
	{
		Set<OfflinePlayer> players = new HashSet<>();

		for (Team t : teams.values())
		{
			players.addAll(t.getPlayers());
		}

		return players;
	}

	@Override
	public @NotNull Set<String> getEntries()
	{
		Set<String> entries = new HashSet<>();

		for (Team t : teams.values())
		{
			entries.addAll(t.getEntries());
		}

		return entries;
	}

	@Override
	public void clearSlot(@NotNull DisplaySlot slot) throws IllegalArgumentException
	{
		Objective o = objectivesByDisplaySlot.remove(slot);

		if (o != null)
		{
			objectives.remove(o.getName());
		}

	}

	/**
	 * Sets the objective to a specific slot.
	 *
	 * @param objective The objective to set to the slot.
	 * @param slot      The slot to set the objective to.
	 */
	protected void setDisplaySlot(@NotNull ObjectiveMock objective, DisplaySlot slot)
	{
		objectivesByDisplaySlot.put(slot, objective);
	}

	/**
	 * Removes an objective off this scoreboard.
	 *
	 * @param objectiveMock The objective to remove.
	 */
	protected void unregister(@NotNull ObjectiveMock objectiveMock)
	{
		if (objectiveMock.getDisplaySlot() != null)
		{
			objectivesByDisplaySlot.remove(objectiveMock.getDisplaySlot());
		}

		objectives.remove(objectiveMock.getName());
	}

}
