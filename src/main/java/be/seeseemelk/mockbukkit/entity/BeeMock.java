package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import net.kyori.adventure.util.TriState;
import org.bukkit.Location;
import org.bukkit.entity.Bee;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Bee}.
 *
 * @see AnimalsMock
 */
public class BeeMock extends AnimalsMock implements Bee
{

	private @Nullable Location hive;
	private @Nullable Location flower;
	private boolean nectar = false;
	private boolean hasStung = false;
	private int anger = 0;
	private int cannotEnterHiveTicks = 0;
	private @NotNull TriState rollingOverride = TriState.NOT_SET;

	/**
	 * Constructs a new {@link BeeMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public BeeMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @Nullable Location getHive()
	{
		return this.hive;
	}

	@Override
	public void setHive(@Nullable Location location)
	{
		Preconditions.checkArgument(location == null || this.getWorld().equals(location.getWorld()),
				"Hive must be in same world");
		this.hive = location;
	}

	@Override
	public @Nullable Location getFlower()
	{
		return this.flower;
	}

	@Override
	public void setFlower(@Nullable Location location)
	{
		Preconditions.checkArgument(location == null || this.getWorld().equals(location.getWorld()),
				"Flower must be in same world");
		this.flower = location;
	}

	@Override
	public boolean hasNectar()
	{
		return this.nectar;
	}

	@Override
	public void setHasNectar(boolean nectar)
	{
		this.nectar = nectar;
	}

	@Override
	public boolean hasStung()
	{
		return this.hasStung;
	}

	@Override
	public void setHasStung(boolean stung)
	{
		this.hasStung = stung;
	}

	@Override
	public int getAnger()
	{
		return this.anger;
	}

	@Override
	public void setAnger(int anger)
	{
		this.anger = anger;
	}

	@Override
	public int getCannotEnterHiveTicks()
	{
		return this.cannotEnterHiveTicks;
	}

	@Override
	public void setCannotEnterHiveTicks(int ticks)
	{
		this.cannotEnterHiveTicks = ticks;
	}

	@Override
	public void setRollingOverride(@NotNull TriState rolling)
	{
		Preconditions.checkNotNull(rolling, "Rolling override cannot be null");
		this.rollingOverride = rolling;
	}

	@Override
	public @NotNull TriState getRollingOverride()
	{
		return this.rollingOverride;
	}

	@Override
	public boolean isRolling()
	{
		return this.rollingOverride.toBooleanOrElse(false);
	}

	@Override
	public void setCropsGrownSincePollination(int crops)
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getCropsGrownSincePollination()
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTicksSincePollination(int ticks)
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getTicksSincePollination()
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.BEE;
	}

}
