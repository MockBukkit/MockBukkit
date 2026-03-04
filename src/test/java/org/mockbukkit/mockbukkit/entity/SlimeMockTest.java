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
class SlimeMockTest
{

	@MockBukkitInject
	private SlimeMock slime;

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
