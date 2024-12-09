package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CalibratedSculkSensor;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link CalibratedSculkSensor}.
 *
 * @see SculkSensorStateMock
 */
public class CalibratedSculkSensorStateMock extends SculkSensorStateMock implements CalibratedSculkSensor
{

	protected CalibratedSculkSensorStateMock(@NotNull Block block)
	{
		super(block);
	}

	public CalibratedSculkSensorStateMock(@NotNull Material material)
	{
		super(material);
	}

	protected CalibratedSculkSensorStateMock(@NotNull CalibratedSculkSensorStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull CalibratedSculkSensorStateMock getSnapshot()
	{
		return new CalibratedSculkSensorStateMock(this);
	}

	@Override
	public @NotNull CalibratedSculkSensorStateMock copy()
	{
		return new CalibratedSculkSensorStateMock(this);
	}

}
