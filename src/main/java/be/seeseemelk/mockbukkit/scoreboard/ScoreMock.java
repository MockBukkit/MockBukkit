package be.seeseemelk.mockbukkit.scoreboard;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import io.papermc.paper.scoreboard.numbers.NumberFormat;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link Score}.
 */
public class ScoreMock implements Score
{

	private final ObjectiveMock objective;
	private final String entry;
	private @Nullable OfflinePlayer player = null;
	private int score = 0;
	private boolean set = false;

	/**
	 * Constructs a new {@link ScoreMock} for the provided objective with the specified entry.
	 *
	 * @param objective The objective.
	 * @param entry     The entry.
	 */
	public ScoreMock(ObjectiveMock objective, String entry)
	{
		this.objective = objective;
		this.entry = entry;
	}

	/**
	 * Sets the player that this score is tracking.
	 *
	 * @param player The player to track.
	 */
	public void setPlayer(OfflinePlayer player)
	{
		this.player = player;
	}

	@Override
	@Deprecated(since = "1.7.10")
	public @NotNull OfflinePlayer getPlayer()
	{
		return player;
	}

	@Override
	public @NotNull String getEntry()
	{
		return entry;
	}

	@Override
	public @NotNull ObjectiveMock getObjective()
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

	@Override
	public void resetScore() throws IllegalStateException
	{
		score = 0;
		set = false;
	}

	@Override
	public boolean isTriggerable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTriggerable(boolean triggerable)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Component customName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void customName(@Nullable Component customName)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable NumberFormat numberFormat()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void numberFormat(@Nullable NumberFormat numberFormat)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
