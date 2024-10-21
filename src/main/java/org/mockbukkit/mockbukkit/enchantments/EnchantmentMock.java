package org.mockbukkit.mockbukkit.enchantments;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.papermc.paper.enchantments.EnchantmentRarity;
import io.papermc.paper.registry.set.RegistryKeySet;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mock implementation of an {@link Enchantment}.
 */
public class EnchantmentMock extends Enchantment
{

	private static final String LEVEL = "level";
	private static final String COST = "cost";
	private static final String TREASURE_KEY = "treasure";
	private static final String CURSED_KEY = "cursed";
	private static final String MAX_LEVEL_KEY = "maxLevel";
	private static final String START_LEVEL_KEY = "startLevel";
	private static final String NAME_KEY = "name";
	private static final String DISPLAY_NAMES_KEY = "displayNames";
	private static final String MIN_MODIFIED_COSTS_KEY = "minModifiedCosts";
	private static final String MAX_MODIFIED_COSTS_KEY = "maxModifiedCosts";
	private static final String TRADEABLE_KEY = "tradeable";
	private static final String DISCOVERABLE_KEY = "discoverable";
	private static final String CONFLICTS_KEY = "conflicts";
	private static final String ENCHANTABLE_KEY = "enchantable";
	private static final String TRANSLATION_KEY = "translationKey";
	private static final String ANVIL_COST_KEY = "anvilCost";
	private static final String KEY = "key";
	private static final String ENCHANTABLES_KEY = "enchantables";

	private final @NotNull String name;
	private final NamespacedKey namespacedKey;
	private final boolean tradeable;
	private final boolean discoverable;
	private final Set<NamespacedKey> conflicts;
	private final Set<NamespacedKey> enchantables;
	private boolean treasure;
	private final Component[] displayNames;
	private final int[] maxModifiedCosts;
	private boolean cursed;
	private final int[] minModifiedCosts;
	private int maxLevel;
	private int startLevel;
	private final String translationKey;
	private final int anvilCost;

	/**
	 * @param namespacedKey   The key representing this enchantment
	 * @param treasure        Whether this enchantment can be found in a treasure
	 * @param cursed          Whether this enchantment is a curse
	 * @param maxLevel        The max level of this enchantment
	 * @param startLevel      The min level of this enchantment
	 * @param name            The name of the enchantment
	 * @param displayNames    The display name of the enchantment dependent on level
	 * @param minModifiedCost The minimal modified cost for this enchantment dependent on level
	 * @param maxModifiedCost The maximal modified cost for this enchantment dependent on level
	 * @param tradeable       Whether this enchantment can be obtained from trades
	 * @param discoverable    Whether this enchantment is in a loot table
	 * @param conflicts       Namespaced-keys of enchantments that are conflicting with this enchantment
	 * @param enchantables    Namespaced-keys of items which can be enchanted by this enchantment
	 */
	public EnchantmentMock(NamespacedKey namespacedKey, boolean treasure, boolean cursed, int maxLevel,
						   int startLevel, String name, Component[] displayNames, int[] minModifiedCost,
						   int[] maxModifiedCost, boolean tradeable, boolean discoverable,
						   Set<NamespacedKey> conflicts, Set<NamespacedKey> enchantables, String translationKey,
						   int anvilCost)
	{
		this.namespacedKey = namespacedKey;
		this.treasure = treasure;
		this.cursed = cursed;
		this.maxLevel = maxLevel;
		this.startLevel = startLevel;
		this.name = name;
		this.displayNames = displayNames;
		this.minModifiedCosts = minModifiedCost;
		this.maxModifiedCosts = maxModifiedCost;
		this.tradeable = tradeable;
		this.discoverable = discoverable;
		this.conflicts = conflicts;
		this.enchantables = enchantables;
		this.translationKey = translationKey;
		this.anvilCost = anvilCost;
	}

