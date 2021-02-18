package be.seeseemelk.mockbukkit.scoreboard;

import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreMock implements Score
{
	private final ObjectiveMock objective;
	private final String entry;
	private OfflinePlayer player = null;
	private int score = 0;
	private boolean set = false;

	public ScoreMock(ObjectiveMock objective, String entry)
	{
		this.objective = objective;
		this.entry = entry;
	}

	/**
	 * Sets the player that this score is tracking.
	 * @param player The player to track.
	 */
	public void setPlayer(OfflinePlayer player)
	{
		this.player = player;
	}

	@Override
	public OfflinePlayer getPlayer()
	{
		return player;
	}

	@Override
	public String getEntry()
	{
		return entry;
	}

	@Override
	public ObjectiveMock getObjective()
	{
		return objective;
	}

	@Override
	public int getScore() throws IllegalStateException
	{
		if (objective.isRegistered())
			return score;
		else
			throw new IllegalStateException("Objective is not registered");
	}

	@Override
	public void setScore(int score) throws IllegalStateException
	{
		this.score = score;
		set = true;
	}

	@Override
	public boolean isScoreSet() throws IllegalStateException
	{
		return set;
	}

	@Override
	public Scoreboard getScoreboard()
	{
		return objective.getScoreboard();
	}

}
