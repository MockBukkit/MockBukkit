package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class LingeringPotionMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private LingeringPotionMock lingeringPotion;

	@BeforeEach
	void setup()
	{
		this.lingeringPotion = new LingeringPotionMock(server, UUID.randomUUID());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.LINGERING_POTION, lingeringPotion.getType());
	}

}
