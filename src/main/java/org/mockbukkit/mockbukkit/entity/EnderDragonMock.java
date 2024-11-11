package org.mockbukkit.mockbukkit.entity;

import io.papermc.paper.math.Position;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EnderCrystal;
import org.jetbrains.annotations.Unmodifiable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Location;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class EnderDragonMock extends AbstractBossMock implements EnderDragon
{

	private Location podium;
	private Phase phase = Phase.HOVER;
	private final DragonBattle battle;

	/**
	 * Constructs a new {@link MonsterMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public EnderDragonMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid, "Ender Dragon");
		battle = new DragonBattle()
		{
			@Override
			public @Nullable EnderDragon getEnderDragon()
			{
				return EnderDragonMock.this;
			}

			@Override
			public @NotNull BossBar getBossBar()
			{
				return EnderDragonMock.this.bossBarMock;
			}

			@Override
			public @Nullable Location getEndPortalLocation()
			{
				return null;
			}

			@Override
			public boolean generateEndPortal(boolean b)
			{
				return false;
			}

			@Override
			public boolean hasBeenPreviouslyKilled()
			{
				return false;
			}

			@Override
			public void setPreviouslyKilled(boolean b)
			{
				throw new UnimplementedOperationException();
			}

			@Override
			public void initiateRespawn()
			{
				throw new UnimplementedOperationException();
			}

			@Override
			public boolean initiateRespawn(@Nullable Collection<EnderCrystal> collection)
			{
				return false;
			}

			@NotNull
			@Override
			public RespawnPhase getRespawnPhase()
			{
				throw new UnimplementedOperationException();
			}

			@Override
			public boolean setRespawnPhase(@NotNull DragonBattle.RespawnPhase respawnPhase)
			{
				return false;
			}

			@Override
			public void resetCrystals()
			{
				throw new UnimplementedOperationException();
			}

			@Override
			public int getGatewayCount()
			{
				return 0;
			}

			@Override
			public boolean spawnNewGateway()
			{
				return false;
			}

			@Override
			public void spawnNewGateway(@NotNull Position position)
			{
				throw new UnimplementedOperationException();
			}

			@Override
			public @NotNull @Unmodifiable List<EnderCrystal> getRespawnCrystals()
			{
				return List.of();
			}

			@Override
			public @NotNull @Unmodifiable List<EnderCrystal> getHealingCrystals()
			{
				return List.of();
			}
		};
		this.setMaxHealth(200.0D);
		this.setHealth(200.0D);
	}

	@NotNull
	@Override
	public Phase getPhase()
	{
		return phase;
	}

	@Override
	public void setPhase(@NotNull EnderDragon.Phase phase)
	{
		this.phase = phase;
	}

	@Override
	public @Nullable DragonBattle getDragonBattle()
	{
		return battle;
	}

	@Override
	public int getDeathAnimationTicks()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Location getPodium()
	{
		return podium;
	}

	@Override
	public void setPodium(@Nullable Location location)
	{
		podium = location;
	}

	@Override
	public @NotNull Set<ComplexEntityPart> getParts()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.ENDER_DRAGON;
	}

}
