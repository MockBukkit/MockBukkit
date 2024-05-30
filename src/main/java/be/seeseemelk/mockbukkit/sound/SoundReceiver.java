package be.seeseemelk.mockbukkit.sound;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.base.Preconditions;
import net.kyori.adventure.util.ShadyPines;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * This interface provides methods to assert sounds that were heard. This is implemented by {@link PlayerMock}, however
 * the sheer amount of assertion methods did warrant a separate a file at some point.
 *
 * @author TheBusyBiscuit
 * @see PlayerMock
 */
public interface SoundReceiver
{

	/**
	 * This returns a {@link List} of every {@link AudioExperience} this receiver has received.
	 *
	 * @return A mutable {@link List} containing every heard sound.
	 */
	@NotNull
	List<AudioExperience> getHeardSounds();

	/**
	 * Adds a heard sound.
	 *
	 * @param audioExperience An {@link AudioExperience} representing the heard sound.
	 */
	default void addHeardSound(@NotNull AudioExperience audioExperience)
	{
		Preconditions.checkNotNull(audioExperience, "An audio experience must not be null.");
		getHeardSounds().add(audioExperience);
	}

	/**
	 * Clears all heard sounds.
	 */
	default void clearSounds()
	{
		getHeardSounds().clear();
	}

	/**
	 * Assert that a sound type has been played for this sound receiver.
	 * <p>
	 * The {@link #assertSoundHeard(net.kyori.adventure.sound.Sound) adventure method} also checks the source of the sound, the volume and the pitch.
	 *
	 * @param sound The sound type to check.
	 */
	default void assertSoundHeard(@NotNull Sound sound)
	{
		assertSoundHeard("Sound Heard Assertion failed", sound);
	}

	/**
	 * Assert that a sound with a given source, volume and pitch have been played for this sound receiver.
	 *
	 * @param sound The sound to check.
	 */
	default void assertSoundHeard(net.kyori.adventure.sound.@NotNull Sound sound)
	{
		assertSoundHeard("Sound Heard Assertion failed", sound);
	}

	/**
	 * Assert that a sound key has been played for this sound receiver.
	 * <p>
	 * The {@link #assertSoundHeard(net.kyori.adventure.sound.Sound) adventure method} also checks the source of the sound, the volume and the pitch.
	 *
	 * @param sound The sound key to check.
	 */
	default void assertSoundHeard(@NotNull String sound)
	{
		assertSoundHeard("Sound Heard Assertion failed", sound);
	}

	/**
	 * Asserts that a sound was heard.
	 *
	 * @param sound     The sound that should've been heard.
	 * @param predicate A predicate to test the {@link AudioExperience} against.
	 */
	default void assertSoundHeard(@NotNull Sound sound, @NotNull Predicate<AudioExperience> predicate)
	{
		assertSoundHeard("Sound Heard Assertion failed", sound, predicate);
	}

	/**
	 * Asserts that a sound was heard.
	 *
	 * @param sound     The sound that should've been heard.
	 * @param predicate A predicate to test the {@link AudioExperience} against.
	 */
	default void assertSoundHeard(net.kyori.adventure.sound.@NotNull Sound sound,
			@NotNull Predicate<AudioExperience> predicate)
	{
		assertSoundHeard("Sound Heard Assertion failed", sound, predicate);
	}

	/**
	 * Asserts that a sound was heard.
	 *
	 * @param sound     The sound name that should've been heard.
	 * @param predicate A predicate to test the {@link AudioExperience} against.
	 */
	default void assertSoundHeard(@NotNull String sound, @NotNull Predicate<AudioExperience> predicate)
	{
		assertSoundHeard("Sound Heard Assertion failed", sound, predicate);
	}

	/**
	 * Asserts that a sound was heard.
	 *
	 * @param message The message to fail with.
	 * @param sound   The sound that should've been heard.
	 */
	default void assertSoundHeard(@NotNull String message, @NotNull Sound sound)
	{
		assertSoundHeard(message, sound, e -> true);
	}

	/**
	 * Asserts that a sound was heard.
	 *
	 * @param message The message to fail with.
	 * @param sound   The sound that should've been heard.
	 */
	default void assertSoundHeard(@NotNull String message, net.kyori.adventure.sound.@NotNull Sound sound)
	{
		assertSoundHeard(message, sound, e -> true);
	}

	/**
	 * Asserts that a sound was heard.
	 *
	 * @param message The message to fail with.
	 * @param sound   The sound name that should've been heard.
	 */
	default void assertSoundHeard(@NotNull String message, @NotNull String sound)
	{
		assertSoundHeard(message, sound, e -> true);
	}

	/**
	 * Asserts that a sound was heard.
	 *
	 * @param message   The message to fail with.
	 * @param sound     The sound that should've been heard.
	 * @param predicate A predicate to test the {@link AudioExperience} against.
	 */
	default void assertSoundHeard(@NotNull String message, @NotNull Sound sound,
			@NotNull Predicate<AudioExperience> predicate)
	{
		assertSoundHeard(message, sound.getKey().getKey(), predicate);
	}

	/**
	 * Asserts that a sound was heard.
	 *
	 * @param message   The message to fail with.
	 * @param sound     The sound that should've been heard.
	 * @param predicate A predicate to test the {@link AudioExperience} against.
	 */
	default void assertSoundHeard(@NotNull String message, net.kyori.adventure.sound.@NotNull Sound sound,
			@NotNull Predicate<AudioExperience> predicate)
	{
		Predicate<AudioExperience> test = e -> e.getSource() == sound.source()
				&& ShadyPines.equals(sound.volume(), e.getVolume()) && ShadyPines.equals(sound.pitch(), e.getPitch());
		assertSoundHeard(message, sound.name().asString(), test.and(predicate));
	}

	/**
	 * Asserts that a sound was heard.
	 *
	 * @param message   The message to fail with.
	 * @param sound     The sound name that should've been heard.
	 * @param predicate A predicate to test the {@link AudioExperience} against.
	 */
	default void assertSoundHeard(@NotNull String message, @NotNull String sound,
			@NotNull Predicate<AudioExperience> predicate)
	{
		for (AudioExperience audio : getHeardSounds())
		{
			if (audio.getSound().equals(sound) && predicate.test(audio))
			{
				return;
			}
		}

		fail(message);
	}

}
