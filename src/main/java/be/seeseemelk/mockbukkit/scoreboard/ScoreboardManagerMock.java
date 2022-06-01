package be.seeseemelk.mockbukkit.scoreboard;

import be.seeseemelk.mockbukkit.AsyncCatcher;
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
		AsyncCatcher.catchOp("scoreboard registration");
		return new ScoreboardMock();
	}

}
