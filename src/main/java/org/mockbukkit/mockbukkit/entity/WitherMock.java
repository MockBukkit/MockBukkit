package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.UUID;

public class WitherMock extends AbstractBossMock implements Wither
{

	private final LivingEntity[] headsTarget = new LivingEntity[]{ null, null, null }; // DATA_TARGETS for each head
	private int invulnerableTicks = 0; // DATA_ID_INV
	private boolean canPortal = false;

	/**
	 * Constructs a new {@link WitherMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public WitherMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		this(server, uuid, new WorldMock());
	}

	/**
	 * Constructs a new {@link WitherMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 * Within a {@link WorldMock} in order to determine its health with the game difficulty
	 *
	 * @param server    The server to create the entity on.
	 * @param uuid      The UUID of the entity.
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
		int index = head.ordinal();
		headsTarget[index] = livingEntity;
	}

	@Override
	public @Nullable LivingEntity getTarget(@NotNull Wither.Head head)
	{
		int index = head.ordinal();
		return headsTarget[index];
	}

	@Override
	public int getInvulnerabilityTicks()
	{
		return invulnerableTicks;
	}

	@Override
	public void setInvulnerabilityTicks(int invulnerabilityTicks)
	{
		invulnerableTicks = invulnerabilityTicks;
	}

	@Override
	public boolean isCharged()
	{
		return this.getHealth() <= this.getMaxHealth() / 2.0F;
	}

	@Override
	public int getInvulnerableTicks()
	{
		return invulnerableTicks;
	}

	@Override
	public void setInvulnerableTicks(int invulnerableTicks)
	{
		this.invulnerableTicks = invulnerableTicks;
	}

	@Override
	public boolean canTravelThroughPortals()
	{
		return canPortal;
	}

	@Override
	public void setCanTravelThroughPortals(boolean canTravelThroughPortals)
	{
		canPortal = canTravelThroughPortals;
	}

	@Override
	public void enterInvulnerabilityPhase()
	{
		this.setInvulnerableTicks(220);
		this.bossBarMock.setProgress(0.0F);
		this.setHealth(this.getMaxHealth() / 3.0F);
	}

	@Override
	public void rangedAttack(@NotNull LivingEntity livingEntity, float charge)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setChargingAttack(boolean chargingAttack)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.WITHER;
	}

}
