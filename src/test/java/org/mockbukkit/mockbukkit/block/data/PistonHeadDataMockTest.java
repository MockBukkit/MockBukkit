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
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class PistonHeadDataMockTest
{

	private PistonHeadDataMock pistonHead;

	@BeforeEach
	void setUp()
	{
		this.pistonHead = new PistonHeadDataMock(Material.PISTON_HEAD);
	}

	@Nested
	class SetType
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(TechnicalPiston.Type.NORMAL, pistonHead.getType());
		}

		@ParameterizedTest
		@EnumSource(TechnicalPiston.Type.class)
		void givenValidValues(TechnicalPiston.Type type)
		{
			pistonHead.setType(type);
			assertEquals(type, pistonHead.getType());
		}

	}

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, pistonHead.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST", "UP", "DOWN" })
		void givenValidValues(BlockFace face)
		{
			pistonHead.setFacing(face);
			assertEquals(face, pistonHead.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST", "UP", "DOWN" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> pistonHead.setFacing(face));
			assertEquals("Invalid face, only cartesian face are allowed for this property!", e.getMessage());
		}

	}

	@Nested
	class IsShort
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(pistonHead.isShort());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isShort)
		{
			pistonHead.setShort(isShort);
			assertEquals(isShort, pistonHead.isShort());
		}

	}

}
