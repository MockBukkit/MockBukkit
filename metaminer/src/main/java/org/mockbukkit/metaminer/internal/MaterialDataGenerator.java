package org.mockbukkit.metaminer.internal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Brushable;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Hatchable;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.block.data.type.TurtleEgg;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaterialDataGenerator implements DataGenerator
{

	private final File dataFolder;
	private static final Pattern BLOCK_DATA_PATTERN = Pattern.compile("\\[(.*)]");

	public MaterialDataGenerator(File parentDataFolder)
	{
		this.dataFolder = new File(parentDataFolder, "materials");
	}

	@Override
	public void generateData() throws IOException
	{
		JsonObject json = createJsonObject();
		JsonUtil.dump(json, new File(dataFolder, "material_data.json"));
	}

	private static @NotNull JsonObject createJsonObject()
	{
		JsonObject json = new JsonObject();

		Material[] materials = Material.values();
		Arrays.sort(materials, Comparator.comparing(Enum::name));

		for (Material material : materials)
		{
			try
			{
				BlockData data = material.createBlockData();
				String dataString = data.getAsString(false);
				Matcher matcher = BLOCK_DATA_PATTERN.matcher(dataString);
				if (!matcher.find())
				{ // It has no states
					json.add(material.key().toString(), new JsonObject());
					continue;
				}
				String[] states = matcher.group(1).split(",");

				JsonObject obj = new JsonObject();
				JsonObject defaultStates = new JsonObject();

				for (String state : states)
				{
					String[] state_split = state.split("=");
					String key = state_split[0].trim();
					String value = state_split[1].trim();
					switch (value.toLowerCase())
					{
					case "false" -> defaultStates.add(key, new JsonPrimitive(false));
					case "true" -> defaultStates.add(key, new JsonPrimitive(true));
					default -> defaultStates.add(key, new JsonPrimitive(value));
					}
				}

				JsonObject allowedStates = new JsonObject();
				extractCustomBlockDataProperties(data, allowedStates);
				obj.add("allowedStates", allowedStates);
				obj.add("defaultStates", defaultStates);

				json.add(material.key().toString(), obj);
			}
			catch (Exception ignored)
			{
			}
		}

		return json;
	}

	private static void extractCustomBlockDataProperties(BlockData data, JsonObject obj)
	{
		if (data instanceof Ageable ageable)
		{
			obj.addProperty("maxAge", String.valueOf(ageable.getMaximumAge()));
		}

		if (data instanceof AnaloguePowerable analoguePowerable)
		{
			obj.addProperty("maxPower", String.valueOf(analoguePowerable.getMaximumPower()));
		}

		if (data instanceof Sapling sapling)
		{
			obj.addProperty("maxStage", String.valueOf(sapling.getMaximumStage()));
		}

		if (data instanceof Levelled levelled)
		{
			obj.addProperty("maxLevel", String.valueOf(levelled.getMaximumLevel()));
			obj.addProperty("minLevel", String.valueOf(levelled.getMinimumLevel()));
		}

		if (data instanceof Brushable brushable)
		{
			obj.addProperty("maxDusted", String.valueOf(brushable.getMaximumDusted()));
		}

		if (data instanceof Directional directional)
		{
			JsonArray array = new JsonArray();
			directional.getFaces().stream().map(Enum::name).forEach(array::add);
			obj.add("faces", array);
		}

		if (data instanceof MultipleFacing multipleFacing)
		{
			JsonArray array = new JsonArray();
			multipleFacing.getAllowedFaces().stream().map(Enum::name).forEach(array::add);
			obj.add("faces", array);
		}

		if (data instanceof Farmland farmland)
		{
			obj.addProperty("maxMoisture", String.valueOf(farmland.getMaximumMoisture()));
		}

		if (data instanceof Hatchable hatchable)
		{
			obj.addProperty("maxHatch", String.valueOf(hatchable.getMaximumHatch()));
		}

		if (data instanceof TurtleEgg turtleEgg)
		{
			obj.addProperty("minEggs", String.valueOf(turtleEgg.getMinimumEggs()));
			obj.addProperty("maxEggs", String.valueOf(turtleEgg.getMaximumEggs()));
		}
	}

}
