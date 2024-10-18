package org.mockbukkit.mockbukkit.simulate.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.GameEvent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.world.GenericGameEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

public class PlayerSimulation
{

	private final PlayerMock playerMock;

	public PlayerSimulation(PlayerMock playerMock)
	{
		this.playerMock = playerMock;
	}

	/**
	 * Simulates a Player consuming an Edible Item
	 *
	 * @param consumable The Item to consume
	 * @return <p>The consumed item</p>
	 */
	public @NotNull ItemStack simulateConsumeItem(@NotNull ItemStack consumable)
	{
		Preconditions.checkNotNull(consumable, "Consumed Item can't be null");
		Preconditions.checkArgument(consumable.getType().isEdible(), "Item is not Consumable");

		//Since we have no Bukkit way of differentiating between drinks and food, here is a rough estimation of
		//how it would sound like

		//Drinks:Slurp Slurp Slurp
		//Food: Yum Yum Yum

		GenericGameEvent consumeStartEvent =
				new GenericGameEvent(
						GameEvent.ITEM_INTERACT_START,
						playerMock.getLocation(),
						playerMock,
						16,
						!Bukkit.isPrimaryThread());

		Bukkit.getPluginManager().callEvent(consumeStartEvent);

		PlayerItemConsumeEvent event = new PlayerItemConsumeEvent(playerMock, consumable);
		Bukkit.getPluginManager().callEvent(event);

		if (event.isCancelled())
		{
			GenericGameEvent stopConsumeEvent =
					new GenericGameEvent(
							GameEvent.ITEM_INTERACT_FINISH,
							playerMock.getLocation(),
							playerMock,
							16,
							!Bukkit.isPrimaryThread());
			Bukkit.getPluginManager().callEvent(stopConsumeEvent);
		}
		return consumable;
	}

	/**
	 * Simulates the player damaging a block just like {@link #simulateBlockDamage(Block)}. However, if
	 * {@code InstaBreak} is enabled, it will not automatically fire a {@link BlockBreakEvent}. It will also still fire
	 * a {@link BlockDamageEvent} even if the player is not in survival mode.
	 *
	 * @param block The block to damage.
	 * @return The event that has been fired.
	 */
	private @NotNull BlockDamageEvent simulateBlockDamagePure(@NotNull Block block)
	{
		Preconditions.checkNotNull(block, "Block cannot be null");
		BlockDamageEvent event = new BlockDamageEvent(playerMock, block, playerMock.getItemInHand(), false);
		Bukkit.getPluginManager().callEvent(event);
		return event;
	}

	/**
	 * Simulates the player damaging a block. Note that this method does not anything unless the player is in survival
	 * mode. If {@code InstaBreak} is set to true by an event handler, a {@link BlockBreakEvent} is immediately fired.
	 * The result will then still be whether the {@link BlockDamageEvent} was cancelled or not, not the later
	 * {@link BlockBreakEvent}.
	 *
	 * @param block The block to damage.
	 * @return the event that was fired, {@code null} if the player was not in
	 * survival game mode.
	 */
	public @Nullable BlockDamageEvent simulateBlockDamage(@NotNull Block block)
	{
		Preconditions.checkNotNull(block, "Block cannot be null");
		if (playerMock.getGameMode() != GameMode.SURVIVAL)
		{
			return null;
		}

		BlockDamageEvent event = simulateBlockDamagePure(block);
		if (event.getInstaBreak())
		{
			BlockBreakEvent breakEvent = new BlockBreakEvent(block, playerMock);
			Bukkit.getPluginManager().callEvent(breakEvent);
			if (!breakEvent.isCancelled())
				block.setType(Material.AIR);
		}

		return event;
	}

	/**
	 * Simulates the player breaking a block. This method will not break the block if the player is in adventure or
	 * spectator mode. If the player is in survival mode, the player will first damage the block.
	 *
	 * @param block The block to break.
	 * @return The event that was fired, {@code null} if it wasn't or if the player was in adventure mode
	 * or in spectator mode.
	 */
	public @Nullable BlockBreakEvent simulateBlockBreak(@NotNull Block block)
	{
		Preconditions.checkNotNull(block, "Block cannot be null");
		if ((playerMock.getGameMode() == GameMode.SPECTATOR || playerMock.getGameMode() == GameMode.ADVENTURE)
				|| (playerMock.getGameMode() == GameMode.SURVIVAL && simulateBlockDamagePure(block).isCancelled()))
			return null;

		BlockBreakEvent event = new BlockBreakEvent(block, playerMock);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
			block.setType(Material.AIR);
		return event;
	}

