package org.mockbukkit.mockbukkit.inventory.meta;

import com.destroystokyo.paper.Namespaced;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.inventory.meta.components.JukeboxPlayableComponent;
import org.bukkit.inventory.meta.components.ToolComponent;
import org.bukkit.inventory.meta.components.UseCooldownComponent;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.ItemMetaInitException;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.persistence.PersistentDataContainerMock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * Mock implementation of an {@link ItemMeta}, {@link Damageable}, and {@link Repairable}.
 */
public class ItemMetaMock implements ItemMeta, Damageable, Repairable
{
	private static final int ABSOLUTE_MAX_STACK_SIZE = 99;

	// We store the raw JSON representation of all text data. See SPIGOT-5063, SPIGOT-5656, SPIGOT-5304
	private @Nullable String displayName = null;
	private @Nullable String localizedName = null;
	private @Nullable List<String> lore = null;
	private @Nullable Integer damage = null;
	private int repairCost = 0;
	private @Nullable Map<Enchantment, Integer> enchants = new HashMap<>();
	private Multimap<Attribute, AttributeModifier> attributeModifiers;
	private Set<ItemFlag> hideFlags = EnumSet.noneOf(ItemFlag.class);
	private PersistentDataContainerMock persistentDataContainer = new PersistentDataContainerMock();
	private boolean unbreakable = false;
	private @Nullable Integer customModelData = null;
	private Set<Namespaced> destroyableKeys = Sets.newHashSet();
	private Set<Namespaced> placeableKeys = Sets.newHashSet();
	private Integer maxDamage;
	private boolean hideTooltip;
	private boolean fireResistant;
	private Integer maxStackSize = null;
	private Boolean enchantmentGlintOverride = null;
	private ItemRarity rarity;
	private Component itemName = null;

	private @Nullable Integer enchantableValue;

	/**
	 * Constructs a new {@link ItemMetaMock}.
	 */
	public ItemMetaMock()
	{
	}

