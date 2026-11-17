package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.LightningRod;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class LightningRodDataMockTest
{

	private LightningRodDataMock lightningRod;

	@BeforeEach
	void setUp()
	{
		this.lightningRod = new LightningRodDataMock(Material.LIGHTNING_ROD);
	}

	@Test
	void defaultValues()
	{
		assertEquals(BlockFace.UP, this.lightningRod.getFacing());
		assertFalse(this.lightningRod.isPowered());
		assertFalse(this.lightningRod.isWaterlogged());
		assertTrue(this.lightningRod.getFaces().contains(BlockFace.DOWN));
	}

	@Test
	void setProperties()
	{
		this.lightningRod.setFacing(BlockFace.NORTH);
		this.lightningRod.setPowered(true);
		this.lightningRod.setWaterlogged(true);

		assertEquals(BlockFace.NORTH, this.lightningRod.getFacing());
		assertTrue(this.lightningRod.isPowered());
		assertTrue(this.lightningRod.isWaterlogged());
	}

	@Test
	void setFacingRejectsInvalidFace()
	{
		assertThrows(IllegalArgumentException.class, () -> this.lightningRod.setFacing(BlockFace.SELF));
	}

	@Test
	void validateClone()
	{
		this.lightningRod.setFacing(BlockFace.SOUTH);
		this.lightningRod.setPowered(true);
		this.lightningRod.setWaterlogged(true);

		@NotNull LightningRodDataMock cloned = this.lightningRod.clone();
		assertEquals(this.lightningRod, cloned);
		assertTrue(cloned instanceof LightningRod);

		this.lightningRod.setPowered(false);
		assertNotEquals(this.lightningRod, cloned);
		assertTrue(cloned.isPowered());
	}

}
