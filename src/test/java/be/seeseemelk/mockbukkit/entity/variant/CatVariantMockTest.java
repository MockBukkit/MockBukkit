package be.seeseemelk.mockbukkit.entity.variant;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Cat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatVariantMockTest
{

	@Test
	void testSuccessfullyLoaded(){
		Cat.Type catVariant = Cat.Type.BLACK;
		assertEquals(catVariant.name(), "BLACK");
		assertEquals(catVariant.ordinal(), 1);
		assertEquals(catVariant.getKey(), NamespacedKey.fromString("minecraft:black"));
	}
}
