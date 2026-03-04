package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.entity.Horse;
import org.bukkit.util.BoundingBox;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class SkeletonHorseMockTest
{

	@MockBukkitInject
	private SkeletonHorseMock skeletonHorse;

	@Test
	void testVariant()
	{
		assertEquals(Horse.Variant.SKELETON_HORSE, skeletonHorse.getVariant());
	}

	@Test
	void testGetTrappedDefault()
	{
		assertFalse(skeletonHorse.isTrapped());
	}

	@Test
	void testGetTrapTimeDefault()
	{
		assertEquals(0, skeletonHorse.getTrapTime());
	}

	@Test
	void testSetTrapped()
	{
		skeletonHorse.setTrapped(true);
		assertTrue(skeletonHorse.isTrapped());
	}

	@Test
	void testSetTrapTime()
	{
		skeletonHorse.setTrapTime(10);
		assertEquals(10, skeletonHorse.getTrapTime());
	}

	@Test
	void testIsTrapDefault()
	{
		assertFalse(skeletonHorse.isTrap());
	}

	@Test
	void testSetTrap()
	{
		skeletonHorse.setTrap(true);
		assertTrue(skeletonHorse.isTrap());
	}

	@Test
	void getEyeHeight_GivenDefaultSkeletonHorse()
	{
		assertEquals(1.52D, skeletonHorse.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabySkeletonHorse()
	{
		skeletonHorse.setBaby();
		assertEquals(0.76D, skeletonHorse.getEyeHeight());
	}

	@Test
	void getBoundingBox_GivenDefaultLocation()
	{
		BoundingBox actual = skeletonHorse.getBoundingBox();
		assertNotNull(actual);

		assertEquals(-0.6982422, actual.getMinX());
		assertEquals(0, actual.getMinY());
		assertEquals(-0.6982422, actual.getMinZ());

		assertEquals(0.6982422, actual.getMaxX());
		assertEquals(1.6, actual.getMaxY());
		assertEquals(0.6982422, actual.getMaxZ());
	}

	@Test
	void getBoundingBox_GivenDefaultLocationAndBaby()
	{
		skeletonHorse.setBaby();

		BoundingBox actual = skeletonHorse.getBoundingBox();
		assertNotNull(actual);

		assertEquals(-0.3491, actual.getMinX());
		assertEquals(0, actual.getMinY());
		assertEquals(-0.3491, actual.getMinZ());

		assertEquals(0.3491, actual.getMaxX());
		assertEquals(0.8, actual.getMaxY());
		assertEquals(0.3491, actual.getMaxZ());
	}

	@Test
	void getBoundingBox_GivenCustomLocation()
	{
		skeletonHorse.teleport(new Location(skeletonHorse.getWorld(), 10, 5, 10));

		BoundingBox actual = skeletonHorse.getBoundingBox();
		assertNotNull(actual);

		assertEquals(9.3017578, actual.getMinX());
		assertEquals(5, actual.getMinY());
		assertEquals(9.3017578, actual.getMinZ());

		assertEquals(10.6982422, actual.getMaxX());
		assertEquals(6.6, actual.getMaxY());
		assertEquals(10.6982422, actual.getMaxZ());
	}

}
