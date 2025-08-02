package org.mockbukkit.mockbukkit.registry;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.MusicInstrument;
import org.bukkit.Registry;
import org.bukkit.generator.structure.StructureType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockBukkitExtension.class)
class RegistryAccessMockTest
{

	@MockBukkitInject
	private RegistryAccessMock registryAccess;

	@Test
	void getRegistry_RegistryKey_createOnlyOnce()
	{
		Registry<?> registry = this.registryAccess.getRegistry(RegistryKey.STRUCTURE);
		Registry<?> duplicateRegistry = this.registryAccess.getRegistry(RegistryKey.STRUCTURE);
		assertSame(registry, duplicateRegistry);
	}

	@Test
	void getRegistry_Class_createOnlyOnce()
	{
		Registry<?> registry = this.registryAccess.getRegistry(StructureType.class);
		Registry<?> duplicateRegistry = this.registryAccess.getRegistry(StructureType.class);
		assertSame(registry, duplicateRegistry);
	}

	@Test
	void locale_independent()
	{
		Locale prevLocale = Locale.getDefault();
		Locale.setDefault(Locale.forLanguageTag("tr"));
		assertDoesNotThrow(() -> RegistryAccess.registryAccess().getRegistry(MusicInstrument.class));
		Locale.setDefault(prevLocale);
	}

}
