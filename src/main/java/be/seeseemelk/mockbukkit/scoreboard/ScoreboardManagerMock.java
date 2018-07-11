package be.seeseemelk.mockbukkit.scoreboard;

import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreboardManagerMock implements ScoreboardManager
{
	private final ScoreboardMock mainScoreboard = new ScoreboardMock();

	@Override
	public ScoreboardMock getMainScoreboard()
	{
		return mainScoreboard;
	}

	@Override
	public ScoreboardMock getNewScoreboard()
	{
		return new ScoreboardMock();
	}

}
