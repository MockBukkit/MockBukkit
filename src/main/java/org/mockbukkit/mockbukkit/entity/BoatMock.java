package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.UUID;

/**
 * Mock implementation of a {@link Boat}.
 *
 * @see VehicleMock
 */
public class BoatMock extends VehicleMock implements Boat
{
	private double maxSpeed = 0.4D;
	private double occupiedDeceleration = 0.2D;
	private double unoccupiedDeceleration = -1;
	private boolean workOnLand = false;

	/**
	 * Constructs a new {@link BoatMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public BoatMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	@Deprecated(since = "1.19")
	public @NotNull TreeSpecies getWoodType()
	{
		EntityType boatType = getType();
		if (boatType == EntityType.SPRUCE_BOAT || boatType == EntityType.SPRUCE_CHEST_BOAT) {
			return TreeSpecies.REDWOOD;
		}

		if (boatType == EntityType.BIRCH_BOAT || boatType == EntityType.BIRCH_CHEST_BOAT) {
			return TreeSpecies.BIRCH;
		}

		if (boatType == EntityType.JUNGLE_BOAT || boatType == EntityType.JUNGLE_CHEST_BOAT) {
			return TreeSpecies.JUNGLE;
		}

		if (boatType == EntityType.ACACIA_BOAT || boatType == EntityType.ACACIA_CHEST_BOAT) {
			return TreeSpecies.ACACIA;
		}

		if (boatType == EntityType.DARK_OAK_BOAT || boatType == EntityType.DARK_OAK_CHEST_BOAT) {
			return TreeSpecies.DARK_OAK;
		}

		return TreeSpecies.GENERIC;
	}

	@Override
	@Deprecated(since = "1.19")
	public void setWoodType(@NotNull TreeSpecies species)
	{
		throw new UnsupportedOperationException("Not supported - you must spawn a new entity to change boat type.");
	}

	@Override
	@Deprecated(since = "1.21.2")
	public @NotNull Type getBoatType()
	{
		EntityType boatType = getType();
		if (boatType == EntityType.OAK_BOAT || boatType == EntityType.OAK_CHEST_BOAT) {
			return Type.OAK;
		}

		if (boatType == EntityType.BIRCH_BOAT || boatType == EntityType.BIRCH_CHEST_BOAT) {
			return Type.BIRCH;
		}

		if (boatType == EntityType.ACACIA_BOAT || boatType == EntityType.ACACIA_CHEST_BOAT) {
			return Type.ACACIA;
		}

		if (boatType == EntityType.CHERRY_BOAT || boatType == EntityType.CHERRY_CHEST_BOAT) {
			return Type.CHERRY;
		}

		if (boatType == EntityType.JUNGLE_BOAT || boatType == EntityType.JUNGLE_CHEST_BOAT) {
			return Type.JUNGLE;
		}

		if (boatType == EntityType.SPRUCE_BOAT || boatType == EntityType.SPRUCE_CHEST_BOAT) {
			return Type.SPRUCE;
		}

		if (boatType == EntityType.DARK_OAK_BOAT || boatType == EntityType.DARK_OAK_CHEST_BOAT) {
			return Type.DARK_OAK;
		}

		if (boatType == EntityType.MANGROVE_BOAT || boatType == EntityType.MANGROVE_CHEST_BOAT) {
			return Type.MANGROVE;
		}

		if (boatType == EntityType.BAMBOO_RAFT || boatType == EntityType.BAMBOO_CHEST_RAFT) {
			return Type.BAMBOO;
		}

		throw new EnumConstantNotPresentException(Type.class, boatType.toString());
	}

	@Override
	@Deprecated(since = "1.21.2")
	public void setBoatType(@NotNull Type type)
	{
		throw new UnsupportedOperationException("Not supported - you must spawn a new entity to change boat type.");
	}

	@Override
	@Deprecated(since = "1.9")
	public double getMaxSpeed()
	{
		return this.maxSpeed;
	}

	@Override
	@Deprecated(since = "1.9")
	public void setMaxSpeed(double speed)
	{
		Preconditions.checkArgument(speed >= 0.0D, "Speed cannot be negative");
		this.maxSpeed = speed;
	}

	@Override
	@Deprecated(since = "1.9")
	public double getOccupiedDeceleration()
	{
		return this.occupiedDeceleration;
	}

	@Override
	@Deprecated(since = "1.9")
	public void setOccupiedDeceleration(double rate)
	{
		Preconditions.checkArgument(rate >= 0.0D, "Rate cannot be negative");
		this.occupiedDeceleration = rate;
	}

	@Override
	@Deprecated(since = "1.9")
	public double getUnoccupiedDeceleration()
	{
		return this.unoccupiedDeceleration;
	}

	@Override
	@Deprecated(since = "1.9")
	public void setUnoccupiedDeceleration(double rate)
	{
		this.unoccupiedDeceleration = rate;
	}

	@Override
	@Deprecated(since = "1.9")
	public boolean getWorkOnLand()
	{
		return workOnLand;
	}

	@Override
	@Deprecated(since = "1.9")
	public void setWorkOnLand(boolean workOnLand)
	{
		this.workOnLand = workOnLand;
	}

	@Override
	public @NotNull Status getStatus()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Material getBoatMaterial()
	{
		return switch (getBoatType())
		{
			case OAK -> Material.OAK_BOAT;
			case SPRUCE -> Material.SPRUCE_BOAT;
			case BIRCH -> Material.BIRCH_BOAT;
			case JUNGLE -> Material.JUNGLE_BOAT;
			case ACACIA -> Material.ACACIA_BOAT;
			case CHERRY -> Material.CHERRY_BOAT;
			case DARK_OAK -> Material.DARK_OAK_BOAT;
			case MANGROVE -> Material.MANGROVE_BOAT;
			case BAMBOO -> Material.BAMBOO_RAFT;
		};
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.OAK_BOAT;
	}

	@Override
	public boolean isLeashed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NonNull Entity getLeashHolder() throws IllegalStateException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setLeashHolder(@Nullable Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
