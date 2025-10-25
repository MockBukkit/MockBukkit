package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class LadderDataMockTest
{

	private LadderDataMock ladderData;

	@BeforeEach
	void setUp()
	{
		this.ladderData = new LadderDataMock(Material.LADDER);
	}

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, ladderData.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST"})
		void givenValidValues(BlockFace face)
		{
			ladderData.setFacing(face);
			assertEquals(face, ladderData.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST", "UP", "DOWN" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ladderData.setFacing(face));
			assertEquals(String.format("BlockFace %s is not a valid BlockFace.", face), e.getMessage());
		}

	}
	@Test
	void isWaterlogged_default()
	{
		assertFalse(ladderData.isWaterlogged());
	}

	@Test
	void setWaterlogged()
	{
		ladderData.setWaterlogged(true);
		assertTrue(ladderData.isWaterlogged());
	}

	@Test
	void validateClone()
	{
		@NotNull LadderDataMock cloned = ladderData.clone();

		assertEquals(ladderData, cloned);
		assertEquals(ladderData.isWaterlogged(), cloned.isWaterlogged());

		ladderData.setWaterlogged(true);

		assertNotEquals(ladderData, cloned);
		assertTrue(ladderData.isWaterlogged());
		assertFalse(cloned.isWaterlogged());
	}
}
