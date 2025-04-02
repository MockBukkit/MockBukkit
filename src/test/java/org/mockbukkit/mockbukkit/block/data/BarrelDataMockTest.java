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
class BarrelDataMockTest
{

	private BarrelDataMock barrel;

	@BeforeEach
	void setUp()
	{
		this.barrel = new BarrelDataMock(Material.BARREL);
	}

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, barrel.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
					mode = EnumSource.Mode.INCLUDE,
					names = {"NORTH", "SOUTH", "EAST", "WEST", "UP", "DOWN" })
		void givenValidValues(BlockFace face)
		{
			barrel.setFacing(face);
			assertEquals(face, barrel.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
					mode = EnumSource.Mode.EXCLUDE,
					names = { "NORTH", "SOUTH", "EAST", "WEST", "UP", "DOWN" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> barrel.setFacing(face));
			assertEquals(String.format("BlockFace %s is not a valid BlockFace.", face), e.getMessage());
		}

	}

	@Nested
	class SetOpen
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(barrel.isOpen());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenPossibleValues(boolean isLit)
		{
			barrel.setOpen(isLit);
			assertEquals(isLit, barrel.isOpen());
		}

	}

}
