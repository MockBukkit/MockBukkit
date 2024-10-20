package org.mockbukkit.mockbukkit.sound;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class MusicInstrumentMock extends MusicInstrument
{

	private final NamespacedKey key;

	/**
	 * @param key The namespaced key representing this music instrument
	 */
	MusicInstrumentMock(NamespacedKey key)
	{
		this.key = key;
	}

	/**
	 * @param data Json data
	 * @deprecated Use {@link #MusicInstrumentMock(NamespacedKey)} instead
	 */
	@Deprecated(forRemoval = true)
	MusicInstrumentMock(JsonObject data)
	{
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

	@ApiStatus.Internal
	public static MusicInstrumentMock from(JsonObject data)
	{
		Preconditions.checkNotNull(data);
		Preconditions.checkArgument(data.has("key"), "Missing json key");
		NamespacedKey key = NamespacedKey.fromString(data.get("key").getAsString());
		return new MusicInstrumentMock(key);
	}

}
