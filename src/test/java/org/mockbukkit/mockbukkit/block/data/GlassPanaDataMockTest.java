package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class GlassPanaDataMockTest
{
	private GlassPaneDataMock glassPaneData;

	@BeforeEach
	void setUp()
	{
		this.glassPaneData = new GlassPaneDataMock(Material.GLASS_PANE);
	}

	@ParameterizedTest
	@MethodSource("allowedFaces")
	void hasFace_default(BlockFace face)
	{
		assertFalse(glassPaneData.hasFace(face));
	}

	@Test
	void hasFace_disallowed()
	{
		assertThrows(IllegalArgumentException.class, () -> glassPaneData.hasFace(BlockFace.UP));
	}

	@ParameterizedTest
	@MethodSource("allowedFaces")
	void setFace(BlockFace face)
	{
		glassPaneData.setFace(face, true);
		assertTrue(glassPaneData.hasFace(face));
	}

	@Test
	void setFace_disallowed()
	{
		assertThrows(IllegalArgumentException.class, () -> glassPaneData.setFace(BlockFace.UP, true));
	}

	@Test
	void getFaces_default()
	{
		assertTrue(glassPaneData.getFaces().isEmpty());
	}

	@Test
	void getFaces()
	{
		glassPaneData.setFace(BlockFace.SOUTH, true);
		glassPaneData.setFace(BlockFace.WEST, true);
		assertTrue(glassPaneData.getFaces().contains(BlockFace.SOUTH));
		assertTrue(glassPaneData.getFaces().contains(BlockFace.WEST));
	}

	@Test
	void isWaterlogged_default()
	{
		assertFalse(glassPaneData.isWaterlogged());
	}

	@Test
	void setWaterlogged()
	{
		glassPaneData.setWaterlogged(true);
		assertTrue(glassPaneData.isWaterlogged());
	}

	@Test
	void validateClone()
	{
		@NotNull GlassPaneDataMock cloned = glassPaneData.clone();

		assertEquals(glassPaneData, cloned);
		assertEquals(glassPaneData.isWaterlogged(), cloned.isWaterlogged());

		glassPaneData.setWaterlogged(true);

		assertNotEquals(glassPaneData, cloned);
		assertTrue(glassPaneData.isWaterlogged());
		assertFalse(cloned.isWaterlogged());
	}

	static Stream<Arguments> allowedFaces()
	{
		return Stream.of(BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH)
				.map(Arguments::of);
	}

}