	/**
	 * Simulates the player placing a block. This method will not place the block if the player is in adventure or
	 * spectator mode.
	 *
	 * @param material The material of the location to set to
	 * @param location The location of the material to set to
	 * @return The event that was fired. {@code null} if it wasn't or the player was in adventure
	 * mode.
	 */
	public @Nullable BlockPlaceEvent simulateBlockPlace(@NotNull Material material, @NotNull Location location)
	{
		Preconditions.checkNotNull(material, "Material cannot be null");
		Preconditions.checkNotNull(location, "Location cannot be null");
		if (playerMock.getGameMode() == GameMode.ADVENTURE || playerMock.getGameMode() == GameMode.SPECTATOR)
			return null;
		Block block = location.getBlock();
		BlockState blockState = block.getState();
		block.setType(material);
		BlockPlaceEvent event = new BlockPlaceEvent(block, blockState, null, playerMock.getItemInHand(),
				playerMock, true, EquipmentSlot.HAND);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled() || !event.canBuild())
		{
			blockState.update(true, false);
		}
		return event;
	}

	/**
	 * Simulates the player clicking an Inventory.
	 *
	 * @param slot The slot in the player's open inventory
	 * @return The event that was fired.
	 */
	public @NotNull InventoryClickEvent simulateInventoryClick(int slot)
	{
		return simulateInventoryClick(playerMock.getOpenInventory(), slot);
	}

	/**
	 * Simulates the player clicking an Inventory.
	 *
	 * @param inventoryView The inventory view we want to click
	 * @param slot          The slot in the provided Inventory
	 * @return The event that was fired.
	 */
	public @NotNull InventoryClickEvent simulateInventoryClick(@NotNull InventoryView inventoryView, int slot)
	{
		return simulateInventoryClick(inventoryView, ClickType.LEFT, slot);
	}

	/**
	 * Simulates the player clicking an Inventory.
	 *
	 * @param inventoryView The inventory view we want to click
	 * @param clickType     The click type we want to fire
	 * @param slot          The slot in the provided Inventory
	 * @return The event that was fired.
	 */
	public @NotNull InventoryClickEvent simulateInventoryClick(@NotNull InventoryView inventoryView, @NotNull ClickType clickType, int slot)
	{
		Preconditions.checkNotNull(inventoryView, "InventoryView cannot be null");
		InventoryClickEvent inventoryClickEvent = new InventoryClickEvent(inventoryView, InventoryType.SlotType.CONTAINER, slot, clickType, InventoryAction.UNKNOWN);
		Bukkit.getPluginManager().callEvent(inventoryClickEvent);
		return inventoryClickEvent;
	}

	/**
	 * This method simulates the {@link Player} respawning and also calls a {@link PlayerRespawnEvent}. Should the
	 * {@link Player} not be dead (when {@link Player#isDead()} returns false) then this will throw an
	 * {@link UnsupportedOperationException}. Otherwise, the {@link Location} will be set to
	 * {@link Player#getBedSpawnLocation()} or {@link World#getSpawnLocation()}. Lastly the health of this
	 * {@link Player} will be restored and set to the max health.
	 *
	 * @return <p>The respawn event</p>
	 */
	public PlayerRespawnEvent respawn()
	{
		return playerMock.respawn();
	}

	/**
	 * This method moves player instantly with respect to PlayerMoveEvent
	 *
	 * @param moveLocation Location to move player to
	 * @return The event that is fired
	 */
	public @NotNull PlayerMoveEvent simulatePlayerMove(@NotNull Location moveLocation)
	{
		Preconditions.checkNotNull(moveLocation, "Location cannot be null");
		PlayerMoveEvent event = new PlayerMoveEvent(playerMock, playerMock.getLocation(), moveLocation);
		playerMock.setLocation(event.getTo());
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled())
		{
			playerMock.setLocation(event.getFrom());
		}
		return event;
	}

	/**
	 * Simulates sneaking.
	 *
	 * @param sneak Whether the player is beginning to sneak.
	 * @return The event.
	 */
	public @NotNull PlayerToggleSneakEvent simulateSneak(boolean sneak)
	{
		PlayerToggleSneakEvent event = new PlayerToggleSneakEvent(playerMock, sneak);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			playerMock.setSneaking(event.isSneaking());
		}
		return event;
	}

	/**
	 * Simulates sprinting.
	 *
	 * @param sprint Whether the player is beginning to sprint.
	 * @return The event.
	 */
	public @NotNull PlayerToggleSprintEvent simulateSprint(boolean sprint)
	{
		PlayerToggleSprintEvent event = new PlayerToggleSprintEvent(playerMock, sprint);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			playerMock.setSprinting(event.isSprinting());
		}
		return event;
	}

	/**
	 * Simulates toggling flight.
	 *
	 * @param fly Whether the player is starting to fly.
	 * @return The event.
	 */
	public @NotNull PlayerToggleFlightEvent simulateToggleFlight(boolean fly)
	{
		PlayerToggleFlightEvent event = new PlayerToggleFlightEvent(playerMock, fly);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			playerMock.setFlying(event.isFlying());
		}
		return event;
	}


}
