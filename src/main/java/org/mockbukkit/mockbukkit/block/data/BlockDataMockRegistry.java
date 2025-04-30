package org.mockbukkit.mockbukkit.block.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.InternalDataLoadException;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.util.ResourceLoader;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

@ApiStatus.Internal
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

	private static class MaterialDataDeserializer implements JsonDeserializer<MaterialData>
	{

		@Override
		public MaterialData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			if (!(json instanceof JsonObject jsonObject))
			{
				throw new IllegalArgumentException("Expected json object");
			}
			Gson gson = new Gson();
			Map<String, Object> defaultStates = gson.fromJson(jsonObject.get("defaultStates"), new TypeToken<Map<String, Object>>()
			{
			}.getType());
			Map<String, Set<Object>> allowedStates = gson.fromJson(jsonObject.get("allowedStates"), new TypeToken<Map<String, Set<Object>>>()
			{
			}.getType());
			return new MaterialData(defaultStates, allowedStates);
		}

	}

	private static BlockDataMockRegistry instance = null;
	private Map<Material, MaterialData> blockData = null;

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
		gsonBuilder.registerTypeAdapter(MaterialData.class, new MaterialDataDeserializer());
		Gson gson = gsonBuilder.create();

		Type type = new TypeToken<Map<Material, MaterialData>>()
		{
		}.getType();
		blockData = gson.fromJson(jsonObject, type);
	}

	public @Nullable Map<String, Object> getDefaultData(@NotNull Material material)
	{
		MaterialData materialData = blockData.get(material);
		if (materialData == null)
		{
			return null;
		}
		return materialData.defaultValues();
	}

	public @Nullable Object getDefault(@NotNull Material material, @NotNull String state)
	{
		Map<String, Object> blockData = getDefaultData(material);
		if (blockData == null)
		{
			return null;
		}
		return blockData.get(state);
	}

	private @Nullable Map<String, Set<Object>> getAllowedValues(Material material)
	{
		MaterialData materialData = blockData.get(material);
		if (materialData == null)
		{
			return null;
		}
		return materialData.allowedValues();
	}

	public boolean isAllowedValue(Material material, BlockDataKey blockDataKey, Object value)
	{
		Map<String, Set<Object>> allowedValues = getAllowedValues(material);
		if (allowedValues == null)
		{
			throw new UnimplementedOperationException();
		}
		Set<Object> allowed = allowedValues.get(blockDataKey.name());
		if (allowed == null)
		{
			throw new IllegalStateException("Expected a valid block data key!");
		}
		return allowed.stream()
				.map(object ->
				{
					if (object instanceof String string)
					{
						return blockDataKey.constructValue(string);
					}
					return object;
				})
				.anyMatch(value::equals);
	}

	private record MaterialData(Map<String, Object> defaultValues, Map<String, Set<Object>> allowedValues)
	{

	}

}
