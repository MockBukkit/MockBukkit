package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Axis;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class OrientableMockTest
{

	private OrientableMock orientable;

	@BeforeEach
	void setUp()
	{
		this.orientable = new OrientableMock(Material.ACACIA_LOG);
	}

	@Nested
	class SetAxis
	{

		@Test
		void givenDefaultAxis()
		{
			assertEquals(Axis.Y, orientable.getAxis());
		}

		@ParameterizedTest
		@EnumSource(Axis.class)
		void givenAxisChange(Axis axis)
		{
			orientable.setAxis(axis);
			assertEquals(axis, orientable.getAxis());
		}

	}

	@Nested
	class GetAxes
	{

		@Test
		void allValuesArePresent()
		{
			assertEquals(Set.of(Axis.values()), orientable.getAxes());
		}

	}

}
