package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AnimalsMock extends AgeableMock implements Animals
{

	private UUID breedCause;
	private int isInLoveTicks;

	public AnimalsMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public UUID getBreedCause()
	{
		return this.breedCause;
	}

	@Override
	public void setBreedCause(UUID uuid)
	{
		this.breedCause = uuid;
	}

	@Override
	public boolean isLoveMode()
	{
		return this.isInLoveTicks > 0;
	}

	@Override
	public void setLoveModeTicks(int ticks)
	{
		Preconditions.checkArgument(ticks >= 0, "Love mode ticks must be positive or 0");
		this.isInLoveTicks = ticks;
	}

	@Override
	public int getLoveModeTicks()
	{
		return isInLoveTicks;
	}

	@Override
	public boolean isBreedItem(@NotNull ItemStack stack)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isBreedItem(@NotNull Material material)
	{
		return this.isBreedItem(new ItemStack(material));
	}

	@NotNull
	@Override
	public SpawnCategory getSpawnCategory()
	{
		return SpawnCategory.ANIMAL;
	}

	@Override
	public String toString()
	{
		return "AnimalsMock";
	}

}
