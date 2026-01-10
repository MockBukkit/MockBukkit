package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Villager;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.AsyncCatcher;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.EnderChestInventoryMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.mockbukkit.mockbukkit.inventory.PlayerInventoryMock;
import org.mockbukkit.mockbukkit.inventory.PlayerInventoryViewMock;
import org.mockbukkit.mockbukkit.inventory.SimpleInventoryViewMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Mock implementation of a {@link HumanEntity}.
 *
 * @see LivingEntityMock
 * @see PlayerMock
 */
public abstract class HumanEntityMock extends LivingEntityMock implements HumanEntity
{

	private final Set<NamespacedKey> discoveredRecipes = new HashSet<>();
	private final PlayerInventoryMock inventory = new PlayerInventoryMock(this);
	private final EnderChestInventoryMock enderChest = new EnderChestInventoryMock(this);
	private final Map<Material, Integer> cooldowns = new HashMap<>();
	private InventoryView inventoryView;
	private @NotNull ItemStack cursor = ItemStack.empty();
	private @NotNull GameMode gameMode = GameMode.SURVIVAL;
	private @NotNull MainHand mainHand = MainHand.RIGHT;
	private @Nullable Location lastDeathLocation = new Location(new WorldMock(), 0, 0, 0);
	private @Nullable FishHook fishHook;
	/**
	 * How much EXP this {@link HumanEntity} has.
	 */
	protected int expLevel = 0;
	private float exhaustion = 0.0F;
	private float saturation = 5.0F;
	private int foodLevel = 20;
	protected boolean blocking;
	private int sleepTicks = 0;
	private int saturatedRegenRate = 10;
	private int unsaturatedRegenRate = 80;
	private int starvationRate = 80;
	private int enchantmentSeed = 0;

	/**
	 * Constructs a new {@link HumanEntityMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected HumanEntityMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	/**
	 * Assert that the player is in a specific gamemode.
	 *
	 * @param expectedGamemode The gamemode the player should be in.
	 */
	@Deprecated(forRemoval = true)
	public void assertGameMode(GameMode expectedGamemode)
	{
		assertEquals(expectedGamemode, gameMode);
	}

	@Override
	public @NotNull PlayerInventory getInventory()
	{
		return this.inventory;
	}

	@Override
	public @NotNull Inventory getEnderChest()
	{
		return this.enderChest;
	}

	@Override
	public @NotNull MainHand getMainHand()
	{
		return this.mainHand;
	}

	/**
	 * Sets the player's selected main hand
	 *
	 * @param mainHand the players main hand
	 */
	public void setMainHand(@NotNull MainHand mainHand)
	{
		Preconditions.checkArgument(mainHand != null, "main hand cannot be null");
		this.mainHand = mainHand;
	}

	@Override
	public void closeInventory()
	{
		closeInventory(InventoryCloseEvent.Reason.PLUGIN);
	}

	@Override
	public void closeInventory(InventoryCloseEvent.@NotNull Reason reason)
	{
		if (inventoryView instanceof PlayerInventoryViewMock)
		{
			new InventoryCloseEvent(inventoryView, reason).callEvent();

			if (inventoryView.getTopInventory() instanceof InventoryMock inventoryMock)
			{
				inventoryMock.removeViewer(this);
			}
		}

		// reset the cursor as it is a new InventoryView
		this.cursor = null;
		inventoryView = new SimpleInventoryViewMock(this, null, inventory, InventoryType.CRAFTING);
	}

	@Override
	public @NotNull InventoryView getOpenInventory()
	{
		return this.inventoryView;
	}

	@Override
	public void openInventory(@NotNull InventoryView inventory)
	{
		Preconditions.checkNotNull(inventory, "Inventory cannot be null");
		closeInventory();
		if (!new InventoryOpenEvent(inventory).callEvent())
		{
			return;
		}
		this.inventoryView = inventory;
	}

	@Override
	public InventoryView openInventory(@NotNull Inventory inventory)
	{
		AsyncCatcher.catchOp("open inventory");
		Preconditions.checkNotNull(inventory, "Inventory cannot be null");
		InventoryView prev = this.inventoryView;
		closeInventory();
		InventoryView newView = new PlayerInventoryViewMock(this, inventory);
		if (new InventoryOpenEvent(newView).callEvent())
		{
			if (inventory instanceof InventoryMock inventoryMock)
			{
				inventoryMock.addViewers(this);
			}
			this.inventoryView = newView;
		}

		return this.inventoryView == prev ? null : this.inventoryView;
	}

	@Override
	public @NotNull ItemStack getItemOnCursor()
	{
		return cursor == null ? new ItemStackMock(Material.AIR, 0) : cursor.clone();
	}

	@Override
	public void setItemOnCursor(@Nullable ItemStack item)
	{
		this.cursor = item == null ? ItemStackMock.empty() : item.clone();
	}

	@Override
	public @Nullable Location getLastDeathLocation()
	{
		return lastDeathLocation;
	}

	@Override
	public void setLastDeathLocation(@Nullable Location location)
	{
		this.lastDeathLocation = location;
	}

