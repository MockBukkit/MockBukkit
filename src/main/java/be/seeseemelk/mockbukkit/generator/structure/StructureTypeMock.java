package be.seeseemelk.mockbukkit.generator.structure;

import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.generator.structure.StructureType;
import org.jetbrains.annotations.NotNull;

public class StructureTypeMock extends StructureType
{

	private final NamespacedKey key;

	public StructureTypeMock(JsonObject data)
	{
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
	}
	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

}
