package be.seeseemelk.mockbukkit.inventory.meta;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.jetbrains.annotations.NotNull;

public class SpawnEggMetaMock extends ItemMetaMock implements SpawnEggMeta
{

	public SpawnEggMetaMock()
	{
		super();
	}

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
