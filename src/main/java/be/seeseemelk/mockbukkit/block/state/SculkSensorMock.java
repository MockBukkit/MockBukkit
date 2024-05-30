package be.seeseemelk.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.SculkSensor;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link SculkSensor}.
 *
 * @see TileStateMock
 */
public class SculkSensorMock extends TileStateMock implements SculkSensor
{

	private int lastVibrationFrequency;
	private int listenerRange;

	/**
	 * Constructs a new {@link SculkSensorMock} for the provided {@link Material}.
	 * Only supports {@link Material#SCULK_SENSOR}
	 *
	 * @param material The material this state is for.
	 */
	public SculkSensorMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SCULK_SENSOR);
	}

	/**
	 * Constructs a new {@link SculkSensorMock} for the provided {@link Block}.
	 * Only supports {@link Material#SCULK_SENSOR}
	 *
	 * @param block The block this state is for.
	 */
	protected SculkSensorMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SCULK_SENSOR);
	}

	/**
	 * Constructs a new {@link SculkSensorMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected SculkSensorMock(@NotNull SculkSensorMock state)
	{
		super(state);
		this.lastVibrationFrequency = state.lastVibrationFrequency;
		this.listenerRange = state.listenerRange;
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new SculkSensorMock(this);
	}

	@Override
	public int getLastVibrationFrequency()
	{
		return this.lastVibrationFrequency;
	}

	@Override
	public void setLastVibrationFrequency(int lastVibrationFrequency)
	{
		Preconditions.checkArgument(0 <= lastVibrationFrequency && lastVibrationFrequency <= 15,
				"Vibration frequency must be between 0-15");
		this.lastVibrationFrequency = lastVibrationFrequency;
	}

	@Override
	public int getListenerRange()
	{
		return this.listenerRange;
	}

	@Override
	public void setListenerRange(int range)
	{
		Preconditions.checkArgument(range > 0, "Vibration listener range must be greater than 0");
		this.listenerRange = range;
	}

}
