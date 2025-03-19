package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.UUID;

/**
 * Mock implementation of a {@link LightningStrike}.
 *
 * @see EntityMock
 */
public class LightningStrikeMock extends EntityMock implements LightningStrike
{
	private boolean isEffect = false;
	private int numberOfFlashes = 3;
	private int ticksLived = 0;

	private @Nullable Player causingPlayer = null;

	/**
	 * Constructs a new {@link LightningStrikeMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public LightningStrikeMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isEffect()
	{
		return this.isEffect;
	}

	/**
	 * Set whether the strike is an effect that does no damage.
	 *
	 * @param effect {@code true} if the strike is an effect, otherwise {@code false}
	 */
	public void setEffect(boolean effect)
	{
		this.isEffect = effect;
	}

	@Override
	@Deprecated(forRemoval = true)
	public int getFlashes()
	{
		return this.getFlashCount();
	}

	@Override
	@Deprecated(forRemoval = true)
	public void setFlashes(int flashes)
	{
		this.setFlashCount(flashes);
	}

	@Override
	public int getLifeTicks()
	{
		return this.ticksLived;
	}

	@Override
	public void setLifeTicks(int ticks)
	{
		this.ticksLived = ticks;
	}

	@Override
	public @Nullable Player getCausingPlayer()
	{
		return this.causingPlayer;
	}

	@Override
	public void setCausingPlayer(@Nullable Player player)
	{
		this.causingPlayer = player;
	}

	@Override
	public int getFlashCount()
	{
		return this.numberOfFlashes;
	}

	@Override
	public void setFlashCount(int flashes)
	{
		Preconditions.checkArgument(flashes >= 1, "flashes must be >= 1");
		this.numberOfFlashes = flashes;
	}

	@Override
	public @Nullable Entity getCausingEntity()
	{
		return this.getCausingPlayer();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.LIGHTNING_BOLT;
	}

	@Override
	@Deprecated(forRemoval = true)
	public @NotNull LightningStrike.Spigot spigot()
	{
		throw new UnimplementedOperationException("spigot() method is not implemented in LightningStrikeMock");
	}

}
