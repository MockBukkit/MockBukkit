package org.mockbukkit.metaminer.tags;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.keyed.KeyedClassTracker;
import org.mockbukkit.metaminer.util.JsonUtil;
import org.mockbukkit.mockbukkit.util.RegistryUtils;

import java.io.File;
import java.io.IOException;
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
		for (Map.Entry<RegistryKey<? extends Keyed>, Class<?>> entry : KeyedClassTracker.CLASS_REGISTRY_KEY_RELATION.entrySet())
		{
			RegistryKey<? extends Keyed> registryKey = entry.getKey();
			Registry<? extends Keyed> registry = RegistryAccess.registryAccess().getRegistry((RegistryKey) registryKey);
			String tagType = RegistryUtils.getPlural(registryKey);

			try
			{
				for (io.papermc.paper.registry.tag.Tag<? extends Keyed> tag : registry.getTags())
				{
					writeTag(tag, tagType);
				}
			}
			catch (UnsupportedOperationException ignored)
			{
				// This registry does not support tags.
			}
		}
	}

	private void writeTag(io.papermc.paper.registry.tag.Tag<? extends Keyed> tag, String tagTypeName) throws IOException
	{
		JsonArray jsonArray = new JsonArray();
		Set<? extends Keyed> values = ((org.bukkit.Tag<? extends Keyed>) tag).getValues();
		values.forEach(tagValue -> jsonArray.add(tagValue.getKey().toString()));
		JsonObject rootObject = new JsonObject();
		rootObject.add("replace", new JsonPrimitive(false));
		rootObject.add("values", jsonArray);

		File destinationFile = new File(new File(this.dataFolder, tagTypeName), tag.tagKey().key().value() + ".json");
		JsonUtil.dump(rootObject, destinationFile);
	}


}