	/**
	 * @param data Json data
	 * @deprecated Use {@link #EnchantmentMock(NamespacedKey, boolean, boolean, int, int, String, Component[], int[],
	 * int[], boolean, boolean, Set, Set, String, int)}
	 * instead.
	 */
	@Deprecated(forRemoval = true, since = "v3.82.0")
	public EnchantmentMock(JsonObject data)
	{
		this.namespacedKey = NamespacedKey.fromString(data.get(KEY).getAsString());
		this.treasure = data.get(TREASURE_KEY).getAsBoolean();
		this.cursed = data.get(CURSED_KEY).getAsBoolean();
		this.maxLevel = data.get(MAX_LEVEL_KEY).getAsInt();
		this.startLevel = data.get(START_LEVEL_KEY).getAsInt();
		this.name = data.get(NAME_KEY).getAsString();
		this.displayNames = getDisplayNames(data.get(DISPLAY_NAMES_KEY).getAsJsonArray(), this.maxLevel);
		this.minModifiedCosts = getModifiedCosts(data.get(MIN_MODIFIED_COSTS_KEY).getAsJsonArray(), this.maxLevel);
		this.maxModifiedCosts = getModifiedCosts(data.get(MAX_MODIFIED_COSTS_KEY).getAsJsonArray(), this.maxLevel);
		this.tradeable = data.get(TRADEABLE_KEY).getAsBoolean();
		this.discoverable = data.get(DISCOVERABLE_KEY).getAsBoolean();
		this.conflicts = getConflicts(data.get(CONFLICTS_KEY).getAsJsonArray());
		this.enchantables = getEnchantables(data.get(ENCHANTABLE_KEY).getAsJsonArray());
		this.translationKey = data.get(TRANSLATION_KEY).getAsString();
		this.anvilCost = data.get(ANVIL_COST_KEY).getAsInt();
	}

	@Override
	public @NotNull Component displayName(int level)
	{
		return displayNames[level - 1];
	}

	@Override
	public boolean isTradeable()
	{
		return this.tradeable;
	}

	@Override
	public boolean isDiscoverable()
	{
		return this.discoverable;
	}

	@Override
	public int getMaxModifiedCost(int level)
	{
		return maxModifiedCosts[level - 1];
	}

	@Override
	public int getAnvilCost()
	{
		return anvilCost;
	}

