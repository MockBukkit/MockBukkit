package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Mock implementation of an {@link SpawnEggMeta}.
 *
 * @see ItemMetaMock
 */
public class SpawnEggMetaMock extends ItemMetaMock implements SpawnEggMeta
{

	/**
	 * Constructs a new {@link SpawnEggMetaMock}.
	 */
	public SpawnEggMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link SpawnEggMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public SpawnEggMetaMock(@NotNull SpawnEggMeta meta)
	{
		super(meta);
	}

	@Override
	public EntityType getSpawnedType()
	{
		throw new UnsupportedOperationException("Must check item type to get spawned type");
	}

	@Override
	public void setSpawnedType(EntityType type)
	{
		throw new UnsupportedOperationException("Must change item type to set spawned type");
	}

	@Override
	public void setSpawnedEntity(@NotNull EntitySnapshot snapshot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable EntitySnapshot getSpawnedEntity()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable EntityType getCustomSpawnedType()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCustomSpawnedType(@Nullable EntityType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}

	@Override
	public @NotNull SpawnEggMetaMock clone()
	{
		return (SpawnEggMetaMock) super.clone();
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized SpawnEggMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the SpawnEggMetaMock class.
	 */
	public static @NotNull SpawnEggMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		SpawnEggMetaMock serialMock = new SpawnEggMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

	@Override
	protected String getTypeName()
	{
		return "SPAWN_EGG";
	}

}
