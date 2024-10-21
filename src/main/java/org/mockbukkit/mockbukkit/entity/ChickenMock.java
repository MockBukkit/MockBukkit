package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Mock implementation of a {@link Chicken}.
 *
 * @see AnimalsMock
 */
public class ChickenMock extends AnimalsMock implements Chicken
{

	private boolean isChickenJockey = false;
	private int eggTime;

	/**
	 * Constructs a new {@link ChickenMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ChickenMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
		this.eggTime = ThreadLocalRandom.current().nextInt(6000) + 6000;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.CHICKEN;
	}

	@Override
	public boolean isBreedItem(@NotNull ItemStack stack)
	{
		Preconditions.checkNotNull(stack, "ItemStack cannot be null");
		return stack.getType() == Material.WHEAT_SEEDS;
	}

	@Override
	public boolean isChickenJockey()
	{
		return this.isChickenJockey;
	}

	@Override
	public void setIsChickenJockey(boolean isChickenJockey)
	{
		this.isChickenJockey = isChickenJockey;
	}

	@Override
	public int getEggLayTime()
	{
		return this.eggTime;
	}

	@Override
	public void setEggLayTime(int eggLayTime)
	{
		this.eggTime = eggLayTime;
	}

}
