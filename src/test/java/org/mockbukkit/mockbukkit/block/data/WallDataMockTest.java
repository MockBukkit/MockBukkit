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
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class WallDataMockTest
{
	private final WallDataMock wall = new WallDataMock(Material.COBBLESTONE_WALL);

	@Nested
	class SetUp
	{

		@Test
		void givenDefaultValue()
		{
			assertTrue(wall.isUp());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isUp)
		{
			wall.setUp(isUp);
			assertEquals(isUp, wall.isUp());
		}

	}

	@Nested
	class SetWaterlogged
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(wall.isWaterlogged());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isWaterLogged)
		{
			wall.setWaterlogged(isWaterLogged);
			assertEquals(isWaterLogged, wall.isWaterlogged());
		}

	}

}
