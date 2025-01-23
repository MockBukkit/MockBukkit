package org.mockbukkit.mockbukkit.block;

import org.bukkit.block.BlockType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTypeMockTest
{
	@Test
	void getTyped(){
		assertNotNull(BlockType.ACACIA_BUTTON);
	}
}
