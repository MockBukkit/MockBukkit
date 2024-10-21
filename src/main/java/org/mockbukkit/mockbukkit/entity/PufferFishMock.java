package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.data.EntityState;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PufferFish;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link PufferFish}.
 *
 * @see FishMock
 */
public class PufferFishMock extends FishMock implements PufferFish
{

	private int puffState = 0;

	/**
	 * Constructs a new {@link PufferFishMock} on the provided {@link ServerMock}
	 * with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public PufferFishMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull ItemStack getBaseBucketItem()
	{
		return new ItemStackMock(Material.PUFFERFISH_BUCKET);
	}

	@Override
	public int getPuffState()
	{
		return this.puffState;
	}

	@Override
	public void setPuffState(int state)
	{
		Preconditions.checkArgument((state >= 0) && (state <= 2), "State has to be between 0 and 2");
		this.puffState = state;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.PUFFERFISH;
	}

	@Override
	protected EntityState getEntityState()
	{
		return switch (getPuffState())
		{
			case 1 -> EntityState.SEMI_PUFFED;
			case 2 -> EntityState.PUFFED;
			default -> super.getEntityState();
		};
	}

}
