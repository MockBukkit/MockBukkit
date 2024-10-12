package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.Location;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public class EnderDragonMock extends AbstractBossMock implements EnderDragon
{

	/**
	 * Constructs a new {@link MonsterMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public EnderDragonMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@NotNull
	@Override
	public Phase getPhase()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setPhase(@NotNull EnderDragon.Phase phase)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable DragonBattle getDragonBattle()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public int getDeathAnimationTicks()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Location getPodium()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setPodium(@Nullable Location location)
	{
		throw new UnimplementedOperationException();
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
