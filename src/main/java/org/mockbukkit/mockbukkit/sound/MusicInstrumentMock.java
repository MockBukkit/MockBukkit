package org.mockbukkit.mockbukkit.sound;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class MusicInstrumentMock extends MusicInstrument
{

	private final NamespacedKey key;
	private final String translationKey;
	private final float duration;
	private final float range;
	private final Component description;
	private final Sound sound;

	/**
	 * @param key         The namespaced key representing this music instrument
	 * @param duration    The music duration.
	 * @param range       The music range.
	 * @param description The music description.
	 * @param sound       The sound id.
	 */
	MusicInstrumentMock(@NotNull NamespacedKey key, @NotNull String translationKey, float duration, float range,
						@NotNull Component description, @NotNull Sound sound)
	{
		this.key = Preconditions.checkNotNull(key, "key cannot be null");
		this.translationKey = Preconditions.checkNotNull(translationKey, "translationKey cannot be null");
		this.duration = duration;
		this.range = range;
		this.description = Preconditions.checkNotNull(description, "description cannot be null");
		this.sound = Preconditions.checkNotNull(sound, "sound cannot be null");

		Preconditions.checkArgument(Float.isFinite(this.duration) && this.duration >= 0, "duration must be >= 0");
		Preconditions.checkArgument(Float.isFinite(this.duration) && this.range >= 0, "range must be >= 0");
	}

	@Override
	public float getDuration()
	{
		return this.duration;
	}

	@Override
	public float getRange()
	{
		return this.range;
	}

	@Override
	public @NotNull Component description()
	{
		return this.description;
	}

	@Override
	public @NotNull Sound getSound()
	{
		return this.sound;
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
		Preconditions.checkNotNull(key, "Invalid namespaced key: " + data.get("key").getAsString());

		Preconditions.checkArgument(data.has("translationKey"), "Missing json translationKey");
		String translationKey = data.get("translationKey").getAsString();

		Preconditions.checkArgument(data.has("duration"), "Missing json duration");
		float duration = data.get("duration").getAsFloat();

		Preconditions.checkArgument(data.has("range"), "Missing json range");
		float range = data.get("range").getAsFloat();

		Preconditions.checkArgument(data.has("sound"), "Missing json sound");
		NamespacedKey soundKey = NamespacedKey.fromString(data.get("sound").getAsString());
		Preconditions.checkNotNull(soundKey, "Unknown sound with key: " + data.get("sound").getAsString());
		Sound sound = Registry.SOUNDS.get(soundKey);
		Preconditions.checkNotNull(sound, "Unknown sound with key: " + soundKey.key().asString());

		return new MusicInstrumentMock(key, translationKey, duration, range, Component.translatable(translationKey), sound);
	}

}
