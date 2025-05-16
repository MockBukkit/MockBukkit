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
class SplashPotionMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private SplashPotionMock splashPotion;

	@BeforeEach
	void setup()
	{
		this.splashPotion = new SplashPotionMock(server, UUID.randomUUID());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.SPLASH_POTION, splashPotion.getType());
	}

}
