package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.DaylightDetector;
import org.jetbrains.annotations.NotNull;

public class DaylightDetectorMock extends TileStateMock implements DaylightDetector
{

	protected DaylightDetectorMock(@NotNull Material material)
	{
		super(material);
		if (material != Material.DAYLIGHT_DETECTOR)
			throw new IllegalArgumentException("Cannot create a Daylight Detector state from " + material);
	}

	protected DaylightDetectorMock(@NotNull Block block)
	{
		super(block);
		if (block.getType() != Material.DAYLIGHT_DETECTOR)
			throw new IllegalArgumentException("Cannot create a Daylight Detector state from " + block.getType());
	}

	protected DaylightDetectorMock(@NotNull DaylightDetectorMock state)
	{
		super(state);
	}

	@Override
	public @NotNull DaylightDetectorMock getSnapshot()
	{
		return new DaylightDetectorMock(this);
	}

}
