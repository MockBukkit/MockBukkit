package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TadpoleMockTest
{

	private TadpoleMock tadpole;

	@BeforeEach
	void setup()
	{
		ServerMock server = MockBukkit.mock();
		tadpole = new TadpoleMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

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
