package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.TechnicalPiston;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class TechnicalPistonDataMockTest
{
	private TechnicalPistonDataMock piston;

	@BeforeEach
	void setUp()
	{
		this.piston = new TechnicalPistonDataMock(Material.MOVING_PISTON);
	}

	@Nested
	class SetType
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(TechnicalPiston.Type.NORMAL, piston.getType());
		}

		@ParameterizedTest
		@EnumSource(TechnicalPiston.Type.class)
		void givenValidValues(TechnicalPiston.Type type)
		{
			piston.setType(type);
			assertEquals(type, piston.getType());
		}

	}

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, piston.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST", "UP", "DOWN" })
		void givenValidValues(BlockFace face)
		{
			piston.setFacing(face);
			assertEquals(face, piston.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST", "UP", "DOWN" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> piston.setFacing(face));
			assertEquals("Invalid face, only cartesian face are allowed for this property!", e.getMessage());
		}

	}

}
