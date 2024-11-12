package org.mockbukkit.mockbukkit.entity;

import io.papermc.paper.math.Position;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DragonBattleMock implements DragonBattle
{

	public static final int GATEWAY_COUNT = 20;
	public int gateways = 20; // looks like this is used as some kind of for cycle to store/spawn portals

	private final EnderDragonMock enderDragonMock;
	private Location portalLocation = null; // this is the exit portal, default not spawned
	private boolean previouslyKilled;
	private List<EnderCrystal> respawnCrystals;
	private DragonBattle.RespawnPhase respawnPhase;

	public DragonBattleMock(EnderDragonMock enderDragonMock)
	{
		this.enderDragonMock = enderDragonMock;
		previouslyKilled = false; // normally obtained through save data, assume false
		respawnPhase = RespawnPhase.START; // assume it is spawning just now
		respawnCrystals = List.of(); // assume naturally spawned so no respawn crystals
	}

	@Override
	public @Nullable EnderDragon getEnderDragon()
	{
		return enderDragonMock;
	}

	@Override
	public @NotNull BossBar getBossBar()
	{
		return Objects.requireNonNull(enderDragonMock.getBossBar());
	}

	@Override
	public @Nullable Location getEndPortalLocation()
	{
		return portalLocation;
	}

	@Override
	public boolean generateEndPortal(boolean withPortals)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasBeenPreviouslyKilled()
	{
		return previouslyKilled;
	}

	@Override
	public void setPreviouslyKilled(boolean previouslyKilled)
	{
		this.previouslyKilled = previouslyKilled;
	}

	@Override
	public void initiateRespawn()
	{
		respawnPhase = RespawnPhase.START;
	}

	@Override
	public boolean initiateRespawn(@Nullable Collection<EnderCrystal> enderCrystalCollection)
	{
		if (this.hasBeenPreviouslyKilled() && this.getRespawnPhase() == RespawnPhase.NONE)
		{

			if (portalLocation == null)
			{
				portalLocation = new Location(enderDragonMock.getWorld(), 0, 0, 0);
			}

			if (enderCrystalCollection != null)
			{
				respawnCrystals = enderCrystalCollection.stream()
						.filter(Objects::nonNull)
						.filter(it -> it.getWorld().equals(enderDragonMock.getWorld()))
						.toList();
			}
			else
			{
				respawnCrystals = List.of();
			}

			respawnPhase = RespawnPhase.START;
			return true;
		}
		return false;
	}

	@NotNull
	@Override
	public DragonBattle.RespawnPhase getRespawnPhase()
	{
		return respawnPhase;
	}

	@Override
	public boolean setRespawnPhase(@NotNull DragonBattle.RespawnPhase respawnPhase)
	{
		this.respawnPhase = respawnPhase;
		return true;
	}

	@Override
	public void resetCrystals()
	{
		respawnCrystals = List.of();
	}

	@Override
	public int getGatewayCount()
	{
		return GATEWAY_COUNT - this.gateways;
	}

	@Override
	public boolean spawnNewGateway()
	{
		if (this.gateways <= 0)
		{
			return false;
		}

		gateways--;
		return true;
	}

	@Override
	public void spawnNewGateway(@NotNull Position position)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull @Unmodifiable List<EnderCrystal> getRespawnCrystals()
	{
		return Collections.unmodifiableList(respawnCrystals);
	}

	@Override
	public @NotNull @Unmodifiable List<EnderCrystal> getHealingCrystals()
	{
		throw new UnimplementedOperationException();
	}

}
