package be.seeseemelk.mockbukkit.inventory.meta.trim;

import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;

public class TrimPatternMock implements TrimPattern
{

	private final NamespacedKey key;

	public TrimPatternMock(JsonObject data)
	{
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

}
