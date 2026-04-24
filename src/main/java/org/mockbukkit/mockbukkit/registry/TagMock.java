package org.mockbukkit.mockbukkit.registry;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.tag.Tag;
import io.papermc.paper.registry.tag.TagKey;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mock implementation of {@link Tag}.
 *
 * @param <T> The type of the tagged objects.
 */
@SuppressWarnings("UnstableApiUsage")
public class TagMock<T extends Keyed> implements Tag<T>, org.bukkit.Tag<T>
{

	private final TagKey<T> tagKey;
	private final Set<T> values;

	public TagMock(TagKey<T> tagKey, Collection<T> values)
	{
		this.tagKey = tagKey;
		this.values = Set.copyOf(values);
	}

	@Override
	public TagKey<T> tagKey()
	{
		return this.tagKey;
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return NamespacedKey.fromString(this.tagKey.key().asString());
	}

	@Override
	public boolean isTagged(T value)
	{
		return contains(TypedKey.create(this.tagKey.registryKey(), value.getKey()));
	}

	@Override
	public Set<T> getValues()
	{
		return this.values;
	}

	@Override
	public RegistryKey<T> registryKey()
	{
		return this.tagKey.registryKey();
	}

	@Override
	public @Unmodifiable Collection<TypedKey<T>> values()
	{
		return this.values.stream()
				.map(v -> TypedKey.create(registryKey(), v.getKey()))
				.collect(Collectors.toUnmodifiableSet());
	}

	@Override
	public @Unmodifiable Collection<T> resolve(Registry<T> registry)
	{
		return this.values;
	}

	@Override
	public boolean contains(TypedKey<T> valueKey)
	{
		return this.values.stream().anyMatch(v -> v.getKey().equals(valueKey.key()));
	}

	@Override
	public int size()
	{
		return this.values.size();
	}

	@Override
	public @NotNull Iterator<TypedKey<T>> iterator()
	{
		return values().iterator();
	}

}
