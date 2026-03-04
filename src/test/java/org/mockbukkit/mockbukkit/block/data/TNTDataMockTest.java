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
class TNTDataMockTest
{

	private TNTDataMock tnt;

	@BeforeEach
	void setUp()
	{
		this.tnt = new TNTDataMock(Material.TNT);
	}

	@Nested
	class SetWaterlogged
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(tnt.isUnstable());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isUnstable)
		{
			tnt.setUnstable(isUnstable);
			assertEquals(isUnstable, tnt.isUnstable());
		}

	}

	@Test
	void validateClone()
	{
		@NotNull TNTDataMock cloned = tnt.clone();

		assertEquals(tnt, cloned);
		assertEquals(tnt.isUnstable(), cloned.isUnstable());

		tnt.setUnstable(true);

		assertNotEquals(tnt, cloned);
		assertTrue(tnt.isUnstable());
		assertFalse(cloned.isUnstable());
	}


}
