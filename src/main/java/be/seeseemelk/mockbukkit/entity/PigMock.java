package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Mock implementation of an {@link Pig}.
 *
 * @see AnimalsMock
 */
public class PigMock extends AnimalsMock implements Pig
{

	private boolean hasSaddle = false;
	private int boostTicks = 0;
	private int currentBoostTicks = 0;

	/**
	 * Constructs a new {@link PigMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public PigMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
		boostTicks = ThreadLocalRandom.current().nextInt(841) + 140;
	}

	@Override
	public boolean hasSaddle()
	{
		return this.hasSaddle;
	}

	@Override
	public void setSaddle(boolean saddled)
	{
		this.hasSaddle = saddled;
	}

	@Override
	public int getBoostTicks()
	{
		return this.boostTicks;
	}

	@Override
	public void setBoostTicks(int ticks)
	{
		Preconditions.checkArgument(ticks >= 0, "ticks must be bigger than or equal to 0");
		this.boostTicks = ticks;
	}

	@Override
	public int getCurrentBoostTicks()
	{
		return this.currentBoostTicks;
	}

	@Override
	public void setCurrentBoostTicks(int ticks)
	{
		if (!this.hasSaddle)
		{
			return;
		}
		Preconditions.checkArgument(ticks >= 0 && ticks <= this.boostTicks,
				"Current Boost Ticks must be less than Boost Ticks (#getBoostTicks)");
		this.currentBoostTicks = ticks;
	}

	@Override
	public @NotNull Material getSteerMaterial()
	{
		return Material.CARROT_ON_A_STICK;
	}

	@Override
	public EntityType getType()
	{
		return EntityType.PIG;
	}

}
