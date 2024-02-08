package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PigZombieMockTest
{

	private PigZombieMock pigZombie;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		pigZombie = new PigZombieMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testAngerDefault()
	{
		assertEquals(0, pigZombie.getAnger());
	}

	@Test
	void testSetAnger()
	{
		pigZombie.setAnger(100);
		assertEquals(100, pigZombie.getAnger());
		assertTrue(pigZombie.isAngry());
	}

	@Test
	void testAngryDefault()
	{
		assertFalse(pigZombie.isAngry());
	}

	@Test
	void testSetAngry()
	{
		pigZombie.setAngry(true);
		assertTrue(pigZombie.isAngry());
	}

	@Test
	void testIsConverting()
	{
		assertFalse(pigZombie.isConverting());
	}

	@Test
	void testGetConversionTime()
	{
		assertThrows(UnsupportedOperationException.class, pigZombie::getConversionTime);
	}

	@Test
	void testSetConversionTime()
	{
		assertThrows(UnsupportedOperationException.class, () -> pigZombie.setConversionTime(100));
	}

	@Test
	void testType()
	{
		assertEquals(EntityType.ZOMBIFIED_PIGLIN, pigZombie.getType());
	}

}
