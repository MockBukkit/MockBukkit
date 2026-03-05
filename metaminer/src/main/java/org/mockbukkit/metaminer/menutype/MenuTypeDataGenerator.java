package org.mockbukkit.metaminer.menutype;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import org.bukkit.inventory.MenuType;
import org.bukkit.inventory.view.builder.LocationInventoryViewBuilder;
import org.bukkit.inventory.view.builder.MerchantInventoryViewBuilder;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.util.JsonUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MenuTypeDataGenerator implements DataGenerator
{
	private static final Map<String, ResidualMenuTypeData> RESIDUAL_DATA = Map.ofEntries(
			Map.entry("minecraft:generic_9x1", new ResidualMenuTypeData("CHEST", 9)),
			Map.entry("minecraft:generic_9x2", new ResidualMenuTypeData("CHEST", 18)),
			Map.entry("minecraft:generic_9x3", new ResidualMenuTypeData("CHEST", 27)),
			Map.entry("minecraft:generic_9x4", new ResidualMenuTypeData("CHEST", 36)),
			Map.entry("minecraft:generic_9x5", new ResidualMenuTypeData("CHEST", 45)),
			Map.entry("minecraft:generic_9x6", new ResidualMenuTypeData("CHEST", 54)),
			Map.entry("minecraft:generic_3x3", new ResidualMenuTypeData("DISPENSER", 9)),
			Map.entry("minecraft:crafter_3x3", new ResidualMenuTypeData("CRAFTER", 9)),
			Map.entry("minecraft:anvil", new ResidualMenuTypeData("ANVIL", 3)),
			Map.entry("minecraft:beacon", new ResidualMenuTypeData("BEACON", 1)),
			Map.entry("minecraft:blast_furnace", new ResidualMenuTypeData("BLAST_FURNACE", 3)),
			Map.entry("minecraft:brewing_stand", new ResidualMenuTypeData("BREWING", 5)),
			Map.entry("minecraft:crafting", new ResidualMenuTypeData("WORKBENCH", 10)),
			Map.entry("minecraft:enchantment", new ResidualMenuTypeData("ENCHANTING", 2)),
			Map.entry("minecraft:furnace", new ResidualMenuTypeData("FURNACE", 3)),
			Map.entry("minecraft:grindstone", new ResidualMenuTypeData("GRINDSTONE", 3)),
			Map.entry("minecraft:hopper", new ResidualMenuTypeData("HOPPER", 5)),
			Map.entry("minecraft:lectern", new ResidualMenuTypeData("LECTERN", 1)),
			Map.entry("minecraft:loom", new ResidualMenuTypeData("LOOM", 4)),
			Map.entry("minecraft:merchant", new ResidualMenuTypeData("MERCHANT", 3)),
			Map.entry("minecraft:shulker_box", new ResidualMenuTypeData("SHULKER_BOX", 27)),
			Map.entry("minecraft:smithing", new ResidualMenuTypeData("SMITHING", 4)),
			Map.entry("minecraft:smoker", new ResidualMenuTypeData("SMOKER", 3)),
			Map.entry("minecraft:cartography_table", new ResidualMenuTypeData("CARTOGRAPHY", 3)),
			Map.entry("minecraft:stonecutter", new ResidualMenuTypeData("STONECUTTER", 2))
	);

	private final File menuJsonFile;

	public MenuTypeDataGenerator(@NotNull File dataFolder)
	{
		this.menuJsonFile = new File(dataFolder, "keyed/menu.json");
	}

	@Override
	public void generateData() throws IOException
	{
		JsonObject root;
		try (FileReader reader = new FileReader(menuJsonFile, StandardCharsets.UTF_8))
		{
			root = JsonParser.parseReader(reader).getAsJsonObject();
		}

		Registry<MenuType> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.MENU);
		Map<String, MenuType> menuTypesByKey = new HashMap<>();
		for (Keyed keyed : registry)
		{
			menuTypesByKey.put(keyed.getKey().toString(), (MenuType) keyed);
		}

		JsonArray values = root.get("values").getAsJsonArray();
		for (JsonElement element : values)
		{
			JsonObject entry = element.getAsJsonObject();
			String key = entry.get("key").getAsString();

			MenuType menuType = menuTypesByKey.get(key);
			if (menuType != null)
			{
				entry.addProperty("inventoryViewClass", menuType.getInventoryViewClass().getSimpleName());
				entry.addProperty("builderType", resolveBuilderType(menuType));

				ResidualMenuTypeData residual = RESIDUAL_DATA.get(key);
				if (residual != null)
				{
					entry.addProperty("inventoryTypeName", residual.inventoryTypeName());
					entry.addProperty("inventorySize", residual.inventorySize());
				}
			}
		}

		JsonUtil.dump(root, menuJsonFile);
	}

	@SuppressWarnings("unchecked")
	private static String resolveBuilderType(MenuType menuType)
	{
		var builder = menuType.typed().builder();
		if (builder instanceof MerchantInventoryViewBuilder<?>)
		{
			return "MERCHANT";
		}
		else if (builder instanceof LocationInventoryViewBuilder<?>)
		{
			return "LOCATION";
		}
		return "BASE";
	}

	private record ResidualMenuTypeData(String inventoryTypeName, int inventorySize)
	{
	}

}
