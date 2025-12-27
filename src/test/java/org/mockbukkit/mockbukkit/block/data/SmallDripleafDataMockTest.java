package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
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
class SmallDripleafDataMockTest
{

	private final SmallDripleafDataMock dripleaf = new SmallDripleafDataMock(Material.SMALL_DRIPLEAF);

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, dripleaf.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenValidValues(BlockFace face)
		{
			dripleaf.setFacing(face);
			assertEquals(face, dripleaf.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> dripleaf.setFacing(face));
			assertEquals("Invalid face, only cartesian horizontal face are allowed for this property!", e.getMessage());
		}

	}

	@Nested
	class SetWaterlogged
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(dripleaf.isWaterlogged());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isWaterLogged)
		{
			dripleaf.setWaterlogged(isWaterLogged);
			assertEquals(isWaterLogged, dripleaf.isWaterlogged());
		}

	}

	@Nested
	class SetHalf
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(Bisected.Half.BOTTOM, dripleaf.getHalf());
		}

		@ParameterizedTest
		@EnumSource(Bisected.Half.class)
		void givenValidValues(Bisected.Half half)
		{
			dripleaf.setHalf(half);
			assertEquals(half, dripleaf.getHalf());
		}

	}

}
