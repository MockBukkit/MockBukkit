package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Horse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class ZombieHorseMockTest
{

	@MockBukkitInject
	private ZombieHorseMock zombieHorse;

	@Test
	void testVariant()
	{
		assertEquals(Horse.Variant.UNDEAD_HORSE, zombieHorse.getVariant());
	}

}
