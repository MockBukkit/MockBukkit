package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sniffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


/**
 * Mock implementation of an {@link Sniffer}.
 *
 * @see AnimalsMock
 */
public class SnifferMock extends AnimalsMock implements Sniffer
{
	private final List<Location> exploredLocations = new ArrayList<>();

	private State state = State.IDLING;

	/**
	 * Constructs a new {@link Sniffer} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SnifferMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull Collection<Location> getExploredLocations()
	{
		return Collections.unmodifiableList(exploredLocations);
	}

	@Override
	public void removeExploredLocation(@NotNull Location location)
	{
		Preconditions.checkArgument(location != null, "location cannot be null");

		Location blockPosition = location.toBlockLocation();
		World world = blockPosition.getWorld() == null ? getWorld() : blockPosition.getWorld();
		blockPosition.setWorld(world);
		exploredLocations.remove(blockPosition);
	}

	@Override
	public void addExploredLocation(@NotNull Location location)
	{
		Preconditions.checkArgument(location != null, "location cannot be null");
		if (location.getWorld() != this.getWorld())
		{
			return;
		}

		exploredLocations.add(location.toBlockLocation());
	}

	@Override
	public @NotNull State getState()
	{
		return this.state;
	}

	@Override
	public void setState(@NotNull State state)
	{
		Preconditions.checkArgument(state != null, "state cannot be null");

		this.state = state;

		switch (state)
		{
		case FEELING_HAPPY -> playSound(Sound.sound(
				org.bukkit.Sound.ENTITY_SNIFFER_HAPPY,
				Sound.Source.AMBIENT,
				1.0f,
				1.0f
		));
		case SCENTING -> playSound(Sound.sound(
				org.bukkit.Sound.ENTITY_SNIFFER_SCENTING,
				Sound.Source.AMBIENT,
				1.0f,
				isAdult() ? 1.0f : 1.3F
		));
		case SNIFFING -> playSound(Sound.sound(
				org.bukkit.Sound.ENTITY_SNIFFER_SNIFFING,
				Sound.Source.AMBIENT,
				1.0f,
				1.0f
		));
		case RISING -> playSound(Sound.sound(
				org.bukkit.Sound.ENTITY_SNIFFER_DIGGING_STOP,
				Sound.Source.AMBIENT,
				1.0f,
				1.0f
		));
		default ->
		{
			// No sound is emitted
		}
		}
	}

	@Override
	public @Nullable Location findPossibleDigLocation()
	{
		throw new UnimplementedOperationException("Method findPossibleDigLocation is not implemented.");
	}

	@Override
	public boolean canDig()
	{
		throw new UnimplementedOperationException("Method canDig is not implemented.");
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SNIFFER;
	}

}
