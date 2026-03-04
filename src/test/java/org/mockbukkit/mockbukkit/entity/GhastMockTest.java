package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class GhastMockTest
{

	@MockBukkitInject
	private GhastMock ghast;

	@Test
	void testIsChargingDefault()
	{
		assertFalse(ghast.isCharging());
	}

	@Test
	void testSetCharging()
	{
		ghast.setCharging(true);
		assertTrue(ghast.isCharging());
	}

	@Test
	void testGetExplosionPowerDefault()
	{
		assertEquals(1, ghast.getExplosionPower());
	}

	@Test
	void testSetExplosionPower()
	{
		ghast.setExplosionPower(10);
		assertEquals(10, ghast.getExplosionPower());
	}

	@Test
	void testSetExplosionPowerInvalidToSmall()
	{
		assertThrows(IllegalArgumentException.class, () -> ghast.setExplosionPower(-1));
	}

	@Test
	void testSetExplosionPowerInvalidToBig()
	{
		assertThrows(IllegalArgumentException.class, () -> ghast.setExplosionPower(128));
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.GHAST, ghast.getType());
	}

}
