package be.seeseemelk.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

/**
 * This class represents a {@link Sound} that was heard by a {@link Player}.
 *
 * @author TheBusyBiscuit
 *
 */
public final class AudioExperience
{
	private final Sound sound;
	private final SoundCategory category;
	private final Location location;
	private final float volume;
	private final float pitch;

	AudioExperience(Sound sound, SoundCategory category, Location l, float volume, float pitch)
	{
		this.sound = sound;
		this.category = category;
		this.location = l;
		this.volume = volume;
		this.pitch = pitch;
	}

	/**
	 * This returns the {@link Sound} that was played.
	 *
	 * @return The {@link Sound}
	 */
	public Sound getSound()
	{
		return sound;
	}

	/**
	 * This method returns the {@link SoundCategory} with which the {@link Sound} was played.
	 *
	 * @return The {@link SoundCategory}
	 */
	public SoundCategory getCategory()
	{
		return category;
	}

	/**
	 * This returns the {@link Location} at which this {@link Sound} was played.
	 *
	 * @return The {@link Location}
	 */
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
