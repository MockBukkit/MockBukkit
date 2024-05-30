package be.seeseemelk.mockbukkit.block.data;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		catch (IOException ex)
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

	private void loadBlockData() throws IOException
	{
		InputStream stream = MockBukkit.class.getResourceAsStream("/materials/material_data.json");
		if (stream == null)
		{
			throw new IOException("Failed to load materials data, file not found");
		}

		try (InputStreamReader reader = new InputStreamReader(stream))
		{
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Material.class, new MaterialDeserializer());
			Gson gson = gsonBuilder.create();

			Type type = new TypeToken<Map<Material, Map<String, Object>>>()
			{
			}.getType();
			blockData = gson.fromJson(reader, type);
		}
	}

	public @Nullable Map<String, Object> getBlockData(@NotNull Material material)
	{
		return blockData.get(material);
	}

	public boolean isValidMaterialForBlock(@NotNull Material material)
	{
		return material.isBlock();
	}

	public boolean isValidStateForBlockWithMaterial(@NotNull Material material, @NotNull String state)
	{
		Map<String, Object> tmp = blockData.get(material);
		if (tmp == null)
			return false;

		return tmp.get(state) != null;
	}

	public @Nullable Object getDefault(@NotNull Material material, @NotNull String state)
	{
		Map<String, Object> bd = getBlockData(material);
		if (bd == null)
			return null;
		return bd.get(state);
	}

}
