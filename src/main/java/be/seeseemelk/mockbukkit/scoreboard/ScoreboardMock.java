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
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class ScoreboardMock implements Scoreboard
{
	private Map<String, ObjectiveMock> objectives = new HashMap<>();
	private Map<DisplaySlot, ObjectiveMock> objectivesByDisplaySlot = new EnumMap<>(DisplaySlot.class);
	
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
		return objectives.values().stream().filter(objective -> objective.getCriteria().equals(criteria))
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Set<Score> getScores(String entry) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void resetScores(OfflinePlayer player) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void resetScores(String entry) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Team getPlayerTeam(OfflinePlayer player) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Team getEntryTeam(String entry) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Team getTeam(String teamName) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Set<Team> getTeams()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Team registerNewTeam(String name) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Set<OfflinePlayer> getPlayers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Set<String> getEntries()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void clearSlot(DisplaySlot slot) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	/**
	 * Sets the objective to a specific slot.
	 * 
	 * @param objective The objective to set to the slot.
	 * @param slot The slot to set the objective to.
	 */
	protected void setDisplaySlot(ObjectiveMock objective, DisplaySlot slot)
	{
		objectivesByDisplaySlot.put(slot, objective);
	}
	
	/**
	 * Removes an objective off this scoreboard.
	 * 
	 * @param objectiveMock The objective to remove.
	 */
	protected void unregister(ObjectiveMock objectiveMock)
	{
		objectives.remove(objectiveMock.getName());
		if (objectiveMock.getDisplaySlot() != null)
			objectivesByDisplaySlot.remove(objectiveMock.getDisplaySlot());
	}
	
}
