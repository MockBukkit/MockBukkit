package org.mockbukkit.mockbukkit.entity;


import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Salmon;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Salmon}.
 *
 * @see SchoolableFishMock
 */
public class SalmonMock extends SchoolableFishMock implements Salmon
{

	/**
	 * Constructs a new {@link SalmonMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SalmonMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull ItemStack getBaseBucketItem()
	{
		return new ItemStackMock(Material.SALMON_BUCKET);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SALMON;
	}

}
