package org.mockbukkit.mockbukkit.generator.structure;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.generator.structure.StructureType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class StructureTypeMock extends StructureType
{

	private final NamespacedKey key;

	/**
	 * @param key The namespaced key representing this structure type
	 */
	public StructureTypeMock(NamespacedKey key)
	{
		this.key = key;
	}

	/**
	 * @param data Json data
	 * @deprecated Use {@link #StructureTypeMock(NamespacedKey)} instead
	 */
	@Deprecated(forRemoval = true)
	public StructureTypeMock(JsonObject data)
	{
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

	@ApiStatus.Internal
	public static StructureTypeMock from(JsonObject data)
	{
		Preconditions.checkNotNull(data);
		Preconditions.checkArgument(data.has("key"), "Missing json key");
		NamespacedKey key = NamespacedKey.fromString(data.get("key").getAsString());
		return new StructureTypeMock(key);
	}

}
