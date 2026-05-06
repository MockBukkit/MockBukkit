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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class SignDataMockTest
{
	private final SignDataMock sign = new SignDataMock(Material.OAK_SIGN);

	@Nested
	class SetRotation
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, sign.getRotation());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "UP", "DOWN", "SELF" })
		void givenValidValues(BlockFace face)
		{
			sign.setRotation(face);
			assertEquals(face, sign.getRotation());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "UP", "DOWN", "SELF" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sign.setRotation(face));
			assertEquals("Invalid face, only horizontal face are allowed for this property!", e.getMessage());
		}

	}

	@Nested
	class SetWaterlogged
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(sign.isWaterlogged());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isWaterLogged)
		{
			sign.setWaterlogged(isWaterLogged);
			assertEquals(isWaterLogged, sign.isWaterlogged());
		}

	}

}
