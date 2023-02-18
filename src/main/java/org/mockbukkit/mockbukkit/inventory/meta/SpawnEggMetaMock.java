package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.jetbrains.annotations.NotNull;

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

}
