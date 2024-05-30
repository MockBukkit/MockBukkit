package be.seeseemelk.mockbukkit;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.StringReader;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
		assertDoesNotThrow(() -> {
			PluginDescriptionFile pluginDescriptionFile = new PluginDescriptionFile("VersionTest", "1.0", "not.exists");
			mockUnsafeValues.checkSupported(pluginDescriptionFile);
		});
	}

	@Test
	void minimumApiVersion_GreaterThanCurrentVersion()
	{
		assertThrows(InvalidPluginException.class, () -> {
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

	@Test
	void testMaterialThatIsOnlyItemTranslationKey()
	{
		assertEquals("item.minecraft.saddle", Material.SADDLE.getItemTranslationKey());
		assertNull(Material.SADDLE.getBlockTranslationKey());
	}

	@ParameterizedTest
	@MethodSource("materialAndBlockTranslationKeyProvider")
	void testMaterialThatIsItemAndBlockTranslationKey(String expectedBlockKey, String expectedItemKey,
			Material material)
	{
		assertEquals(expectedBlockKey, mockUnsafeValues.getBlockTranslationKey(material));
		assertEquals(expectedItemKey, mockUnsafeValues.getItemTranslationKey(material));
	}

	static Stream<Arguments> materialAndBlockTranslationKeyProvider()
	{
		return Stream.of(Arguments.of("block.minecraft.stone", "block.minecraft.stone", Material.STONE),
				Arguments.of("block.minecraft.dirt", "block.minecraft.dirt", Material.DIRT),
				Arguments.of("item.minecraft.wheat", "item.minecraft.wheat", Material.WHEAT),
				Arguments.of("item.minecraft.nether_wart", "item.minecraft.nether_wart", Material.NETHER_WART));
	}

	@ParameterizedTest
	@MethodSource("wallMaterialTranslationKeyProvider")
	void testWallMaterialTranslationKey(String expectedKey, Material material)
	{
		assertEquals(expectedKey, material.getBlockTranslationKey());
	}

	static Stream<Arguments> wallMaterialTranslationKeyProvider()
	{
		return Stream.of(Arguments.of("block.minecraft.acacia_sign", Material.ACACIA_SIGN),
				Arguments.of("block.minecraft.acacia_sign", Material.ACACIA_WALL_SIGN),
				Arguments.of("block.minecraft.acacia_hanging_sign", Material.ACACIA_HANGING_SIGN),
				Arguments.of("block.minecraft.acacia_hanging_sign", Material.ACACIA_WALL_HANGING_SIGN),
				Arguments.of("block.minecraft.white_banner", Material.WHITE_BANNER),
				Arguments.of("block.minecraft.white_banner", Material.WHITE_WALL_BANNER),
				Arguments.of("block.minecraft.torch", Material.TORCH),
				Arguments.of("block.minecraft.torch", Material.WALL_TORCH),
				Arguments.of("block.minecraft.skeleton_skull", Material.SKELETON_SKULL),
				Arguments.of("block.minecraft.skeleton_skull", Material.SKELETON_WALL_SKULL),
				Arguments.of("block.minecraft.creeper_head", Material.CREEPER_HEAD),
				Arguments.of("block.minecraft.creeper_head", Material.CREEPER_WALL_HEAD));
	}

	@Test
	void testEntityTranslationKey()
	{
		assertEquals("entity.minecraft.pig", mockUnsafeValues.getTranslationKey(EntityType.PIG));
		assertThrows(IllegalArgumentException.class, () -> mockUnsafeValues.getTranslationKey(EntityType.UNKNOWN));
	}

	@ParameterizedTest
	@MethodSource("itemStackTranslationKeyProvider")
	void testItemStackTranslationKey(String expectedKey, ItemStack itemStack)
	{
		assertEquals(expectedKey, mockUnsafeValues.getTranslationKey(itemStack));
	}

	static Stream<Arguments> itemStackTranslationKeyProvider()
	{
		return Stream.of(Arguments.of("item.minecraft.saddle", new ItemStack(Material.SADDLE)),
				Arguments.of("block.minecraft.stone", new ItemStack(Material.STONE)),
				Arguments.of("item.minecraft.wheat", new ItemStack(Material.WHEAT)),
				Arguments.of("item.minecraft.nether_wart", new ItemStack(Material.NETHER_WART)));
	}

	@ParameterizedTest
	@MethodSource("itemStackEmptyEffectTranslationKeyProvider")
	void testItemStackEmptyEffectTranslationKey(String expectedMaterialKey, Material material,
			String expectedItemStackKey, ItemStack itemStack)
	{
		assertEquals(expectedMaterialKey, material.getItemTranslationKey());
		assertEquals(expectedItemStackKey, itemStack.translationKey());
	}

	static Stream<Arguments> itemStackEmptyEffectTranslationKeyProvider()
	{
		return Stream.of(
				Arguments.of("item.minecraft.potion", Material.POTION, "item.minecraft.potion.effect.empty",
						new ItemStack(Material.POTION)),
				Arguments.of("item.minecraft.splash_potion", Material.SPLASH_POTION,
						"item.minecraft.splash_potion.effect.empty", new ItemStack(Material.SPLASH_POTION)),
				Arguments.of("item.minecraft.tipped_arrow", Material.TIPPED_ARROW,
						"item.minecraft.tipped_arrow.effect.empty", new ItemStack(Material.TIPPED_ARROW)),
				Arguments.of("item.minecraft.lingering_potion", Material.LINGERING_POTION,
						"item.minecraft.lingering_potion.effect.empty", new ItemStack(Material.LINGERING_POTION)));
	}

}
