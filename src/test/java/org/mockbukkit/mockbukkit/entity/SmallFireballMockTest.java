package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.SmallFireball;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class SmallFireballMockTest
{

	@MockBukkitInject
	private SmallFireball smallFireball;

	@Test
	void testGetType()
	{
		assertEquals(EntityType.SMALL_FIREBALL, smallFireball.getType());
	}

}
