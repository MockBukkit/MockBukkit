package be.seeseemelk.mockbukkit.persistence;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is a Mock of the {@link PersistentDataContainer} interface to allow the "persistent" storage of data. Only that
 * it isn't persistent of course since it only ever exists in a test environment.
 *
 * @author TheBusyBiscuit
 */
public class PersistentDataContainerMock implements PersistentDataContainer
{

	private final PersistentDataAdapterContext context = new PersistentDataAdapterContextMock();
	private final Map<NamespacedKey, Object> map;

	public PersistentDataContainerMock()
	{
		this.map = new HashMap<>();
	}

	public PersistentDataContainerMock(PersistentDataContainerMock mock)
	{
		this.map = new HashMap<>(mock.map);
	}

	@Override
	public int hashCode()
	{
		int hashCode = 3;
		hashCode += map.hashCode();
		return hashCode;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof PersistentDataContainerMock))
		{
			return false;
		}

		return map.equals(((PersistentDataContainerMock) obj).map);
	}

	@Override
	public <T, Z> @Nullable Z get(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type)
	{
		Object value = map.get(key);

		if (value == null || !type.getPrimitiveType().isInstance(value))
		{
			return null;
		}

		return type.fromPrimitive(type.getPrimitiveType().cast(value), context);
	}

	@Override
	public <T, Z> void set(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, @NotNull Z value)
	{
		map.put(key, type.toPrimitive(value, context));
	}

	@Override
	public <T, Z> boolean has(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type)
	{
		Object value = map.get(key);
		return value != null && type.getPrimitiveType().isInstance(value);
	}

	@Override
	public @NotNull PersistentDataAdapterContext getAdapterContext()
	{
		return context;
	}

	@Override
	public boolean has(@NotNull NamespacedKey key)
	{
		return map.containsKey(key);
	}

	@Override
	public <T, Z> @NotNull Z getOrDefault(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type,
										  @NotNull Z defaultValue)
	{
		Z value = get(key, type);
		return value != null ? value : defaultValue;
	}

	@Override
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	@Override
	public void remove(@NotNull NamespacedKey key)
	{
		map.remove(key);
	}

	@Override
	public Set<NamespacedKey> getKeys()
	{
		return Collections.unmodifiableSet(map.keySet());
	}

	public Map<String, Object> serialize()
	{
		return map.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue));
	}

	public static PersistentDataContainerMock deserialize(Map<String, Object> args)
	{
		PersistentDataContainerMock mock = new PersistentDataContainerMock();
		for (Map.Entry<String, Object> entry : args.entrySet())
		{
			mock.map.put(NamespacedKey.fromString(entry.getKey()), entry.getValue());
		}
		return mock;
	}

}
