package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Piglin;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Mock implementation of a {@link Piglin}.
 *
 * @see PiglinAbstractMock
 */
public class PiglinMock extends PiglinAbstractMock implements Piglin
{
	private final Set<Material> allowedInterestItems = new HashSet<>();
	private final Set<Material> allowedBarteringItems = new HashSet<>();

	private final InventoryMock inventory;

	private boolean canHunt = true;
	private boolean isChargingCrossbow = false;

	/**
	 * Constructs a new {@link PiglinMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public PiglinMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);

		this.inventory = new InventoryMock(null, 8, InventoryType.CHEST);
	}

	@Override
	public boolean isAbleToHunt()
	{
		return this.canHunt;
	}

	@Override
	public void setIsAbleToHunt(boolean flag)
	{
		this.canHunt = flag;
	}

	@Override
	public boolean addBarterMaterial(@NotNull Material material)
	{
		Preconditions.checkArgument(material != null, "material cannot be null");
		return this.allowedBarteringItems.add(material);
	}

	@Override
	public boolean removeBarterMaterial(@NotNull Material material)
	{
		Preconditions.checkArgument(material != null, "material cannot be null");
		return this.allowedBarteringItems.remove(material);
	}

	@Override
	public boolean addMaterialOfInterest(@NotNull Material material)
	{
		Preconditions.checkArgument(material != null, "material cannot be null");
		return this.allowedInterestItems.add(material);
	}

	@Override
	public boolean removeMaterialOfInterest(@NotNull Material material)
	{
		Preconditions.checkArgument(material != null, "material cannot be null");
		return this.allowedInterestItems.remove(material);
	}

	@Override
	public @NotNull Set<Material> getInterestList()
	{
		return Collections.unmodifiableSet(this.allowedInterestItems);
	}

	@Override
	public @NotNull Set<Material> getBarterList()
	{
		return Collections.unmodifiableSet(this.allowedBarteringItems);
	}

	@Override
	public void setChargingCrossbow(boolean chargingCrossbow)
	{
		this.isChargingCrossbow = chargingCrossbow;
	}

	@Override
	public boolean isChargingCrossbow()
	{
		return this.isChargingCrossbow;
	}

	@Override
	public void setDancing(boolean dancing)
	{
		throw new UnimplementedOperationException("SetDancing was not implemented yet.");
	}

	@Override
	public void setDancing(long duration)
	{
		throw new UnimplementedOperationException("SetDancing was not implemented yet.");
	}

	@Override
	public boolean isDancing()
	{
		throw new UnimplementedOperationException("IsDancing was not implemented yet.");
	}

	@Override
	public void rangedAttack(LivingEntity target, float charge)
	{
		throw new UnimplementedOperationException("RangedAttack was not implemented yet.");
	}

	@Override
	public void setChargingAttack(boolean raiseHands)
	{
		throw new UnimplementedOperationException("SetChargingAttack was not implemented yet.");
	}

	@Override
	public @NotNull InventoryMock getInventory()
	{
		return this.inventory;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.PIGLIN;
	}

}
