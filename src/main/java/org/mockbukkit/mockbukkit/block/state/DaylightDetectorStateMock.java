package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.DaylightDetector;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link DaylightDetector}.
 *
 * @see TileStateMock
 */
public class DaylightDetectorStateMock extends TileStateMock implements DaylightDetector
{

	/**
	 * Constructs a new {@link DaylightDetectorStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#DAYLIGHT_DETECTOR}
	 *
	 * @param material The material this state is for.
	 */
	public DaylightDetectorStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.DAYLIGHT_DETECTOR);
	}

	/**
	 * Constructs a new {@link DaylightDetectorStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#DAYLIGHT_DETECTOR}
	 *
	 * @param block The block this state is for.
	 */
	protected DaylightDetectorStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.DAYLIGHT_DETECTOR);
	}

	/**
	 * Constructs a new {@link CreatureSpawnerStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected DaylightDetectorStateMock(@NotNull DaylightDetectorStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull DaylightDetectorStateMock getSnapshot()
	{
		return new DaylightDetectorStateMock(this);
	}

}
