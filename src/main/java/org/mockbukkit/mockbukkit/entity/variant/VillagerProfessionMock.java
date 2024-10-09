package org.mockbukkit.mockbukkit.entity.variant;

import org.mockbukkit.mockbukkit.util.OldKeyedEnumMock;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Villager;

public class VillagerProfessionMock extends OldKeyedEnumMock<Villager.Profession> implements Villager.Profession
{

	public VillagerProfessionMock(String name, int ordinal, NamespacedKey key)
	{
		super(name, ordinal, key);
	}

	public static VillagerProfessionMock from(JsonObject jsonObject)
	{
		String name = jsonObject.get("name").getAsString();
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		int ordinal = jsonObject.get("ordinal").getAsInt();
		return new VillagerProfessionMock(name, ordinal, key);
	}

}