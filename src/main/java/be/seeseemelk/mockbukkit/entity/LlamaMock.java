package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.LlamaInventoryMock;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Mock implementation of a {@link Llama}.
 *
 * @see ChestedHorseMock
 */
public class LlamaMock extends ChestedHorseMock implements Llama
{

	private @NotNull Color color = Color.BROWN;
	private int strength = 1;

	private final Map<LivingEntity, Pair<Float, Boolean>> attackedMobs = new HashMap<>();
	private boolean isAgressive = false;

	private final @NotNull LlamaInventoryMock inventory;

	/**
	 * Constructs a new {@link LlamaMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public LlamaMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
		this.inventory = new LlamaInventoryMock(this);
	}

	@Override
	public @NotNull Color getColor()
	{
		return this.color;
	}

	@Override
	public void setColor(@NotNull Color color)
	{
		Preconditions.checkNotNull(color, "Color cannot be null");
		this.color = color;
	}

	@Override
	public int getStrength()
	{
		return this.strength;
	}

	@Override
	public void setStrength(int strength)
	{
		Preconditions.checkArgument(strength >= 1 && strength <= 5, "Strength cannot be negative");
		this.strength = strength;
	}

	@Override
	public void rangedAttack(@NotNull LivingEntity target, float charge)
	{
		Preconditions.checkNotNull(target, "Target cannot be null");
		Preconditions.checkArgument(charge >= 0 && charge <= 1, "Charge must be between 0 and 1");
		attackedMobs.put(target, Pair.of(charge, isAgressive));
	}

	@Override
	public void setChargingAttack(boolean raiseHands)
	{
		this.isAgressive = raiseHands;
	}

	/**
	 * Asserts that the given entity was attacked by this llama with the given charge.
	 *
	 * @param entity The {@link LivingEntity} to check.
	 * @param charge The charge of the attack.
	 */
	public void assertAttacked(LivingEntity entity, float charge)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		Preconditions.checkArgument(charge >= 0 && charge <= 1, "Charge must be between 0 and 1");

		if (!attackedMobs.containsKey(entity) || attackedMobs.get(entity).getLeft() != charge)
		{
			fail();
		}
	}

	/**
	 * Asserts that the given entity was attacked by this llama with the given charge and is agressive.
	 *
	 * @param entity The {@link LivingEntity} to check.
	 * @param charge The charge of the attack.
	 */
	public void assertAgressiveAttack(LivingEntity entity, float charge)
	{
		assertAttacked(entity, charge);
		if (!attackedMobs.get(entity).getRight())
		{
			fail();
		}
	}

	@Override
	@Deprecated
	public Horse.@NotNull Variant getVariant()
	{
		return Horse.Variant.LLAMA;
	}

	@Override
	public @NotNull LlamaInventoryMock getInventory()
	{
		return this.inventory;
	}

	@Override
	public boolean inCaravan()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void joinCaravan(@NotNull Llama llama)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void leaveCaravan()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Llama getCaravanHead()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasCaravanTail()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Llama getCaravanTail()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isChargingAttack()
	{
		return this.isAgressive;
	}

	@Override
	public EntityType getType()
	{
		return EntityType.LLAMA;
	}
}
