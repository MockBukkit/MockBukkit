package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.util.BoundingBox;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.entity.data.EntitySubType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class ZombieMockTest
{

	@MockBukkitInject
	private ZombieMock zombie;

	@Test
	void getType()
	{
		assertEquals(EntityType.ZOMBIE, zombie.getType());
	}

	@Test
	void getSubType_GivenDefaultZombie()
	{
		assertEquals(EntitySubType.DEFAULT, zombie.getSubType());
	}

	@Test
	void getSubType_GivenBabyZombie()
	{
		zombie.setBaby();
		assertEquals(EntitySubType.BABY, zombie.getSubType());
	}

	@Test
	void getEyeHeight_GivenDefaultZombie()
	{
		assertEquals(1.74D, zombie.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyZombie()
	{
		zombie.setBaby();
		assertEquals(0.87D, zombie.getEyeHeight());
	}

	@Test
	void getBoundingBox_GivenDefaultLocation()
	{
		BoundingBox actual = zombie.getBoundingBox();
		assertNotNull(actual);

		assertEquals(-0.3, actual.getMinX());
		assertEquals(0, actual.getMinY());
		assertEquals(-0.3, actual.getMinZ());

		assertEquals(0.3, actual.getMaxX());
		assertEquals(1.95, actual.getMaxY());
		assertEquals(0.3, actual.getMaxZ());
	}

	@Test
	void getBoundingBox_GivenCustomLocation()
	{
		zombie.teleport(new Location(zombie.getWorld(), 10, 5, 10));

		BoundingBox actual = zombie.getBoundingBox();
		assertNotNull(actual);

		assertEquals(9.7, actual.getMinX());
		assertEquals(5, actual.getMinY());
		assertEquals(9.7, actual.getMinZ());

		assertEquals(10.3, actual.getMaxX());
		assertEquals(6.95, actual.getMaxY());
		assertEquals(10.3, actual.getMaxZ());
	}

}
