package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.block.data.WallSignDataMock;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BlockDisplayMockTest
{

	private BlockDisplayMock blockDisplay;

	@BeforeEach
	void setUp()
	{
		ServerMock serverMock = MockBukkit.mock();
		this.blockDisplay = new BlockDisplayMock(serverMock, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

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
