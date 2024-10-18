package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SlimeMockTest
{

	private SlimeMock slime;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		slime = new SlimeMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetSize()
	{
		assertEquals(1, slime.getSize());
	}

	@Test
	void testSetSize()
	{
		slime.setSize(2);
		assertEquals(2, slime.getSize());
		assertEquals(4, slime.getHealth());
	}

	@Test
	void testSetMaxSize()
	{
		slime.setSize(127);
		assertEquals(127, slime.getSize());
		assertEquals(16129, slime.getHealth());
	}

	@Test
	void testSetSize_Negative()
	{
		assertThrows(IllegalArgumentException.class, () -> slime.setSize(-1));
	}

	@Test
	void testSetSize_TooBig()
	{
		assertThrows(IllegalArgumentException.class, () -> slime.setSize(128));
	}

	@Test
	void testCanWander()
	{
		assertTrue(slime.canWander());
	}

	@Test
	void testSetCanWander()
	{
		slime.setWander(false);
		assertFalse(slime.canWander());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.SLIME, slime.getType());
	}

}
