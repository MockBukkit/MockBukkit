package org.mockbukkit.mockbukkit.sound;

import com.google.gson.JsonObject;
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
		this.musicInstrument = new MusicInstrumentMock(key, "instrument.custom.custom_music_instrument");
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
	void from()
	{
		JsonObject invalid = new JsonObject();
		assertThrows(IllegalArgumentException.class, () -> MusicInstrumentMock.from(invalid));
	}

}
