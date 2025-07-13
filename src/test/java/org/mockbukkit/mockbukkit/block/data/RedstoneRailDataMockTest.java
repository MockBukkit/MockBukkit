package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.Rail;
import org.jetbrains.annotations.NotNull;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class RedstoneRailDataMockTest
{

	private RedstoneRailDataMock rail;

	@BeforeEach
	void setUp()
	{
		this.rail = new RedstoneRailDataMock(Material.POWERED_RAIL);
	}

	@Nested
	class IsPowered
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(rail.isPowered());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isPowered)
		{
			rail.setPowered(isPowered);
			assertEquals(isPowered, rail.isPowered());
		}

	}

	@Nested
	class SetShape
	{

		@Test
		void givenDefaultAxis()
		{
			assertEquals(Rail.Shape.NORTH_SOUTH, rail.getShape());
		}

		@ParameterizedTest
		@EnumSource(value = Rail.Shape.class,
				mode = EnumSource.Mode.INCLUDE,
				names = {
						"NORTH_SOUTH", "EAST_WEST", "ASCENDING_NORTH", "ASCENDING_SOUTH", "ASCENDING_EAST", "ASCENDING_WEST"
				})
		void givenValidValues(Rail.Shape shape)
		{
			rail.setShape(shape);
			assertEquals(shape, rail.getShape());
		}

		@ParameterizedTest
		@EnumSource(value = Rail.Shape.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = {
						"NORTH_SOUTH", "EAST_WEST", "ASCENDING_NORTH", "ASCENDING_SOUTH", "ASCENDING_EAST", "ASCENDING_WEST"
				})
		void givenInvalidValues(Rail.Shape shape)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> rail.setShape(shape));
			assertEquals("Invalid shape. Allowed values are: [NORTH_SOUTH, EAST_WEST, ASCENDING_EAST, ASCENDING_WEST, ASCENDING_NORTH, ASCENDING_SOUTH]", e.getMessage());
		}

	}

	@Nested
	class SetWaterlogged
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(rail.isWaterlogged());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isWaterLogged)
		{
			rail.setWaterlogged(isWaterLogged);
			assertEquals(isWaterLogged, rail.isWaterlogged());
		}

	}

	@Test
	void validateClone()
	{
		@NotNull RedstoneRailDataMock cloned = rail.clone();

		assertEquals(rail, cloned);
		assertEquals(rail.isWaterlogged(), cloned.isWaterlogged());

		rail.setWaterlogged(true);

		assertNotEquals(rail, cloned);
		assertTrue(rail.isWaterlogged());
		assertFalse(cloned.isWaterlogged());
	}

}
