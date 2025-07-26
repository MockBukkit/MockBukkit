package org.mockbukkit.mockbukkit.dialog;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class DialogMockTest
{

	@Test
	void getKey()
	{
		Registry<@NotNull Dialog> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.DIALOG);

		Dialog serverLinks = registry.get(NamespacedKey.minecraft("server_links"));

		assertNotNull(serverLinks);
		assertEquals("minecraft:server_links", serverLinks.getKey().asString());
	}

}
