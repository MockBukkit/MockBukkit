package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.Rail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class RailDataMockTest
{

	private RailDataMock rail;

	@BeforeEach
	void setUp()
	{
		this.rail = new RailDataMock(Material.RAIL);
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
		@EnumSource(Rail.Shape.class)
		void givenAxisChange(Rail.Shape shape)
		{
			rail.setShape(shape);
			assertEquals(shape, rail.getShape());
		}

	}

	@Nested
	class GetShapes
	{

		@Test
		void allValuesArePresent()
		{
			assertEquals(Set.of(Rail.Shape.values()), rail.getShapes());
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
	void deserialize()
	{
		RailDataMock blockDataMock = (RailDataMock) BlockDataMock.newData(null, "minecraft:rail[shape=south_east, waterlogged=true]");
		assertEquals(Rail.Shape.SOUTH_EAST, blockDataMock.getShape());
		assertTrue(blockDataMock.isWaterlogged());
	}

}
