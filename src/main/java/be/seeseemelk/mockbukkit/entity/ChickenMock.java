package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ChickenMock extends AnimalsMock implements Chicken
{

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
