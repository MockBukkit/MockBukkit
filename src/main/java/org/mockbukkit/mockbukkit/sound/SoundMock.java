package org.mockbukkit.mockbukkit.sound;

import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.mockbukkit.mockbukkit.util.OldKeyedEnumMock;

public class SoundMock extends OldKeyedEnumMock<Sound> implements Sound
{

	public SoundMock(String name, int ordinal, NamespacedKey key)
	{
		super(name, ordinal, key);
	}

	public static SoundMock from(JsonObject jsonObject)
	{
		String name = jsonObject.get("name").getAsString();
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		int ordinal = jsonObject.get("ordinal").getAsInt();

		return new SoundMock(name, ordinal, key);
	}

}
