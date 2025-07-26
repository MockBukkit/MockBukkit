package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class TadpoleMockTest
{

	@MockBukkitInject
	private TadpoleMock tadpole;

	@Test
	void testGetBaseBucketItem()
	{
		assertEquals(Material.TADPOLE_BUCKET, tadpole.getBaseBucketItem().getType());
	}

	@Test
	void testGetPickupSound()
	{
		assertEquals(Sound.ITEM_BUCKET_FILL_TADPOLE, tadpole.getPickupSound());
	}

	@Test
	void testGetAge()
	{
		assertEquals(0, tadpole.getAge());
	}

	@Test
	void testSetAge()
	{
		tadpole.setAge(15000);
		assertEquals(15000, tadpole.getAge());
	}

	@Test
	void testSetAgeToBig()
	{
		assertThrows(IllegalArgumentException.class, () -> tadpole.setAge(426900));
	}

	@Test
	void testSetAgeToBigWithPreviousValue()
	{
		tadpole.setAge(15000);
		assertThrows(IllegalArgumentException.class, () -> tadpole.setAge(426900));
	}

	@Test
	void testGetAgeLockDefault()
	{
		assertFalse(tadpole.getAgeLock());
	}

	@Test
	void testSetAgeLock()
	{
		tadpole.setAgeLock(true);
		assertTrue(tadpole.getAgeLock());
	}

}
