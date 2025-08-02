package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Fireball;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class FireballMockTest
{

	@MockBukkitInject
	private Fireball fireball;

	@Test
	void testGetDirectionDefault()
	{
		assertEquals(new Vector(), fireball.getDirection());
	}

	@Test
	void testSetDirection()
	{
		Vector direction = new Vector(1, 2, 3);
		fireball.setDirection(direction);
		assertEquals(direction, fireball.getDirection());
	}

	@Test
	void testSetDirectionNullThrowsException()
	{
		assertThrows(NullPointerException.class, () -> fireball.setDirection(null));
	}

	@Test
	void testGetYieldDefault()
	{
		assertEquals(1.0f, fireball.getYield());
	}

	@Test
	void testSetYield()
	{
		fireball.setYield(2.0f);
		assertEquals(2.0f, fireball.getYield());
	}

	@Test
	void testIsIncendiaryDefault()
	{
		assertFalse(fireball.isIncendiary());
	}

	@Test
	void testSetIsIncendiary()
	{
		fireball.setIsIncendiary(true);
		assertTrue(fireball.isIncendiary());
	}

	@Test
	void testGetAccelerationDefault()
	{
		assertEquals(new Vector(), fireball.getAcceleration());
	}

	@Test
	void testSetAcceleration()
	{
		Vector direction = new Vector(1, 2, 3);
		fireball.setAcceleration(direction);
		assertEquals(direction, fireball.getAcceleration());
	}

	@Test
	void testSetAccelerationNullThrowsException()
	{
		assertThrows(IllegalArgumentException.class, () -> fireball.setAcceleration(null));
	}

	@Test
	void testGetPowerDefault()
	{
		assertEquals(new Vector(), fireball.getPower());
	}

	@Test
	void testSetPower()
	{
		Vector direction = new Vector(1, 2, 3);
		fireball.setPower(direction);
		assertEquals(direction, fireball.getPower());
	}

	@Test
	void testSetPowerNullThrowsException()
	{
		assertThrows(IllegalArgumentException.class, () -> fireball.setPower(null));
	}

}
