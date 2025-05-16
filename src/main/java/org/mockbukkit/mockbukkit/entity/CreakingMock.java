package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;
import org.bukkit.entity.Creaking;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link Creaking}.
 *
 * @see MonsterMock
 */
@ApiStatus.Experimental
public class CreakingMock extends MonsterMock implements Creaking
{

	private @Nullable Player activatedBy = null;
	private @Nullable Location homeLocation = null;

	/**
	 * Constructs a new {@link Creaking} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public CreakingMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.CREAKING;
	}

	@Override
	public void activate(@NotNull Player player)
	{
		Preconditions.checkArgument(player != null, "player cannot be null");
		this.activatedBy = player;

		playSound(Sound.sound(
				org.bukkit.Sound.ENTITY_CREAKING_ACTIVATE,
				Sound.Source.HOSTILE,
				1.0f,
				1.0f
		));
	}

	/**
	 * Sets the home location for this creaking (where its {@link org.bukkit.block.CreakingHeart} could be found).
	 *
	 * @param homeLocation the location of the home if available, or null otherwise
	 */
	public void setHomeLocation(@Nullable Location homeLocation)
	{
		this.homeLocation = homeLocation == null ? null : homeLocation.clone();
	}

	@Override
	public @Nullable Location getHome()
	{
		return this.homeLocation != null ? this.homeLocation.clone() : null;
	}

	@Override
	public void deactivate()
	{
		this.activatedBy = null;

		playSound(Sound.sound(
				org.bukkit.Sound.ENTITY_CREAKING_DEACTIVATE,
				Sound.Source.HOSTILE,
				1.0f,
				1.0f
		));
	}

	@Override
	public boolean isActive()
	{
		return this.activatedBy != null;
	}

}
