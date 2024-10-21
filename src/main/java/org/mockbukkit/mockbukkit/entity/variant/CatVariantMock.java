package org.mockbukkit.mockbukkit.entity.variant;

import org.mockbukkit.mockbukkit.util.OldKeyedEnumMock;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Cat;

public class CatVariantMock extends OldKeyedEnumMock<Cat.Type> implements Cat.Type
{

	public CatVariantMock(String name, int ordinal, NamespacedKey key)
	{
		super(name, ordinal, key);
	}

	public static CatVariantMock from(JsonObject jsonObject)
	{
		String name = jsonObject.get("name").getAsString();
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		int ordinal = jsonObject.get("ordinal").getAsInt();
		return new CatVariantMock(name, ordinal, key);
	}

}
