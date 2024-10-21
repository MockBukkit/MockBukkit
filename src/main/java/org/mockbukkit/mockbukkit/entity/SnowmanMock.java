package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import io.papermc.paper.entity.Shearable;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowman;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Snowman}.
 *
 * @see GolemMock
 */
public class SnowmanMock extends GolemMock implements Snowman, MockRangedEntity<SnowmanMock>, Shearable
{
	private boolean derpMode;

	/**
	 * Constructs a new {@link SnowmanMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SnowmanMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isDerp()
	{
		return this.derpMode;
	}

	@Override
	public void setDerp(boolean derpMode)
	{
		this.derpMode = derpMode;
	}

	@Override
	public void shear(@NotNull Sound.Source source)
	{
		setDerp(true);
	}

	@Override
	public boolean readyToBeSheared()
	{
		return this.isValid() && !this.isDerp();
	}

	@Override
	public org.bukkit.@Nullable Sound getAmbientSound()
	{
		return org.bukkit.Sound.ENTITY_SNOW_GOLEM_AMBIENT;
	}

	@Override
	public org.bukkit.@Nullable Sound getHurtSound()
	{
		return org.bukkit.Sound.ENTITY_SNOW_GOLEM_HURT;
	}

	@Override
	public org.bukkit.@Nullable Sound getDeathSound()
	{
		return org.bukkit.Sound.ENTITY_SNOW_GOLEM_DEATH;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SNOW_GOLEM;
	}

}
