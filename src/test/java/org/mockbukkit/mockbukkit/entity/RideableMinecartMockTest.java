package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.RideableMinecart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class RideableMinecartMockTest
{

	@MockBukkitInject
	private RideableMinecart minecart;

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(Material.MINECART, minecart.getMinecartMaterial());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.MINECART, minecart.getType());
	}

}
