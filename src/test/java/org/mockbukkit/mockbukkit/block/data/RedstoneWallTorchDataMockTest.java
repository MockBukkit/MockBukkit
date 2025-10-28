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
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class RedstoneWallTorchDataMockTest
{

	private RedstoneWallTorchDataMock torch;

	@BeforeEach
	void setUp()
	{
		this.torch = new RedstoneWallTorchDataMock(Material.REDSTONE_WALL_TORCH);
	}

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, torch.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenValidValues(BlockFace face)
		{
			torch.setFacing(face);
			assertEquals(face, torch.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> torch.setFacing(face));
			assertEquals(String.format("BlockFace %s is not a valid BlockFace.", face), e.getMessage());
		}

	}

	@Nested
	class SetLit
	{

		@Test
		void givenDefaultValue()
		{
			assertTrue(torch.isLit());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isLit)
		{
			torch.setLit(isLit);
			assertEquals(isLit, torch.isLit());
		}

	}

	@Test
	void validateClone()
	{
		@NotNull RedstoneWallTorchDataMock cloned = torch.clone();

		assertEquals(torch, cloned);
		assertEquals(torch.isLit(), cloned.isLit());

		torch.setLit(false);

		assertNotEquals(torch, cloned);
		assertFalse(torch.isLit());
		assertTrue(cloned.isLit());
	}

}
