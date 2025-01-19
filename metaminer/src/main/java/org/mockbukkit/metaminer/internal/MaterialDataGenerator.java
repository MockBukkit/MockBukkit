package org.mockbukkit.metaminer.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.util.JsonUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;

public class MaterialDataGenerator implements DataGenerator
{

	private final File dataFolder;

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
				String s = data.getAsString(false);

				if (!s.contains("["))
				{ // It has no states
					json.add(s.trim(), new JsonObject());
					continue;
				}

				String[] split = s.split("\\[");
				String material_name = split[0];
				String[] states = split[1].substring(0, split[1].length() - 1).split(",");

				JsonObject obj = new JsonObject();

				for (String state : states)
				{
					String[] state_split = state.split("=");
					String key = state_split[0].trim();
					String value = state_split[1].trim();

                    switch (value.toLowerCase()) {
                        case "false" -> obj.add(key, new JsonPrimitive(false));
                        case "true" -> obj.add(key, new JsonPrimitive(true));
                        default -> obj.add(key, new JsonPrimitive(value));
                    }
				}
				json.add(material_name.trim(), obj);
			}
			catch (Exception ignored)
			{
			}
		}

		return json;
	}

}
