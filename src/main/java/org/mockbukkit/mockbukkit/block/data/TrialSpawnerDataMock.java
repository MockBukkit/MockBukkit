package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.type.TrialSpawner;
import org.jetbrains.annotations.NotNull;

public class TrialSpawnerDataMock extends BlockDataMock implements TrialSpawner
{

	/**
	 * Constructs a new {@link TrialSpawnerDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public TrialSpawnerDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link TrialSpawnerDataMock} based on an existing {@link TrialSpawnerDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected TrialSpawnerDataMock(@NotNull TrialSpawnerDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull State getTrialSpawnerState()
	{
		return this.get(BlockDataKey.TRIAL_SPAWNER_STATE);
	}

	@Override
	public void setTrialSpawnerState(@NotNull State state)
	{
		Preconditions.checkArgument(state != null, "state cannot be null!");
		this.set(BlockDataKey.TRIAL_SPAWNER_STATE, state);
	}

	@Override
	public boolean isOminous()
	{
		return this.get(BlockDataKey.OMINOUS);
	}

	@Override
	public void setOminous(boolean ominous)
	{
		this.set(BlockDataKey.OMINOUS, ominous);
	}

	@Override
	@SuppressWarnings({"java:S2975", "java:S1182"})
	public @NotNull TrialSpawnerDataMock clone()
	{
		return new TrialSpawnerDataMock(this);
	}

}
