package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
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
class RotatableDataMockTest
{

	private RotatableDataMock rotatable;

	@BeforeEach
	void setUp()
	{
		this.rotatable = new RotatableDataMock(Material.BLACK_BANNER);
	}

	@Nested
	class SetRotation
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, rotatable.getRotation());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "UP", "DOWN", "SELF" })
		void givenValidValues(BlockFace face)
		{
			rotatable.setRotation(face);
			assertEquals(face, rotatable.getRotation());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "UP", "DOWN", "SELF" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> rotatable.setRotation(face));
			assertEquals(String.format("Illegal rotation %s", face), e.getMessage());
		}

	}

}
