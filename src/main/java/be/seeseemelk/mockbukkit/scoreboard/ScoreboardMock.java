package be.seeseemelk.mockbukkit.scoreboard;

import java.util.*;
import java.util.stream.Collectors;

import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

@SuppressWarnings("deprecation")
public class ScoreboardMock implements Scoreboard
{
	private Map<String, ObjectiveMock> objectives = new HashMap<>();
	private Map<DisplaySlot, ObjectiveMock> objectivesByDisplaySlot = new EnumMap<>(DisplaySlot.class);
	private Map<String,Team> teams = new HashMap<>();

	@Override
	public ObjectiveMock registerNewObjective(String name, String criteria) throws IllegalArgumentException
	{
		ObjectiveMock objective = new ObjectiveMock(this, name, criteria);
		objectives.put(name, objective);
		return objective;
	}

	@Override
	public ObjectiveMock getObjective(String name) throws IllegalArgumentException
	{
		return objectives.get(name);
	}

	@Override
	public Set<Objective> getObjectivesByCriteria(String criteria) throws IllegalArgumentException
	{
		return objectives.values().stream()
				.filter(objective -> objective.getCriteria().equals(criteria))
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Objective> getObjectives()
	{
		return Collections.unmodifiableSet(new HashSet<>(objectives.values()));
	}

	@Override
	public ObjectiveMock getObjective(DisplaySlot slot) throws IllegalArgumentException
	{
		return objectivesByDisplaySlot.get(slot);
	}

	@Override
	public Set<Score> getScores(OfflinePlayer player) throws IllegalArgumentException
	{
		return getScores(player.getName());
	}

	@Override
	public Set<Score> getScores(String entry) throws IllegalArgumentException
	{
		Set<Score> scores = new HashSet<>();
		for(Objective o: objectives.values()){
			Score score = o.getScore(entry);
			if(score != null){
				scores.add(score);
			}
		}
		return scores;
	}

	@Override
	public void resetScores(OfflinePlayer player) throws IllegalArgumentException
	{
		resetScores(player.getName());
	}

	@Override
	public void resetScores(String entry) throws IllegalArgumentException
	{
		for(Objective o: objectives.values()){
			Score score = o.getScore(entry);
			if(score != null){
				score.setScore(0);
			}
		}
	}

	@Override
	public Team getPlayerTeam(OfflinePlayer player) throws IllegalArgumentException
	{
		return getEntryTeam(player.getName());
	}

	@Override
	public Team getEntryTeam(String entry) throws IllegalArgumentException
	{
		for(Team t:teams.values()){
			if(t.hasEntry(entry))return t;
		}
		return null;
	}

	@Override
	public Team getTeam(String teamName) throws IllegalArgumentException
	{
		return teams.get(teamName);
	}

	@Override
	public Set<Team> getTeams()
	{
		HashSet<Team> v = new HashSet<>(teams.values());
		return v;
	}

	@Override
	public Team registerNewTeam(String name) throws IllegalArgumentException
	{
		Team team = new TeamMock(name,this);
		teams.put(name,team);
		return team;
	}

	@Override
	public Set<OfflinePlayer> getPlayers()
	{
		Set<OfflinePlayer> players = new HashSet<>();
		for(Team t:teams.values()){
			players.addAll(t.getPlayers());
		}
		return players;
	}

	@Override
	public Set<String> getEntries()
	{
		Set<String> entries = new HashSet<>();
		for(Team t:teams.values()){
			entries.addAll(t.getEntries());
		}
		return entries;
	}

	@Override
	public void clearSlot(DisplaySlot slot) throws IllegalArgumentException
	{
		Objective o = objectivesByDisplaySlot.remove(slot);
		if(o != null)objectives.remove(o.getName());
		
	}
	
	/**
	 * Sets the objective to a specific slot.
	 * @param objective The objective to set to the slot.
	 * @param slot The slot to set the objective to.
	 */
	protected void setDisplaySlot(ObjectiveMock objective, DisplaySlot slot)
	{
		objectivesByDisplaySlot.put(slot, objective);
	}

	/**
	 * Removes an objective off this scoreboard.
	 * @param objectiveMock The objective to remove.
	 */
	protected void unregister(ObjectiveMock objectiveMock)
	{
		objectives.remove(objectiveMock.getName());
		if (objectiveMock.getDisplaySlot() != null)
			objectivesByDisplaySlot.remove(objectiveMock.getDisplaySlot());
	}

	@Override
	public Objective registerNewObjective(String name, String criteria, String displayName)
			throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Objective registerNewObjective(String name, String criteria, String displayName, RenderType renderType)
			throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
