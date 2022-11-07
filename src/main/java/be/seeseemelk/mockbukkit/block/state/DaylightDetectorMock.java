package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.DaylightDetector;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link DaylightDetector}.
 *
 * @see TileStateMock
 */
public class DaylightDetectorMock extends TileStateMock implements DaylightDetector
{

	/**
	 * Constructs a new {@link DaylightDetectorMock} for the provided {@link Material}.
	 * Only supports {@link Material#DAYLIGHT_DETECTOR}
	 *
	 * @param material The material this state is for.
	 */
	public DaylightDetectorMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.DAYLIGHT_DETECTOR);
	}

	/**
	 * Constructs a new {@link DaylightDetectorMock} for the provided {@link Block}.
	 * Only supports {@link Material#DAYLIGHT_DETECTOR}
	 *
	 * @param block The block this state is for.
	 */
	protected DaylightDetectorMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.DAYLIGHT_DETECTOR);
	}

	/**
	 * Constructs a new {@link CreatureSpawnerMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
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
