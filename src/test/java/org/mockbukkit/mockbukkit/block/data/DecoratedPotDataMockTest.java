package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.DecoratedPot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class DecoratedPotDataMockTest
{

	private DecoratedPot pot;

	@BeforeEach
	void setUp()
	{
		this.pot = (DecoratedPot) BlockDataMock.mock(Material.DECORATED_POT);
	}

	@Test
	void isCracked_default()
	{
		assertFalse(pot.isCracked());
	}

	@Test
	void setCracked()
	{
		pot.setCracked(true);
		assertTrue(pot.isCracked());
	}

	@Test
	void getFacing_default()
	{
		assertEquals(BlockFace.NORTH, pot.getFacing());
	}

	@Test
	void setFacing()
	{
		pot.setFacing(BlockFace.WEST);
		assertEquals(BlockFace.WEST, pot.getFacing());
	}

	@Test
	void setFacing_invalid()
	{
		assertThrows(IllegalArgumentException.class, () -> pot.setFacing(BlockFace.UP));
	}

	@Test
	void setFacing_null()
	{
		assertThrows(NullPointerException.class, () -> pot.setFacing(null));
	}

	@Test
	void isWaterlogged()
	{
		assertFalse(pot.isWaterlogged());
	}

	@Test
	void setWaterlogged()
	{
		pot.setWaterlogged(true);
		assertTrue(pot.isWaterlogged());
	}

}
