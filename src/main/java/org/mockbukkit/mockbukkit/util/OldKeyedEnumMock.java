package org.mockbukkit.mockbukkit.util;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.util.OldEnum;
import org.jetbrains.annotations.NotNull;

public abstract class OldKeyedEnumMock<T extends OldEnum<T>> extends OldEnumMock<T> implements Keyed
{

	private final NamespacedKey key;

	public OldKeyedEnumMock(String name, int ordinal, NamespacedKey key)
	{
		super(name, ordinal);
		this.key = key;
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

}
