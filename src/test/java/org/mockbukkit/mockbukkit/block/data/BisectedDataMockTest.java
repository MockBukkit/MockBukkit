package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.Bisected;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

	@Nested
	class Encoder
	{

		@ParameterizedTest
		@CsvSource(value = {
			"null, null",
			"TOP, 'upper'",
			"BOTTOM, 'lower'"
		}, nullValues = "null")
		void validatePossibilities(Bisected.Half half, String expected)
		{
			Object actual = BisectedDataMock.HalfEncoder.INSTANCE.encode(half);
			assertEquals(expected, actual);
		}

	}

	@Nested
	class Decoder
	{

		@ParameterizedTest
		@CsvSource(value = {
				"null, null",
				"'upper', TOP",
				"'Upper', TOP",
				"'lower', BOTTOM",
				"'LOWER', BOTTOM",
				"'top', TOP",
				"'tOp', TOP",
				"'bottom', BOTTOM",
				"'BoTtOm', BOTTOM"
		}, nullValues = "null")
		void validatePossibilities(String half, Bisected.Half expected)
		{
			Object actual = BisectedDataMock.HalfDecoder.INSTANCE.decode(half);
			assertEquals(expected, actual);
		}

	}

}
