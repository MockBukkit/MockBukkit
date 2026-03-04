package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class LevelledDataMockTest
{

	private LevelledDataMock levelled;

	@BeforeEach
	void setUp()
	{
		this.levelled = new LevelledDataMock(Material.LAVA);
	}

	@Nested
	class SetLevel
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, levelled.getLevel());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
		void givenLevelChange(int level)
		{
			levelled.setLevel(level);
			assertEquals(level, levelled.getLevel());
		}

		@ParameterizedTest
		@ValueSource(ints = { -2, -1, 16, 17 })
		void givenInvalidValues(int level)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> levelled.setLevel(level));
			assertEquals("Level should be between 0 and 15", e.getMessage());
		}

	}

	@Nested
	class GetMaximumLevel
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(15, levelled.getMaximumLevel());
		}

	}

	@Nested
	class GetMinimumLevel
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, levelled.getMinimumLevel());
		}

	}

	@Test
	void validateClone()
	{
		@NotNull LevelledDataMock cloned = levelled.clone();

		assertEquals(levelled, cloned);
		assertEquals(levelled.getLevel(), cloned.getLevel());

		levelled.setLevel(3);

		assertNotEquals(levelled, cloned);
		assertEquals(3, levelled.getLevel());
		assertEquals(0, cloned.getLevel());
	}

}
