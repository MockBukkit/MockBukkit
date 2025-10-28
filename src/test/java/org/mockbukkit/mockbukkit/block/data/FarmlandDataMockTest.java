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
class FarmlandDataMockTest
{

	private FarmlandDataMock farmland;

	@BeforeEach
	void setUp()
	{
		this.farmland = new FarmlandDataMock(Material.FARMLAND);
	}

	@Nested
	class SetMoisture
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, farmland.getMoisture());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7 })
		void givenLevelChange(int level)
		{
			farmland.setMoisture(level);
			assertEquals(level, farmland.getMoisture());
		}

		@ParameterizedTest
		@ValueSource(ints = { -2, -1, 8, 9 })
		void givenInvalidValues(int level)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> farmland.setMoisture(level));
			assertEquals("Moisture must be between 0 and 7", e.getMessage());
		}

	}

}