	@Override
	public int getMinModifiedCost(int level)
	{
		return minModifiedCosts[level - 1];
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.5")
	public @NotNull EnchantmentRarity getRarity()
	{
		throw new UnsupportedOperationException("Enchantments don't have a rarity anymore in 1.20.5+.");
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.5")
	public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.21")
	public float getDamageIncrease(int level, @NotNull EntityType entityType)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.21")
	public @NotNull Set<EquipmentSlot> getActiveSlots()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Set<EquipmentSlotGroup> getActiveSlotGroups()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Component description()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull RegistryKeySet<ItemType> getSupportedItems()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable RegistryKeySet<ItemType> getPrimaryItems()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getWeight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull RegistryKeySet<Enchantment> getExclusiveWith()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true) //Mark as deprecated as this method assumes that the enchantments description always
	// be a translatable component which is not guaranteed.
	public @NotNull String translationKey()
	{
		return translationKey;
	}

	@Override
	public @NotNull String getName()
	{
		return name;
	}

	@Override
	public int getMaxLevel()
	{
		return this.maxLevel;
	}

	/**
	 * Sets the return value of {@link #getMaxLevel()}.
	 *
	 * @param maxLevel The max level.
	 * @see #getMaxLevel()
	 */
	public void setMaxLevel(int maxLevel)
	{
		this.maxLevel = maxLevel;
	}

	@Override
	public int getStartLevel()
	{
		return this.startLevel;
	}

	/**
	 * Sets the return value of {@link #getStartLevel()}.
	 *
	 * @param startLevel The start level.
	 * @see #getStartLevel()
	 */
	public void setStartLevel(int startLevel)
	{
		this.startLevel = startLevel;
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.5")
	public @NotNull EnchantmentTarget getItemTarget()
	{
		throw new UnsupportedOperationException("Method no longer applicable. Use Tags instead.");
	}

	/**
	 * Sets the return value of {@link #getItemTarget()}.
	 *
	 * @param itemTarget The item target.
	 * @see #getItemTarget()
	 */
	@Deprecated(forRemoval = true)
	public void setItemTarget(@NotNull EnchantmentTarget itemTarget)
	{
		throw new UnsupportedOperationException("Method no longer applicable. Use Tags instead.");
	}

	@Override
	public boolean isTreasure()
	{
		return this.treasure;
	}

	/**
	 * Sets the return value of {@link #isTreasure()}.
	 *
	 * @param isTreasure Whether the enchantment is treasure.
	 * @see #isTreasure()
	 */
	public void setTreasure(boolean isTreasure)
	{
		this.treasure = isTreasure;
	}

	@Override
	public boolean isCursed()
	{
		return this.cursed;
	}

	/**
	 * Sets the return value of {@link #isCursed()}.
	 *
	 * @param isCursed Whether the enchantment is cursed.
	 * @see #isCursed()
	 */
	public void setCursed(boolean isCursed)
	{
		this.cursed = isCursed;
	}

	@Override
	public boolean conflictsWith(@NotNull Enchantment other)
	{
		Preconditions.checkArgument(other != null, "others can't be null");
		return conflicts.contains(other.getKey());
	}

	@Override
	public boolean canEnchantItem(@NotNull ItemStack item)
	{
		Preconditions.checkNotNull(item, "Item cannot be null");
		NamespacedKey namespacedKey = item.getType().getKey();
		return enchantables.contains(namespacedKey);
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return this.namespacedKey;
	}

	@Override
	@Deprecated(forRemoval = true)
	public @NotNull String getTranslationKey()
	{
		return translationKey.toString();
	}

	@ApiStatus.Internal
	public static EnchantmentMock from(JsonObject data)
	{
		Preconditions.checkNotNull(data);
		List<String> expectedArguments = List.of(KEY, TREASURE_KEY, CURSED_KEY, MAX_LEVEL_KEY, START_LEVEL_KEY,
				NAME_KEY, DISPLAY_NAMES_KEY, MIN_MODIFIED_COSTS_KEY, MAX_MODIFIED_COSTS_KEY, TRADEABLE_KEY, DISCOVERABLE_KEY,
				CONFLICTS_KEY, ENCHANTABLES_KEY);
		expectedArguments.forEach(expectedKey ->
				Preconditions.checkArgument(data.has(expectedKey), "Missing json key: " + expectedKey));

		NamespacedKey key = NamespacedKey.fromString(data.get(KEY).getAsString());
		boolean treasure = data.get(TREASURE_KEY).getAsBoolean();
		boolean cursed = data.get(CURSED_KEY).getAsBoolean();
		int maxLevel = data.get(MAX_LEVEL_KEY).getAsInt();
		int startLevel = data.get(START_LEVEL_KEY).getAsInt();
		String name = data.get(NAME_KEY).getAsString();
		Component[] displayNames = getDisplayNames(data.get(DISPLAY_NAMES_KEY).getAsJsonArray(), maxLevel);
		int[] minModifiedCosts = getModifiedCosts(data.get(MIN_MODIFIED_COSTS_KEY).getAsJsonArray(), maxLevel);
		int[] maxModifiedCosts = getModifiedCosts(data.get(MAX_MODIFIED_COSTS_KEY).getAsJsonArray(), maxLevel);
		boolean tradeable = data.get(TRADEABLE_KEY).getAsBoolean();
		boolean discoverable = data.get(DISCOVERABLE_KEY).getAsBoolean();
		Set<NamespacedKey> conflicts = getConflicts(data.get(CONFLICTS_KEY).getAsJsonArray());
		Set<NamespacedKey> enchantables = getEnchantables(data.get(ENCHANTABLES_KEY).getAsJsonArray());
		String translationKey = data.get(TRANSLATION_KEY).getAsString();
		int anvilCost = data.get(ANVIL_COST_KEY).getAsInt();

		return new EnchantmentMock(key, treasure, cursed, maxLevel, startLevel, name, displayNames, minModifiedCosts,
				maxModifiedCosts, tradeable, discoverable, conflicts, enchantables, translationKey, anvilCost);
	}

	private static Set<NamespacedKey> getConflicts(JsonArray conflicts)
	{
		Set<NamespacedKey> output = new HashSet<>();
		for (JsonElement conflict : conflicts)
		{
			output.add(NamespacedKey.fromString(conflict.getAsString()));
		}
		return output;
	}

	private static Set<NamespacedKey> getEnchantables(JsonArray enchantables)
	{
		Set<NamespacedKey> output = new HashSet<>();
		for (JsonElement enchantable : enchantables)
		{
			output.add(NamespacedKey.fromString(enchantable.getAsString()));
		}
		return output;
	}

	private static Component[] getDisplayNames(JsonArray displayNamesData, int maxLevel)
	{
		Component[] output = new Component[maxLevel];
		for (JsonElement element : displayNamesData)
		{
			JsonObject displayNameData = element.getAsJsonObject();
			int level = displayNameData.get(LEVEL).getAsInt();
			GsonComponentSerializer gsonComponentSerializer = GsonComponentSerializer.builder().build();
			output[level - 1] = gsonComponentSerializer.deserializeFromTree(displayNameData.get("text"));
		}
		return output;
	}

	private static int[] getModifiedCosts(JsonArray minModifiedCosts, int maxLevel)
	{
		int[] output = new int[maxLevel];
		for (JsonElement element : minModifiedCosts)
		{
			JsonObject minModifiedCost = element.getAsJsonObject();
			int level = minModifiedCost.get(LEVEL).getAsInt();
			output[level - 1] = minModifiedCost.get(COST).getAsInt();
		}
		return output;
	}

}
