package be.seeseemelk.mockbukkit.sound;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a {@link Sound} that was heard by a {@link Player}.
 *
 * @author TheBusyBiscuit
 */
public final class AudioExperience
{

	private final @NotNull String sound;
	private final @NotNull SoundCategory category;
	private final @NotNull Location location;
	private final float volume;
	private final float pitch;

	public AudioExperience(@NotNull String sound, @NotNull SoundCategory category, @NotNull Location loc, float volume,
						   float pitch)
	{
		Preconditions.checkNotNull(sound, "The played sound cannot be null!");
		Preconditions.checkNotNull(category, "The category cannot be null!");
		Preconditions.checkNotNull(loc, "The location cannot be null!");

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

	public AudioExperience(net.kyori.adventure.sound.@NotNull Sound sound, @NotNull Location loc)
	{
		this(sound.name().asString(), switch (sound.source())
				{
					case MASTER -> SoundCategory.MASTER;
					case MUSIC -> SoundCategory.MUSIC;
					case RECORD -> SoundCategory.RECORDS;
					case WEATHER -> SoundCategory.WEATHER;
					case BLOCK -> SoundCategory.BLOCKS;
					case HOSTILE -> SoundCategory.HOSTILE;
					case NEUTRAL -> SoundCategory.NEUTRAL;
					case PLAYER -> SoundCategory.PLAYERS;
					case AMBIENT -> SoundCategory.AMBIENT;
					case VOICE -> SoundCategory.VOICE;
				}, loc, sound.volume(), sound.pitch());
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
	 * This method returns the {@link net.kyori.adventure.sound.Sound.Source} with which the {@link Sound} was played.
	 *
	 * @return The {@link net.kyori.adventure.sound.Sound.Source}
	 */
	public net.kyori.adventure.sound.Sound.@NotNull Source getSource()
	{
		return category.soundSource();
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
