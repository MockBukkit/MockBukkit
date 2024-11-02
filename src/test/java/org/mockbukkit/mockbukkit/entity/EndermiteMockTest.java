package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class EndermiteMockTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	private Endermite enderMite;

	@BeforeEach
	void setUp()
	{
		enderMite = new EndermiteMock(serverMock, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.ENDERMITE, enderMite.getType());
	}

	@Test
	void testIsPlayerSpawned()
	{
		Assertions.assertFalse(enderMite.isPlayerSpawned());
	}

	@Test
	void testSetPlayerSpawned()
	{
		assertDoesNotThrow(() -> enderMite.setPlayerSpawned(true));
		Assertions.assertFalse(enderMite.isPlayerSpawned());
	}

	@Test
	void testGetLifetimeTicksDefault()
	{
		assertEquals(0, enderMite.getLifetimeTicks());
	}

	@Test
	void testSetLifetimeTicks()
	{
		enderMite.setLifetimeTicks(123);
		assertEquals(123, enderMite.getLifetimeTicks());
	}

}
