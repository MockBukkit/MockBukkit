package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.UUID;

public class WitherMock extends AbstractBossMock implements Wither
{

	private int invulnerable_ticks = 0;

	/**
	 * Constructs a new {@link MonsterMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public WitherMock(@NotNull ServerMock server, @NotNull UUID uuid) {
		this(server, uuid, new WorldMock());
	}

	/**
	 * Constructs a new {@link MonsterMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 * Within a {@link WorldMock} in order to determine its health with the game difficulty
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 * @param worldMock World where the wither is
	 */
	public WitherMock(@NotNull ServerMock server, @NotNull UUID uuid, @NotNull WorldMock worldMock)
	{
		super(server, uuid, "Wither");
		this.setLocation(new Location(worldMock, 0, 0, 0));
		setMaxHealth(entityData.getHealth(this.getSubType(), this.getEntityState(), this.getWorld().getDifficulty()));
		setHealth(getMaxHealth());
	}

	@Override
	public void setTarget(@NotNull Wither.Head head, @Nullable LivingEntity livingEntity)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable LivingEntity getTarget(@NotNull Wither.Head head)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public int getInvulnerabilityTicks()
	{
		return invulnerable_ticks;
	}

	@Override
	public void setInvulnerabilityTicks(int i)
	{
		invulnerable_ticks = i;
	}

	@Override
	public boolean isCharged()
	{
		return this.getHealth() <= this.getMaxHealth() / 2.0F;
	}

	@Override
	public int getInvulnerableTicks()
	{
		return invulnerable_ticks;
	}

	@Override
	public void setInvulnerableTicks(int i)
	{
		invulnerable_ticks = i;
	}

	@Override
	public boolean canTravelThroughPortals()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCanTravelThroughPortals(boolean b)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void enterInvulnerabilityPhase()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void rangedAttack(@NotNull LivingEntity livingEntity, float v)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setChargingAttack(boolean b)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.WITHER;
	}

}
