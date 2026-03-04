package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.data.Bisected;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

	@Nested
	class IsSingleBlock
	{

		@Nested
		class SingleBlock
		{
			@ParameterizedTest
			@MethodSource("getStairs()")
			void givenStairs(Material input)
			{
				BlockDataMock data = BlockDataMockFactory.mock(input);
				boolean actual = BisectedDataMock.isSingleBlock(data);
				assertTrue(actual);
			}

			public static List<Arguments> getStairs()
			{
				return Tag.STAIRS.getValues().stream().map(Arguments::of).toList();
			}

			@ParameterizedTest
			@MethodSource("getTrapdoors()")
			void givenTrapdoor(Material input)
			{
				BlockDataMock data = BlockDataMockFactory.mock(Material.BAMBOO_TRAPDOOR);
				boolean actual = BisectedDataMock.isSingleBlock(data);
				assertTrue(actual);
			}

			public static List<Arguments> getTrapdoors()
			{
				return Tag.TRAPDOORS.getValues().stream().map(Arguments::of).toList();
			}
		}

		@Nested
		class MultiBlock
		{
			@ParameterizedTest
			@MethodSource("getDoors()")
			void givenDoors(Material input)
			{
				BlockDataMock data = BlockDataMockFactory.mock(input);
				boolean actual = BisectedDataMock.isSingleBlock(data);
				assertFalse(actual);
			}

			public static List<Arguments> getDoors()
			{
				return Tag.DOORS.getValues().stream().map(Arguments::of).toList();
			}

			@ParameterizedTest
			@MethodSource("getBanners()")
			void givenBanner(Material input)
			{
				BlockDataMock data = BlockDataMockFactory.mock(input);
				boolean actual = BisectedDataMock.isSingleBlock(data);
				assertFalse(actual);
			}

			public static List<Arguments> getBanners()
			{
				return Tag.BANNERS.getValues().stream().map(Arguments::of).toList();
			}

			@ParameterizedTest
			@CsvSource({
				"SMALL_DRIPLEAF",
				"TALL_GRASS",
				"TALL_SEAGRASS",
				"SUNFLOWER",
			})
			void givenOther(Material input)
			{
				BlockDataMock data = BlockDataMockFactory.mock(input);
				boolean actual = BisectedDataMock.isSingleBlock(data);
				assertFalse(actual);
			}


		}


	}

}
