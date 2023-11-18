package org.mockbukkit.metaminer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.bukkit.GameEvent;
import org.bukkit.Keyed;
import org.bukkit.MusicInstrument;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;

public class KeyedDataGenerator implements DataGenerator
{

	private final File dataFolder;

	public KeyedDataGenerator(@NotNull File dataFolder)
	{
		this.dataFolder = new File(dataFolder, "keyed");
	}

	@Override
	public void generateData() throws IOException
	{
		if(!this.dataFolder.exists() && !this.dataFolder.mkdirs()){
			throw new IOException("Could not make directory: " + this.dataFolder);
		}

		List<Class<? extends Keyed>> keyedClasses = List.of(Structure.class,
				StructureType.class, TrimMaterial.class, TrimPattern.class,
				MusicInstrument.class, GameEvent.class);
		for (Class<? extends Keyed> tClass : keyedClasses)
		{
			JsonArray array = new JsonArray();
			for (Field field : tClass.getDeclaredFields())
			{
				JsonObject jsonObject = new JsonObject();
				try
				{
					Object object = field.get(null);
					if (tClass.isInstance(object))
					{
						Keyed keyedObject = (Keyed) object;
						jsonObject.add("key", new JsonPrimitive(keyedObject.getKey().toString()));
						if (keyedObject instanceof Structure structure)
						{
							jsonObject.add("type", new JsonPrimitive(structure.getStructureType().getKey().toString()));
						}
					}
					array.add(jsonObject);
				}
				catch (NullPointerException | IllegalAccessException ignored)
				{
				}
			}
			File destinationFile = new File(dataFolder, tClass.getSimpleName().toLowerCase() + ".json");
			JsonObject rootObject = new JsonObject();
			rootObject.add("values", array);
			if(!destinationFile.exists() && !destinationFile.createNewFile()){
				throw new IOException("Could not create file: " + destinationFile);
			}
			try(Writer writer = new FileWriter(destinationFile))
			{
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				gson.toJson(rootObject, writer);
			}
		}
	}

}
