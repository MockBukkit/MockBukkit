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
class CommandBlockDataMockTest
{

	private CommandBlockDataMock commandBlock;

	@BeforeEach
	void setUp()
	{
		this.commandBlock = new CommandBlockDataMock(Material.COMMAND_BLOCK);
	}

	@Nested
	class IsConditional
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(commandBlock.isConditional());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isConditional)
		{
			commandBlock.setConditional(isConditional);
			assertEquals(isConditional, commandBlock.isConditional());
		}

	}

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, commandBlock.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST", "UP", "DOWN" })
		void givenValidValues(BlockFace face)
		{
			commandBlock.setFacing(face);
			assertEquals(face, commandBlock.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST", "UP", "DOWN" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> commandBlock.setFacing(face));
			assertEquals("Invalid face, only cartesian face are allowed for this property!", e.getMessage());
		}

	}

}
