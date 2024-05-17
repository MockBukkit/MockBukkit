package be.seeseemelk.mockbukkit.registry;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.MusicInstrument;
import org.bukkit.Registry;
import org.bukkit.StructureType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;

class RegistryAccessMockTest
{

	private RegistryAccessMock registryAccess;

	@BeforeEach
	void setUp()
	{
		this.registryAccess = new RegistryAccessMock();
	}

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
		try
		{
			Registry<?> registry = this.registryAccess.getRegistry(StructureType.class);
			Registry<?> duplicateRegistry = this.registryAccess.getRegistry(StructureType.class);
			assertSame(registry, duplicateRegistry);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
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
