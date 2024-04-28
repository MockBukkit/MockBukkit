package org.mockbukkit.mockbukkit.matcher.sound;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.util.ShadyPines;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.sound.AudioExperience;
import org.mockbukkit.mockbukkit.sound.SoundReceiver;

import java.util.function.Predicate;

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
		description.appendText("to have match with the specified filter and be of sound ").appendValue(soundKey);
	}

	@Override
	protected void describeMismatchSafely(SoundReceiver soundReceiver, Description description)
	{
		description.appendText("has received the following sounds ").appendValueList("[", ",", "]", soundReceiver.getHeardSounds());
	}

	public static SoundReceiverSoundHeardMatcher hasHeard(Sound sound, Predicate<AudioExperience> filter)
	{
		// Extra fields in the Sound instance needs to be checked, this is added to the filter
		Predicate<AudioExperience> soundFilter = e -> e.getSource() == sound.source()
				&& ShadyPines.equals(sound.volume(), e.getVolume())
				&& ShadyPines.equals(sound.pitch(), e.getPitch());
		return hasHeard(sound.name().asString(), soundFilter.and(filter));
	}

	public static SoundReceiverSoundHeardMatcher hasHeard(org.bukkit.Sound sound, Predicate<AudioExperience> filter)
	{
		return hasHeard(sound.getKey().getKey(), filter);
	}

	public static SoundReceiverSoundHeardMatcher hasHeard(String soundKey, Predicate<AudioExperience> filter)
	{
		return new SoundReceiverSoundHeardMatcher(soundKey, filter);
	}

	public static SoundReceiverSoundHeardMatcher hasHeard(String sound)
	{
		return hasHeard(sound, ignored -> true);
	}

	public static SoundReceiverSoundHeardMatcher hasHeard(Sound sound)
	{
		return hasHeard(sound, ignored -> true);
	}

	public static SoundReceiverSoundHeardMatcher hasHeard(org.bukkit.Sound sound)
	{
		return hasHeard(sound, ignored -> true);
	}

}
