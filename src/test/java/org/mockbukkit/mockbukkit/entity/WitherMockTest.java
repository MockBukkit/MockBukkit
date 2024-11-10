package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Difficulty;
import org.bukkit.entity.Wither;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class WitherMockTest
{
	@MockBukkitInject
	private ServerMock server;
	private WitherMock wither;
	@BeforeEach
	void setUp()
	{
		wither = new WitherMock(server, UUID.randomUUID());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.WITHER, wither.getType());
	}

	@Test
	void getHeight()
	{
		assertEquals(3.5D, wither.getHeight());
	}

	@Test
	void getWidth()
	{
		assertEquals(0.9D, wither.getWidth());
	}

	@Test
	void getEyeHeight()
	{
		assertEquals(2.9750001D, wither.getEyeHeight());
	}

	@Test
	void getMaxHeathEasy()
	{
		WorldMock worldMock = new WorldMock();
		worldMock.setDifficulty(Difficulty.EASY);
		WitherMock tempWither = new WitherMock(server, UUID.randomUUID(), worldMock);
		assertEquals(300.0D, tempWither.getMaxHealth());
	}

	@Test
	void getMaxHeathNormal()
	{
		WorldMock worldMock = new WorldMock();
		worldMock.setDifficulty(Difficulty.NORMAL);
		WitherMock tempWither = new WitherMock(server, UUID.randomUUID(), worldMock);
		assertEquals(450.0D, tempWither.getMaxHealth());
	}

	@Test
	void getMaxHeathHard()
	{
		WorldMock worldMock = new WorldMock();
		worldMock.setDifficulty(Difficulty.HARD);
		WitherMock tempWither = new WitherMock(server, UUID.randomUUID(), worldMock);
		assertEquals(600.0D, tempWither.getMaxHealth());
	}

	@Test
	void nullGetHeadTarget()
	{
		assertNull(wither.getTarget(Wither.Head.CENTER));
	}

	@Test
	void getSetHeadTarget()
	{
		LivingEntityMock mock = new ZombieMock(server, UUID.randomUUID());
		wither.setTarget(Wither.Head.CENTER, mock);
		assertEquals(wither.getTarget(Wither.Head.CENTER), mock);
	}

}
