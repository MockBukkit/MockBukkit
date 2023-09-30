package be.seeseemelk.mockbukkit.generator.structure;


import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.jetbrains.annotations.NotNull;

public class StructureMock extends Structure
{

	private final NamespacedKey key;
	private final StructureType type;

	public StructureMock(JsonObject data)
	{
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
		this.type = Registry.STRUCTURE_TYPE.get(Preconditions.checkNotNull(NamespacedKey.fromString(data.get("type").getAsString())));
	}

	@Override
	public @NotNull StructureType getStructureType()
	{
		return type;
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

}
