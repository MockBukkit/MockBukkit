package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Witch;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Witch}.
 *
 * @see RaiderMock
 */
public class WitchMock extends RaiderMock implements Witch, MockRangedEntity<WitchMock>
{
	private ItemStack drinkingPotion = ItemStack.empty();
	private int drinkingTimeLeft;
	private boolean drinking;

	/**
	 * Constructs a new {@link WitchMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public WitchMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isDrinkingPotion()
	{
		return this.drinking;
	}

	/**
	 * Sets whether the witch is drinking a potion.
	 *
	 * @param drinking is the witch drinking a potion?
	 */
	public void setDrinkingPotion(boolean drinking)
	{
		this.drinking = drinking;
	}

	@Override
	public int getPotionUseTimeLeft()
	{
		return this.drinkingTimeLeft;
	}

	@Override
	public void setPotionUseTimeLeft(int ticks)
	{
		this.drinkingTimeLeft = ticks;
	}

	@Override
	public @NotNull ItemStack getDrinkingPotion()
	{
		return this.drinkingPotion;
	}

	@Override
	public void setDrinkingPotion(@Nullable ItemStack potion)
	{
		Preconditions.checkArgument(potion == null || potion.getType().isEmpty() || potion.getType() == Material.POTION, "must be potion, air, or null");
		this.drinkingPotion = (potion == null ? ItemStack.empty() : potion.clone());
	}

	@Override
	public @NotNull Sound getCelebrationSound()
	{
		return Sound.ENTITY_WITCH_CELEBRATE;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.WITCH;
	}

}
