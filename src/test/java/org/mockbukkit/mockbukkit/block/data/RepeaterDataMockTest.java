package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class RepeaterDataMockTest
{

	private RepeaterDataMock repeater;

	@BeforeEach
	void setUp()
	{
		this.repeater = new RepeaterDataMock(Material.REPEATER);
	}

	@Nested
	class SetDelay
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(1, repeater.getDelay());
		}

		@ParameterizedTest
		@ValueSource(ints = { 1, 2, 3, 4 })
		void givenLevelChange(int level)
		{
			repeater.setDelay(level);
			assertEquals(level, repeater.getDelay());
		}

		@ParameterizedTest
		@ValueSource(ints = { -2, -1, 0, 5, 6, 7 })
		void givenInvalidValues(int level)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> repeater.setDelay(level));
			assertEquals("Delay must be between 1 and 4", e.getMessage());
		}

	}

	@Nested
	class SetLocked
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(repeater.isLocked());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isLocked)
		{
			repeater.setLocked(isLocked);
			assertEquals(isLocked, repeater.isLocked());
		}

	}

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, repeater.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenValidValues(BlockFace face)
		{
			repeater.setFacing(face);
			assertEquals(face, repeater.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> repeater.setFacing(face));
			assertEquals(String.format("BlockFace %s is not a valid BlockFace.", face), e.getMessage());
		}

	}

	@Nested
	class SetPowered
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(repeater.isPowered());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isLocked)
		{
			repeater.setPowered(isLocked);
			assertEquals(isLocked, repeater.isPowered());
		}

	}

}
