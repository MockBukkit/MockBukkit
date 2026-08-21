package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith(MockBukkitExtension.class)
class DolphinMockTest
{

	@MockBukkitInject
	private Dolphin dolphin;

	@Test
	void getTreasureLocation_ReturnsCopy()
	{
		Location location = new Location(dolphin.getWorld(), 1, 2, 3);
		dolphin.setTreasureLocation(location);
		location.add(1, 1, 1);

		assertEquals(new Location(dolphin.getWorld(), 1, 2, 3), dolphin.getTreasureLocation());
		assertNotSame(dolphin.getTreasureLocation(), dolphin.getTreasureLocation());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.DOLPHIN, dolphin.getType());
	}

	@Test
	void testGetMoistness_Default()
	{
		assertEquals(2400, dolphin.getMoistness());
	}

	@Test
	void testSetMoistness()
	{
		dolphin.setMoistness(100);
		assertEquals(100, dolphin.getMoistness());
	}

	@Test
	void testGotFish_Default()
	{
		assertFalse(dolphin.hasFish());
	}

	@Test
	void testSetGotFish()
	{
		dolphin.setHasFish(true);
		assertTrue(dolphin.hasFish());
	}

	@Test
	void testGetTreasureLocation_Default()
	{
		assertEquals(new Location(null, 0, 0, 0), dolphin.getTreasureLocation());
	}

	@Test
	void testSetTreasureLocation()
	{
		Location location = new Location(null, 1, 2, 3);
		dolphin.setTreasureLocation(location);
		assertEquals(location, dolphin.getTreasureLocation());
	}

	@Test
	void testSetTreasureLocation_Null_ThrowsException()
	{
		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
				dolphin.setTreasureLocation(null));

		assertEquals("Location can't be null.", illegalArgumentException.getMessage());
	}

	@Test
	void getEyeHeight_GivenDefaultDolphin()
	{
		assertEquals(0.3D, dolphin.getEyeHeight());
	}

}
