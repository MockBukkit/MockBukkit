package org.mockbukkit.mockbukkit.entity.variant;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Cat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CatVariantMockTest
{

	@Test
	void testSuccessfullyLoaded()
	{
		Cat.Type catVariant = Cat.Type.BLACK;
		assertEquals("BLACK", catVariant.name());
		assertEquals(1, catVariant.ordinal());
		assertEquals(NamespacedKey.fromString("minecraft:black"), catVariant.getKey());
	}

}
