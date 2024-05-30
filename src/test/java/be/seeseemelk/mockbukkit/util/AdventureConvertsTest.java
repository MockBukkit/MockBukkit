package be.seeseemelk.mockbukkit.util;

import net.kyori.adventure.sound.Sound;
import org.bukkit.SoundCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdventureConvertsTest
{

	@Test
	void testConstructorIsPrivate() throws NoSuchMethodException
	{
		Constructor<AdventureConverters> constructor = AdventureConverters.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
		assertTrue(exception.getCause() instanceof IllegalStateException);
		assertTrue(exception.getCause().getMessage().contains("Utility class"));
	}

	@ParameterizedTest
	@MethodSource("getSoundSourceToCategoryTestCases")
	void testAdventureConverts(Sound.Source name, SoundCategory expected)
	{
		SoundCategory actual = AdventureConverters.soundSourceToCategory(name);
		assertEquals(expected, actual);
	}

	public static Stream<Arguments> getSoundSourceToCategoryTestCases()
	{
		return Stream.of(Arguments.of(Sound.Source.MASTER, SoundCategory.MASTER),
				Arguments.of(Sound.Source.MUSIC, SoundCategory.MUSIC),
				Arguments.of(Sound.Source.RECORD, SoundCategory.RECORDS),
				Arguments.of(Sound.Source.WEATHER, SoundCategory.WEATHER),
				Arguments.of(Sound.Source.BLOCK, SoundCategory.BLOCKS),
				Arguments.of(Sound.Source.HOSTILE, SoundCategory.HOSTILE),
				Arguments.of(Sound.Source.NEUTRAL, SoundCategory.NEUTRAL),
				Arguments.of(Sound.Source.PLAYER, SoundCategory.PLAYERS),
				Arguments.of(Sound.Source.AMBIENT, SoundCategory.AMBIENT),
				Arguments.of(Sound.Source.VOICE, SoundCategory.VOICE));
	}

}
