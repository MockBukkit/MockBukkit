package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.entity.Cod;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Cod}.
 *
 * @see SchoolableFishMock
 */
public class CodMock extends SchoolableFishMock implements Cod
{

	/**
	 * Constructs a new {@link CodMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public CodMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull ItemStack getBaseBucketItem()
	{
		return new ItemStackMock(Material.COD_BUCKET);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.COD;
	}

}
