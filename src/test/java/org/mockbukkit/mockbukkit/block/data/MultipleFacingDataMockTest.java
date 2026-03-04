package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class MultipleFacingDataMockTest
{

	private final MultipleFacingDataMock multipleFacing = new MultipleFacingDataMock(Material.MUSHROOM_STEM);

	@Nested
	class SetFace
	{

		private static final Set<BlockFace> VALID_FACES = Set.of(
				BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN
		);

		@ParameterizedTest
		@MethodSource("getValidFaces")
		void givenDefaultValue(BlockFace face)
		{
			assertTrue(multipleFacing.hasFace(face));
		}

		@ParameterizedTest
		@MethodSource("getInvalidFaces")
		void givenGetWithInvalidValue(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> multipleFacing.hasFace(face));
			assertEquals(String.format("Illegal facing: %s", face), e.getMessage());
		}

		@ParameterizedTest
		@MethodSource("getValidFaces")
		void givenValidValues(BlockFace face)
		{
			multipleFacing.setFace(face, true);
			assertTrue(multipleFacing.hasFace(face));
			assertEquals(VALID_FACES, multipleFacing.getFaces());

			multipleFacing.setFace(face, false);
			assertFalse(multipleFacing.hasFace(face));

			Set<BlockFace> faces = new HashSet<>(VALID_FACES);
			faces.remove(face);
			assertEquals(faces, multipleFacing.getFaces());
		}

		@ParameterizedTest
		@MethodSource("getInvalidFaces")
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> multipleFacing.setFace(face, true));
			assertEquals(String.format("Illegal facing: %s", face), e.getMessage());
		}

		@Test
		void getAllowedFaces()
		{
			assertEquals(VALID_FACES, multipleFacing.getAllowedFaces());
		}

		public static Stream<Arguments> getValidFaces()
		{
			return VALID_FACES.stream().map(Arguments::of);
		}

		public static Stream<Arguments> getInvalidFaces()
		{
			return Stream.of(BlockFace.values())
					.filter(p -> !VALID_FACES.contains(p))
					.map(Arguments::of);
		}

	}

}
