package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class CaveVinesPlantDataMockTest
{
	private final CaveVineDataMock caveVineData = new CaveVineDataMock(Material.CAVE_VINES_PLANT);

	@Test
	void hasBerries()
	{
		assertThat(caveVineData.hasBerries(), is(false));
	}

	@Test
	void setBerries()
	{
		caveVineData.setBerries(true);
		assertThat(caveVineData.hasBerries(), is(true));
	}

	@Test
	void validateClone()
	{
		@NotNull CaveVineDataMock cloned = caveVineData.clone();

		assertEquals(caveVineData, cloned);
		assertEquals(caveVineData.hasBerries(), cloned.hasBerries());

		caveVineData.setBerries(true);

		assertNotEquals(caveVineData, cloned);
		assertTrue(caveVineData.hasBerries());
		assertFalse(cloned.hasBerries());
	}
}
