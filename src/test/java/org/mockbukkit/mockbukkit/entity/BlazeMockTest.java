package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class BlazeMockTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	@MockBukkitInject
	private BlazeMock blaze;

	@Test
	void testGetType()
	{
		assertEquals(EntityType.BLAZE, blaze.getType());
	}

}
