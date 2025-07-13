package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.Bisected;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockBukkitExtension.class)
class BisectedDataMockTest
{

	private BisectedDataMock bisected;

	@BeforeEach
	void setUp()
	{
		this.bisected = new BisectedDataMock(Material.ROSE_BUSH);
	}

	@Nested
	class setHalf
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(Bisected.Half.BOTTOM, bisected.getHalf());
		}

		@ParameterizedTest
		@EnumSource(Bisected.Half.class)
		void givenValidValues(Bisected.Half face)
		{
			bisected.setHalf(face);
			assertEquals(face, bisected.getHalf());
		}

	}

	@Test
	void validateClone()
	{
		@NotNull BisectedDataMock cloned = bisected.clone();

		assertEquals(bisected, cloned);
		assertEquals(bisected.getHalf(), cloned.getHalf());

		bisected.setHalf(Bisected.Half.TOP);

		assertNotEquals(bisected, cloned);
		assertEquals(Bisected.Half.TOP, bisected.getHalf());
		assertEquals(Bisected.Half.BOTTOM, cloned.getHalf());
	}

}
