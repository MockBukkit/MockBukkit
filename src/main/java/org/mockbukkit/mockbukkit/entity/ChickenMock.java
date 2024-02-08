package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.excpetion.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Chicken}.
 *
 * @see AnimalsMock
 */
public class ChickenMock extends AnimalsMock implements Chicken
{

	/**
	 * Constructs a new {@link ChickenMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ChickenMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
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
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setIsChickenJockey(boolean isChickenJockey)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public int getEggLayTime()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setEggLayTime(int eggLayTime)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
