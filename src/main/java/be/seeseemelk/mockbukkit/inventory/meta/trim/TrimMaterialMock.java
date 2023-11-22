package be.seeseemelk.mockbukkit.inventory.meta.trim;

import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.jetbrains.annotations.NotNull;

public class TrimMaterialMock implements TrimMaterial
{

	private final NamespacedKey key;

	public TrimMaterialMock(JsonObject data)
	{
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

}
