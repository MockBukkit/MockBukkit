package be.seeseemelk.mockbukkit.generator.structure;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class StructureMock extends Structure
{

	private final NamespacedKey key;
	private final StructureType type;

	/**
	 * @param key  The namespaced key representing this structure
	 * @param type The type of structure
	 */
	public StructureMock(NamespacedKey key, StructureType type)
	{
		this.key = key;
		this.type = type;
	}

	/**
	 * @param data Json data
	 * @deprecated Will use {@link #StructureMock(NamespacedKey, StructureType)} instead.
	 */
	@Deprecated(forRemoval = true)
	public StructureMock(JsonObject data)
	{
		Preconditions.checkNotNull(data);
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
		this.type = Registry.STRUCTURE_TYPE
				.get(Preconditions.checkNotNull(NamespacedKey.fromString(data.get("type").getAsString())));
	}

	@ApiStatus.Internal
	public static StructureMock from(JsonObject data)
	{
		Preconditions.checkNotNull(data);
		Preconditions.checkArgument(data.has("key"), "Missing json key");
		Preconditions.checkArgument(data.has("type"), "Missing json key");
		NamespacedKey key = NamespacedKey.fromString(data.get("key").getAsString());
		StructureType type = Registry.STRUCTURE_TYPE
				.get(Preconditions.checkNotNull(NamespacedKey.fromString(data.get("type").getAsString())));
		return new StructureMock(key, type);
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
