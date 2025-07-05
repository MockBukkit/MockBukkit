package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class BrushableDataMockTest
{

	private final BrushableDataMock brushable = new BrushableDataMock(Material.SUSPICIOUS_GRAVEL);

	@Nested
	class SetDusted
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, brushable.getDusted());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3 })
		void givenLevelChange(int level)
		{
			brushable.setDusted(level);
			assertEquals(level, brushable.getDusted());
		}

		@ParameterizedTest
		@ValueSource(ints = { -2, -1, 4, 5 })
		void givenInvalidValues(int level)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> brushable.setDusted(level));
			assertEquals("Dusted level should be between 0 and 3", e.getMessage());
		}

	}

	@Nested
	class GetMaximumDusted
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(3, brushable.getMaximumDusted());
		}

	}

}
