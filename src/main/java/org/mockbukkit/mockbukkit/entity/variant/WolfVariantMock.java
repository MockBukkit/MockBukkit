package org.mockbukkit.mockbukkit.entity.variant;

import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Wolf;
import org.jetbrains.annotations.NotNull;

public class WolfVariantMock implements Wolf.Variant
{

	private final NamespacedKey namespacedKey;

	private WolfVariantMock(NamespacedKey namespacedKey)
	{
		this.namespacedKey = namespacedKey;
	}

	public static WolfVariantMock from(JsonObject jsonObject)
	{
		return new WolfVariantMock(NamespacedKey.fromString(jsonObject.get("key").getAsString()));
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return namespacedKey;
	}

}
