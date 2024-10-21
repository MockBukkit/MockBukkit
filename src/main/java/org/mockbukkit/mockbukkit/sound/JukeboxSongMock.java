package org.mockbukkit.mockbukkit.sound;

import com.google.gson.JsonObject;
import org.bukkit.JukeboxSong;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;


public class JukeboxSongMock implements JukeboxSong
{
	private NamespacedKey key;
	private final String translationKey;

	public JukeboxSongMock(NamespacedKey key, String translationKey)
	{
		this.translationKey = translationKey;
		this.key = key;
	}

	@ApiStatus.Internal
	public static JukeboxSongMock from(JsonObject jsonObject)
	{
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		String translationKey = jsonObject.get("translationKey").getAsString();
		return new JukeboxSongMock(key, translationKey);
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.21")
	public @NotNull String getTranslationKey()
	{
		return this.translationKey;
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return this.key;
	}

}
