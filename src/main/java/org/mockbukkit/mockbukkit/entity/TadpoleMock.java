package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Tadpole;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link Tadpole}.
 *
 * @see FishMock
 */
public class TadpoleMock extends FishMock implements Tadpole
{

	private int age = 0;
	private boolean agelock;

	/**
	 * Constructs a new {@link TadpoleMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public TadpoleMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull ItemStack getBaseBucketItem()
	{
		return new ItemStackMock(Material.TADPOLE_BUCKET);
	}

	@Override
	public @NotNull Sound getPickupSound()
	{
		return Sound.ITEM_BUCKET_FILL_TADPOLE;
	}

	@Override
	public int getAge()
	{
		return this.age;
	}

	@Override
	public void setAge(int age)
	{
		Preconditions.checkArgument((this.age + age) < 24000, "Tadpole age can't be greater than 24000");
		this.age = age;
	}

	@Override
	public void setAgeLock(boolean lock)
	{
		this.agelock = lock;
	}

	@Override
	public boolean getAgeLock()
	{
		return this.agelock;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.TADPOLE;
	}

}
