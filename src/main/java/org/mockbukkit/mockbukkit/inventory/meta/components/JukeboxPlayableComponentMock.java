package org.mockbukkit.mockbukkit.inventory.meta.components;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.bukkit.JukeboxSong;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.meta.components.JukeboxPlayableComponent;
import org.jetbrains.annotations.NotNullByDefault;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.inventory.SerializableMeta;

import java.util.LinkedHashMap;
import java.util.Map;

@Builder
@NotNullByDefault
@EqualsAndHashCode
@SerializableAs("JukeboxPlayable")
@SuppressWarnings("UnstableApiUsage")
public class JukeboxPlayableComponentMock implements JukeboxPlayableComponent
{
	private NamespacedKey soundKey;

	@Override
	public @Nullable JukeboxSong getSong()
	{
		return RegistryAccess.registryAccess().getRegistry(RegistryKey.JUKEBOX_SONG).get(this.getSongKey());
	}

	@Override
	public NamespacedKey getSongKey()
	{
		return this.soundKey;
	}

	@Override
	public void setSong(JukeboxSong song)
	{
		Preconditions.checkArgument(song != null, "song cannot be null");
		this.soundKey = song.getKey();
	}

	@Override
	public void setSongKey(NamespacedKey song)
	{
		Preconditions.checkArgument(song != null, "song cannot be null");
		this.soundKey = song;
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("song", this.getSongKey().toString());
		return result;
	}

	public static JukeboxPlayableComponentMock deserialize(Map<String, Object> map)
	{
		String song = SerializableMeta.getObject(String.class, map, "song", false);
		Preconditions.checkNotNull(song, "song can't be null!");

		NamespacedKey songKey = NamespacedKey.fromString(song);
		Preconditions.checkNotNull(songKey, "Invalid song key!");

		return JukeboxPlayableComponentMock.builder()
				.soundKey(songKey)
				.build();
	}

	public static JukeboxPlayableComponent useDefault()
	{
		return builder().build();
	}

}
