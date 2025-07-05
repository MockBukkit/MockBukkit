package org.mockbukkit.mockbukkit.persistence;

import com.google.common.base.Preconditions;
import io.papermc.paper.persistence.PersistentDataContainerView;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.io.IOException;

public abstract class PersistentDataContainerViewMock implements PersistentDataContainerView
{

	private final PersistentDataAdapterContext context = new PersistentDataAdapterContextMock();

	@Override
	public <T, Z> boolean has(NamespacedKey key, PersistentDataType<T, Z> type)
	{
		Preconditions.checkArgument(key != null, "The NamespacedKey key cannot be null");
		Preconditions.checkArgument(type != null, "The provided type cannot be null");

		final Object value = this.get(key, type);
		return value != null;
	}

	@Override
	public boolean has(NamespacedKey key)
	{
		return getKeys().contains(key);
	}

	@Override
	public <T, Z> Z getOrDefault(NamespacedKey key, PersistentDataType<T, Z> type, Z defaultValue)
	{
		Z value = get(key, type);
		return value != null ? value : defaultValue;
	}

	@Override
	public boolean isEmpty()
	{
		return getKeys().isEmpty();
	}

	@Override
	public PersistentDataAdapterContext getAdapterContext()
	{
		return context;
	}

	@Override
	public void copyTo(PersistentDataContainer other, boolean replace)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public byte[] serializeToBytes() throws IOException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
