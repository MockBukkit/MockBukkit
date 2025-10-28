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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class SnowableDataMockTest
{

	private SnowableDataMock snowable;

	@BeforeEach
	void setUp()
	{
		this.snowable = new SnowableDataMock(Material.GRASS_BLOCK);
	}

	@Nested
	class SetSnowy
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(snowable.isSnowy());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isLit)
		{
			snowable.setSnowy(isLit);
			assertEquals(isLit, snowable.isSnowy());
		}

	}

	@Test
	void validateClone()
	{
		@NotNull SnowableDataMock cloned = snowable.clone();

		assertEquals(snowable, cloned);
		assertEquals(snowable.isSnowy(), cloned.isSnowy());

		snowable.setSnowy(true);

		assertNotEquals(snowable, cloned);
		assertTrue(snowable.isSnowy());
		assertFalse(cloned.isSnowy());
	}

}
