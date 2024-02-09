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

	@Test
	void testMaterialThatIsOnlyItemTranslationKey()
	{
		assertEquals("item.minecraft.saddle", Material.SADDLE.translationKey());
		assertNull(Material.SADDLE.getBlockTranslationKey());

		assertEquals("item.minecraft.flint_and_steel", Material.FLINT_AND_STEEL.translationKey());
		assertNull(Material.FLINT_AND_STEEL.getBlockTranslationKey());


	}

	@Test
	void testMaterialThatIsItemAndBlockTranslationKey()
	{
		assertEquals("block.minecraft.stone", mockUnsafeValues.getBlockTranslationKey(Material.STONE));
		assertEquals("block.minecraft.stone", mockUnsafeValues.getItemTranslationKey(Material.STONE));

		assertEquals("block.minecraft.dirt", mockUnsafeValues.getBlockTranslationKey(Material.DIRT));
		assertEquals("block.minecraft.dirt", mockUnsafeValues.getItemTranslationKey(Material.DIRT));

		// wheat and nether_wart are the two exceptions, they are items and blocks, but start with item
		assertEquals("item.minecraft.wheat", mockUnsafeValues.getBlockTranslationKey(Material.WHEAT));
		assertEquals("item.minecraft.wheat", mockUnsafeValues.getItemTranslationKey(Material.WHEAT));

		assertEquals("item.minecraft.nether_wart", mockUnsafeValues.getBlockTranslationKey(Material.NETHER_WART));
		assertEquals("item.minecraft.nether_wart", mockUnsafeValues.getItemTranslationKey(Material.NETHER_WART));
	}

	@Test
	void testWallMaterialTranslationKey()
	{
		assertEquals("block.minecraft.acacia_sign", mockUnsafeValues.getBlockTranslationKey(Material.ACACIA_SIGN));
		assertEquals("block.minecraft.acacia_sign", mockUnsafeValues.getBlockTranslationKey(Material.ACACIA_WALL_SIGN));

		assertEquals("block.minecraft.acacia_hanging_sign", mockUnsafeValues.getBlockTranslationKey(Material.ACACIA_HANGING_SIGN));
		assertEquals("block.minecraft.acacia_hanging_sign", mockUnsafeValues.getBlockTranslationKey(Material.ACACIA_WALL_HANGING_SIGN));

		assertEquals("block.minecraft.white_banner", mockUnsafeValues.getBlockTranslationKey(Material.WHITE_BANNER));
		assertEquals("block.minecraft.white_banner", mockUnsafeValues.getBlockTranslationKey(Material.WHITE_WALL_BANNER));

		assertEquals("block.minecraft.torch", mockUnsafeValues.getBlockTranslationKey(Material.TORCH));
		assertEquals("block.minecraft.torch", mockUnsafeValues.getBlockTranslationKey(Material.WALL_TORCH));

		assertEquals("block.minecraft.skeleton_skull", mockUnsafeValues.getBlockTranslationKey(Material.SKELETON_SKULL));
		assertEquals("block.minecraft.skeleton_skull", mockUnsafeValues.getBlockTranslationKey(Material.SKELETON_WALL_SKULL));

		assertEquals("block.minecraft.creeper_head", mockUnsafeValues.getBlockTranslationKey(Material.CREEPER_HEAD));
		assertEquals("block.minecraft.creeper_head", mockUnsafeValues.getBlockTranslationKey(Material.CREEPER_WALL_HEAD));
	}

	@Test
	void testEntityTranslationKey()
	{
		assertEquals("entity.minecraft.pig",mockUnsafeValues.getTranslationKey(EntityType.PIG));
		assertEquals("entity.minecraft.ender_dragon", mockUnsafeValues.getTranslationKey(EntityType.ENDER_DRAGON));
		assertThrows(IllegalArgumentException.class, () -> mockUnsafeValues.getTranslationKey(EntityType.UNKNOWN));
	}

	@Test
	void testItemStackTranslationKey()
	{
		assertEquals("item.minecraft.saddle", mockUnsafeValues.getTranslationKey(new ItemStack(Material.SADDLE)));
		assertEquals("item.minecraft.flint_and_steel", mockUnsafeValues.getTranslationKey(new ItemStack(Material.FLINT_AND_STEEL)));

		assertEquals("block.minecraft.stone", mockUnsafeValues.getTranslationKey(new ItemStack(Material.STONE)));
		assertEquals("block.minecraft.dirt", mockUnsafeValues.getTranslationKey(new ItemStack(Material.DIRT)));

		// wheat and nether_wart are the two exceptions, they are items and blocks, but start with item
		assertEquals("item.minecraft.wheat", mockUnsafeValues.getTranslationKey(new ItemStack(Material.WHEAT)));
		assertEquals("item.minecraft.nether_wart", mockUnsafeValues.getTranslationKey(new ItemStack(Material.NETHER_WART)));
	}

	@Test
	void testItemStackEmptyEffectTranslationKey()
	{
		assertEquals("item.minecraft.potion", Material.POTION.translationKey());
		assertEquals("item.minecraft.potion.effect.empty", new ItemStack(Material.POTION).translationKey());

		assertEquals("item.minecraft.splash_potion", Material.SPLASH_POTION.translationKey());
		assertEquals("item.minecraft.splash_potion.effect.empty", new ItemStack(Material.SPLASH_POTION).translationKey());

		assertEquals("item.minecraft.tipped_arrow", Material.TIPPED_ARROW.translationKey());
		assertEquals("item.minecraft.tipped_arrow.effect.empty", new ItemStack(Material.TIPPED_ARROW).translationKey());

		assertEquals("item.minecraft.lingering_potion", Material.LINGERING_POTION.translationKey());
		assertEquals("item.minecraft.lingering_potion.effect.empty", new ItemStack(Material.LINGERING_POTION).translationKey());
	}

}
