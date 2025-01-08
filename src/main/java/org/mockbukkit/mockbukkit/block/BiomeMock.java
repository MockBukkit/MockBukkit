package org.mockbukkit.mockbukkit.block;

import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.mockbukkit.mockbukkit.util.OldKeyedEnumMock;

public class BiomeMock extends OldKeyedEnumMock<Biome> implements Biome
{

	public BiomeMock(String name, int ordinal, NamespacedKey key)
	{
		super(name, ordinal, key);
	}

	public BiomeMock(NamespacedKey key)
	{
		super(key.getKey(), 0, key);
	}



	public static BiomeMock from(JsonObject jsonObject)
	{
		String name = jsonObject.get("name").getAsString();
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		int ordinal = jsonObject.get("ordinal").getAsInt();

		return new BiomeMock(name, ordinal, key);
	}

}
