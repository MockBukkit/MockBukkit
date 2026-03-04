package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class GlowItemFrameMockTest
{

	@MockBukkitInject
	private GlowItemFrameMock itemFrame;

	@Test
	void getType()
	{
		assertEquals(EntityType.GLOW_ITEM_FRAME, itemFrame.getType());
	}

}
