package org.mockbukkit.mockbukkit.entity.variant;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Frog;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FrogVariantMockTest
{


	@Test
	void successfullyLoaded()
	{
		Frog.Variant variant = Frog.Variant.COLD;
		assertEquals("COLD", variant.name());
		assertEquals(NamespacedKey.fromString("minecraft:cold"), variant.getKey());
		assertEquals(2, variant.ordinal());
	}

}
