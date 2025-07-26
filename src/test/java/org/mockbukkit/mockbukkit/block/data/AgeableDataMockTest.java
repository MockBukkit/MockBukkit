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
class AgeableDataMockTest
{

	private AgeableDataMock ageable;

	@BeforeEach
	void setUp()
	{
		this.ageable = new AgeableDataMock(Material.WHEAT);
	}

	@Nested
	class SetAge
	{

		@Test
		void getAge()
		{
			assertEquals(0, ageable.getAge());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7 })
		void setAge(int age)
		{
			ageable.setAge(age);
			assertEquals(age, ageable.getAge());
		}

		@ParameterizedTest
		@ValueSource(ints = { -5, -4, -3, -2, -1 })
		void givenNegative(int age)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ageable.setAge(age));
			assertEquals("Age cannot be negative.", e.getMessage());
		}

	}

	@Nested
	class GetMaximumAge
	{

		@Test
		void givenAge1Material()
		{
			AgeableDataMock ageableData = new AgeableDataMock(Material.BAMBOO);
			assertEquals(1, ageableData.getMaximumAge());
		}

		@Test
		void givenAge2Material()
		{
			AgeableDataMock ageableData = new AgeableDataMock(Material.COCOA);
			assertEquals(2, ageableData.getMaximumAge());
		}

		@Test
		void givenAge3Material()
		{
			AgeableDataMock ageableData = new AgeableDataMock(Material.NETHER_WART);
			assertEquals(3, ageableData.getMaximumAge());
		}

		@Test
		void givenAge4Material()
		{
			AgeableDataMock ageableData = new AgeableDataMock(Material.PITCHER_CROP);
			assertEquals(4, ageableData.getMaximumAge());
		}

		@Test
		void givenAge5Material()
		{
			AgeableDataMock ageableData = new AgeableDataMock(Material.CHORUS_FLOWER);
			assertEquals(5, ageableData.getMaximumAge());
		}

		@Test
		void givenAge15Material()
		{
			AgeableDataMock ageableData = new AgeableDataMock(Material.CACTUS);
			assertEquals(15, ageableData.getMaximumAge());
		}

	}

}
