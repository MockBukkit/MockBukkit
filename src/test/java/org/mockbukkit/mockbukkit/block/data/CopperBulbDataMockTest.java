package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockBukkitExtension.class)
class CopperBulbDataMockTest
{

	private final CopperBulbDataMock bulb = new CopperBulbDataMock(Material.COPPER_BULB);

	@Nested
	class SetLit
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(bulb.isLit());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isLit)
		{
			bulb.setLit(isLit);
			assertEquals(isLit, bulb.isLit());
		}

	}

	@Nested
	class SetPowered
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(bulb.isPowered());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean inWall)
		{
			bulb.setPowered(inWall);
			assertEquals(inWall, bulb.isPowered());
		}

	}

}
