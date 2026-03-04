package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Endermite;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockBukkitExtension.class)
class EndermiteMockTest
{

	@MockBukkitInject
	private Endermite enderMite;

	@Test
	void testGetType()
	{
		assertEquals(EntityType.ENDERMITE, enderMite.getType());
	}

	@Test
	void testIsPlayerSpawned()
	{
		assertFalse(enderMite.isPlayerSpawned());
	}

	@Test
	void testSetPlayerSpawned()
	{
		assertDoesNotThrow(() -> enderMite.setPlayerSpawned(true));
		assertFalse(enderMite.isPlayerSpawned());
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
