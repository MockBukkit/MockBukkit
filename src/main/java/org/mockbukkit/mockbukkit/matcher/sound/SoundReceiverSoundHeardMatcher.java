package org.mockbukkit.mockbukkit.matcher.sound;

import com.google.common.base.Preconditions;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.util.ShadyPines;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.sound.AudioExperience;
import org.mockbukkit.mockbukkit.sound.SoundReceiver;

import java.util.function.Predicate;

import static org.hamcrest.Matchers.not;

public class SoundReceiverSoundHeardMatcher extends TypeSafeMatcher<SoundReceiver>
{

	private final String soundKey;
	private final Predicate<AudioExperience> filter;

	public SoundReceiverSoundHeardMatcher(String soundKey, Predicate<AudioExperience> filter)
	{
		this.soundKey = soundKey;
		this.filter = filter;
	}

	@Override
	protected boolean matchesSafely(SoundReceiver soundReceiver)
	{
		return soundReceiver.getHeardSounds().stream().filter(audioExperience -> audioExperience.getSound().equals(soundKey)).anyMatch(filter);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have matched with the specified filter and be of sound ").appendValue(soundKey);
	}

	@Override
	protected void describeMismatchSafely(SoundReceiver soundReceiver, Description description)
	{
		description.appendText("has received the following sounds ").appendValueList("[", ",", "]", soundReceiver.getHeardSounds());
	}

	/**
	 * @param sound  The required sound for a match
	 * @param filter A custom filter
	 * @return A matcher which matches with any sound receiver which has heard the specified sound with filter
	 */
	public static @NotNull SoundReceiverSoundHeardMatcher hasHeard(@NotNull Sound sound, @NotNull Predicate<AudioExperience> filter)
	{
		Preconditions.checkNotNull(sound);
		Preconditions.checkNotNull(filter);
		// Extra fields in the Sound instance needs to be checked, this is added to the filter
		Predicate<AudioExperience> soundFilter = e -> e.getSource() == sound.source()
				&& ShadyPines.equals(sound.volume(), e.getVolume())
				&& ShadyPines.equals(sound.pitch(), e.getPitch());
		return hasHeard(sound.name().asString(), soundFilter.and(filter));
	}

	/**
	 * @param sound  The required sound for a match
	 * @param filter A custom filter
	 * @return A matcher which matches with any sound receiver which has heard the specified sound with filter
	 */
	public static @NotNull SoundReceiverSoundHeardMatcher hasHeard(@NotNull org.bukkit.Sound sound, @NotNull Predicate<AudioExperience> filter)
	{
		Preconditions.checkNotNull(sound);
		return hasHeard(sound.getKey().getKey(), filter);
	}

	/**
	 * @param sound  The required sound for no match
	 * @param filter A custom filter
	 * @return A matcher which matches with any sound receiver which has not heard the specified sound with filter
	 */
	public static @NotNull Matcher<SoundReceiver> hasNotHeard(@NotNull org.bukkit.Sound sound, @NotNull Predicate<AudioExperience> filter)
	{
		return not(hasHeard(sound, filter));
	}

	/**
	 * @param soundKey The required sound for a match
	 * @param filter   A custom filter
	 * @return A matcher which matches with any sound receiver which has heard the specified sound with filter
	 */
	public static @NotNull SoundReceiverSoundHeardMatcher hasHeard(@NotNull String soundKey, @NotNull Predicate<AudioExperience> filter)
	{
		Preconditions.checkNotNull(soundKey);
		Preconditions.checkNotNull(filter);
		return new SoundReceiverSoundHeardMatcher(soundKey, filter);
	}

	/**
	 * @param soundKey The required sound for no match
	 * @param filter   A custom filter
	 * @return A matcher which matches with any sound receiver which has not heard the specified sound with filter
	 */
	public static @NotNull Matcher<SoundReceiver> hasNotHeard(@NotNull String soundKey, @NotNull Predicate<AudioExperience> filter)
	{
		return not(hasHeard(soundKey, filter));
	}

	/**
	 * @param sound The required sound for a match
	 * @return A matcher which matches with any sound receiver which has heard the specified sound
	 */
	public static @NotNull SoundReceiverSoundHeardMatcher hasHeard(@NotNull String sound)
	{
		return hasHeard(sound, ignored -> true);
	}

	/**
	 * @param sound The required sound for no match
	 * @return A matcher which matches with any sound receiver which has not heard the specified sound
	 */
	public static @NotNull Matcher<SoundReceiver> hasNotHeard(@NotNull String sound)
	{
		return not(hasHeard(sound));
	}

	/**
	 * @param sound The required sound for a match
	 * @return A matcher which matches with any sound receiver which has heard the specified sound
	 */
	public static @NotNull SoundReceiverSoundHeardMatcher hasHeard(@NotNull Sound sound)
	{
		return hasHeard(sound, ignored -> true);
	}

	/**
	 * @param sound The required sound for no match
	 * @return A matcher which matches with any sound receiver which has not heard the specified sound
	 */
	public static @NotNull Matcher<SoundReceiver> hasNotHeard(@NotNull Sound sound)
	{
		return not(hasHeard(sound));
	}

	/**
	 * @param sound The required sound for a match
	 * @return A matcher which matches with any sound receiver which has heard the specified sound
	 */
	public static @NotNull SoundReceiverSoundHeardMatcher hasHeard(@NotNull org.bukkit.Sound sound)
	{
		return hasHeard(sound, ignored -> true);
	}

	/**
	 * @param sound The required sound for no match
	 * @return A matcher which matches with any sound receiver which has not heard the specified sound
	 */
	public static @NotNull Matcher<SoundReceiver> hasNotHeard(@NotNull org.bukkit.Sound sound)
	{
		return not(hasHeard(sound));
	}

}
