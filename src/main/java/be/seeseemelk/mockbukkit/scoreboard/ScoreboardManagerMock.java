package be.seeseemelk.mockbukkit.scoreboard;

import org.bukkit.scoreboard.ScoreboardManager;

import org.jetbrains.annotations.NotNull;

public class ScoreboardManagerMock implements ScoreboardManager
{
	private final ScoreboardMock mainScoreboard = new ScoreboardMock();

	@Override
	public @NotNull ScoreboardMock getMainScoreboard()
	{
		return mainScoreboard;
	}

	@Override
	public @NotNull ScoreboardMock getNewScoreboard()
	{
		return new ScoreboardMock();
	}

}
