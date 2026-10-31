package org.mockbukkit.mockbukkit.block.pot;

import io.papermc.paper.block.pot.PotPatternType;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class PotPatternTypeMockTest
{

	@Test
	void givenValidKey_shouldMatch()
	{
		NamespacedKey namespacedKey = NamespacedKey.minecraft("brewer");
		Registry<PotPatternType> decoratedPotRegistry = RegistryAccess.registryAccess().getRegistry(RegistryKey.DECORATED_POT_PATTERN);

		PotPatternType pattern = decoratedPotRegistry.get(namespacedKey);

		assertNotNull(pattern);
		assertEquals(namespacedKey, pattern.getKey());
	}

}
