package org.mockbukkit.mockbukkit.scoreboard;

import org.mockbukkit.mockbukkit.AsyncCatcher;
import org.bukkit.scoreboard.ScoreboardManager;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link ScoreboardManager}.
 */
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
		AsyncCatcher.catchOp("scoreboard registration");
		return new ScoreboardMock();
	}

}