	@Override
	public @Nullable Firework fireworkBoost(@NotNull ItemStack fireworkItemStack)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull GameMode getGameMode()
	{
		return this.gameMode;
	}

	@Override
	public void setGameMode(@NotNull GameMode mode)
	{
		Preconditions.checkNotNull(mode, "GameMode cannot be null");
		if (this.gameMode == mode)
		{
			return;
		}

		this.gameMode = mode;
	}

	@Override
	public boolean setWindowProperty(@NotNull InventoryView.Property prop, int value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getEnchantmentSeed()
	{
		return this.enchantmentSeed;
	}

	@Override
	public void setEnchantmentSeed(int seed)
	{
		this.enchantmentSeed = seed;
	}

	@Override
	public InventoryView openWorkbench(Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public InventoryView openEnchanting(Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public InventoryView openMerchant(@NotNull Villager trader, boolean force)
	{
		Preconditions.checkNotNull(trader, "Trader cannot be null");
		return openMerchant((Merchant) trader, force);
	}

	@Override
	public InventoryView openMerchant(@NotNull Merchant merchant, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable InventoryView openAnvil(@Nullable Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable InventoryView openCartographyTable(@Nullable Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable InventoryView openGrindstone(@Nullable Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable InventoryView openLoom(@Nullable Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable InventoryView openSmithingTable(@Nullable Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable InventoryView openStonecutter(@Nullable Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack getItemInHand()
	{
		return getInventory().getItemInMainHand();
	}

	@Override
	public void setItemInHand(@Nullable ItemStack item)
	{
		getInventory().setItemInMainHand(item);
	}

	@Override
	public boolean hasCooldown(@NotNull Material material)
	{
		Preconditions.checkArgument(material != null, "Material cannot be null");
		Preconditions.checkArgument(material.isItem(), "Material %s is not an item", material);

		return this.hasCooldown(ItemStack.of(material));
	}

	@Override
	public int getCooldown(@NotNull Material material)
	{
		Preconditions.checkArgument(material != null, "Material cannot be null");
		Preconditions.checkArgument(material.isItem(), "Material %s is not an item", material);

		return this.getCooldown(ItemStack.of(material));
	}

	@Override
	public void setCooldown(@NotNull Material material, int ticks)
	{
		Preconditions.checkArgument(material != null, "Material cannot be null");
		Preconditions.checkArgument(material.isItem(), "Material %s is not an item", material);

		this.setCooldown(ItemStack.of(material), ticks);
	}

	@Override
	public boolean hasCooldown(@NotNull ItemStack itemStack)
	{
		Preconditions.checkArgument(itemStack != null, "ItemStack cannot be null");

		Material material = itemStack.getType();
		Integer cooldown = this.cooldowns.get(material);
		return cooldown != null && cooldown > 0;
	}

	@Override
	public int getCooldown(@NotNull ItemStack itemStack)
	{
		Preconditions.checkArgument(itemStack != null, "ItemStack cannot be null");

		Material material = itemStack.getType();
		return this.cooldowns.getOrDefault(material, 0);
	}

	@Override
	public void setCooldown(@NotNull ItemStack itemStack, int ticks)
	{
		Preconditions.checkArgument(itemStack != null, "ItemStack cannot be null");
		Preconditions.checkArgument(ticks >= 0, "Cooldown ticks must be greater than or equal to 0");

		Material material = itemStack.getType();
		if (ticks <= 0)
		{
			this.cooldowns.remove(material);
		}
		else
		{
			this.cooldowns.put(material, ticks);
		}
	}

	@Override
	public boolean isDeeplySleeping()
	{
		return isSleeping() && getSleepTicks() > 100;
	}

	public void setSleepTicks(int sleepTicks)
	{
		this.sleepTicks = sleepTicks;
	}

	@Override
	public int getSleepTicks()
	{
		return this.sleepTicks;
	}

	@Override
	public @Nullable Location getPotentialBedLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable FishHook getFishHook()
	{
		return this.fishHook;
	}

	/**
	 * Sets the player's fishing hook when fishing.
	 *
	 * @param fishHook the player's fishing hook if they are fishing
	 */
	public void setFishHook(@Nullable FishHook fishHook)
	{
		this.fishHook = fishHook;
	}

	@Override
	public boolean sleep(@NotNull Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void wakeup(boolean setSpawnLocation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void startRiptideAttack(int duration, float damage, @Nullable ItemStack itemStack)
	{
		Preconditions.checkArgument(duration > 0, "Duration must be greater than 0");
		Preconditions.checkArgument(damage >= 0, "Damage must not be negative");

		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Location getBedLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isBlocking()
	{
		return blocking;
	}

	/**
	 * Set whether this entity is blocking. If the entity is not holding a shield, this will be ignored.
	 *
	 * @param blocking If this entity is blocking
	 */
	public void setBlocking(boolean blocking)
	{
		if (blocking)
		{
			ItemStack offHand = getInventory().getItemInOffHand();
			ItemStack mainHand = getInventory().getItemInMainHand();
			if (offHand.getType() != Material.SHIELD && mainHand.getType() != Material.SHIELD)
			{
				this.blocking = false;
				return;
			}
		}
		this.blocking = blocking;
	}

	@Override
	public int getExpToLevel()
	{
		// Formula from https://minecraft.wiki/w/Experience#Leveling_up
		if (this.expLevel >= 31)
		{
			return (9 * this.expLevel) - 158;
		}
		if (this.expLevel >= 16)
		{
			return (5 * this.expLevel) - 38;
		}
		return (2 * this.expLevel) + 7;
	}

	@Override
	public @Nullable Entity releaseLeftShoulderEntity()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Entity releaseRightShoulderEntity()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getAttackCooldown()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean discoverRecipe(@NotNull NamespacedKey recipe)
	{
		Preconditions.checkNotNull(recipe, "Recipe cannot be null");
		return discoverRecipes(Collections.singletonList(recipe)) != 0;
	}

	@Override
	public int discoverRecipes(@NotNull Collection<NamespacedKey> recipes)
	{
		Preconditions.checkArgument(recipes != null, "Recipes cannot be null");
		AtomicInteger count = new AtomicInteger();
		recipes.forEach(recipe ->
		{
			if (server.getRecipe(recipe) == null)
			{
				// The requested recipe does not exist.
				return;
			}

			if (this.discoveredRecipes.add(recipe))
			{
				count.incrementAndGet();
			}
		});
		return count.get();
	}

	@Override
	public boolean undiscoverRecipe(@NotNull NamespacedKey recipe)
	{
		Preconditions.checkNotNull(recipe, "Recipe cannot be null");
		return undiscoverRecipes(Collections.singletonList(recipe)) != 0;
	}

	@Override
	public int undiscoverRecipes(@NotNull Collection<NamespacedKey> recipes)
	{
		Preconditions.checkArgument(recipes != null, "Recipes cannot be null");

		AtomicInteger count = new AtomicInteger();
		recipes.forEach(recipe ->
		{
			if (this.discoveredRecipes.remove(recipe))
			{
				count.incrementAndGet();
			}
		});
		return count.get();
	}

	@Override
	public boolean hasDiscoveredRecipe(@NotNull NamespacedKey recipe)
	{
		Preconditions.checkArgument(recipe != null, "recipe cannot be null");
		return this.discoveredRecipes.contains(recipe);
	}

	@Override
	public @NotNull Set<NamespacedKey> getDiscoveredRecipes()
	{
		ImmutableSet.Builder<NamespacedKey> builder = ImmutableSet.builder();
		builder.addAll(this.discoveredRecipes);
		return builder.build();
	}

	@Override
	public Entity getShoulderEntityLeft()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setShoulderEntityLeft(Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Entity getShoulderEntityRight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setShoulderEntityRight(Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true)
	public void openSign(@NotNull Sign sign)
	{
		this.openSign(sign, org.bukkit.block.sign.Side.FRONT);
	}

	@Override
	public void openSign(@NotNull Sign sign, @NotNull Side side)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean dropItem(boolean dropAll)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Item dropItem(int slot, int amount, boolean throwRandomly, @Nullable Consumer<Item> entityOperation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Item dropItem(@NotNull EquipmentSlot slot, int amount, boolean throwRandomly, @Nullable Consumer<Item> entityOperation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Item dropItem(@NotNull ItemStack itemStack, boolean throwRandomly, @Nullable Consumer<Item> entityOperation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getExhaustion()
	{
		return this.exhaustion;
	}

	@Override
	public void setExhaustion(float value)
	{
		this.exhaustion = value;
	}

	@Override
	public float getSaturation()
	{
		return this.saturation;
	}

	@Override
	public void setSaturation(float value)
	{
		// Saturation is constrained by the current food level
		this.saturation = Math.min(getFoodLevel(), value);
	}

	@Override
	public int getFoodLevel()
	{
		return this.foodLevel;
	}

	@Override
	public void setFoodLevel(int foodLevel)
	{
		this.foodLevel = foodLevel;
	}

	@Override
	public int getSaturatedRegenRate()
	{
		return this.saturatedRegenRate;
	}

	@Override
	public void setSaturatedRegenRate(int ticks)
	{
		this.saturatedRegenRate = ticks;
	}

	@Override
	public int getUnsaturatedRegenRate()
	{
		return this.unsaturatedRegenRate;
	}

	@Override
	public void setUnsaturatedRegenRate(int ticks)
	{
		this.unsaturatedRegenRate = ticks;
	}

	@Override
	public int getStarvationRate()
	{
		return this.starvationRate;
	}

	@Override
	public void setStarvationRate(int ticks)
	{
		this.starvationRate = ticks;
	}

	@Override
	public @Nullable Location getPotentialRespawnLocation()
	{
		// TODO: Auto generated stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void tick()
	{
		super.tick();

		// Decrease all cooldowns by 1 tick
		this.cooldowns.entrySet().removeIf(entry ->
		{
			entry.setValue(entry.getValue() - 1);
			return entry.getValue() <= 0;
		});
	}

}
