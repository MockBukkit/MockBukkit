package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Comparator;
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
class ComparatorDataMockTest
{

	private final ComparatorDataMock comparator = new ComparatorDataMock(Material.COMPARATOR);

	@Nested
	class SetMode
	{

		@Test
		void givenDefaultMode()
		{
			assertEquals(Comparator.Mode.COMPARE, comparator.getMode());
		}

		@ParameterizedTest
		@EnumSource(Comparator.Mode.class)
		void givenModeChange(Comparator.Mode mode)
		{
			comparator.setMode(mode);
			assertEquals(mode, comparator.getMode());
		}

		@Test
		void givenNullMode()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> comparator.setMode(null));
			assertEquals("mode cannot be null!", e.getMessage());
		}

	}

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, comparator.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenValidValues(BlockFace face)
		{
			comparator.setFacing(face);
			assertEquals(face, comparator.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> comparator.setFacing(face));
			assertEquals("Invalid face, only cartesian horizontal face are allowed for this property!", e.getMessage());
		}

	}

	@Nested
	class SetPowered
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(comparator.isPowered());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean inWall)
		{
			comparator.setPowered(inWall);
			assertEquals(inWall, comparator.isPowered());
		}

	}

}
