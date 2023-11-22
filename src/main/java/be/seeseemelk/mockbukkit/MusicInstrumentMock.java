package be.seeseemelk.mockbukkit;

import com.google.gson.JsonObject;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class MusicInstrumentMock extends MusicInstrument
{

	private final NamespacedKey key;

	MusicInstrumentMock(JsonObject data)
	{
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

}
