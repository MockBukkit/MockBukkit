package be.seeseemelk.mockbukkit.scoreboard;

import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class ObjectiveMock implements Objective
{
	private final ScoreboardMock scoreboard;
	private final String name;
	private final String criteria;
	private String displayName;
	private DisplaySlot displaySlot;

	public ObjectiveMock(ScoreboardMock scoreboard, String name, String criteria)
	{
		this.scoreboard = scoreboard;
		this.name = name;
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setDisplaySlot(DisplaySlot slot) throws IllegalStateException
	{
		displaySlot = slot;
	}

	@Override
	public DisplaySlot getDisplaySlot() throws IllegalStateException
	{
		return displaySlot;
	}

	@Override
	public Score getScore(OfflinePlayer player) throws IllegalArgumentException, IllegalStateException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Score getScore(String entry) throws IllegalArgumentException, IllegalStateException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
