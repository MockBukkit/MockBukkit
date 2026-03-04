package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.jetbrains.annotations.NotNull;

public class AgeableDataMock extends BlockDataMock implements Ageable
{

	/**
	 * Constructs a new {@link AgeableDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public AgeableDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link AgeableDataMock} based on an existing {@link AgeableDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected AgeableDataMock(@NotNull AgeableDataMock other)
	{
		super(other);
	}

	@Override
	public int getAge()
	{
		return this.get(BlockDataKey.AGE_KEY);
	}

	@Override
	public void setAge(int age)
	{
		Preconditions.checkArgument(age >= 0, "Age cannot be negative.");
		this.set(BlockDataKey.AGE_KEY, age);
	}

	@Override
	public int getMaximumAge()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_AGE);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull AgeableDataMock clone()
	{
		return new AgeableDataMock(this);
	}

}
