package org.mockbukkit.mockbukkit.sound;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

public class MusicInstrumentMock extends MusicInstrument
{

	private final NamespacedKey key;
	private final String translationKey;

	/**
	 * @param key The namespaced key representing this music instrument
	 */
	MusicInstrumentMock(NamespacedKey key, String translationKey)
	{
		this.key = key;
		this.translationKey = translationKey;
	}

	/**
	 * @param data Json data
	 * @deprecated Use {@link MusicInstrumentMock(NamespacedKey)} instead
	 */
	@Deprecated(forRemoval = true)
	MusicInstrumentMock(JsonObject data)
	{
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
		this.translationKey = data.get("translationKey").getAsString();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.5")
	public @NotNull NamespacedKey getKey()
	{
		return this.key;
	}

	@Override
	@Deprecated(forRemoval = true)
	public @NotNull String translationKey()
	{
		return this.translationKey;
	}

	@ApiStatus.Internal
	public static MusicInstrumentMock from(JsonObject data)
	{
		Preconditions.checkNotNull(data);
		Preconditions.checkArgument(data.has("key"), "Missing json key");
		NamespacedKey key = NamespacedKey.fromString(data.get("key").getAsString());
		Preconditions.checkArgument(data.has("translationKey"), "Missing json translationKey");
		String translationKey = data.get("translationKey").getAsString();
		return new MusicInstrumentMock(key, translationKey);
	}

}
