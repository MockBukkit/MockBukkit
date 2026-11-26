package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.bukkit.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith(MockBukkitExtension.class)
class EnderDragonMockTest
{

	@MockBukkitInject
	private EnderDragonMock enderDragon;

	@Test
	void getPodium_ReturnsCopy()
	{
		Location location = new Location(enderDragon.getWorld(), 1, 2, 3);
		enderDragon.setPodium(location);
		location.add(1, 1, 1);

		assertEquals(new Location(enderDragon.getWorld(), 1, 2, 3), enderDragon.getPodium());
		assertNotSame(enderDragon.getPodium(), enderDragon.getPodium());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.ENDER_DRAGON, enderDragon.getType());
	}

	@Test
	void getHeight()
	{
		assertEquals(8.0D, enderDragon.getHeight());
	}

	@Test
	void getWidth()
	{
		assertEquals(16.0D, enderDragon.getWidth());
	}

	@Test
	void getEyeHeight()
	{
		assertEquals(6.8D, enderDragon.getEyeHeight());
	}

}
