package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.data.Orientable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class OrientableMock extends BlockDataMock implements Orientable
{

	/**
	 * Constructs a new {@link Orientable} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public OrientableMock(@NotNull Material material)
	{
		super(material);
	}

	@Override
	public @NotNull Axis getAxis()
	{
		return this.get(BlockDataKey.AXIS);
	}

	@Override
	public void setAxis(@NotNull Axis axis)
	{
		Preconditions.checkArgument(this.getAxes().contains(axis), "Invalid Axis. Possible values are: %s", this.getAxes());
		this.set(BlockDataKey.AXIS, axis);
	}

	@Override
	public @NotNull Set<Axis> getAxes()
	{
        return Arrays.stream(Axis.values()).collect(Collectors.toSet());
	}

}
