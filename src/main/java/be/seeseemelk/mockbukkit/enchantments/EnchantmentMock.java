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
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Mock implementation of an {@link Enchantment}.
 */
public class EnchantmentMock extends Enchantment
{

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

	private Set<NamespacedKey> getConflicts(JsonArray conflicts)
	{
		Set<NamespacedKey> output = new HashSet<>();
		for (JsonElement conflict : conflicts)
		{
			output.add(NamespacedKey.fromString(conflict.getAsString()));
		}
		return output;
	}

	private Component[] getDisplayNames(JsonArray displayNamesData, int maxLevel)
	{
		Component[] output = new Component[maxLevel];
		for (JsonElement element : displayNamesData)
		{
			JsonObject displayNameData = element.getAsJsonObject();
			int level = displayNameData.get("level").getAsInt();
			GsonComponentSerializer gsonComponentSerializer = GsonComponentSerializer.builder().build();
			output[level - 1] = gsonComponentSerializer.deserializeFromTree(displayNameData.get("text"));
		}
		return output;
	}

	private int[] getMinModifiedCosts(JsonArray minModifiedCosts, int maxLevel)
	{
		int[] output = new int[maxLevel];
		for (JsonElement element : minModifiedCosts)
		{
			JsonObject minModifiedCost = element.getAsJsonObject();
			int level = minModifiedCost.get("level").getAsInt();
			output[level - 1] = minModifiedCost.get("cost").getAsInt();
		}
		return output;
	}

	private int[] getMaxModifiedCosts(JsonArray maxModifiedCosts, int maxLevel)
	{
		int[] output = new int[maxLevel];
		for (JsonElement element : maxModifiedCosts)
		{
			JsonObject maxModifiedCost = element.getAsJsonObject();
			int level = maxModifiedCost.get("level").getAsInt();
			output[level - 1] = maxModifiedCost.get("cost").getAsInt();
		}
		return output;
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
		Preconditions.checkArgument(other != null, "others can't be null") ;
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

}
