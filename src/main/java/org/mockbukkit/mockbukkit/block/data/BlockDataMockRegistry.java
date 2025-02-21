package org.mockbukkit.mockbukkit.block.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.InternalDataLoadException;
import org.mockbukkit.mockbukkit.util.ResourceLoader;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Map;

public class BlockDataMockRegistry
{

	private static class MaterialDeserializer implements JsonDeserializer<Material>
	{

		@Override
		public Material deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		{
			String minecraftId = json.getAsString();
			Material tmp = Material.matchMaterial(minecraftId);
			if (tmp == null)
			{
				throw new IllegalArgumentException("No corresponding Material enum found for " + minecraftId);
			}
			return tmp;
		}

	}

	private static BlockDataMockRegistry instance = null;
	private Map<Material, Map<String, Object>> blockData = null;

	private BlockDataMockRegistry()
	{
		try
		{
			loadBlockData();
		}
		catch (InternalDataLoadException ex)
		{
			ex.printStackTrace();
		}
	}

	public static BlockDataMockRegistry getInstance()
	{
		if (instance == null)
		{
			instance = new BlockDataMockRegistry();
		}
		return instance;
	}

	private void loadBlockData() throws InternalDataLoadException
	{
		JsonObject jsonObject = ResourceLoader.loadResource("/materials/material_data.json").getAsJsonObject();

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Material.class, new MaterialDeserializer());
		Gson gson = gsonBuilder.create();

		Type type = new TypeToken<Map<Material, Map<String, Object>>>() {}.getType();
		blockData = gson.fromJson(jsonObject, type);
	}

	public @Nullable Map<String, Object> getBlockData(@NotNull Material material)
	{
		return blockData.get(material);
	}

	public @Nullable Object getDefault(@NotNull Material material, @NotNull String state)
	{
		Map<String, Object> blockData = getBlockData(material);
		if (blockData == null)
		{
			return null;
		}
		return blockData.get(state);
	}

}
