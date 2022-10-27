package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.DaylightDetector;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of {@link DaylightDetector}.
 */
public class DaylightDetectorMock extends TileStateMock implements DaylightDetector
{

	public DaylightDetectorMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.DAYLIGHT_DETECTOR);
	}

	protected DaylightDetectorMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.DAYLIGHT_DETECTOR);
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
