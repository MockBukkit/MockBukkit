package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SizedFireball;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link SizedFireball}.
 *
 * @see FireballMock
 */
public class SizedFireballMock extends FireballMock implements SizedFireball
{

	private ItemStack displayItem = new ItemStackMock(Material.FIRE_CHARGE);

	/**
	 * Constructs a new {@link SizedFireballMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SizedFireballMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull ItemStack getDisplayItem()
	{
		return this.displayItem;
	}

	@Override
	public void setDisplayItem(@NotNull ItemStack item)
	{
		Preconditions.checkNotNull(item, "Item cannot be null");
		this.displayItem = item;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.FIREBALL;
	}

}
