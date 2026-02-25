package org.mockbukkit.mockbukkit.inventory.meta.components;

import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class JukeboxPlayableComponentMockTest
{

	private final JukeboxPlayableComponentMock component = JukeboxPlayableComponentMock.builder().build();

	@Nested
	class Serialize
	{

		@Test
		void givenSongSerialization()
		{
			component.setSongKey(NamespacedKey.minecraft("ambient.basalt_deltas.loop"));

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals("minecraft:ambient.basalt_deltas.loop", actual.get("song"));
		}

	}

	@Nested
	class Deserialize
	{

		@Test
		void givenSongDeserialization()
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("song", "minecraft:ambient.basalt_deltas.loop");

			JukeboxPlayableComponentMock actual = JukeboxPlayableComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(NamespacedKey.minecraft("ambient.basalt_deltas.loop"), actual.getSongKey());
		}

		@Test
		void givenSerializationAndDeserialization()
		{
			component.setSongKey(NamespacedKey.minecraft("ambient.basalt_deltas.loop"));

			JukeboxPlayableComponentMock actual = JukeboxPlayableComponentMock.deserialize(component.serialize());

			assertNotNull(actual);
			assertEquals(component.getSongKey(), actual.getSongKey());
		}

	}
}
