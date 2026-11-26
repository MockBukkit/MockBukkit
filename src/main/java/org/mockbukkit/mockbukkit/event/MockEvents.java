package org.mockbukkit.mockbukkit.event;

import org.jetbrains.annotations.NotNull;

/**
 * Entry point for building the events a test needs to fire by hand.
 * <p>
 * Bukkit's damage and death events have no undeprecated constructor a test can call, so plugin suites end up
 * carrying {@code @SuppressWarnings("removal")} and rediscovering the same argument traps. These builders own that
 * so the test does not have to.
 */
public final class MockEvents
{

	private MockEvents()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	/**
	 * Starts building an {@link org.bukkit.event.entity.EntityDamageByEntityEvent}.
	 *
	 * @return A new damage event builder.
	 */
	public static @NotNull DamageEventBuilder damage()
	{
		return new DamageEventBuilder();
	}

	/**
	 * Starts building a {@link org.bukkit.event.entity.PlayerDeathEvent}.
	 *
	 * @return A new death event builder.
	 */
	public static @NotNull DeathEventBuilder death()
	{
		return new DeathEventBuilder();
	}

}
