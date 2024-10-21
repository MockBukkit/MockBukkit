package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;

/**
 * Mock implementation of a {@link ThrownPotion}.
 *
 * @see ThrowableProjectileMock
 */
public class ThrownPotionMock extends ThrowableProjectileMock implements ThrownPotion
{
	private @NotNull ItemStack potionItem = new ItemStack(Material.SPLASH_POTION);

	/**
	 * Constructs a new {@link ThrownPotionMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ThrownPotionMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull Collection<PotionEffect> getEffects()
	{
		return getPotionMeta().getCustomEffects();
	}

	@Override
	public @NotNull PotionMeta getPotionMeta()
	{
		return (PotionMeta) this.potionItem.getItemMeta();
	}

	@Override
	public void setPotionMeta(@NotNull PotionMeta meta)
	{
		Preconditions.checkArgument(meta != null, "PotionMeta cannot be null");
		this.potionItem.setItemMeta(meta.clone());
	}

	@Override
	public void splash()
	{
		throw new UnimplementedOperationException("Splash function was not implemented yet!");
	}

	@Override
	public @NotNull ItemStack getItem()
	{
		return this.potionItem.clone();
	}

	@Override
	public void setItem(@NotNull ItemStack item)
	{
		Preconditions.checkArgument(item != null, "ItemStack cannot be null");

		PotionMeta meta = (Material.LINGERING_POTION.equals(item.getType()) || Material.SPLASH_POTION.equals(item.getType())) ? null : this.getPotionMeta();
		this.potionItem = item.clone();
		if (meta != null)
		{
			this.setPotionMeta(meta);
		}
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.POTION;
	}

}
