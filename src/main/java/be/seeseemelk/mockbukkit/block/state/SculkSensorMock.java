package be.seeseemelk.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.SculkSensor;
import org.jetbrains.annotations.NotNull;

public class SculkSensorMock extends TileStateMock implements SculkSensor
{

	private int lastVibrationFrequency;
	private int listenerRange;

	public SculkSensorMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SCULK_SENSOR);
	}

	protected SculkSensorMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SCULK_SENSOR);
	}

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
		Preconditions.checkArgument(0 <= lastVibrationFrequency && lastVibrationFrequency <= 15, "Vibration frequency must be between 0-15");
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
