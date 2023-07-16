package be.seeseemelk.mockbukkit;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("deprecation")
class UnsafeValuesTest
{

	private static final String PLUGIN_INFO_FORMAT = "name: VersionTest\nversion: 1.0\nmain: not.exists\napi-version: %s";

	private ServerMock server;
	private MockUnsafeValues mockUnsafeValues;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		mockUnsafeValues = server.getUnsafe();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	private void checkVersion(String version) throws InvalidPluginException
	{
		String pluginInfo = String.format(PLUGIN_INFO_FORMAT, version);
		try (StringReader stringReader = new StringReader(pluginInfo))
		{
			PluginDescriptionFile pluginDescriptionFile = new PluginDescriptionFile(stringReader);
			mockUnsafeValues.checkSupported(pluginDescriptionFile);
		}
		catch (InvalidDescriptionException ex)
		{
			// exception shouldn't ever be thrown
			ex.printStackTrace();
		}
	}

	@Test
	void checkSupported_currentServerVersion()
	{
		String currentVersion = server.getMinecraftVersion();
		// if version is in pattern MAJOR.MINOR.FIX, transform to MAJOR.MINOR
		if (Pattern.matches(".{1,3}\\..{1,3}\\..*", currentVersion))
		{
			currentVersion = currentVersion.substring(0, currentVersion.indexOf(".", currentVersion.indexOf(".") + 1));
		}

		assertTrue(mockUnsafeValues.isSupportedApiVersion(currentVersion));
	}

	@Test
	void checkSupported_supportedVersion() throws InvalidPluginException
	{
		checkVersion("1.13");
	}

	@Test
	void checkSupported_unsupportedVersion()
	{
		assertThrows(InvalidPluginException.class, () -> checkVersion("1.8"));
	}

	@Test
	void checkSupported_noSpecifiedVersion()
	{
		assertDoesNotThrow(() ->
		{
			PluginDescriptionFile pluginDescriptionFile = new PluginDescriptionFile("VersionTest", "1.0", "not.exists");
			mockUnsafeValues.checkSupported(pluginDescriptionFile);
		});
	}

	@Test
	void minimumApiVersion_GreaterThanCurrentVersion()
	{
		assertThrows(InvalidPluginException.class, () ->
		{
			mockUnsafeValues.setMinimumApiVersion("1.15");
			checkVersion("1.13");
		});
	}

	@Test
	void fromLegacy_Material_NotLegacy_ReturnsSame()
	{
		for (Material material : Material.values())
		{
			if (material.isLegacy())
				continue;
			assertEquals(material, mockUnsafeValues.fromLegacy(material));
		}
	}

	@Test
	void fromLegacy_Material_Legacy_Throws()
	{
		for (Material material : Material.values())
		{
			if (!material.isLegacy())
				continue;
			assertThrows(UnimplementedOperationException.class, () -> mockUnsafeValues.fromLegacy(material));
		}
	}

	@Test
	void fromLegacy_Material_Null_ReturnsNull()
	{
		assertNull(mockUnsafeValues.fromLegacy((Material) null));
	}

	@Test
	void fromLegacy_MaterialData_NotLegacy_ReturnsSame()
	{
		for (Material material : Material.values())
		{
			if (material.isLegacy())
				continue;
			assertEquals(material, mockUnsafeValues.fromLegacy(new MaterialData(material)));
		}
	}

	@Test
	void fromLegacy_MaterialData_Legacy_Throws()
	{
		for (Material material : Material.values())
		{
			if (!material.isLegacy())
				continue;
			MaterialData materialData = new MaterialData(material);
			assertThrows(UnimplementedOperationException.class, () -> mockUnsafeValues.fromLegacy(materialData));
		}
	}

	@Test
	void fromLegacy_MaterialData_Null_ReturnsNull()
	{
		assertThrows(NullPointerException.class, () -> mockUnsafeValues.fromLegacy((MaterialData) null));
	}

}
