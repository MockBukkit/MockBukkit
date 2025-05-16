package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Chest;
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
class ChestDataMockTest
{

	private ChestDataMock chest;

	@BeforeEach
	void setUp()
	{
		this.chest = new ChestDataMock(Material.CHEST);
	}

	@Nested
	class SetType
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(Chest.Type.SINGLE, chest.getType());
		}

		@ParameterizedTest
		@EnumSource(Chest.Type.class)
		void givenValidValues(Chest.Type type)
		{
			chest.setType(type);
			assertEquals(type, chest.getType());
		}

	}

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, chest.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenValidValues(BlockFace face)
		{
			chest.setFacing(face);
			assertEquals(face, chest.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> chest.setFacing(face));
			assertEquals(String.format("BlockFace %s is not a valid BlockFace.", face), e.getMessage());
		}

	}

	@Nested
	class SetWaterlogged
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(chest.isWaterlogged());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isWaterLogged)
		{
			chest.setWaterlogged(isWaterLogged);
			assertEquals(isWaterLogged, chest.isWaterlogged());
		}

	}

}
