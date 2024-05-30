package be.seeseemelk.mockbukkit.scoreboard;

import be.seeseemelk.mockbukkit.entity.EntityMock;
import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection;

/**
 * Mock implementation of a {@link Scoreboard}.
 */
public class ScoreboardMock implements Scoreboard
{

	private static final String OFFLINE_PLAYER_CANNOT_BE_NULL = "OfflinePlayer cannot be null";
	private static final String ENTITY_CANNOT_BE_NULL = "Entity cannot be null";

	private final @NotNull Map<String, ObjectiveMock> objectives = new HashMap<>();
	private final @NotNull Map<DisplaySlot, ObjectiveMock> objectivesByDisplaySlot = new EnumMap<>(DisplaySlot.class);
	private final @NotNull Map<String, Team> teams = new HashMap<>();

	@Override
	@Deprecated(since = "1.13")
	public @NotNull ObjectiveMock registerNewObjective(@NotNull String name, @NotNull String criteria)
			throws IllegalArgumentException
	{
		return registerNewObjective(name, criteria, name, RenderType.INTEGER);
	}

	@Override
	public @NotNull ObjectiveMock registerNewObjective(@NotNull String name, @NotNull String criteria,
			@Nullable Component displayName) throws IllegalArgumentException
	{
		return registerNewObjective(name, criteria, displayName, RenderType.INTEGER);
	}

	@Override
	public @NotNull ObjectiveMock registerNewObjective(@NotNull String name, @NotNull String criteria,
			@Nullable Component displayName, @NotNull RenderType renderType) throws IllegalArgumentException
	{
		return registerNewObjective(name, Criteria.create(criteria), displayName, renderType);
	}

	@Override
	public @NotNull ObjectiveMock registerNewObjective(@NotNull String name, @NotNull Criteria criteria,
			@Nullable Component displayName) throws IllegalArgumentException
	{
		return registerNewObjective(name, criteria, displayName, RenderType.INTEGER);
	}

	@Override
	public @NotNull ObjectiveMock registerNewObjective(@NotNull String name, @NotNull Criteria criteria,
			@Nullable Component displayName, @NotNull RenderType renderType) throws IllegalArgumentException
	{
		Preconditions.checkNotNull(name, "Objective name cannot be null");
		Preconditions.checkNotNull(criteria, "Criteria cannot be null");
		Preconditions.checkNotNull(displayName, "Display name cannot be null");
		Preconditions.checkNotNull(renderType, "RenderType cannot be null");
		Preconditions.checkArgument(name.length() <= Short.MAX_VALUE,
				"The name '" + name + "' is longer than the limit of 32767 characters");
		Preconditions.checkArgument(!this.objectives.containsKey(name),
				"An objective of name '" + name + "' already exists");
		ObjectiveMock objective = new ObjectiveMock(this, name, displayName, criteria, renderType);
		this.objectives.put(name, objective);
		return objective;
	}

	@Override
	@Deprecated(since = "1.16")
	public @NotNull ObjectiveMock registerNewObjective(@NotNull String name, @NotNull String criteria,
			@NotNull String displayName) throws IllegalArgumentException
	{
		return registerNewObjective(name, criteria, displayName, RenderType.INTEGER);
	}

	@Override
	@Deprecated(since = "1.16")
	public @NotNull ObjectiveMock registerNewObjective(@NotNull String name, @NotNull String criteria,
			@NotNull String displayName, @NotNull RenderType renderType) throws IllegalArgumentException
	{
		return registerNewObjective(name, criteria, legacySection().deserialize(displayName), renderType);
	}

	@Override
	public @NotNull ObjectiveMock registerNewObjective(@NotNull String name, @NotNull Criteria criteria,
			@NotNull String displayName) throws IllegalArgumentException
	{
		return registerNewObjective(name, criteria, displayName, RenderType.INTEGER);
	}

