package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Mock implementation of an {@link AbstractSkeleton}.
 *
 * @see MonsterMock
 */
public abstract class AbstractSkeletonMock extends MonsterMock implements AbstractSkeleton
{

	private boolean shouldBurnInDay = true;
	private boolean isChargingAttack = false;
	private final Map<LivingEntity, Pair<Float, Boolean>> attackedMobs = new HashMap<>();

	/**
	 * Constructs a new {@link AbstractSkeletonMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected AbstractSkeletonMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	@Deprecated(since = "1.17")
	public void setSkeletonType(Skeleton.SkeletonType type)
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean shouldBurnInDay()
	{
		return shouldBurnInDay;
	}

	@Override
	public void setShouldBurnInDay(boolean shouldBurnInDay)
	{
		this.shouldBurnInDay = shouldBurnInDay;
	}

	@Override
	public boolean isChargingAttack()
	{
		return this.isChargingAttack;
	}

	@Override
	public void rangedAttack(@NotNull LivingEntity target, float charge)
	{
		Preconditions.checkNotNull(target, "Entity cannot be null");
		Preconditions.checkArgument(charge < 1F && charge > 0F, "Charge needs to be between 0 and 1");

		this.attackedMobs.put(target, Pair.of(charge, this.isChargingAttack));
	}

	@Override
	public void setChargingAttack(boolean raiseHands)
	{
		this.isChargingAttack = raiseHands;
	}

	/**
	 * Asserts that the given entity was attacked by this Skeleton with the given charge.
	 *
	 * @param entity The {@link LivingEntity} to check.
	 * @param charge The charge of the attack.
	 */
	public void assertAttacked(LivingEntity entity, float charge)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		Preconditions.checkArgument(charge >= 0F && charge <= 1F, "Charge must be between 0 and 1");

		if (!attackedMobs.containsKey(entity) || attackedMobs.get(entity).getLeft() != charge)
		{
			fail();
		}
	}

	/**
	 * Asserts that the given entity was attacked by this Skeleton with the given charge and is agressive.
	 *
	 * @param entity The {@link LivingEntity} to check.
	 * @param charge The charge of the attack.
	 */
	public void assertAggressiveAttack(LivingEntity entity, float charge)
	{
		assertAttacked(entity, charge);
		if (!attackedMobs.get(entity).getRight())
		{
			fail();
		}
	}

	/**
	 * @deprecated Use {@link #assertAggressiveAttack(LivingEntity, float)}.
	 */
	@Deprecated(forRemoval = true)
	public void assertAgressiveAttack(LivingEntity entity, float charge)
	{
		assertAggressiveAttack(entity, charge);
	}

}
