package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WitherMock extends AbstractBossMock implements Wither
{

	/**
	 * Constructs a new {@link MonsterMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public WitherMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
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
		throw new UnimplementedOperationException();
	}

	@Override
	public void setInvulnerabilityTicks(int i)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isCharged()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public int getInvulnerableTicks()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setInvulnerableTicks(int i)
	{
		throw new UnimplementedOperationException();
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
