package be.seeseemelk.mockbukkit.enchantments;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

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

	private final @NotNull String name;
	private final NamespacedKey key;
	private final boolean tradeable;
	private final boolean discoverable;
	private final EnchantmentRarity rarity;
	private final Set<NamespacedKey> conflicts;
	private boolean treasure;
	private final Component[] displayNames;
	private final int[] maxModifiedCosts;
	private boolean cursed;
	private final int[] minModifiedCosts;
	private int maxLevel;
	private int startLevel;
	private EnchantmentTarget itemTarget;

	/**
	 * @param key             The key representing this enchantment
	 * @param target          the item targets of this enchantment
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
	 * @param rarity          The rarity of this enchantment
	 * @param conflicts       Namespaced-keys of enchantments that are conflicting with this enchantment
	 */
	public EnchantmentMock(NamespacedKey key, EnchantmentTarget target, boolean treasure, boolean cursed, int maxLevel,
			int startLevel, String name, Component[] displayNames, int[] minModifiedCost, int[] maxModifiedCost,
			boolean tradeable, boolean discoverable, EnchantmentRarity rarity, Set<NamespacedKey> conflicts)
	{
		this.key = key;
		this.itemTarget = target;
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
		this.rarity = rarity;
		this.conflicts = conflicts;
	}

	/**
	 * @param data Json data
	 * @deprecated Use {@link #EnchantmentMock(NamespacedKey, EnchantmentTarget, boolean, boolean, int, int, String, Component[], int[], int[], boolean, boolean, EnchantmentRarity, Set)}
	 * instead.
	 */
	@Deprecated(forRemoval = true)
	public EnchantmentMock(JsonObject data)
	{
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
		this.itemTarget = EnchantmentTarget.valueOf(data.get("itemTarget").getAsString());
		this.treasure = data.get("treasure").getAsBoolean();
		this.cursed = data.get("cursed").getAsBoolean();
		this.maxLevel = data.get("maxLevel").getAsInt();
		this.startLevel = data.get("startLevel").getAsInt();
		this.name = data.get("name").getAsString();
		this.displayNames = getDisplayNames(data.get("displayNames").getAsJsonArray(), this.maxLevel);
		this.minModifiedCosts = getMinModifiedCosts(data.get("minModifiedCosts").getAsJsonArray(), this.maxLevel);
		this.maxModifiedCosts = getMaxModifiedCosts(data.get("maxModifiedCosts").getAsJsonArray(), this.maxLevel);
		this.tradeable = data.get("tradeable").getAsBoolean();
		this.discoverable = data.get("discoverable").getAsBoolean();
		String rarityString = data.get("rarity").getAsString();
		this.rarity = EnchantmentRarity.valueOf(rarityString);
		this.conflicts = getConflicts(data.get("conflicts").getAsJsonArray());
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
	public int getMinModifiedCost(int level)
	{
		return minModifiedCosts[level - 1];
	}

	@Override
	public @NotNull EnchantmentRarity getRarity()
	{
		return this.rarity;
	}

	@Override
	public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Set<EquipmentSlot> getActiveSlots()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull String translationKey()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public @NotNull EnchantmentTarget getItemTarget()
	{
		return this.itemTarget;
	}

	/**
	 * Sets the return value of {@link #getItemTarget()}.
	 *
	 * @param itemTarget The item target.
	 * @see #getItemTarget()
	 */
	public void setItemTarget(@NotNull EnchantmentTarget itemTarget)
	{
		Preconditions.checkNotNull(itemTarget, "EnchantmentTarget cannot be null");
		this.itemTarget = itemTarget;
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
		return false;
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return this.key;
	}

	@Override
	public @NotNull String getTranslationKey()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@ApiStatus.Internal
	public static EnchantmentMock from(JsonObject data)
	{
		Preconditions.checkNotNull(data);
		List<String> expectedArguments = List.of("key", "itemTarget", "treasure", "cursed", "maxLevel", "startLevel",
				"name", "displayNames", "minModifiedCosts", "maxModifiedCosts", "tradeable", "discoverable", "rarity",
				"conflicts");
		expectedArguments.forEach(
				expectedKey -> Preconditions.checkArgument(data.has(expectedKey), "Missing json key: " + expectedKey));

		NamespacedKey key = NamespacedKey.fromString(data.get("key").getAsString());
		EnchantmentTarget itemTarget = EnchantmentTarget.valueOf(data.get("itemTarget").getAsString());
		boolean treasure = data.get("treasure").getAsBoolean();
		boolean cursed = data.get("cursed").getAsBoolean();
		int maxLevel = data.get("maxLevel").getAsInt();
		int startLevel = data.get("startLevel").getAsInt();
		String name = data.get("name").getAsString();
		Component[] displayNames = getDisplayNames(data.get("displayNames").getAsJsonArray(), maxLevel);
		int[] minModifiedCosts = getMinModifiedCosts(data.get("minModifiedCosts").getAsJsonArray(), maxLevel);
		int[] maxModifiedCosts = getMaxModifiedCosts(data.get("maxModifiedCosts").getAsJsonArray(), maxLevel);
		boolean tradeable = data.get("tradeable").getAsBoolean();
		boolean discoverable = data.get("discoverable").getAsBoolean();
		String rarityString = data.get("rarity").getAsString();
		EnchantmentRarity rarity = EnchantmentRarity.valueOf(rarityString);
		Set<NamespacedKey> conflicts = getConflicts(data.get("conflicts").getAsJsonArray());
		return new EnchantmentMock(key, itemTarget, treasure, cursed, maxLevel, startLevel, name, displayNames,
				minModifiedCosts, maxModifiedCosts, tradeable, discoverable, rarity, conflicts);
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

	private static int[] getMinModifiedCosts(JsonArray minModifiedCosts, int maxLevel)
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

	private static int[] getMaxModifiedCosts(JsonArray maxModifiedCosts, int maxLevel)
	{
		int[] output = new int[maxLevel];
		for (JsonElement element : maxModifiedCosts)
		{
			JsonObject maxModifiedCost = element.getAsJsonObject();
			int level = maxModifiedCost.get(LEVEL).getAsInt();
			output[level - 1] = maxModifiedCost.get(COST).getAsInt();
		}
		return output;
	}

}
