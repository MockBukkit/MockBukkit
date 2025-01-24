package org.mockbukkit.mockbukkit.block;

import org.bukkit.block.BlockType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class BlockTypeMockTest
{

	@Test
	void getTyped()
	{
		assertNotNull(BlockType.ACACIA_BUTTON);
	}

}
