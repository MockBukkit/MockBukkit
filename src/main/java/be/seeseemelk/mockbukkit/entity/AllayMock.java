package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Allay;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Mock implementation of an {@link Allay}.
 *
 * @see CreatureMock
 */
public class AllayMock extends CreatureMock implements Allay
{

	private final @NotNull Inventory inventory;
	private Material currentItem;
	private boolean canDuplicate = true;
	private long duplicationCoolDown = 0;
	private boolean isDancing = false;
	private Location jukebox = null;

	/**
	 * Constructs a new {@link AgeableMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public AllayMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
		this.inventory = Bukkit.createInventory(null, 9);
	}

	/**
	 * Simulates the Interaction of a Player with the Allay to set it's current item.
	 *
	 * @param material The {@link Material} of the Item the Allay should collect
	 */
	public void simulatePlayerInteract(@NotNull Material material)
	{
		Preconditions.checkNotNull(material, "Material cannot be null");
		this.currentItem = material;
	}

	/**
	 * Simulate the retrieval of the Allay's current items.
	 *
	 * @return A {@link List} of {@link ItemStack}s that the Allay is holding
	 */
	public @Nullable ItemStack simulateItemRetrieval()
	{
		ItemStack item = this.inventory.getContents()[0];
		this.inventory.clear();

		return item;
	}

	/**
	 * Simulate the Allay picking up an {@link ItemStack} from the ground.
	 * If the Itemstack is not of the current Type, this will throw a {@link IllegalArgumentException}.
	 * If the Inventory is full, this will throw a {@link IllegalStateException}.
	 *
	 * @param item The {@link ItemStack} to pick up
	 */
	public void simulateItemPickup(@NotNull ItemStack item)
	{
		Preconditions.checkNotNull(item, "ItemStack cannot be null");

		if (item.getType() != this.currentItem)
		{
			throw new IllegalArgumentException("Item is not the same type as the Allay is currently holding");
		}

		inventory.addItem(item);

		if (Arrays.stream(inventory.getContents()).filter(Objects::nonNull).count() > 1)
		{
			throw new IllegalStateException("Allay cannot hold more than 1 ItemStack");
		}
	}

	/**
	 * Asserts that the Allay uses the given {@link Material} to pick up an {@link ItemStack} from the ground.
	 *
	 * @param item The {@link Material} to pick up
	 */
	public void assertCurrentItem(@NotNull Material item)
	{
		assertCurrentItem(item, "Allay is not holding the correct item");
	}

	/**
	 * Asserts that the Allay uses the given {@link Material} to pick up an {@link ItemStack} from the ground.
	 *
	 * @param item    The {@link Material} to pick up
	 * @param message The message to display if the assertion fails
	 */
	public void assertCurrentItem(@NotNull Material item, @Nullable String message)
	{
		if (item != this.currentItem)
		{
			fail(message);
		}
	}

	@Override
	public @NotNull Inventory getInventory()
	{
		return this.inventory;
	}

	/**
	 * Asserts that the Allay's inventory contains an item.
	 *
	 * @param item The item to check.
	 */
	public void assertInventoryContains(ItemStack item)
	{
		assertInventoryContains(item, "Inventory does not contain the given ItemStack");
	}

	/**
	 * Asserts that the Allay's inventory contains an item.
	 *
	 * @param item The item to check.
	 * @param s    The message to fail with.
	 */
	public void assertInventoryContains(ItemStack item, String s)
	{
		if (!inventory.contains(item))
		{
			fail(s);
		}
	}

	@Override
	public boolean canDuplicate()
	{
		return this.canDuplicate;
	}

	@Override
	public void setCanDuplicate(boolean canDuplicate)
	{
		this.canDuplicate = canDuplicate;
	}

	@Override
	public long getDuplicationCooldown()
	{
		return this.duplicationCoolDown;
	}

	@Override
	public void setDuplicationCooldown(long cooldown)
	{
		this.duplicationCoolDown = cooldown;
	}

	@Override
	public void resetDuplicationCooldown()
	{
		this.duplicationCoolDown = 6000L;
		this.canDuplicate = false;
	}

	@Override
	public boolean isDancing()
	{
		return this.isDancing;
	}

	@Override
	public void startDancing(@NotNull Location location)
	{
		Preconditions.checkArgument(location != null, "Location cannot be null");
		Preconditions.checkArgument(location.getBlock().getType() == Material.JUKEBOX, "Location must be a Jukebox");

		this.isDancing = true;
		this.jukebox = location.clone();
	}

	@Override
	public void startDancing()
	{
		this.isDancing = true;
	}

	@Override
	public void stopDancing()
	{
		this.isDancing = false;
		this.jukebox = null;
	}

	@Override
	public @Nullable Allay duplicateAllay()
	{
		Allay allay = this.getWorld().spawn(this.getLocation(), Allay.class, null,
				CreatureSpawnEvent.SpawnReason.DUPLICATION);
		allay.resetDuplicationCooldown();
		this.resetDuplicationCooldown();

		return allay;
	}

	@Override
	public @Nullable Location getJukebox()
	{
		return this.jukebox != null ? jukebox.clone() : null;
	}

	@Override
	public EntityType getType()
	{
		return EntityType.ALLAY;
	}

}
