package org.mockbukkit.mockbukkit.entity.variant;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Villager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class VillagerTypeMockTest
{

	@Test
	void successfullyLoaded()
	{
		Villager.Type type = Villager.Type.JUNGLE;
		assertEquals("JUNGLE", type.name());
		assertEquals(NamespacedKey.fromString("minecraft:jungle"), type.getKey());
	}

}
