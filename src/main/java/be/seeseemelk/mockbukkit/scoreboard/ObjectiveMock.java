package be.seeseemelk.mockbukkit.scoreboard;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.entity.EntityMock;
import com.google.common.base.Preconditions;
import io.papermc.paper.scoreboard.numbers.NumberFormat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock implementation of an {@link Objective}.
 */
public class ObjectiveMock implements Objective
{

	private @Nullable ScoreboardMock scoreboard;
	private final @NotNull String name;
	private final @NotNull Criteria criteria;
	private final Map<String, ScoreMock> scores = new HashMap<>();
	private @NotNull Component displayName;
	private @Nullable DisplaySlot displaySlot;
	private RenderType renderType;

	/**
	 * Constructs a new {@link ObjectiveMock} with the provided parameters.
	 *
	 * @param scoreboard  The scoreboard this objective is part of.
	 * @param name        The name of the objective.
	 * @param displayName The display name of the objective.
	 * @param criteria    The criteria of the objective.
	 * @param renderType  The render type of the objective.
	 */
	public ObjectiveMock(@NotNull ScoreboardMock scoreboard, @NotNull String name, @Nullable Component displayName,
						 @NotNull Criteria criteria, @NotNull RenderType renderType)
	{
		Preconditions.checkNotNull(scoreboard, "When registering an Objective to the Scoreboard the scoreboard cannot be null.");
		Preconditions.checkNotNull(name, "The name cannot be null");
		Preconditions.checkNotNull(criteria, "The criteria cannot be null");

		this.scoreboard = scoreboard;
		this.name = name;
		this.displayName = displayName == null ? Component.empty() : displayName;
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
	public @NotNull String getName() throws IllegalStateException
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
		this.displayName = displayName == null ? Component.empty() : displayName;
	}

	@Override
	@Deprecated(since = "1.16")
	public @NotNull String getDisplayName() throws IllegalStateException
	{
		validate();
		return LegacyComponentSerializer.legacySection().serialize(displayName);
	}

	@Override
	@Deprecated(since = "1.16")
	public void setDisplayName(@NotNull String displayName) throws IllegalStateException, IllegalArgumentException
	{
		Preconditions.checkNotNull(displayName, "The display name cannot be null");
		Preconditions.checkArgument(displayName.length() <= 128, "The display name cannot be longer than 128 characters");

		validate();
		this.displayName = LegacyComponentSerializer.legacySection().deserialize(displayName);
	}

	@Override
	public @NotNull String getCriteria() throws IllegalStateException
	{
		validate();
		return this.criteria.getName();
	}

	@Override
	public @NotNull Criteria getTrackedCriteria() throws IllegalStateException
	{
		validate();
		return this.criteria;
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
	public @NotNull RenderType getRenderType() throws IllegalStateException
	{
		validate();
		return renderType;
	}

	@Override
	@Deprecated(since = "1.7.10")
	public @NotNull ScoreMock getScore(@NotNull OfflinePlayer player) throws IllegalArgumentException, IllegalStateException
	{
		Preconditions.checkNotNull(player, "The player cannot be null");
		validate();

		return getScore(player.getName());
	}

	@Override
	public @NotNull ScoreMock getScore(@NotNull String entry) throws IllegalArgumentException, IllegalStateException
	{
		Preconditions.checkNotNull(entry, "The entry cannot be null");
		Preconditions.checkArgument(entry.length() <= 40, "Objective entries cannot be longer than 40 characters");
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
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		return getScore(((EntityMock) entity).getScoreboardEntry());
	}

	@Override
	public boolean willAutoUpdateDisplay()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAutoUpdateDisplay(boolean autoUpdateDisplay)
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

	@Override
	public boolean isModifiable() throws IllegalStateException
	{
		return !getTrackedCriteria().isReadOnly();
	}

}
