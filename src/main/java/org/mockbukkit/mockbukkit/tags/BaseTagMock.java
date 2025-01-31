package org.mockbukkit.mockbukkit.tags;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

public abstract class BaseTagMock<T extends Keyed> implements Tag<T>
{

	private final NamespacedKey key;
	private final Set<T> values;

	protected BaseTagMock(@NotNull NamespacedKey key, @NotNull Collection<T> values)
	{
		this.key = Preconditions.checkNotNull(key, "key must not be null");
		this.values = new HashSet<>(Preconditions.checkNotNull(values, "values must not be null"));
	}

	@SafeVarargs
	protected BaseTagMock(@NotNull NamespacedKey key, @NotNull T... values)
	{
		this(key, Lists.newArrayList(Preconditions.checkNotNull(values, "values must not be null")));
	}

	@Override
	public boolean isTagged(@NotNull T item)
	{
		return this.values.contains(item);
	}

	@Override
	public @NotNull Set<T> getValues()
	{
		return ImmutableSet.copyOf(this.values);
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return this.key;
	}

}
