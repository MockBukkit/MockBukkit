package org.mockbukkit.mockbukkit.entity.variant;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Villager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class VillagerProfessionMockTest
{

	@Test
	void successfullyLoaded()
	{
		Villager.Profession profession = Villager.Profession.BUTCHER;
		assertEquals("BUTCHER", profession.name());
		assertEquals(NamespacedKey.fromString("minecraft:butcher"), profession.getKey());
		assertEquals("entity.minecraft.villager.butcher", profession.translationKey());
	}

}
