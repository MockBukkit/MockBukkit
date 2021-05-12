package be.seeseemelk.mockbukkit.sound;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a {@link Sound} that was heard by a {@link Player}.
 *
 * @author TheBusyBiscuit
 *
 */
public final class AudioExperience
{
	private final String sound;
	private final SoundCategory category;
	private final Location location;
	private final float volume;
	private final float pitch;

	public AudioExperience(@NotNull String sound, @NotNull SoundCategory category, @NotNull Location loc, float volume,
	                       float pitch)
	{
		Validate.notNull(sound, "The played sound cannot be null!");
		Validate.notNull(category, "The category must not be null!");
		Validate.notNull(loc, "The location cannot be null!");

		this.sound = sound;
		this.category = category;
		this.location = loc;
		this.volume = volume;
		this.pitch = pitch;
	}

	public AudioExperience(@NotNull Sound sound, @NotNull SoundCategory category, @NotNull Location loc, float volume,
	                       float pitch)
	{
		this(sound.getKey().getKey(), category, loc, volume, pitch);
	}

	/**
	 * This returns the {@link Sound} that was played. We return the {@link String} representation of the actual sound,
	 * not the sound itself.
	 *
	 * @return The {@link String} of the heard {@link Sound}.
	 */
	@NotNull
	public String getSound()
	{
		return sound;
	}

	/**
	 * This method returns the {@link SoundCategory} with which the {@link Sound} was played.
	 *
	 * @return The {@link SoundCategory}
	 */
	@NotNull
	public SoundCategory getCategory()
	{
		return category;
	}

	/**
	 * This returns the {@link Location} at which this {@link Sound} was played.
	 *
	 * @return The {@link Location}
	 */
	@NotNull
	public Location getLocation()
	{
		return location;
	}

	/**
	 * The volume of this {@link Sound}
	 *
	 * @return The volume
	 */
	public float getVolume()
	{
		return volume;
	}

	/**
	 * The pitch of this {@link Sound}
	 *
	 * @return The pitch
	 */
	public float getPitch()
	{
		return pitch;
	}

}
