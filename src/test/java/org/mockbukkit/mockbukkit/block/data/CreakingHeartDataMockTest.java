package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.data.type.CreakingHeart;
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

@ExtendWith(MockBukkitExtension.class)
class CreakingHeartDataMockTest
{
	private final CreakingHeartDataMock creakingHeart = new CreakingHeartDataMock(Material.CREAKING_HEART);

	@Nested
	class SetCreakingHeartState
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(CreakingHeart.State.UPROOTED, creakingHeart.getCreakingHeartState());
		}

		@ParameterizedTest
		@EnumSource(CreakingHeart.State.class)
		void givenPossibleValues(CreakingHeart.State expected)
		{
			creakingHeart.setCreakingHeartState(expected);

			assertEquals(expected, creakingHeart.getCreakingHeartState());
		}

	}

	@Nested
	class SetNatural
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(creakingHeart.isNatural());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isLit)
		{
			creakingHeart.setNatural(isLit);
			assertEquals(isLit, creakingHeart.isNatural());
		}

	}

	@Nested
	class SetAxis
	{

		@Test
		void givenDefaultAxis()
		{
			assertEquals(Axis.Y, creakingHeart.getAxis());
		}

		@ParameterizedTest
		@EnumSource(Axis.class)
		void givenAxisChange(Axis axis)
		{
			creakingHeart.setAxis(axis);
			assertEquals(axis, creakingHeart.getAxis());
		}

	}

	@Nested
	class GetAxes
	{

		@Test
		void allValuesArePresent()
		{
			Set<Axis> axes = Set.of(Axis.X, Axis.Y, Axis.Z);
			assertEquals(axes, creakingHeart.getAxes());
		}

	}

}
