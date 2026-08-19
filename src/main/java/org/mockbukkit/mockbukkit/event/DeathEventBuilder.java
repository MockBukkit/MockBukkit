package org.mockbukkit.mockbukkit.event;

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds a {@link PlayerDeathEvent} without the caller having to spell out six arguments, four of which are almost
 * always the same placeholder.
 * <p>
 * Obtain one from {@link MockEvents#death()}.
 */
public class DeathEventBuilder
{

	private Player victim;
	private DamageType damageType = DamageType.GENERIC;
	private List<ItemStack> drops = new ArrayList<>();
	private int droppedExp = 0;
	private @Nullable Component deathMessage;
	private boolean keepInventory = false;

	DeathEventBuilder()
	{
	}

	/**
	 * @param victim The player who died.
	 * @return This builder.
	 */
	public @NotNull DeathEventBuilder of(@NotNull Player victim)
	{
		Preconditions.checkArgument(victim != null, "Victim cannot be null");
		this.victim = victim;
		return this;
	}

	/**
	 * @param damageType What killed them. Defaults to {@link DamageType#GENERIC}.
	 * @return This builder.
	 */
	public @NotNull DeathEventBuilder type(@NotNull DamageType damageType)
	{
		Preconditions.checkArgument(damageType != null, "Damage type cannot be null");
		this.damageType = damageType;
		return this;
	}

	/**
	 * @param drops What the player drops. Defaults to nothing.
	 * @return This builder.
	 */
	public @NotNull DeathEventBuilder drops(@NotNull List<ItemStack> drops)
	{
		Preconditions.checkArgument(drops != null, "Drops cannot be null");
		this.drops = new ArrayList<>(drops);
		return this;
	}

	/**
	 * @param droppedExp How much experience drops. Defaults to zero.
	 * @return This builder.
	 */
	public @NotNull DeathEventBuilder droppedExp(int droppedExp)
	{
		Preconditions.checkArgument(droppedExp >= 0, "Dropped experience cannot be negative");
		this.droppedExp = droppedExp;
		return this;
	}

	/**
	 * @param deathMessage The message announced, or null for none.
	 * @return This builder.
	 */
	public @NotNull DeathEventBuilder deathMessage(@Nullable Component deathMessage)
	{
		this.deathMessage = deathMessage;
		return this;
	}

	/**
	 * @param keepInventory Whether the player keeps their inventory. Defaults to false.
	 * @return This builder.
	 */
	public @NotNull DeathEventBuilder keepInventory(boolean keepInventory)
	{
		this.keepInventory = keepInventory;
		return this;
	}

	/**
	 * @return The built event, not yet fired.
	 */
	public @NotNull PlayerDeathEvent build()
	{
		Preconditions.checkState(this.victim != null, "A victim is required -- call of(...)");

		DamageSource source = DamageSource.builder(this.damageType).build();
		Component message = this.deathMessage == null ? Component.empty() : this.deathMessage;

		// The trailing boolean here is doExpDrop, not keepInventory -- an easy one to get backwards, which is
		// part of why this builder exists. keepInventory has its own setter.
		PlayerDeathEvent event = new PlayerDeathEvent(this.victim, source, this.drops, this.droppedExp,
				message, true);
		event.setKeepInventory(this.keepInventory);
		return event;
	}

}
