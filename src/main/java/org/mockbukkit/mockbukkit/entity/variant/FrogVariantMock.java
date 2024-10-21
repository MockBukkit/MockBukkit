package org.mockbukkit.mockbukkit.entity.variant;

import org.mockbukkit.mockbukkit.util.OldKeyedEnumMock;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Frog;

public class FrogVariantMock extends OldKeyedEnumMock<Frog.Variant> implements Frog.Variant
{

	public FrogVariantMock(String name, int ordinal, NamespacedKey key)
	{
		super(name, ordinal, key);
	}

	public static FrogVariantMock from(JsonObject jsonObject)
	{
		String name = jsonObject.get("name").getAsString();
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		int ordinal = jsonObject.get("ordinal").getAsInt();
		return new FrogVariantMock(name, ordinal, key);
	}

}
