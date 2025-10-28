package org.mockbukkit.mockbukkit.structure;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.structure.Palette;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.block.state.BlockStateMockFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaletteMock implements Palette
{

	/**
	 * Helper function to create a palette based on the materials.
	 *
	 * @param materials The materials.
	 *
	 * @return The palette.
	 */
	public static PaletteMock of(@NotNull Material... materials)
	{
		Preconditions.checkNotNull(materials, "materials must not be null");

		List<BlockState> blockStates = new ArrayList<>(materials.length);
		for (Material material : materials)
		{
			BlockState blockState = BlockStateMockFactory.mock(material);
			blockStates.add(blockState);
		}
		return new PaletteMock(blockStates);
	}

	private final List<BlockState> blockStates;

	public PaletteMock(@NotNull List<BlockState> blockStates)
	{
		Preconditions.checkNotNull(blockStates, "blockStates cannot be null");
		this.blockStates = blockStates;
	}

	@Override
	public @NotNull List<BlockState> getBlocks()
	{
		return Collections.unmodifiableList(blockStates);
	}

	@Override
	public int getBlockCount()
	{
		return this.blockStates.size();
	}

}
