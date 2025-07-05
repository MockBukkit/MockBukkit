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

}
