package be.seeseemelk.mockbukkit;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.GameEvent;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class GameEventMock extends GameEvent
{

	private final NamespacedKey key;

	/**
	 * @param key A namespaced key
	 */
	public GameEventMock(NamespacedKey key)
	{
		this.key = key;
	}

	/**
	 * @param data Json data
	 * @deprecated Use {@link #GameEventMock(NamespacedKey)} instead.
	 */
	@Deprecated(forRemoval = true)
	public GameEventMock(JsonObject data)
	{
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

	@ApiStatus.Internal
	public static GameEventMock from(JsonObject data)
	{
		Preconditions.checkNotNull(data);
		Preconditions.checkArgument(data.has("key"), "Missing json key");
		NamespacedKey key = NamespacedKey.fromString(data.get("key").getAsString());
		return new GameEventMock(key);
	}

}
