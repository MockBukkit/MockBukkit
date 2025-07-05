package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class FenceDataMockTest
{

	private FenceDataMock fenceData;

	@BeforeEach
	void setUp()
	{
		this.fenceData = new FenceDataMock(Material.ACACIA_FENCE);
	}

	@ParameterizedTest
	@MethodSource("allowedFaces")
	void hasFace_default(BlockFace face)
	{
		assertFalse(fenceData.hasFace(face));
	}

	@Test
	void hasFace_disallowed()
	{
		assertThrows(IllegalArgumentException.class, () -> fenceData.hasFace(BlockFace.UP));
	}

	@ParameterizedTest
	@MethodSource("allowedFaces")
	void setFace(BlockFace face)
	{
		fenceData.setFace(face, true);
		assertTrue(fenceData.hasFace(face));
	}

	@Test
	void setFace_disallowed()
	{
		assertThrows(IllegalArgumentException.class, () -> fenceData.setFace(BlockFace.UP, true));
	}

	@Test
	void getFaces_default()
	{
		assertTrue(fenceData.getFaces().isEmpty());
	}

	@Test
	void getFaces()
	{
		fenceData.setFace(BlockFace.SOUTH, true);
		fenceData.setFace(BlockFace.WEST, true);
		assertTrue(fenceData.getFaces().contains(BlockFace.SOUTH));
		assertTrue(fenceData.getFaces().contains(BlockFace.WEST));
	}

	@Test
	void isWaterlogged_default()
	{
		assertFalse(fenceData.isWaterlogged());
	}

	@Test
	void setWaterlogged()
	{
		fenceData.setWaterlogged(true);
		assertTrue(fenceData.isWaterlogged());
	}

	static Stream<Arguments> allowedFaces()
	{
		return Stream.of(BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH)
				.map(Arguments::of);
	}

}
