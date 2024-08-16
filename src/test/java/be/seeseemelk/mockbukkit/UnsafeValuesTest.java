package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.inventory.ItemStackMock;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.WorldCreator;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
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
	private static final Logger log = LoggerFactory.getLogger(UnsafeValuesTest.class);

	private ServerMock server;
	private MockUnsafeValues mockUnsafeValues;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.getOrCreateMock();
		WorldCreator.name("world").createWorld();
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

	private static Stream<Arguments> provideTestItems() throws IOException
	{
		MockBukkit.mock();
		List<Arguments> args = new ArrayList<>();
		try (InputStream inputStream = MockBukkit.class.getResourceAsStream("/itemstack/metaItemTypes.json"))
		{
			JsonElement jsonArray = JsonParser.parseReader(new InputStreamReader(inputStream));
			for (JsonElement jsonElement : jsonArray.getAsJsonArray())
			{
				Material material = Registry.MATERIAL.get(NamespacedKey.fromString(jsonElement.getAsString()));
				if (material.isLegacy() || material.isAir())
				{
					continue;
				}
				if (material.asItemType() == null) //We dont have a way to serialize these properly right now
				{
					continue;
				}
				ItemStack item = new ItemStackMock(material);
				args.add(Arguments.of(Named.of(material.name(), item)));
			}
			ItemStack item = new ItemStackMock(Material.STONE);
			args.add(Arguments.of(Named.of(Material.STONE.name(), item)));
		}
		return args.stream();
	}

	@ParameterizedTest
	@MethodSource("provideTestItems")
	void serializeItemTest(ItemStack expected)
	{
		populateItemMeta(expected);
		byte[] serialized = mockUnsafeValues.serializeItem(expected);
		ItemStack actual = mockUnsafeValues.deserializeItem(serialized);
		assertEquals(expected, actual);
		assertEquals(expected.getItemMeta(), actual.getItemMeta());
	}

	private void populateItemMeta(ItemStack item)
	{
		item.editMeta(meta ->
		{
			meta.setDisplayName("Test");
			meta.setLore(List.of("Test1", "Test2"));
			meta.setUnbreakable(true);
			meta.addEnchant(Enchantment.SHARPNESS, 1, true);
			final PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
			persistentDataContainer.set(new NamespacedKey("test", "test"), PersistentDataType.STRING, "test");
		});
		item.editMeta(meta ->
		{
			if (meta instanceof BundleMeta bundleMeta)
			{
				bundleMeta.addItem(item.clone());
			}
			if (meta instanceof BannerMeta bannerMeta)
			{
				bannerMeta.addPattern(new org.bukkit.block.banner.Pattern(DyeColor.BLACK, PatternType.BASE));
			}
			if (meta instanceof CompassMeta compassMeta)
			{
				compassMeta.setLodestone(new Location(Bukkit.getWorlds().get(0), 1, 2, 3));
				compassMeta.setLodestoneTracked(true);
			}
			if (meta instanceof CrossbowMeta crossbowMeta)
			{
				ItemStack arrow = new ItemStackMock(Material.ARROW);
				arrow.editMeta(itemMeta ->
				{
				});
				crossbowMeta.addChargedProjectile(arrow);
			}
			if (meta instanceof EnchantmentStorageMeta storageMeta)
			{
				storageMeta.addStoredEnchant(Enchantment.SHARPNESS, 1, true);
			}
		});
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

	@Test
	void testMaterialThatIsOnlyItemTranslationKey()
	{
		assertEquals("item.minecraft.saddle", Material.SADDLE.getItemTranslationKey());
		assertNull(Material.SADDLE.getBlockTranslationKey());
	}

	@ParameterizedTest
	@MethodSource("materialAndBlockTranslationKeyProvider")
	void testMaterialThatIsItemAndBlockTranslationKey(String expectedBlockKey, String expectedItemKey, Material material)
	{
		assertEquals(expectedBlockKey, mockUnsafeValues.getBlockTranslationKey(material));
		assertEquals(expectedItemKey, mockUnsafeValues.getItemTranslationKey(material));
	}

	static Stream<Arguments> materialAndBlockTranslationKeyProvider()
	{
		return Stream.of(
				Arguments.of("block.minecraft.stone", "block.minecraft.stone", Material.STONE),
				Arguments.of("block.minecraft.dirt", "block.minecraft.dirt", Material.DIRT),
				Arguments.of("item.minecraft.wheat", "item.minecraft.wheat", Material.WHEAT),
				Arguments.of("item.minecraft.nether_wart", "item.minecraft.nether_wart", Material.NETHER_WART)
		);
	}

	@ParameterizedTest
	@MethodSource("wallMaterialTranslationKeyProvider")
	void testWallMaterialTranslationKey(String expectedKey, Material material)
	{
		assertEquals(expectedKey, material.getBlockTranslationKey());
	}

	static Stream<Arguments> wallMaterialTranslationKeyProvider()
	{
		return Stream.of(
				Arguments.of("block.minecraft.acacia_sign", Material.ACACIA_SIGN),
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
				Arguments.of("block.minecraft.creeper_head", Material.CREEPER_WALL_HEAD)
		);
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
		return Stream.of(
				Arguments.of("item.minecraft.saddle", new ItemStackMock(Material.SADDLE)),
				Arguments.of("block.minecraft.stone", new ItemStackMock(Material.STONE)),
				Arguments.of("item.minecraft.wheat", new ItemStackMock(Material.WHEAT)),
				Arguments.of("item.minecraft.nether_wart", new ItemStackMock(Material.NETHER_WART))
		);
	}

	@ParameterizedTest
	@MethodSource("itemStackEmptyEffectTranslationKeyProvider")
	void testItemStackEmptyEffectTranslationKey(
			String expectedMaterialKey,
			Material material,
			String expectedItemStackKey,
			ItemStack itemStack
	)
	{
		assertEquals(expectedMaterialKey, material.getItemTranslationKey());
		assertEquals(expectedItemStackKey, itemStack.translationKey());
	}

	static Stream<Arguments> itemStackEmptyEffectTranslationKeyProvider()
	{
		return Stream.of(
				Arguments.of(
						"item.minecraft.potion",
						Material.POTION,
						"item.minecraft.potion.effect.empty",
						new ItemStackMock(Material.POTION)
				),
				Arguments.of(
						"item.minecraft.splash_potion",
						Material.SPLASH_POTION,
						"item.minecraft.splash_potion.effect.empty",
						new ItemStackMock(Material.SPLASH_POTION)
				),
				Arguments.of(
						"item.minecraft.tipped_arrow",
						Material.TIPPED_ARROW,
						"item.minecraft.tipped_arrow.effect.empty",
						new ItemStackMock(Material.TIPPED_ARROW)
				),
				Arguments.of(
						"item.minecraft.lingering_potion",
						Material.LINGERING_POTION,
						"item.minecraft.lingering_potion.effect.empty",
						new ItemStackMock(Material.LINGERING_POTION)
				)
		);
	}

}
