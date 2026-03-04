package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class BatMockTest
{

	@MockBukkitInject
	private BatMock bat;

	@Test
	void testIsAwakeDefault()
	{
		assertTrue(bat.isAwake());
	}

	@Test
	void testSetAwake()
	{
		bat.setAwake(false);
		assertFalse(bat.isAwake());
	}

	@Test
	void testEntityType()
	{
		assertEquals(EntityType.BAT, bat.getType());
	}

	@Test
	void testTargetLocationDefault()
	{
		assertNull(bat.getTargetLocation());
	}

	@Test
	void testTargetLocation()
	{
		bat.setTargetLocation(bat.getLocation());
		assertEquals(bat.getLocation(), bat.getTargetLocation());
	}

	@Test
	void testSetTargetLocationNull()
	{
		bat.setTargetLocation(new Location(null, 0, 0, 0));
		assertNotNull(bat.getTargetLocation());
		bat.setTargetLocation(null);
		assertNull(bat.getTargetLocation());
	}

}