	/**
	 * Constructs a new {@link ItemMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public ItemMetaMock(@NotNull ItemMeta meta)
	{
		unbreakable = meta.isUnbreakable();
		enchants = new HashMap<>(meta.getEnchants());
		customModelData = meta.hasCustomModelData() ? meta.getCustomModelData() : null;
		hideFlags.addAll(meta.getItemFlags());
		if (meta.hasMaxStackSize())
		{
			maxStackSize = meta.getMaxStackSize();
		}
		if (meta.hasDisplayName())
		{
			displayName = GsonComponentSerializer.gson().serialize(meta.displayName());
		}
		if (meta.hasLore())
		{
			lore = meta.lore().stream().map(c -> GsonComponentSerializer.gson().serialize(c)).collect(Collectors.toList());
		}
		if (meta instanceof Damageable d)
		{
			this.damage = d.hasDamageValue() ? d.getDamage() : null;
			this.maxDamage = d.hasMaxDamage() ? d.getMaxDamage() : null;
		}
		if (meta instanceof Repairable r)
		{
			this.repairCost = r.getRepairCost();
		}
		if (meta instanceof ItemMetaMock m)
		{
			this.persistentDataContainer = m.persistentDataContainer;
		}
		if (meta.hasAttributeModifiers())
		{
			this.attributeModifiers = LinkedHashMultimap.create(meta.getAttributeModifiers());
		}
	}

	static boolean checkConflictingEnchants(@Nullable Map<Enchantment, Integer> enchantments, @NotNull Enchantment ench)
	{
		if (enchantments == null || enchantments.isEmpty())
		{
			return false;
		}

		Iterator<Enchantment> var2 = enchantments.keySet().iterator();

		Enchantment enchant;
		do
		{
			if (!var2.hasNext())
			{
				return false;
			}
			enchant = var2.next();
		}
		while (!enchant.conflictsWith(ench));

		return true;
	}

	@Override
	public boolean hasDisplayName()
	{
		return this.displayName != null;
	}

	@Override
	public @Nullable Component displayName()
	{
		return this.displayName == null ? null : GsonComponentSerializer.gson().deserialize(this.displayName);
	}

	@Override
	public void displayName(@Nullable Component displayName)
	{
		this.displayName = displayName == null ? null : GsonComponentSerializer.gson().serialize(displayName);
	}

	@Override
	public @NotNull String getDisplayName()
	{
		return this.displayName == null ? "" : LegacyComponentSerializer.legacySection().serialize(GsonComponentSerializer.gson().deserialize(this.displayName));
	}

	@Override
	public @NotNull BaseComponent @NotNull [] getDisplayNameComponent()
	{
		return BungeeComponentSerializer.get().serialize(GsonComponentSerializer.gson().deserialize(this.displayName));
	}

	@Override
	public void setDisplayName(@Nullable String name)
	{
		this.displayName = name == null ? null : GsonComponentSerializer.gson().serialize(LegacyComponentSerializer.legacySection().deserialize(name));
	}

	@Override
	public void setDisplayNameComponent(BaseComponent @NotNull [] components)
	{
		this.displayName = GsonComponentSerializer.gson().serialize(BungeeComponentSerializer.get().deserialize(Arrays.stream(components).filter(Objects::nonNull).toArray(BaseComponent[]::new)));
	}

	/**
	 * Checks if this items lore is equal to some other lore.
	 *
	 * @param meta The other item meta whose lore should be compared.
	 * @return {@code true} if they are the same, {@code false} if they're not.
	 */
	private boolean isLoreEquals(@NotNull ItemMeta meta)
	{
		if (lore == null)
		{
			return !meta.hasLore();
		}
		else if (!meta.hasLore())
		{
			return false;
		}

		List<Component> otherLore = meta.lore();
		if (lore.size() == otherLore.size())
		{
			for (int i = 0; i < lore.size(); i++)
			{
				if (!GsonComponentSerializer.gson().deserialize(lore.get(i)).equals(otherLore.get(i)))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Checks if the display name of this item meta is equal to the display name of
	 * another one.
	 *
	 * @param meta The other item meta to check against.
	 * @return {@code true} if both display names are equal, {@code false} if
	 * they're not.
	 */
	private boolean isDisplayNameEqual(@NotNull ItemMeta meta)
	{
		if (displayName != null)
		{
			if (meta.hasDisplayName())
			{
				return GsonComponentSerializer.gson().deserialize(displayName).equals(meta.displayName());
			}
			else
			{
				return false;
			}
		}
		else
		{
			return !meta.hasDisplayName();
		}
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(
				displayName,
				lore,
				customModelData,
				repairCost,
				enchants,
				attributeModifiers,
				hideFlags,
				persistentDataContainer,
				unbreakable,
				customModelData,
				damage,
				maxDamage,
				hideTooltip,
				fireResistant,
				maxStackSize);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ItemMeta meta))
		{
			return false;
		}

		if (!isDisplayNameEqual(meta))
		{
			return false;
		}
		if (!isLoreEquals(meta))
		{
			return false;
		}
		if (obj instanceof Damageable damageable)
		{
			if (hasDamage() != damageable.hasDamage() || hasDamage() && getDamage() != damageable.getDamage())
			{
				return false;
			}
			if (hasMaxDamage() != damageable.hasMaxDamage() || hasMaxDamage() && getMaxDamage() != damageable.getMaxDamage())
			{
				return false;
			}
		}
		else if (hasDamage() || hasMaxDamage())
		{
			return false;
		}
		if (obj instanceof Repairable repairable)
		{
			if (hasRepairCost() != repairable.hasRepairCost() || hasRepairCost() && getRepairCost() != repairable.getRepairCost())
			{
				return false;
			}
		}
		else if (hasRepairCost())
		{
			return false;
		}
		if (!Objects.equals(hasMaxStackSize(), meta.hasMaxStackSize()))
		{
			return false;
		}
		if (hasMaxStackSize() && !Objects.equals(getMaxStackSize(), meta.getMaxStackSize()))
		{
			return false;
		}
		if (!Objects.equals(hasCustomModelData(), meta.hasCustomModelData()))
		{
			return false;
		}
		if (hasCustomModelData() && !Objects.equals(getCustomModelData(), meta.getCustomModelData()))
		{
			return false;
		}

		return isUnbreakable() == meta.isUnbreakable()
				&& isHideTooltip() == meta.isHideTooltip()
				&& isFireResistant() == meta.isFireResistant()
				&& Objects.equals(getEnchants(), meta.getEnchants())
				&& Objects.equals(hasAttributeModifiers(), meta.hasAttributeModifiers())
				&& Objects.equals(getAttributeModifiers(), meta.getAttributeModifiers())
				&& Objects.equals(getItemFlags(), meta.getItemFlags())
				&& Objects.equals(getPersistentDataContainer(), meta.getPersistentDataContainer());
	}

	@Override
	public ItemMetaMock clone()
	{
		try
		{
			ItemMetaMock meta = (ItemMetaMock) super.clone();
			meta.displayName = displayName;
			if (lore != null)
			{
				meta.lore = new ArrayList<>(lore);
			}

			meta.unbreakable = unbreakable;
			meta.customModelData = customModelData;
			meta.enchants = new HashMap<>(enchants);
			meta.persistentDataContainer = new PersistentDataContainerMock(persistentDataContainer);
			meta.damage = damage;
			meta.repairCost = repairCost;
			meta.hideFlags = EnumSet.copyOf(hideFlags);
			meta.destroyableKeys = destroyableKeys != null ? new HashSet<>(destroyableKeys) : null;
			meta.placeableKeys = placeableKeys != null ? new HashSet<>(placeableKeys) : null;
			return meta;
		}
		catch (CloneNotSupportedException e)
		{
			throw new ItemMetaInitException(e);
		}
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.13")
	public Set<Material> getCanDestroy()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.13")
	public void setCanDestroy(Set<Material> set)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.13")
	public Set<Material> getCanPlaceOn()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.13")
	public void setCanPlaceOn(Set<Material> set)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public @NotNull Set<com.destroystokyo.paper.Namespaced> getDestroyableKeys()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public void setDestroyableKeys(@NotNull Collection<com.destroystokyo.paper.Namespaced> collection)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public @NotNull Set<com.destroystokyo.paper.Namespaced> getPlaceableKeys()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public void setPlaceableKeys(@NotNull Collection<com.destroystokyo.paper.Namespaced> collection)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public boolean hasPlaceableKeys()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public boolean hasDestroyableKeys()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasLore()
	{
		return this.lore != null && !lore.isEmpty();
	}

	@Override
	public @Nullable List<Component> lore()
	{
		return this.lore == null ? null : new ArrayList<>(this.lore.stream()
				.map(s -> GsonComponentSerializer.gson().deserialize(s))
				.toList());
	}

	@Override
	public void lore(@Nullable List<? extends Component> lore)
	{
		if (lore != null && !lore.isEmpty())
		{
			this.lore = new ArrayList<>(lore.stream().map(s -> GsonComponentSerializer.gson().serialize(s)).toList());
		}
		else
		{
			this.lore = null;
		}
	}

	@Override
	public @Nullable List<String> getLore()
	{
		return this.lore == null ? null : new ArrayList<>(this.lore.stream()
				.map(s -> LegacyComponentSerializer
						.legacySection()
						.serialize(GsonComponentSerializer.gson().deserialize(s)))
				.toList());
	}

	@Override
	public @Nullable List<BaseComponent[]> getLoreComponents()
	{
		return this.lore == null ? null : this.lore.stream()
				.map(c -> BungeeComponentSerializer
						.get()
						.serialize(GsonComponentSerializer.gson().deserialize(c))
				).toList();
	}

	@Override
	public void setLore(@Nullable List<String> lore)
	{
		if (lore != null && !lore.isEmpty())
		{
			this.lore = lore.stream().map(s -> GsonComponentSerializer.gson().serialize(LegacyComponentSerializer.legacySection().deserialize(s).asComponent())).collect(Collectors.toList());
		}
		else
		{
			this.lore = null;
		}
	}

	@Override
	public void setLoreComponents(@Nullable List<BaseComponent[]> lore)
	{
		lore(lore == null ? null : lore.stream().map(c -> BungeeComponentSerializer.get().deserialize(c)).toList());
	}

	/**
	 * Asserts if the lore contains the given lines in order.
	 *
	 * @param lines The lines the lore should contain
	 */
	@Deprecated(forRemoval = true)
	public void assertLore(@NotNull List<String> lines)
	{
		assertComponentLore(lines.stream().map(s -> LegacyComponentSerializer.legacySection().deserialize(s).asComponent()).toList());
	}

	/**
	 * Asserts if the lore contains the given lines in order.
	 *
	 * @param lines The lines the lore should contain
	 */
	@Deprecated(forRemoval = true)
	public void assertComponentLore(@NotNull List<Component> lines)
	{
		if (this.lore == null)
		{
			throw new AssertionError("No lore was set");
		}
		if (this.lore.size() != lines.size())
		{
			throw new AssertionError("Lore size mismatch: expected " + lines.size() + " but was " + this.lore.size());
		}
		for (int i = 0; i < this.lore.size(); i++)
		{
			if (GsonComponentSerializer.gson().deserialize(this.lore.get(i)).equals(lines.get(i)))
			{
				continue;
			}
			throw new AssertionError(String.format("Line %d should be '%s' but was '%s'", i, lines.get(i), this.lore.get(i)));
		}
	}

	/**
	 * Asserts if the lore contains the given lines in order.
	 *
	 * @param lines The lines the lore should contain
	 */
	@Deprecated(forRemoval = true)
	public void assertLore(String... lines)
	{
		assertLore(Arrays.asList(lines));
	}

	/**
	 * Asserts that the item meta contains no lore.
	 *
	 * @throws AssertionError if the item meta contains some lore.
	 */
	@Deprecated(forRemoval = true)
	public void assertHasNoLore() throws AssertionError
	{
		if (hasLore())
		{
			throw new AssertionError("Lore was set but shouldn't have been set");
		}
	}

	/**
	 * Serializes the properties of an ItemMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the ItemMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		// Make new map and add relevant properties to it.
		Map<String, Object> map = new HashMap<>();

		if (this.displayName != null)
		{
			map.put("display-name", this.displayName);
		}

		if (this.localizedName != null)
		{
			map.put("loc-name", this.localizedName);
		}

		if (this.lore != null)
		{
			map.put("lore", this.lore);
		}

		if (this.customModelData != null)
		{
			map.put("custom-model-data", this.customModelData);
		}

		if (this.enchants != null)
		{
			map.put("enchants", this.enchants.entrySet().stream()
					.collect(Collectors.toMap(entry -> entry.getKey().getKey().value(), Map.Entry::getValue)));
		}
		else
		{
			map.put("enchants", new HashMap<String, Integer>());
		}

		if (hasAttributeModifiers())
		{
			map.put("attribute-modifiers", this.attributeModifiers);
		}

		map.put("repair-cost", this.repairCost);
		map.put("ItemFlags", this.hideFlags);
		map.put("Unbreakable", this.unbreakable);
		if(this.damage != null)
		{
			map.put("Damage", this.damage);
		}

		/* Not implemented.
		if (!this.customTagContainer.isEmpty())
		{
			map.put("customTagContainer", this.customTagContainer);
		}
		*/

		map.put("PublicBukkitValues", this.persistentDataContainer.serialize());
		map.put("meta-type", getTypeName());

		// Return map
		return map;
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized ItemMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the ItemMetaMock class.
	 */
	public static @NotNull ItemMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		ItemMetaMock serialMock = new ItemMetaMock();

		serialMock.deserializeInternal(args);
		return serialMock;
	}

	@SuppressWarnings("unchecked")
	@ApiStatus.Internal
	protected void deserializeInternal(@NotNull Map<String, Object> args)
	{
		displayName = (String) args.get("display-name");
		lore = (List<String>) args.get("lore");
		localizedName = (String) args.get("loc-name");

		enchants = new HashMap<>();
		for (Map.Entry<String, Integer> entry : ((Map<String, Integer>) args.get("enchants")).entrySet())
		{
			Enchantment enchantment = Registry.ENCHANTMENT.get(NamespacedKey.minecraft(entry.getKey()));
			if (enchantment != null)
			{
				enchants.put(enchantment, entry.getValue());
			}
		}

		hideFlags = (Set<ItemFlag>) args.get("ItemFlags");
		unbreakable = (boolean) args.get("Unbreakable");
		setAttributeModifiers((Multimap<Attribute, AttributeModifier>) args.get("AttributeModifiers"));
		// customTagContainer is also unimplemented in mock.
		customModelData = (Integer) args.get("custom-model-data");
		Map<String, Object> map = (Map<String, Object>) args.get("PublicBukkitValues");
		persistentDataContainer = PersistentDataContainerMock.deserialize(map);
		damage = (Integer) args.get("Damage");
		repairCost = (int) args.get("repair-cost");
		destroyableKeys = (Set<Namespaced>) args.get("destroyable-keys");
		placeableKeys = (Set<Namespaced>) args.get("placeable-keys");
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public boolean hasLocalizedName()
	{
		return localizedName != null;
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public @NotNull String getLocalizedName()
	{
		return localizedName;
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public void setLocalizedName(@Nullable String name)
	{
		localizedName = name;
	}

	@Override
	public boolean hasEnchants()
	{
		return !enchants.isEmpty();
	}

	@Override
	public boolean hasEnchant(Enchantment ench)
	{
		return enchants.containsKey(ench);
	}

	@Override
	public int getEnchantLevel(Enchantment ench)
	{
		return hasEnchant(ench) ? enchants.get(ench) : 0;
	}

	@Override
	public @NotNull Map<Enchantment, Integer> getEnchants()
	{
		return enchants != null ? ImmutableSortedMap.copyOf(enchants,
				Comparator.comparing(o -> o.getKey().toString())
				) : ImmutableMap.of();
	}

	@Override
	public boolean addEnchant(@NotNull Enchantment ench, int level, boolean ignoreLevelRestriction)
	{
		Integer existingLevel = this.enchants.get(ench);
		if (nonNull(existingLevel) && existingLevel.equals(level))
		{
			return false; // Already exists with the same level
		}

		if (ignoreLevelRestriction || (level >= ench.getStartLevel() && level <= ench.getMaxLevel()))
		{
			this.enchants.put(ench, level);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean removeEnchant(Enchantment ench)
	{
		return nonNull(this.enchants.remove(ench));
	}

	@Override
	public void removeEnchantments()
	{
		this.enchants.clear();
	}

	@Override
	public boolean hasConflictingEnchant(Enchantment ench)
	{
		boolean b = this.hasEnchants() && enchants.remove(ench) != null;
		if (enchants != null && enchants.isEmpty())
		{
			enchants = null;
		}

		return b;
	}

	@Override
	public void addItemFlags(ItemFlag... itemFlags)
	{
		hideFlags.addAll(Arrays.asList(itemFlags));
	}

	@Override
	public void removeItemFlags(ItemFlag... itemFlags)
	{
		hideFlags.removeAll(Arrays.asList(itemFlags));
	}

	@Override
	public @NotNull Set<ItemFlag> getItemFlags()
	{
		return Set.copyOf(hideFlags);
	}

	@Override
	public boolean hasItemFlag(ItemFlag flag)
	{
		return hideFlags.contains(flag);
	}

	@Override
	public boolean isUnbreakable()
	{
		return unbreakable;
	}

	@Override
	public void setUnbreakable(boolean unbreakable)
	{
		this.unbreakable = unbreakable;
	}

	@Override
	public boolean hasDamage()
	{
		return damage != null && damage > 0;
	}

	@Override
	public int getDamage()
	{
		return damage == null ? 0 : damage;
	}

	@Override
	public void setDamage(int damage)
	{
		Preconditions.checkState(damage >= 0, "damage cannot be negative");
		this.damage = damage;
	}

	@Override
	public boolean hasDamageValue()
	{
		return damage != null;
	}

	@Override
	public void resetDamage()
	{
		this.damage = null;
	}

	@Override
	public boolean hasRepairCost()
	{
		return repairCost > 0;
	}

	@Override
	public int getRepairCost()
	{
		return repairCost;
	}

	@Override
	public void setRepairCost(int cost)
	{
		this.repairCost = cost;
	}

	private void checkAttributeMap()
	{
		if (this.attributeModifiers == null)
		{
			this.attributeModifiers = LinkedHashMultimap.create();
		}
	}

	@Override
	public boolean hasAttributeModifiers()
	{
		return attributeModifiers != null && !attributeModifiers.isEmpty();
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers()
	{
		return this.hasAttributeModifiers()
				? ImmutableMultimap.copyOf(attributeModifiers)
				: null;
	}

	@Override
	public void setAttributeModifiers(@Nullable Multimap<Attribute, AttributeModifier> attributeModifiers)
	{
		if (attributeModifiers == null || attributeModifiers.isEmpty())
		{
			this.attributeModifiers = LinkedHashMultimap.create();
			return;
		}

		this.checkAttributeMap();
		this.attributeModifiers.clear();

		attributeModifiers.entries().stream()
				.filter(entry -> entry.getKey() != null && entry.getValue() != null)
				.forEach(entry -> this.attributeModifiers.put(entry.getKey(), entry.getValue()));
	}

	@Override
	public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers(@NotNull EquipmentSlot slot)
	{
		this.checkAttributeMap();
		SetMultimap<Attribute, AttributeModifier> result = LinkedHashMultimap.create();

		this.attributeModifiers.entries().stream()
				.filter(entry -> entry.getValue().getSlot() != null && entry.getValue().getSlot() == slot)
				.forEach(entry -> result.put(entry.getKey(), entry.getValue()));

		return result;
	}

	@Override
	public Collection<AttributeModifier> getAttributeModifiers(@NotNull Attribute attribute)
	{
		Preconditions.checkNotNull(attribute, "Attribute cannot be null");
		this.checkAttributeMap();
		return this.attributeModifiers.containsKey(attribute)
				? ImmutableList.copyOf(this.attributeModifiers.get(attribute))
				: null;
	}

	@Override
	public boolean addAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier)
	{
		Preconditions.checkNotNull(attribute, "Attribute cannot be null");
		Preconditions.checkNotNull(modifier, "AttributeModifier cannot be null");
		this.checkAttributeMap();
		for (Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entries())
		{
			Preconditions.checkArgument(!entry.getValue().getKey().equals(modifier.getKey()), "Cannot register AttributeModifier. Modifier is already applied! %s", modifier);
		}
		return this.attributeModifiers.put(attribute, modifier);
	}

	@Override
	public boolean removeAttributeModifier(@NotNull Attribute attribute)
	{
		Preconditions.checkNotNull(attribute, "Attribute cannot be null");
		this.checkAttributeMap();
		return !this.attributeModifiers.removeAll(attribute).isEmpty();
	}

	@Override
	public boolean removeAttributeModifier(@NotNull EquipmentSlot slot)
	{
		this.checkAttributeMap();
		// Match against null because as of 1.13, AttributeModifiers without a set slot are active in any slot.
		return this.attributeModifiers.entries().removeIf(entry -> entry.getValue().getSlot() == null || entry.getValue().getSlot() == slot);
	}

	@Override
	public boolean removeAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier)
	{
		Preconditions.checkNotNull(attribute, "Attribute cannot be null");
		Preconditions.checkNotNull(modifier, "AttributeModifier cannot be null");
		this.checkAttributeMap();

		return this.attributeModifiers.entries().removeIf(entry ->
				(entry.getKey() == null || entry.getValue() == null) || (entry.getKey() == attribute && entry.getValue().getKey().equals(modifier.getKey()))
		);
	}

	@NotNull
	@Override
	public String getAsString()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull String getAsComponentString()
	{
		//TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@SuppressWarnings("deprecation")
	public @NotNull CustomItemTagContainer getCustomTagContainer()
	{
		// This was replaced by PersistentDataContainer!
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull PersistentDataContainer getPersistentDataContainer()
	{
		return this.persistentDataContainer;
	}

	@Override
	public boolean hasCustomModelData()
	{
		return this.customModelData != null;
	}

	@Override
	public int getCustomModelData()
	{
		Preconditions.checkState(hasCustomModelData(), "We don't have CustomModelData! Check hasCustomModelData first!");
		return this.customModelData;
	}

	@Override
	public void setCustomModelData(@Nullable Integer data)
	{
		this.customModelData = data;
	}

	@Override
	public boolean hasEnchantable()
	{
		return this.enchantableValue != null;
	}

	@Override
	public int getEnchantable()
	{
		Preconditions.checkState(this.hasEnchantable(), "We don't have Enchantable! Check hasEnchantable first!");
		return this.enchantableValue;
	}

	@Override
	public void setEnchantable(@Nullable Integer data)
	{
		Preconditions.checkArgument(data == null || data > 0, "Enchantability must be positive"); // Paper
		this.enchantableValue = data;
	}

	@Override
	public void setVersion(int version)
	{
		// No use yet
	}

	@Deprecated(since = "1.20")
	private Set<Material> legacyGetMatsFromKeys(Collection<Namespaced> names)
	{
		Set<Material> mats = Sets.newHashSet();
		for (Namespaced key : names)
		{
			if (!(key instanceof org.bukkit.NamespacedKey))
			{
				continue;
			}

			Material material = Material.matchMaterial(key.toString(), false);
			if (material != null)
			{
				mats.add(material);
			}
		}

		return mats;
	}

	@Deprecated(since = "1.20")
	private void legacyClearAndReplaceKeys(Collection<Namespaced> toUpdate, Collection<Material> beingSet)
	{
		if (beingSet.stream().anyMatch(Material::isLegacy))
		{
			throw new IllegalArgumentException("Set must not contain any legacy materials!");
		}

		toUpdate.clear();
		toUpdate.addAll(beingSet.stream().map(Material::getKey).collect(java.util.stream.Collectors.toSet()));
	}

	@Override
	public boolean hasMaxDamage()
	{
		return this.maxDamage != null;
	}

	@Override
	public int getMaxDamage()
	{
		Preconditions.checkState(this.hasMaxDamage(), "We don't have max_damage! Check hasMaxDamage first!");
		return this.maxDamage;
	}

	@Override
	public void setMaxDamage(@Nullable Integer maxDamage)
	{
		this.maxDamage = maxDamage;
	}

	@Override
	public boolean hasItemName()
	{
		return this.itemName != null;
	}

	@Override
	public @NotNull Component itemName()
	{
		return this.itemName == null ? Component.empty() : this.itemName;
	}

	@Override
	public void itemName(@Nullable Component name)
	{
		this.itemName = name;
	}

	@Override
	@Deprecated
	public @NotNull String getItemName()
	{
		return LegacyComponentSerializer.legacySection().serialize(itemName());
	}

	@Override
	@Deprecated
	public void setItemName(@Nullable String name)
	{
		if (name == null)
		{
			this.itemName = null;
		} else
		{
			this.itemName = LegacyComponentSerializer.legacySection().deserialize(name);
		}
	}

	@Override
	public boolean isHideTooltip()
	{
		return this.hideTooltip;
	}

	@Override
	public void setHideTooltip(boolean hideTooltip)
	{
		this.hideTooltip = hideTooltip;
	}

	@Override
	public boolean hasTooltipStyle()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable NamespacedKey getTooltipStyle()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTooltipStyle(@Nullable NamespacedKey namespacedKey)
	{
        //TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasItemModel()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable NamespacedKey getItemModel()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isGlider()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasDamageResistant()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Tag<DamageType> getDamageResistant()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasUseRemainder()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable ItemStack getUseRemainder()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasUseCooldown()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull UseCooldownComponent getUseCooldown()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasEquippable()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EquippableComponent getEquippable()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setEquippable(@Nullable EquippableComponent equippableComponent)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setUseCooldown(@Nullable UseCooldownComponent useCooldownComponent)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setUseRemainder(@Nullable ItemStack itemStack)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setDamageResistant(@Nullable Tag<DamageType> tag)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setGlider(boolean b)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setItemModel(@Nullable NamespacedKey namespacedKey)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasEnchantmentGlintOverride()
	{
		return this.enchantmentGlintOverride != null;
	}

	@Override
	public @NotNull Boolean getEnchantmentGlintOverride()
	{
		Preconditions.checkState(this.hasEnchantmentGlintOverride(), "We don't have enchantment_glint_override! Check hasEnchantmentGlintOverride first!");
		return this.enchantmentGlintOverride;
	}

	@Override
	public void setEnchantmentGlintOverride(@Nullable Boolean override)
	{
		this.enchantmentGlintOverride = override;
	}

	@Override
	public boolean isFireResistant()
	{
		return this.fireResistant;
	}

	@Override
	public void setFireResistant(boolean fireResistant)
	{
		this.fireResistant = fireResistant;
	}

	@Override
	public boolean hasMaxStackSize()
	{
		return this.maxStackSize != null;
	}

	@Override
	public int getMaxStackSize()
	{
		Preconditions.checkState(hasMaxStackSize(), "We don't have max_stack_size! Check hasMaxStackSize first!");
		return this.maxStackSize;
	}

	@Override
	public void setMaxStackSize(@Nullable Integer max)
	{
		Preconditions.checkArgument(max == null || max > 0, "max_stack_size must be > 0");
		Preconditions.checkArgument(max == null || max <= ABSOLUTE_MAX_STACK_SIZE, "max_stack_size must be <= 99");
		this.maxStackSize = max;
	}

	@Override
	public boolean hasRarity()
	{
		return this.rarity != null;
	}

	@Override
	public @NotNull ItemRarity getRarity()
	{
		Preconditions.checkState(this.hasRarity(), "We don't have rarity! Check hasRarity first!");
		return this.rarity;
	}

	@Override
	public void setRarity(@Nullable ItemRarity rarity)
	{
		this.rarity = rarity;
	}

	@Override
	public boolean hasFood()
	{
		//TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull FoodComponent getFood()
	{
		//TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setFood(@Nullable FoodComponent food)
	{
		//TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@ApiStatus.Internal
	protected String getTypeName()
	{
		return "UNSPECIFIC";
	}

	@Override
	public boolean hasTool()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ToolComponent getTool()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTool(@Nullable ToolComponent toolComponent)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasJukeboxPlayable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull JukeboxPlayableComponent getJukeboxPlayable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setJukeboxPlayable(@Nullable JukeboxPlayableComponent jukeboxPlayable)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder(getTypeName() + "(");
		Map<String, Object> data = this.serialize();
		for (Map.Entry<String, Object> entry : data.entrySet())
		{
			stringBuilder.append(entry.getKey());
			stringBuilder.append("=");
			stringBuilder.append(entry.getValue());
			stringBuilder.append(", ");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		stringBuilder.append(")");
		return stringBuilder.toString();
	}

}

