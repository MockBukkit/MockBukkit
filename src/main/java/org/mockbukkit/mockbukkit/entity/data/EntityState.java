package org.mockbukkit.mockbukkit.entity.data;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

/**
 * A state a entity is in
 */
public enum EntityState
{
	/**
	 * Default state
	 */
	DEFAULT("default"),

	/**
	 * Entity is sleeping
	 */
	SLEEPING("sleeping"),

	/**
	 * Entity is sneaking
	 */
	SNEAKING("sneaking"),

	/**
	 * Entity is semi-puffed ({@link org.bukkit.entity.PufferFish})
	 */
	SEMI_PUFFED("semi_puffed"),

	/**
	 * Entity is fully puffed ({@link org.bukkit.entity.PufferFish})
	 */
	PUFFED("puffed"),

	/**
	 * Entity is gliding, for example a player using elytra
	 */
	GLIDING("gliding"),

	/**
	 * Entity is swimming
	 */
	SWIMMING("swimming"),

	/**
	 * Entity is angry ({@link org.bukkit.entity.Enderman})
	 */
	ANGRY("angry"),

	/**
	 * Entity is peeking ({@link org.bukkit.entity.Shulker})
	 */
	PEEKING("peeking"),

	/**
	 * Entity is open ({@link org.bukkit.entity.Shulker})
	 */
	OPEN("open"),

	/**
	 * Entity is sitting ({@link org.bukkit.entity.Camel})
	 */
	SITTING("sitting");

	private final String state;

	EntityState(@NotNull String state)
	{
		Preconditions.checkNotNull(state);
		this.state = state;
	}

	/**
	 * @return The key this property is assigned to
	 */
	public @NotNull String getName()
	{
		return this.state;
	}
}
