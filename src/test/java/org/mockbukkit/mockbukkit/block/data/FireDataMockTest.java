package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class FireDataMockTest
{
	private final FireDataMock fire = new FireDataMock(Material.FIRE);

	@Nested
	class SetAge
	{

		@Test
		void getAge()
		{
			assertEquals(0, fire.getAge());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
		void setAge(int age)
		{
			fire.setAge(age);
			assertEquals(age, fire.getAge());
		}

		@Test
		void getMaximumAge()
		{
			assertEquals(15, fire.getMaximumAge());
		}

	}

	@Nested
	class SetFace
	{

		private static final Set<BlockFace> VALID_FACES = Set.of(
				BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP
		);

		@ParameterizedTest
		@MethodSource("getValidFaces")
		void givenDefaultValue(BlockFace face)
		{
			assertFalse(fire.hasFace(face));
		}

		@ParameterizedTest
		@MethodSource("getInvalidFaces")
		void givenGetWithInvalidValue(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> fire.hasFace(face));
			assertEquals(String.format("Illegal facing: %s", face), e.getMessage());
		}

		@ParameterizedTest
		@MethodSource("getValidFaces")
		void givenValidValues(BlockFace face)
		{
			fire.setFace(face, true);
			assertTrue(fire.hasFace(face));
			assertEquals(Set.of(face), fire.getFaces());

			fire.setFace(face, false);
			assertFalse(fire.hasFace(face));

			assertEquals(Collections.emptySet(), fire.getFaces());
		}

		@ParameterizedTest
		@MethodSource("getInvalidFaces")
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> fire.setFace(face, true));
			assertEquals(String.format("Illegal facing: %s", face), e.getMessage());
		}

		@Test
		void getAllowedFaces()
		{
			assertEquals(VALID_FACES, fire.getAllowedFaces());
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
