package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BoatMock extends VehicleMock implements Boat
{

	private @NotNull Type boatType = Type.OAK;
	private double maxSpeed = 0.4D;
	private double ocupiedDeceleration = 0.2D;
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
	@Deprecated
	public @NotNull TreeSpecies getWoodType()
	{
		return switch (boatType)
		{
		case SPRUCE -> TreeSpecies.REDWOOD;
		case BIRCH -> TreeSpecies.BIRCH;
		case JUNGLE -> TreeSpecies.JUNGLE;
		case ACACIA -> TreeSpecies.ACACIA;
		case DARK_OAK -> TreeSpecies.DARK_OAK;
		default -> TreeSpecies.GENERIC;
		};
	}

	@Override
	@Deprecated
	public void setWoodType(@NotNull TreeSpecies species)
	{
		Preconditions.checkNotNull(species, "Species can not be null");
		Type type;

		type = switch (species)
		{
		case GENERIC -> Type.OAK;
		case BIRCH -> Type.BIRCH;
		case ACACIA -> Type.ACACIA;
		case JUNGLE -> Type.JUNGLE;
		case DARK_OAK -> Type.DARK_OAK;
		case REDWOOD -> Type.SPRUCE;
		};

		this.boatType = type;

	}

	@Override
	public @NotNull Type getBoatType()
	{
		return this.boatType;
	}

	@Override
	public void setBoatType(@NotNull Type type)
	{
		Preconditions.checkNotNull(type, "Type cannot be null");
		this.boatType = type;
	}

	@Override
	@Deprecated
	public double getMaxSpeed()
	{
		return this.maxSpeed;
	}

	@Override
	@Deprecated
	public void setMaxSpeed(double speed)
	{
		Preconditions.checkArgument(speed >= 0.0D, "Speed cannot be negative");
		this.maxSpeed = speed;
	}

	@Override
	@Deprecated
	public double getOccupiedDeceleration()
	{
		return this.ocupiedDeceleration;
	}

	@Override
	@Deprecated
	public void setOccupiedDeceleration(double rate)
	{
		Preconditions.checkArgument(rate >= 0.0D, "Rate cannot be negative");
		this.ocupiedDeceleration = rate;
	}

	@Override
	@Deprecated
	public double getUnoccupiedDeceleration()
	{
		return this.unoccupiedDeceleration;
	}

	@Override
	@Deprecated
	public void setUnoccupiedDeceleration(double rate)
	{
		this.unoccupiedDeceleration = rate;
	}

	@Override
	@Deprecated
	public boolean getWorkOnLand()
	{
		return workOnLand;
	}

	@Override
	@Deprecated
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
		return switch (boatType)
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
		return EntityType.BOAT;
	}

}
