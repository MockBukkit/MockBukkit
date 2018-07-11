package be.seeseemelk.mockbukkit.scoreboard;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class ObjectiveMock implements Objective
{
	private final ScoreboardMock scoreboard;
	private final String name;
	private final String criteria;
	private final Map<String, ScoreMock> scores = new HashMap<>();
	private String displayName;
	private DisplaySlot displaySlot;

	public ObjectiveMock(ScoreboardMock scoreboard, String name, String criteria)
	{
		this.scoreboard = scoreboard;
		this.name = name;
		this.displayName = name;
		this.criteria = criteria;
	}

	@Override
	public String getName() throws IllegalStateException
	{
		return name;
	}

	@Override
	public String getDisplayName() throws IllegalStateException
	{
		return displayName;
	}

	@Override
	public void setDisplayName(String displayName) throws IllegalStateException, IllegalArgumentException
	{
		this.displayName = displayName;
	}

	@Override
	public String getCriteria() throws IllegalStateException
	{
		return criteria;
	}

	@Override
	public boolean isModifiable() throws IllegalStateException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ScoreboardMock getScoreboard()
	{
		return scoreboard;
	}

	@Override
	public void unregister() throws IllegalStateException
	{
		scoreboard.unregister(this);
	}
	
	/**
	 * Checks if the objective is still registered.
	 * @return {@code true} if the objective is still registered, {@code false} if it has been unregistered.
	 */
	public boolean isRegistered()
	{
		return scoreboard.getObjectives().contains(this);
	}

	@Override
	public void setDisplaySlot(DisplaySlot slot) throws IllegalStateException
	{
		displaySlot = slot;
		scoreboard.setDisplaySlot(this, slot);
	}

	@Override
	public DisplaySlot getDisplaySlot() throws IllegalStateException
	{
		return displaySlot;
	}

	@Override
	public ScoreMock getScore(OfflinePlayer player) throws IllegalArgumentException, IllegalStateException
	{
		return getScore(player.getName());	
	}

	@Override
	public ScoreMock getScore(String entry) throws IllegalArgumentException, IllegalStateException
	{
		if (scores.containsKey(entry))
			return scores.get(entry);
		else
		{
			ScoreMock score = new ScoreMock(this, entry);
			scores.put(entry, score);
			return score;
		}
	}

}
