package be.seeseemelk.mockbukkit.persistence;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is a mock of the {@link PersistentDataContainer} interface to allow the "persistent" storage of data. Only that
 * it isn't persistent of course since it only ever exists in a test environment.
 *
 * @author TheBusyBiscuit
 */
public class PersistentDataContainerMock implements PersistentDataContainer
{

	private final PersistentDataAdapterContext context = new PersistentDataAdapterContextMock();
	private final @NotNull Map<NamespacedKey, Object> map;

	/**
	 * Constructs a new {@link PersistentDataContainerMock} with no stored data.
	 */
	public PersistentDataContainerMock()
	{
		this.map = new HashMap<>();
	}

	/**
	 * Constructs a new {@link PersistentDataContainerMock}, cloning the data of another.
	 *
	 * @param mock The {@link PersistentDataContainerMock} to clone.
	 */
	public PersistentDataContainerMock(@NotNull PersistentDataContainerMock mock)
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
	public byte @NotNull [] serializeToBytes() throws IOException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void readFromBytes(byte @NotNull [] bytes, boolean clear) throws IOException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public void copyTo(@NotNull PersistentDataContainer other, boolean replace)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void remove(@NotNull NamespacedKey key)
	{
		map.remove(key);
	}

	@Override
	public @NotNull Set<NamespacedKey> getKeys()
	{
		return Collections.unmodifiableSet(map.keySet());
	}

	/**
	 * Serializes the held data.
	 *
	 * @return The serialized data.
	 * @see #deserialize(Map)
	 */
	public @NotNull Map<String, Object> serialize()
	{
		return map.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue));
	}

	/**
	 * Deserializes a {@link PersistentDataContainerMock} from a map.
	 *
	 * @param args The map to deserialize.
	 * @return The deserialized {@link PersistentDataContainerMock}.
	 */
	public static @NotNull PersistentDataContainerMock deserialize(@NotNull Map<String, Object> args)
	{
		PersistentDataContainerMock mock = new PersistentDataContainerMock();
		for (Map.Entry<String, Object> entry : args.entrySet())
		{
			mock.map.put(NamespacedKey.fromString(entry.getKey()), entry.getValue());
		}
		return mock;
	}

}
