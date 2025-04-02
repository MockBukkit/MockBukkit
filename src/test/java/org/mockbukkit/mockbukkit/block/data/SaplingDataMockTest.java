package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class SaplingDataMockTest
{
	private SaplingDataMock sapling;

	@BeforeEach
	void setUp()
	{
		this.sapling = new SaplingDataMock(Material.BIRCH_SAPLING);
	}

	@Nested
	class SetStage
	{

		@Test
		void getStage()
		{
			assertEquals(0, sapling.getStage());
		}

		@Test
		void getMaximumStage()
		{
			assertEquals(1, sapling.getMaximumStage());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1})
		void givenChangeWithValidValues(int age)
		{
			sapling.setStage(age);
			assertEquals(age, sapling.getStage());
		}

		@ParameterizedTest
		@ValueSource(ints = {-2, -1, 2, 3})
		void givenChangeWithInvalidValues(int age)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sapling.setStage(age));
			assertEquals("The stage must be between 0 and 1", e.getMessage());
		}

	}

}
