package org.mockbukkit.mockbukkit.entity.memory;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.memory.MemoryKey;

public class MemoryModuleMock
{

	@SuppressWarnings("unchecked")
	public static MemoryKey<Object> from(JsonObject jsonObject)
	{
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		Preconditions.checkNotNull(key, "Key cannot be null");
		return (MemoryKey<Object>) MemoryKey.getByKey(key);
	}

	private MemoryModuleMock()
	{
		// hide the public constructor
	}

}
