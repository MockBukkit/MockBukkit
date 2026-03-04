package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.AbstractNautilus;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.inventory.ArmoredSaddledMountInventory;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.UUID;

@NullMarked
public class AbstractNautilusMock extends AnimalsMock implements AbstractNautilus
{
	private boolean isTamed = false;
	private @Nullable AnimalTamer owner;

	/**
	 * Constructs a new {@link AbstractNautilus} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public AbstractNautilusMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public ArmoredSaddledMountInventory getInventory()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isTamed()
	{
		return this.isTamed;
	}

	@Override
	public void setTamed(boolean tame)
	{
		this.isTamed = tame;
	}

	@Override
	public @Nullable UUID getOwnerUniqueId()
	{
		return this.owner != null ? this.owner.getUniqueId() : null;
	}

	@Override
	public @Nullable AnimalTamer getOwner()
	{
		return this.owner;
	}

	@Override
	public void setOwner(@Nullable AnimalTamer tamer)
	{
		this.owner = tamer;
	}

}
