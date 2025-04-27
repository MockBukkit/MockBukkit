package org.mockbukkit.mockbukkit.entity.variant;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Pig;
import org.jetbrains.annotations.NotNull;

public class PigVariantMock implements Pig.Variant
{
	public static PigVariantMock from(JsonObject jsonObject)
	{
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		Preconditions.checkNotNull(key, "Key cannot be null");
		return new PigVariantMock(key);
	}

	private final NamespacedKey key;

	public PigVariantMock(@NotNull NamespacedKey key)
	{
		this.key = Preconditions.checkNotNull(key, "The key cannot be null");
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return this.key;
	}

}
