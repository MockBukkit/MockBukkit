package org.mockbukkit.metaminer.tags;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.Fluid;
import org.bukkit.GameEvent;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.EntityType;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.util.JsonUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

public class TagDataGenerator implements DataGenerator
{

	private final File dataFolder;

	public TagDataGenerator(File dataFolder)
	{
		this.dataFolder = new File(dataFolder, "tags");
	}

	@Override
	public void generateData() throws IOException
	{
		for (Map.Entry<String, Class<? extends Keyed>> tagTypeData : getTagTypeNames().entrySet())
		{
			for (Tag<? extends Keyed> tag : Bukkit.getTags(tagTypeData.getKey(), tagTypeData.getValue()))
			{
				writeTag(tag, tagTypeData.getKey());
			}
		}
	}

	private void writeTag(Tag<? extends Keyed> tag, String tagTypeName) throws IOException
	{
		JsonArray jsonArray = new JsonArray();
		Set<? extends Keyed> values = tag.getValues();
		values.forEach(tagValue -> jsonArray.add(tagValue.getKey().toString()));
		JsonObject rootObject = new JsonObject();
		rootObject.add("replace", new JsonPrimitive(false));
		rootObject.add("values", jsonArray);

		File destinationFile = new File(new File(this.dataFolder,tagTypeName), tag.getKey().getKey() + ".json");
		JsonUtil.dump(rootObject, destinationFile);
	}

	private Map<String, Class<? extends Keyed>> getTagTypeNames()
	{
		return Map.of("blocks", Material.class,
				"items", Material.class,
				"fluids", Fluid.class,
				"entity_types", EntityType.class,
				"game_events", GameEvent.class);
	}

}
