package org.mockbukkit.mockbukkit.sound;

import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class MusicInstrumentMockTest
{

	private NamespacedKey key;
	private MusicInstrumentMock musicInstrument;

	@BeforeEach
	void setUp()
	{
		this.key = new NamespacedKey("mock_bukkit", "custom_music_instrument");
		this.musicInstrument = new MusicInstrumentMock(key, "instrument.custom.custom_music_instrument",
				7, 256, Component.translatable("instrument.custom.custom_music_instrument"),
				SoundMock.ENTITY_PLAYER_BIG_FALL);
	}

	@Test
	void getKey()
	{
		assertEquals(key, musicInstrument.getKey());
	}

	@Test
	void translationKey()
	{
		assertEquals("instrument.custom.custom_music_instrument", musicInstrument.translationKey());
	}

	@Test
	void getDuration()
	{
		assertEquals(7, musicInstrument.getDuration());
	}

	@Test
	void getRange()
	{
		assertEquals(256, musicInstrument.getRange());
	}

	@Test
	void getDescription()
	{
		Component description = Component.translatable("instrument.custom.custom_music_instrument");
		assertEquals(description, musicInstrument.description());
	}

	@Test
	void getSound()
	{
		assertEquals(SoundMock.ENTITY_PLAYER_BIG_FALL, musicInstrument.getSound());
	}

	@Test
	void from()
	{
		JsonObject invalid = new JsonObject();
		assertThrows(IllegalArgumentException.class, () -> MusicInstrumentMock.from(invalid));
	}

}
