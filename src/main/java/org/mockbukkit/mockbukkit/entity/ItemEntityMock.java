package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of an {@link Item}.
 *
 * @see ItemMock
 * @deprecated {@link ItemEntityMock} is deprecated in favor of {@link ItemMock} to match the other entities pattern.
 */
@Deprecated(forRemoval = true)
public class ItemEntityMock extends ItemMock
{

	/**
	 * Constructs a new {@link ItemMock} on the provided {@link ServerMock} with a specified {@link UUID} and {@link ItemStack}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 * @param item   The item this entity represents.
	 */
	public ItemEntityMock(@NotNull ServerMock server, @NotNull UUID uuid, @NotNull ItemStack item)
	{
		super(server, uuid, item);
	}

}
