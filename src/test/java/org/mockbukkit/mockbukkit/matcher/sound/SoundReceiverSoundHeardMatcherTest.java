package org.mockbukkit.mockbukkit.matcher.sound;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.sound.AudioExperience;
import org.mockbukkit.mockbukkit.sound.SoundReceiver;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

@ExtendWith(MockBukkitExtension.class)
class SoundReceiverSoundHeardMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	private SoundReceiver soundReceiver;
	private Location location1;
	private Sound sound1;
	private Sound sound2;

	@BeforeEach
	void setUp()
	{
		this.soundReceiver = serverMock.addPlayer();
		WorldMock worldMock = serverMock.addSimpleWorld("world");
		this.location1 = new Location(worldMock, 0, 0, 0);
		this.sound1 = Sound.sound().type(Key.key("music_disc.13")).source(Sound.Source.MUSIC).build();
		this.sound2 = Sound.sound().type(Key.key("music_disc.12")).source(Sound.Source.MUSIC).build();
	}

	@Test
	void hasHeard_matches()
	{
		soundReceiver.addHeardSound(new AudioExperience(sound1, location1));
		assertMatches(SoundReceiverSoundHeardMatcher.hasHeard(sound1, ignored -> true), soundReceiver);
	}

	@Test
	void hasHeard_filterMismatch()
	{
		soundReceiver.addHeardSound(new AudioExperience(sound1, location1));
		assertDoesNotMatch(SoundReceiverSoundHeardMatcher.hasHeard(sound1, ignored -> false), soundReceiver);
	}

	@Test
	void hasHeard_differentSound()
	{
		soundReceiver.addHeardSound(new AudioExperience(sound1, location1));
		assertDoesNotMatch(SoundReceiverSoundHeardMatcher.hasHeard(sound2, ignored -> true), soundReceiver);
	}

	@Test
	void hasHeard_noSound()
	{
		assertDoesNotMatch(SoundReceiverSoundHeardMatcher.hasHeard(sound1, ignored -> true), soundReceiver);
	}

	@Test
	void nullSafe()
	{
		testIsNullSafe();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return SoundReceiverSoundHeardMatcher.hasHeard(sound1, ignored -> true);
	}

}
