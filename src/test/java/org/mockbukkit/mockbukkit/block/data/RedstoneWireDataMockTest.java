package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.RedstoneWire;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class RedstoneWireDataMockTest
{

	private RedstoneWireDataMock wire;

	@BeforeEach
	void setUp()
	{
		this.wire = new RedstoneWireDataMock(Material.REDSTONE_WIRE);
	}

	@Nested
	class SetFace
	{

		private static final Set<BlockFace> VALID_FACES = Set.of(
				BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST
		);

		@ParameterizedTest
		@MethodSource("getValidFaces")
		void givenDefaultValue(BlockFace face)
		{
			assertEquals(RedstoneWire.Connection.NONE, wire.getFace(face));
		}

		@ParameterizedTest
		@MethodSource("getInvalidFaces")
		void givenGetWithInvalidValue(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> wire.getFace(face));
			assertEquals(String.format("Invalid face %s", face), e.getMessage());
		}

		@ParameterizedTest
		@MethodSource("getValidFaces")
		void givenValidValues(BlockFace face)
		{
			wire.setFace(face, RedstoneWire.Connection.SIDE);
			assertEquals(RedstoneWire.Connection.SIDE, wire.getFace(face));
		}

		@ParameterizedTest
		@MethodSource("getInvalidFaces")
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> wire.setFace(face, RedstoneWire.Connection.SIDE));
			assertEquals(String.format("Cannot have face %s", face), e.getMessage());
		}

		@Test
		void getAllowedFaces()
		{
			assertEquals(VALID_FACES, wire.getAllowedFaces());
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

	@Nested
	class SetPower
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, wire.getPower());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
		void givenLevelChange(int level)
		{
			wire.setPower(level);
			assertEquals(level, wire.getPower());
		}

		@ParameterizedTest
		@ValueSource(ints = { -2, -1, 16, 17 })
		void givenInvalidValues(int level)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> wire.setPower(level));
			assertEquals("Power must be between 0 and 15", e.getMessage());
		}

	}

	@Nested
	class GetMaximumLevel
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(15, wire.getMaximumPower());
		}

	}

	@Test
	void validateClone()
	{
		@NotNull RedstoneWireDataMock cloned = wire.clone();

		assertEquals(wire, cloned);
		assertEquals(wire.getPower(), cloned.getPower());

		wire.setPower(3);

		assertNotEquals(wire, cloned);
		assertEquals(3, wire.getPower());
		assertEquals(0, cloned.getPower());
	}

}
