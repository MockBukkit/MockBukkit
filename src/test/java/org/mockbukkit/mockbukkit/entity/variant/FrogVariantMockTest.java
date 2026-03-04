package org.mockbukkit.mockbukkit.entity.variant;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Frog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class FrogVariantMockTest
{

	@Test
	void successfullyLoaded()
	{
		Frog.Variant variant = Frog.Variant.COLD;
		assertEquals("COLD", variant.name());
		assertEquals(NamespacedKey.fromString("minecraft:cold"), variant.getKey());
	}

}
