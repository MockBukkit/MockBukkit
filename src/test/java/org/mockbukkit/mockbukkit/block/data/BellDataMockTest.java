package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bell;
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
class BellDataMockTest
{

	private final BellDataMock bell = new BellDataMock(Material.BELL);

	@Nested
	class SetAttachment
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(Bell.Attachment.FLOOR, bell.getAttachment());
		}

		@ParameterizedTest
		@EnumSource(Bell.Attachment.class)
		void givenPossibleAttachments(Bell.Attachment expected)
		{
			bell.setAttachment(expected);

			assertEquals(expected, bell.getAttachment());
		}

		@Test
		void giveNullValue()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> bell.setAttachment(null));
			assertEquals("attachment cannot be null!", e.getMessage());
		}

	}

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, bell.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenValidValues(BlockFace face)
		{
			bell.setFacing(face);
			assertEquals(face, bell.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> bell.setFacing(face));
			assertEquals("Invalid face, only cartesian horizontal face are allowed for this property!", e.getMessage());
		}

	}

	@Nested
	class SetPowered
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(bell.isPowered());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean inWall)
		{
			bell.setPowered(inWall);
			assertEquals(inWall, bell.isPowered());
		}

	}

}
