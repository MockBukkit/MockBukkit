package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import org.bukkit.Sound;
import org.bukkit.entity.Pillager;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Pillager}.
 *
 * @see IllagerMock
 */
public class PillagerMock extends IllagerMock implements Pillager, MockRangedEntity<PillagerMock>
{

	private final Inventory inventory = new InventoryMock(this, 5, InventoryType.CHEST);

	/**
	 * Constructs a new {@link PillagerMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public PillagerMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull Sound getCelebrationSound()
	{
		return Sound.ENTITY_PILLAGER_CELEBRATE;
	}

	@Override
	public @NotNull Inventory getInventory()
	{
		return this.inventory;
	}

}
