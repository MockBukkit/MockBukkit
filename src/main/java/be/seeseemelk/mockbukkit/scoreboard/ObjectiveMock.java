package be.seeseemelk.mockbukkit.scoreboard;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang.Validate;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ObjectiveMock implements Objective
{
	private ScoreboardMock scoreboard;
	private final String name;
	private final String criteria;
	private final Map<String, ScoreMock> scores = new HashMap<>();
	private Component displayName;
	private DisplaySlot displaySlot;
	private RenderType renderType;

	public ObjectiveMock(@NotNull ScoreboardMock scoreboard, @NotNull String name, @NotNull String displayName,
						 @NotNull String criteria, @NotNull RenderType renderType)
	{
		Validate.notNull(scoreboard, "When registering an Objective to the Scoreboard the scoreboard cannot be null.");
		Validate.notNull(name, "The name cannot be null");
		Validate.notNull(criteria, "The criteria cannot be null");

		this.scoreboard = scoreboard;
		this.name = name;
		this.displayName = Component.text(displayName);
		this.criteria = criteria;
		this.renderType = renderType;
	}

	/**
	 * This method checks if this {@link ObjectiveMock} is still valid. If it has been unregistered, the method will
	 * throw an {@link IllegalStateException}.
	 *
	 * @throws IllegalStateException if this objective has been unregistered.
	 */
	private void validate() throws IllegalStateException
	{
		if (!isRegistered())
		{
			throw new IllegalStateException("This objective is no longer registered.");
		}
	}

	@Override
	public String getName() throws IllegalStateException
	{
		validate();
		return name;
	}

	@Override
	public @NotNull Component displayName() throws IllegalStateException
	{
		return displayName;
	}

	@Override
	public void displayName(@Nullable Component displayName) throws IllegalStateException, IllegalArgumentException
	{
		this.displayName = displayName;
	}

	@Override
	@Deprecated
	public String getDisplayName() throws IllegalStateException
	{
		validate();
		return LegacyComponentSerializer.legacySection().serialize(displayName);
	}

	@Override
	@Deprecated
	public void setDisplayName(String displayName) throws IllegalStateException, IllegalArgumentException
	{
		Validate.notNull(displayName, "The display name cannot be null");
		Validate.isTrue(displayName.length() <= 128, "The display name cannot be longer than 128 characters");

		validate();
		this.displayName = LegacyComponentSerializer.legacySection().deserialize(displayName);
	}

	@Override
	public String getCriteria() throws IllegalStateException
	{
		validate();
		return criteria;
	}

	@Override
	public ScoreboardMock getScoreboard()
	{
		return scoreboard;
	}

	@Override
	public void unregister() throws IllegalStateException
	{
		// To unregister the Objective... it must be registered :o
		validate();

		scoreboard.unregister(this);
		scoreboard = null;
	}

	/**
	 * Checks if the objective is still registered.
	 *
	 * @return {@code true} if the objective is still registered, {@code false} if it has been unregistered.
	 */
	public boolean isRegistered()
	{
		return scoreboard != null && scoreboard.getObjectives().contains(this);
	}

	@Override
	public void setDisplaySlot(@Nullable DisplaySlot slot) throws IllegalStateException
	{
		validate();
		displaySlot = slot;
		scoreboard.setDisplaySlot(this, slot);
	}

	@Override
	public DisplaySlot getDisplaySlot() throws IllegalStateException
	{
		validate();
		return displaySlot;
	}

	@Override
	public void setRenderType(@NotNull RenderType renderType) throws IllegalStateException
	{
		validate();
		this.renderType = renderType;
	}

	@Override
	public RenderType getRenderType() throws IllegalStateException
	{
		validate();
		return renderType;
	}

	@Override
	@Deprecated
	public ScoreMock getScore(@NotNull OfflinePlayer player) throws IllegalArgumentException, IllegalStateException
	{
		Validate.notNull(player, "The player cannot be null");
		validate();

		return getScore(player.getName());
	}

	@Override
	public ScoreMock getScore(@NotNull String entry) throws IllegalArgumentException, IllegalStateException
	{
		Validate.notNull(entry, "The entry cannot be null");
		Validate.isTrue(entry.length() <= 40, "Objective entries cannot be longer than 40 characters");
		validate();

		ScoreMock score = scores.get(entry);

		if (score != null)
		{
			return score;
		}
		else
		{
			score = new ScoreMock(this, entry);
			scores.put(entry, score);
			return score;
		}
	}

	@Override
	public @NotNull Score getScoreFor(@NotNull Entity entity) throws IllegalArgumentException, IllegalStateException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isModifiable() throws IllegalStateException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
