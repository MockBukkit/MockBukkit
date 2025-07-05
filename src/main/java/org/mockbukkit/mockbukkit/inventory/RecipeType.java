package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public enum RecipeType
{
	BLASTING("blasting"),
	CAMPFIRE_COOKING("campfire_cooking"),
	CRAFTING("crafting"),
	SMELTING("smelting"),
	SMITHING("smithing"),
	SMOKING("smoking"),
	STONECUTTING("stonecutting");

	private final String key;

	RecipeType(@NotNull String key)
	{
		this.key = Preconditions.checkNotNull(key, "key cannot be null");
	}

	public String getKey()
	{
		return key;
	}
}
