package org.mockbukkit.mockbukkit.block.data;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class ChiseledBookshelfDataMockTest
{
	private ChiseledBookshelfDataMock bookshelf;

	@BeforeEach
	void setUp()
	{
		this.bookshelf = new ChiseledBookshelfDataMock(Material.CHISELED_BOOKSHELF);
	}

	@Nested
	class IsSlotOccupied
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(bookshelf.isSlotOccupied(0));
			assertFalse(bookshelf.isSlotOccupied(1));
			assertFalse(bookshelf.isSlotOccupied(2));
			assertFalse(bookshelf.isSlotOccupied(3));
			assertFalse(bookshelf.isSlotOccupied(4));
			assertFalse(bookshelf.isSlotOccupied(5));
		}

		@ParameterizedTest
		@MethodSource("generateAllCombinationsOfSix()")
		void givenIndexCombination(boolean exp0, boolean exp1, boolean exp2, boolean exp3, boolean exp4, boolean exp5)
		{
			bookshelf.setSlotOccupied(0, exp0);
			bookshelf.setSlotOccupied(1, exp1);
			bookshelf.setSlotOccupied(2, exp2);
			bookshelf.setSlotOccupied(3, exp3);
			bookshelf.setSlotOccupied(4, exp4);
			bookshelf.setSlotOccupied(5, exp5);

			assertEquals(exp0, bookshelf.isSlotOccupied(0));
			assertEquals(exp1, bookshelf.isSlotOccupied(1));
			assertEquals(exp2, bookshelf.isSlotOccupied(2));
			assertEquals(exp3, bookshelf.isSlotOccupied(3));
			assertEquals(exp4, bookshelf.isSlotOccupied(4));
			assertEquals(exp5, bookshelf.isSlotOccupied(5));
		}

		@Test
		void getMaximumOccupiedSlots()
		{
			assertEquals(6, bookshelf.getMaximumOccupiedSlots());
		}

		@Test
		void getOccupiedSlots()
		{
			Set<Integer> expectedOccupiedSlots = Sets.newHashSet(0, 2, 4);

			for (int index : expectedOccupiedSlots)
			{
				bookshelf.setSlotOccupied(index, true);
			}

			assertEquals(expectedOccupiedSlots, bookshelf.getOccupiedSlots());
		}

		public static Stream<Arguments> generateAllCombinationsOfSix()
		{
			return generateAllCombinations(6).stream()
					.map(arr -> Arguments.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]));
		}

	}

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, bookshelf.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenValidValues(BlockFace face)
		{
			bookshelf.setFacing(face);
			assertEquals(face, bookshelf.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> bookshelf.setFacing(face));
			assertEquals("Invalid face, only cartesian horizontal face are allowed for this property!", e.getMessage());
		}

		@Test
		void getValidFaces()
		{
			Set<BlockFace> validFaces = Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
			assertEquals(validFaces, bookshelf.getFaces());
		}

	}

	/**
	 * Generates all possible combinations of boolean values for the given size.
	 *
	 * @param size The size of the boolean array to generate.
	 *
	 * @return A list of all possible combinations of boolean values.
	 */
	public static List<boolean[]> generateAllCombinations(int size)
	{
		int total = 1 << size; // 2^size combinations
		List<boolean[]> combinations = new ArrayList<>(total);

		for (int i = 0; i < total; i++)
		{
			boolean[] combo = new boolean[size];
			for (int j = 0; j < size; j++)
			{
				combo[j] = (i & (1 << j)) != 0;
			}
			combinations.add(combo);
		}

		return combinations;
	}

}
