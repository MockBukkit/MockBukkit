package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.FaceAttachable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class GrindstoneDataMockTest
{

	private final GrindstoneDataMock grindstone = new GrindstoneDataMock(Material.GRINDSTONE);

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, grindstone.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenValidValues(BlockFace face)
		{
			grindstone.setFacing(face);
			assertEquals(face, grindstone.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> grindstone.setFacing(face));
			assertEquals("Invalid face, only cartesian horizontal face are allowed for this property!", e.getMessage());
		}

	}

	@Nested
	class SetAttachedFace
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(FaceAttachable.AttachedFace.WALL, grindstone.getAttachedFace());
		}

		@ParameterizedTest
		@EnumSource(value = FaceAttachable.AttachedFace.class)
		void givenValidValues(FaceAttachable.AttachedFace face)
		{
			grindstone.setAttachedFace(face);
			assertEquals(face, grindstone.getAttachedFace());
		}

	}

}
