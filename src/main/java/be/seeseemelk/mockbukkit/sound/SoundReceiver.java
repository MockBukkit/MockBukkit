package be.seeseemelk.mockbukkit.sound;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * This interface provides methods to assert sounds that were heard. This is implemented by {@link PlayerMock}, however
 * the sheer amount of assertion methods did warrant a seperate a file at some point.
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

	default void addHeardSound(@NotNull AudioExperience audioExperience)
	{
		Preconditions.checkNotNull(audioExperience, "An audio experience must not be null.");
		getHeardSounds().add(audioExperience);
	}

	default void clearSounds()
	{
		getHeardSounds().clear();
	}

	default void assertSoundHeard(@NotNull Sound sound)
	{
		assertSoundHeard("Sound Heard Assertion failed", sound);
	}

	default void assertSoundHeard(@NotNull String sound)
	{
		assertSoundHeard("Sound Heard Assertion failed", sound);
	}

	default void assertSoundHeard(@NotNull Sound sound, @NotNull Predicate<AudioExperience> predicate)
	{
		assertSoundHeard("Sound Heard Assertion failed", sound, predicate);
	}

	default void assertSoundHeard(@NotNull String sound, @NotNull Predicate<AudioExperience> predicate)
	{
		assertSoundHeard("Sound Heard Assertion failed", sound, predicate);
	}

	default void assertSoundHeard(@NotNull String message, @NotNull Sound sound)
	{
		assertSoundHeard(message, sound, e -> true);
	}

	default void assertSoundHeard(@NotNull String message, @NotNull String sound)
	{
		assertSoundHeard(message, sound, e -> true);
	}

	default void assertSoundHeard(@NotNull String message, @NotNull Sound sound,
								  @NotNull Predicate<AudioExperience> predicate)
	{
		assertSoundHeard(message, sound.getKey().getKey(), predicate);
	}

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
