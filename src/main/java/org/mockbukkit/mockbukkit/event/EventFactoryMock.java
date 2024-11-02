package org.mockbukkit.mockbukkit.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EventFactoryMock
{

	/**
	 * Call a {@link EntityRemoveEvent}.
	 *
	 * @param entity The entity being removed.
	 * @param cause	 The cause for removal.
	 */
	public static void callEntityRemoveEvent(@NotNull Entity entity, @Nullable EntityRemoveEvent.Cause cause)
	{
		if (entity instanceof Player)
		{
			return; // Don't call for player
		}

		if (cause == null)
		{
			// Don't call if cause is null
			// This can happen when an entity changes dimension,
			// the entity gets removed during world gen or
			// the entity is removed before it is even spawned (when the spawn event is cancelled for example)
			return;
		}

		Bukkit.getPluginManager().callEvent(new EntityRemoveEvent(entity, cause));
	}

	private EventFactoryMock()
	{
		// Hide the public constructor
	}

}
