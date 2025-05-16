package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WanderingTrader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of an {@link WanderingTrader}.
 *
 * @see AbstractVillagerMock
 */
public class WanderingTraderMock extends AbstractVillagerMock implements WanderingTrader
{

	private int despawnDelay = 0;
	private boolean canDrinkPotion = true;
	private boolean canDrinkMilk = true;

	private @Nullable Location wanderTarget = null;

	/**
	 * Constructs a new {@link AbstractVillagerMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public WanderingTraderMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public int getDespawnDelay()
	{
		return this.despawnDelay;
	}

	@Override
	public void setDespawnDelay(int despawnDelay)
	{
		this.despawnDelay = despawnDelay;
	}

	@Override
	public void setCanDrinkPotion(boolean canDrink)
	{
		this.canDrinkPotion = canDrink;
	}

	@Override
	public boolean canDrinkPotion()
	{
		return this.canDrinkPotion;
	}

	@Override
	public void setCanDrinkMilk(boolean canDrink)
	{
		this.canDrinkMilk = canDrink;
	}

	@Override
	public boolean canDrinkMilk()
	{
		return this.canDrinkMilk;
	}

	@Override
	public @Nullable Location getWanderingTowards()
	{
		return (this.wanderTarget == null) ? null : this.wanderTarget.clone();
	}

	@Override
	public void setWanderingTowards(@Nullable Location location)
	{
		if (location == null)
		{
			this.wanderTarget = null;
		}
		else
		{
			this.wanderTarget = location.toBlockLocation();
		}
	}

	@Override
	protected void updateTrades()
	{
		// TODO: Villager trades at net.minecraft.world.entity.npc.WanderingTrader#updateTrades()
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.WANDERING_TRADER;
	}

}
