package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.block.data.WallSignDataMock;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class BlockDisplayMockTest
{

	@MockBukkitInject
	private BlockDisplayMock blockDisplay;

	@Test
	void getBlock_default()
	{
		assertEquals(Material.AIR.createBlockData(), blockDisplay.getBlock());
	}

	@Test
	void setBlock()
	{
		BlockData blockData = new WallSignDataMock(Material.OAK_WALL_SIGN);
		blockDisplay.setBlock(blockData);
		assertEquals(blockData, blockDisplay.getBlock());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.BLOCK_DISPLAY, blockDisplay.getType());
	}

}
