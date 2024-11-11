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
import java.util.List;
import java.util.Objects;

public class DragonBattleMock implements DragonBattle
{

	private final EnderDragonMock enderDragonMock;

	public DragonBattleMock(EnderDragonMock enderDragonMock)
	{
		this.enderDragonMock = enderDragonMock;
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
		return null;
	}

	@Override
	public boolean generateEndPortal(boolean generatesEndPortal)
	{
		return false;
	}

	@Override
	public boolean hasBeenPreviouslyKilled()
	{
		return false;
	}

	@Override
	public void setPreviouslyKilled(boolean previouslyKilled)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void initiateRespawn()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean initiateRespawn(@Nullable Collection<EnderCrystal> enderCrystalCollection)
	{
		return false;
	}

	@NotNull
	@Override
	public DragonBattle.RespawnPhase getRespawnPhase()
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

}
