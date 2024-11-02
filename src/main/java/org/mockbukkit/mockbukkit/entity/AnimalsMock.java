package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of an {@link Animals}.
 *
 * @see AgeableMock
 */
public class AnimalsMock extends AgeableMock implements Animals
{

	private @Nullable UUID breedCause;
	private int isInLoveTicks;

	/**
	 * Constructs a new Animal on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
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
	public void setBreedCause(@Nullable UUID uuid)
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
		Preconditions.checkNotNull(stack, "ItemStack cannot be null");
		return stack.getType() == Material.WHEAT;
	}

	@Override
	public boolean isBreedItem(@NotNull Material material)
	{
		Preconditions.checkNotNull(material, "Material cannot be null");
		return this.isBreedItem(new ItemStackMock(material));
	}

	@NotNull
	@Override
	public SpawnCategory getSpawnCategory()
	{
		return SpawnCategory.ANIMAL;
	}

	@Override
	public @NotNull String toString()
	{
		return "AnimalsMock";
	}

}
