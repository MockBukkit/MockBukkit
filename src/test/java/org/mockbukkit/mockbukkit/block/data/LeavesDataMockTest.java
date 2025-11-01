package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class LeavesDataMockTest
{

	private LeavesDataMock leavesData;

	@BeforeEach
	void setup()
	{
		leavesData = new LeavesDataMock(Material.OAK_LEAVES);
	}

	@Nested
	class Persistent
	{

		@Test
		void isPersistent()
		{
			assertThat(leavesData.isPersistent(), is(false));
		}

		@Test
		void setPersistent()
		{
			leavesData.setPersistent(true);
			assertThat(leavesData.isPersistent(), is(true));
		}

	}

	@Nested
	class Distance
	{

		@Test
		void getDistance()
		{
			assertThat(leavesData.getDistance(), is(7));
		}

		@Test
		void setDistance()
		{
			leavesData.setDistance(1);
			assertThat(leavesData.getDistance(), is(1));
		}

		@Test
		void setDistanceTooHigh()
		{
			IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
			{
				leavesData.setDistance(24);
			});

			assertThat(illegalArgumentException.getMessage(), is("The distance must be <= 7"));
		}

		@Test
		void setDistanceTooLow()
		{
			IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
			{
				leavesData.setDistance(-1);
			});

			assertThat(illegalArgumentException.getMessage(), is("The distance must be >= 1"));
		}
	}

	@Nested
	class Waterlogged
	{
		@Test
		void getWaterlogged()
		{
			assertThat(leavesData.isWaterlogged(), is(false));
		}

		@Test
		void setWaterlogged()
		{
			leavesData.setWaterlogged(true);
			assertThat(leavesData.isWaterlogged(), is(true));
		}

	}

	@Test
	void validateClone()
	{
		@NotNull LeavesDataMock cloned = leavesData.clone();

		assertEquals(leavesData, cloned);
		assertEquals(leavesData.isWaterlogged(), cloned.isWaterlogged());

		leavesData.setWaterlogged(true);

		assertNotEquals(leavesData, cloned);
		assertTrue(leavesData.isWaterlogged());
		assertFalse(cloned.isWaterlogged());
	}

}
