package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class DragonFireballMockTest
{

	@MockBukkitInject
	private DragonFireball dragonFireball;

	@Test
	void testGetType()
	{
		assertEquals(EntityType.DRAGON_FIREBALL, dragonFireball.getType());
	}

}
