package be.seeseemelk.mockbukkit;

import com.google.gson.JsonObject;
import org.bukkit.GameEvent;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class GameEventMock extends GameEvent
{

	private final NamespacedKey key;

	public GameEventMock(JsonObject data)
	{
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
	}
	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

}
