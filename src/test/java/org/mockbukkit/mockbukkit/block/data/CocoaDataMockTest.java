package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class CocoaDataMockTest
{

	private final CocoaDataMock cocoa = new CocoaDataMock(Material.COCOA);

	@Nested
	class SetAge
	{

		@Test
		void getAge()
		{
			assertEquals(0, cocoa.getAge());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2 })
		void setAge(int age)
		{
			cocoa.setAge(age);
			assertEquals(age, cocoa.getAge());
		}

		@Test
		void getMaximumAge()
		{
			assertEquals(2, cocoa.getMaximumAge());
		}

	}

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, cocoa.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenValidValues(BlockFace face)
		{
			cocoa.setFacing(face);
			assertEquals(face, cocoa.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> cocoa.setFacing(face));
			assertEquals("Invalid face, only cartesian horizontal face are allowed for this property!", e.getMessage());
		}

	}

}