	@Override
	public @NotNull ObjectiveMock registerNewObjective(@NotNull String name, @NotNull Criteria criteria,
			@NotNull String displayName, @NotNull RenderType renderType) throws IllegalArgumentException
	{
		return registerNewObjective(name, criteria, legacySection().deserialize(displayName), renderType);
	}

	@Override
	public ObjectiveMock getObjective(@NotNull String name) throws IllegalArgumentException
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		return objectives.get(name);
	}

	@Override
	public @NotNull Set<Objective> getObjectivesByCriteria(@NotNull String criteria) throws IllegalArgumentException
	{
		Preconditions.checkNotNull(criteria, "Criteria cannot be null");
		return objectives.values().stream().filter(objective -> objective.getCriteria().equals(criteria))
				.collect(Collectors.toSet());
	}

	@Override
	public @NotNull Set<Objective> getObjectivesByCriteria(@NotNull Criteria criteria) throws IllegalArgumentException
	{
		Preconditions.checkNotNull(criteria, "Criteria cannot be null");
		return objectives.values().stream().filter(objective -> objective.getTrackedCriteria().equals(criteria))
				.collect(Collectors.toSet());
	}

	@Override
	public @NotNull Set<Objective> getObjectives()
	{
		return Set.copyOf(objectives.values());
	}

	@Override
	public ObjectiveMock getObjective(@NotNull DisplaySlot slot) throws IllegalArgumentException
	{
		return objectivesByDisplaySlot.get(slot);
	}

	@Override
	public @NotNull Set<Score> getScores(@NotNull OfflinePlayer player) throws IllegalArgumentException
	{
		Preconditions.checkNotNull(player, OFFLINE_PLAYER_CANNOT_BE_NULL);
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
	public void resetScores(@NotNull OfflinePlayer player) throws IllegalArgumentException
	{
		Preconditions.checkNotNull(player, OFFLINE_PLAYER_CANNOT_BE_NULL);
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
	public Team getPlayerTeam(@NotNull OfflinePlayer player) throws IllegalArgumentException
	{
		Preconditions.checkNotNull(player, OFFLINE_PLAYER_CANNOT_BE_NULL);
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
		Preconditions.checkNotNull(teamName, "Team name cannot be null");
		return teams.get(teamName);
	}

	@Override
	public @NotNull Set<Team> getTeams()
	{
		return Set.copyOf(teams.values());
	}

	@Override
	public @NotNull Team registerNewTeam(@NotNull String name) throws IllegalArgumentException
	{
		if (teams.containsKey(name))
		{
			throw new IllegalArgumentException("Team name '" + name + "' is already in use");
		}
		Team team = new TeamMock(name, this);
		teams.put(name, team);
		return team;
	}

	@Override
	@Deprecated(since = "1.7.10")
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
		Preconditions.checkNotNull(slot, "Slot cannot be null");
		objectivesByDisplaySlot.remove(slot);
	}

	@Override
	public @NotNull Set<Score> getScoresFor(@NotNull Entity entity) throws IllegalArgumentException
	{
		Preconditions.checkNotNull(entity, ENTITY_CANNOT_BE_NULL);
		return getScores(((EntityMock) entity).getScoreboardEntry());
	}

	@Override
	public void resetScoresFor(@NotNull Entity entity) throws IllegalArgumentException
	{
		Preconditions.checkNotNull(entity, ENTITY_CANNOT_BE_NULL);
		resetScores(((EntityMock) entity).getScoreboardEntry());
	}

	@Override
	public @Nullable Team getEntityTeam(@NotNull Entity entity) throws IllegalArgumentException
	{
		Preconditions.checkNotNull(entity, ENTITY_CANNOT_BE_NULL);
		return getEntryTeam(((EntityMock) entity).getScoreboardEntry());
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

	/**
	 * Removes a team from this scoreboard.
	 *
	 * @param teamMock The team to remove.
	 */
	protected void unregister(@NotNull TeamMock teamMock)
	{
		teams.remove(teamMock.getName());
	}

}
