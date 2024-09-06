package org.mockbukkit.mockbukkit.entity.variant;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Villager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VillagerProfessionMockTest
{

	@Test
	void successfullyLoaded()
	{
		Villager.Profession profession = Villager.Profession.BUTCHER;
		assertEquals("BUTCHER", profession.name());
		assertEquals(NamespacedKey.fromString("minecraft:butcher"), profession.getKey());
		assertEquals(2, profession.ordinal());
		assertEquals("entity.minecraft.villager.butcher", profession.translationKey());
	}

}
