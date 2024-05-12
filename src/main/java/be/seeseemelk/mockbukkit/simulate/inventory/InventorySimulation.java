package be.seeseemelk.mockbukkit.simulate.inventory;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class InventorySimulation
{

	private final @NotNull PlayerMock player;

	public InventorySimulation(@NotNull PlayerMock player)
	{
		this.player = player;
	}

	/**
	 * Simulates the player clicking an Inventory.
	 *
	 * @param slot The slot in the provided Inventory
	 * @return The event that was fired.
	 */
	public @NotNull InventoryClickEvent simulateInventoryClick(int slot)
	{
		return simulateInventoryClick(ClickType.LEFT, slot);
	}

	/**
	 * Simulates the player clicking an Inventory.
	 *
	 * @param clickType The click type we want to fire
	 * @param slot      The slot in the provided Inventory
	 * @return The event that was fired.
	 */
	public @NotNull InventoryClickEvent simulateInventoryClick(@NotNull ClickType clickType, int slot)
	{
		return player.simulateInventoryClick(player.getOpenInventory(), clickType, slot);
	}

}
