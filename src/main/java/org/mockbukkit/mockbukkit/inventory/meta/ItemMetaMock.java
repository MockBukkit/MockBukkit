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
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
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
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.SerializableMeta;
import org.mockbukkit.mockbukkit.inventory.meta.components.CustomModelDataComponentMock;
import org.mockbukkit.mockbukkit.inventory.meta.components.EquippableComponentMock;
import org.mockbukkit.mockbukkit.inventory.meta.components.FoodComponentMock;
import org.mockbukkit.mockbukkit.inventory.meta.components.JukeboxPlayableComponentMock;
import org.mockbukkit.mockbukkit.inventory.meta.components.ToolComponentMock;
import org.mockbukkit.mockbukkit.inventory.meta.components.UseCooldownComponentMock;
import org.mockbukkit.mockbukkit.persistence.PersistentDataContainerMock;
import org.mockbukkit.mockbukkit.util.NbtParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * Mock implementation of an {@link ItemMeta}, {@link Damageable}, and {@link Repairable}.
 */
@SuppressWarnings("UnstableApiUsage")
@DelegateDeserialization(SerializableMeta.class)
public class ItemMetaMock implements ItemMeta, Damageable, Repairable
{
	public static final String ATTRIBUTE_MODIFIERS = "attribute-modifiers";
	public static final String BLOCK_DATA = "BlockStateTag";
	public static final String CUSTOM_MODEL_DATA = "custom-model-data";
	public static final String DAMAGE = "Damage";
	public static final String DAMAGE_RESISTANT = "damage-resistant";
	public static final String DISPLAY_NAME = "display-name";
	public static final String ENCHANTABLE = "enchantable";
	public static final String ENCHANTMENT_GLINT_OVERRIDE = "enchantment-glint-override";
	public static final String ENCHANTMENTS = "enchantments";
	public static final String EQUIPPABLE = "equippable";
	public static final String FIRE_RESISTANT = "FireResistant";
	public static final String FOOD = "food";
	public static final String GLIDER = "glider";
	public static final String HIDE_TOOLTIP = "HideTooltip";
	public static final String ITEM_FLAGS = "ItemFlags";
	public static final String ITEM_MODEL = "item-model";
	public static final String ITEM_NAME = "item-name";
	public static final String JUKEBOX_PLAYABLE = "jukebox-playable";
	public static final String LORE = "lore";
	public static final String MAX_DAMAGE = "max-damage";
	public static final String MAX_STACK_SIZE = "max-stack-size";
	public static final String META_TYPE = "meta-type";
	public static final String PUBLIC_BUKKIT_VALUES = "PublicBukkitValues";
	public static final String RARITY = "rarity";
	public static final String REPAIR_COST = "repair-cost";
	public static final String TOOL = "tool";
	public static final String TOOL_TIP_STYLE = "tool-tip-style";
	public static final String UNBREAKABLE = "Unbreakable";
	public static final String USE_COOLDOWN = "use-cooldown";
	public static final String USE_REMAINDER = "use-remainder";

	private static final int ABSOLUTE_MAX_STACK_SIZE = 99;

	// We store the raw JSON representation of all text data. See SPIGOT-5063, SPIGOT-5656, SPIGOT-5304
	private @Nullable String displayName = null;
	private @Nullable List<String> lore = null;
	private @Nullable Integer damage = null;
	private @Nullable Integer maxDamage;
	private int repairCost = 0;
	private @NotNull Map<Enchantment, Integer> enchants = new HashMap<>();
	private @NotNull Multimap<Attribute, AttributeModifier> attributeModifiers = LinkedHashMultimap.create();
	private @NotNull Set<ItemFlag> hideFlags = EnumSet.noneOf(ItemFlag.class);
	private @NotNull PersistentDataContainerMock persistentDataContainer = new PersistentDataContainerMock();
	private boolean unbreakable = false;
	private @Nullable Integer customModelData = null;
	private boolean hideTooltip;
	private boolean fireResistant;
	private boolean glider;
	private @Nullable Integer maxStackSize = null;
	private @Nullable Boolean enchantmentGlintOverride = null;
	private @Nullable ItemRarity rarity;
	private @Nullable Component itemName = null;
	private @Nullable Integer enchantable;
	private @Nullable ItemStack useRemainder;
	private @Nullable NamespacedKey itemModel;
	private @Nullable NamespacedKey tooltipStyle;
	private @Nullable NamespacedKey damageResistant;

	private @Nullable CustomModelDataComponent customModelDataComponent;
	private @Nullable UseCooldownComponent useCooldown;
	private @Nullable FoodComponent foodComponent;
	private @Nullable ToolComponent toolComponent;
	private @Nullable EquippableComponent equippableComponent;
	private @Nullable JukeboxPlayableComponent jukeboxPlayableComponent;
	private @Nullable Map<String, String> blockData = null;

	/**
	 * Constructs a new {@link ItemMetaMock}.
	 */
	public ItemMetaMock()
	{
	}

	/**
	 * Constructs a new {@link ItemMetaMock}, copying the data from another.
	 *
	 * @param meta The meta to copy.
	 */
	public ItemMetaMock(@NotNull ItemMeta meta)
	{
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
		if (meta.hasEnchants())
		{
			enchants = new HashMap<>(meta.getEnchants());
		}
		if (meta.hasAttributeModifiers())
		{
			this.attributeModifiers = LinkedHashMultimap.create(meta.getAttributeModifiers());
		}

		var tmpHideFlags = meta.getItemFlags();
		if (!tmpHideFlags.isEmpty())
		{
			hideFlags = EnumSet.copyOf(tmpHideFlags);
		}
		if (meta instanceof ItemMetaMock m)
		{
			this.persistentDataContainer = new PersistentDataContainerMock(m.persistentDataContainer);
		}
		unbreakable = meta.isUnbreakable();
		customModelData = meta.hasCustomModelData() ? meta.getCustomModelData() : null;
		hideTooltip = meta.isHideTooltip();
		fireResistant = meta.isFireResistant();
		if (meta.hasMaxStackSize())
		{
			maxStackSize = meta.getMaxStackSize();
		}
		if (meta.hasEnchantmentGlintOverride())
		{
			enchantmentGlintOverride = meta.getEnchantmentGlintOverride();
		}
		if (meta.hasRarity())
		{
			rarity = meta.getRarity();
		}
		if (meta.hasItemName())
		{
			itemName = meta.itemName();
		}
		if (meta.hasEnchantable())
		{
			enchantable = meta.getEnchantable();
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
	public boolean hasCustomName()
	{
		return displayName != null;
	}

	@Override
	public @Nullable Component customName()
	{
		return displayName == null ? null : GsonComponentSerializer.gson().deserialize(displayName);
	}

	@Override
	public void customName(@Nullable Component component)
	{
		this.displayName = component == null ? null : GsonComponentSerializer.gson().serialize(component);
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
				damage,
				repairCost,
				enchants,
				attributeModifiers,
				hideFlags,
				persistentDataContainer,
				unbreakable,
				customModelData,
				maxDamage,
				hideTooltip,
				fireResistant,
				maxStackSize,
				enchantmentGlintOverride,
				rarity,
				itemName,
				enchantable);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ItemMeta meta))
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

		return isDisplayNameEqual(meta)
				&& isLoreEquals(meta)
				&& isUnbreakable() == meta.isUnbreakable()
				&& isHideTooltip() == meta.isHideTooltip()
				&& isFireResistant() == meta.isFireResistant()
				&& Objects.equals(getEnchants(), meta.getEnchants())
				&& Objects.equals(hasMaxStackSize(), meta.hasMaxStackSize())
				&& (!hasMaxStackSize() || Objects.equals(getMaxStackSize(), meta.getMaxStackSize()))
				&& Objects.equals(hasCustomModelData(), meta.hasCustomModelData())
				&& (!hasCustomModelData() || Objects.equals(getCustomModelData(), meta.getCustomModelData()))
				&& Objects.equals(hasEnchantmentGlintOverride(), meta.hasEnchantmentGlintOverride())
				&& (!hasEnchantmentGlintOverride() || Objects.equals(getEnchantmentGlintOverride(), meta.getEnchantmentGlintOverride()))
				&& Objects.equals(hasRarity(), meta.hasRarity())
				&& (!hasRarity() || Objects.equals(getRarity(), meta.getRarity()))
				&& Objects.equals(hasEnchantable(), meta.hasEnchantable())
				&& (!hasEnchantable() || Objects.equals(getEnchantable(), meta.getEnchantable()))
				&& Objects.equals(hasAttributeModifiers(), meta.hasAttributeModifiers())
				&& (!hasAttributeModifiers() || Objects.equals(getAttributeModifiers(), meta.getAttributeModifiers()))
				&& Objects.equals(getItemFlags(), meta.getItemFlags())
				&& Objects.equals(getPersistentDataContainer(), meta.getPersistentDataContainer());
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public ItemMetaMock clone()
	{
		return new ItemMetaMock(this);
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
	public void assertComponentLore(@NotNull List<? extends Component> lines)
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

		if (this.hasDisplayName())
		{
			map.put(DISPLAY_NAME, toComponentString(this.displayName()));
		}
		if (this.hasLore())
		{
			map.put(LORE, this.lore().stream().map(ItemMetaMock::toComponentString).toList());
		}
		if (this.hasDamage())
		{
			map.put(DAMAGE, this.getDamage());
		}
		if (this.hasMaxDamage())
		{
			map.put(MAX_DAMAGE, this.getMaxDamage());
		}
		if (this.hasRepairCost())
		{
			map.put(REPAIR_COST, this.repairCost);
		}
		if (this.hasEnchants())
		{
			map.put(ENCHANTMENTS, this.enchants.entrySet().stream()
					.collect(Collectors.toMap(entry -> entry.getKey().getKey().asString(), Map.Entry::getValue)));
		}
		if (this.hasAttributeModifiers())
		{
			map.put(ATTRIBUTE_MODIFIERS, this.getAttributeModifiers());
		}
		if (!this.getItemFlags().isEmpty())
		{
			map.put(ITEM_FLAGS, this.getItemFlags().stream().sorted().toList());
		}
		if (!this.persistentDataContainer.isEmpty())
		{
			map.put(PUBLIC_BUKKIT_VALUES, this.persistentDataContainer.serialize());
		}
		if (this.hasTooltipStyle())
		{
			map.put(TOOL_TIP_STYLE, this.getTooltipStyle().asString());
		}
		if (this.hasItemModel())
		{
			map.put(ITEM_MODEL, this.getItemModel().asString());
		}
		if (this.isUnbreakable())
		{
			map.put(UNBREAKABLE, this.isUnbreakable());
		}
		if (this.hasCustomModelData())
		{
			map.put(CUSTOM_MODEL_DATA, this.getCustomModelData());
		}
		if (this.isHideTooltip())
		{
			map.put(HIDE_TOOLTIP, this.hideTooltip);
		}
		if (this.isFireResistant())
		{
			map.put(FIRE_RESISTANT, this.fireResistant);
		}
		if (this.hasDamageResistant())
		{
			map.put(DAMAGE_RESISTANT, this.getDamageResistant().getKey().asString());
		}
		if (this.hasMaxStackSize())
		{
			map.put(MAX_STACK_SIZE, this.getMaxStackSize());
		}
		if (this.hasEnchantmentGlintOverride())
		{
			map.put(ENCHANTMENT_GLINT_OVERRIDE, this.getEnchantmentGlintOverride());
		}
		if (this.isGlider())
		{
			map.put(GLIDER, this.isGlider());
		}
		if (this.hasRarity())
		{
			map.put(RARITY, this.getRarity());
		}
		if (this.hasUseRemainder())
		{
			map.put(USE_REMAINDER, this.getUseRemainder().serialize());
		}
		if (this.hasUseCooldown())
		{
			map.put(USE_COOLDOWN, this.getUseCooldown().serialize());
		}
		if (this.hasFood())
		{
			map.put(FOOD, this.getFood().serialize());
		}
		if (this.hasTool())
		{
			map.put(TOOL, this.getTool().serialize());
		}
		if (this.hasEquippable())
		{
			map.put(EQUIPPABLE, this.getEquippable().serialize());
		}
		if (this.hasJukeboxPlayable())
		{
			map.put(JUKEBOX_PLAYABLE, this.getJukeboxPlayable().serialize());
		}
		if (this.hasItemName())
		{
			map.put(ITEM_NAME, toComponentString(this.itemName()));
		}
		if (this.hasEnchantable())
		{
			map.put(ENCHANTABLE, this.getEnchantable());
		}
		if (this.hasBlockData())
		{
			map.put(BLOCK_DATA, this.getBlockData());
		}

		/* Not implemented.
		if (!this.customTagContainer.isEmpty())
		{
			map.put("customTagContainer", this.customTagContainer);
		}
		*/

		map.put(META_TYPE, getTypeName());

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
		@Nullable Component displayName = NbtParser.parseComponent(args.get(DISPLAY_NAME));
		if (displayName != null)
		{
			displayName(displayName);
		}

		@Nullable Component itemName = NbtParser.parseComponent(args.get(ITEM_NAME));
		if (itemName != null)
		{
			itemName(itemName);
		}

		@Nullable List<@Nullable Component> loreList = NbtParser.parseList(args.get(LORE), NbtParser::parseComponent);
		if (loreList != null)
		{
			lore(loreList);
		}

		customModelData = NbtParser.parseInteger(args.get(CUSTOM_MODEL_DATA));
		enchantable = NbtParser.parseInteger(args.get(ENCHANTABLE));
		damage = NbtParser.parseInteger(args.get(DAMAGE));
		maxDamage = NbtParser.parseInteger(args.get(MAX_DAMAGE));
		repairCost = NbtParser.parseInteger(args.get(REPAIR_COST), 0);
		tooltipStyle = NbtParser.parseNamespacedKey(args.get(TOOL_TIP_STYLE));
		itemModel = NbtParser.parseNamespacedKey(args.get(ITEM_MODEL));
		unbreakable = NbtParser.parseBoolean(args.get(UNBREAKABLE), false);
		hideTooltip = NbtParser.parseBoolean(args.get(HIDE_TOOLTIP), false);
		fireResistant = NbtParser.parseBoolean(args.get(FIRE_RESISTANT), false);
		maxStackSize = NbtParser.parseInteger(args.get(MAX_STACK_SIZE));
		enchantmentGlintOverride = NbtParser.parseBoolean(args.get(ENCHANTMENT_GLINT_OVERRIDE));
		glider = NbtParser.parseBoolean(args.get(GLIDER), false);
		rarity = NbtParser.parseEnum(args.get(RARITY), ItemRarity.class);
		damageResistant = NbtParser.parseNamespacedKey(args.get(DAMAGE_RESISTANT));

		enchants = new HashMap<>();
		Map<String, Integer> enchantMap = NbtParser.parseMap(args.get(ENCHANTMENTS), NbtParser::parseInteger);
		if (enchantMap != null)
		{
			for (Map.Entry<String, Integer> entry : enchantMap.entrySet())
			{
				Enchantment enchantment = Registry.ENCHANTMENT.get(NamespacedKey.fromString(entry.getKey()));
				if (enchantment != null)
				{
					enchants.put(enchantment, entry.getValue());
				}
			}
		}
		setAttributeModifiers((Multimap<Attribute, AttributeModifier>) args.get(ATTRIBUTE_MODIFIERS));
		Set<ItemFlag> tempSet = NbtParser.parseSet(args.get(ITEM_FLAGS), o -> NbtParser.parseEnum(o, ItemFlag.class));
		if (tempSet != null)
		{
			hideFlags = tempSet;
		}
		Map<String, Object> map = NbtParser.parseMap(args.get(PUBLIC_BUKKIT_VALUES), Function.identity());
		if (map != null)
		{
			persistentDataContainer = PersistentDataContainerMock.deserialize(map);
		}

		var useRemainderData = NbtParser.parseMap(args.get(USE_REMAINDER), Function.identity());
		useRemainder = (useRemainderData == null ? null : ItemStack.deserialize(useRemainderData));
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public boolean hasLocalizedName()
	{
		return false;
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public @NotNull String getLocalizedName()
	{
		return getDisplayName();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public void setLocalizedName(@Nullable String name)
	{
		// no-op
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
		return !enchants.isEmpty() ? ImmutableSortedMap.copyOf(enchants,
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
		return checkConflictingEnchants(this.enchants, ench);
	}

	@Override
	public void addItemFlags(ItemFlag... itemFlags)
	{
		hideFlags.addAll(List.of(itemFlags));
	}

	@Override
	public void removeItemFlags(ItemFlag... itemFlags)
	{
		List.of(itemFlags).forEach(hideFlags::remove);
	}

	@Override
	public @NotNull Set<ItemFlag> getItemFlags()
	{
		return Set.copyOf(hideFlags);
	}

	@Override
	public boolean hasItemFlag(@NotNull ItemFlag flag)
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

	@Override
	public boolean hasAttributeModifiers()
	{
		return !attributeModifiers.isEmpty();
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

		this.attributeModifiers.clear();

		attributeModifiers.entries().stream()
				.filter(entry -> entry.getKey() != null && entry.getValue() != null)
				.forEach(entry -> this.attributeModifiers.put(entry.getKey(), entry.getValue()));
	}

	@Override
	public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers(@NotNull EquipmentSlot slot)
	{
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
		return this.attributeModifiers.containsKey(attribute)
				? ImmutableList.copyOf(this.attributeModifiers.get(attribute))
				: null;
	}

	@Override
	public boolean addAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier)
	{
		Preconditions.checkNotNull(attribute, "Attribute cannot be null");
		Preconditions.checkNotNull(modifier, "AttributeModifier cannot be null");
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
		return !this.attributeModifiers.removeAll(attribute).isEmpty();
	}

	@Override
	public boolean removeAttributeModifier(@NotNull EquipmentSlot slot)
	{
		// Match against null because as of 1.13, AttributeModifiers without a set slot are active in any slot.
		return this.attributeModifiers.entries().removeIf(entry -> entry.getValue().getSlot() == null || entry.getValue().getSlot() == slot);
	}

	@Override
	public boolean removeAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier)
	{
		Preconditions.checkNotNull(attribute, "Attribute cannot be null");
		Preconditions.checkNotNull(modifier, "AttributeModifier cannot be null");

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
	public @NotNull CustomModelDataComponent getCustomModelDataComponent()
	{
		return this.hasCustomModelDataComponent() ? this.customModelDataComponent : CustomModelDataComponentMock.useDefault();
	}

	@Override
	public void setCustomModelData(@Nullable Integer data)
	{
		this.customModelData = data;
	}

	@Override
	public boolean hasCustomModelDataComponent()
	{
		return this.customModelDataComponent != null;
	}

	@Override
	public void setCustomModelDataComponent(@Nullable CustomModelDataComponent customModelDataComponent)
	{
		this.customModelDataComponent = customModelDataComponent;
	}

	@Override
	public boolean hasEnchantable()
	{
		return this.enchantable != null;
	}

	@Override
	public int getEnchantable()
	{
		Preconditions.checkState(this.hasEnchantable(), "We don't have Enchantable! Check hasEnchantable first!");
		return this.enchantable;
	}

	@Override
	public void setEnchantable(@Nullable Integer data)
	{
		Preconditions.checkArgument(data == null || data > 0, "Enchantability must be positive"); // Paper
		this.enchantable = data;
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
		Preconditions.checkState(this.hasMaxDamage(), "We don't have max-damage! Check hasMaxDamage first!");
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
		}
		else
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
		return this.tooltipStyle != null;
	}

	@Override
	public @Nullable NamespacedKey getTooltipStyle()
	{
		return this.tooltipStyle;
	}

	@Override
	public void setTooltipStyle(@Nullable NamespacedKey tooltipStyle)
	{
		this.tooltipStyle = tooltipStyle;
	}

	@Override
	public boolean hasItemModel()
	{
		return this.itemModel != null;
	}

	@Override
	public @Nullable NamespacedKey getItemModel()
	{
		return this.itemModel;
	}

	@Override
	public void setItemModel(@Nullable NamespacedKey namespacedKey)
	{
		this.itemModel = namespacedKey;
	}

	@Override
	public boolean isGlider()
	{
		return this.glider;
	}

	@Override
	public void setGlider(boolean glider)
	{
		this.glider = glider;
	}

	@Override
	public boolean hasDamageResistant()
	{
		return this.damageResistant != null;
	}

	@Override
	public @Nullable Tag<DamageType> getDamageResistant()
	{
		return this.damageResistant != null ? Bukkit.getTag("damage_types", this.damageResistant, DamageType.class) : null;
	}

	@Override
	public void setDamageResistant(@Nullable Tag<DamageType> tag)
	{
		this.damageResistant = (tag == null ? null : tag.getKey());
	}

	@Override
	public boolean hasUseRemainder()
	{
		return this.useRemainder != null;
	}

	@Override
	public @Nullable ItemStack getUseRemainder()
	{
		return this.useRemainder;
	}

	@Override
	public void setUseRemainder(@Nullable ItemStack itemStack)
	{
		Preconditions.checkArgument(itemStack == null || !itemStack.isEmpty(), "Item cannot be empty");
		this.useRemainder = itemStack;
	}

	@Override
	public boolean hasUseCooldown()
	{
		return this.useCooldown != null;
	}

	@Override
	public UseCooldownComponent getUseCooldown()
	{
		return this.hasUseCooldown() ? this.useCooldown : UseCooldownComponentMock.useDefault();
	}

	@Override
	public void setUseCooldown(@Nullable UseCooldownComponent useCooldown)
	{
		this.useCooldown = useCooldown;
	}

	@Override
	public boolean hasEquippable()
	{
		return this.equippableComponent != null;
	}

	@Override
	public @NotNull EquippableComponent getEquippable()
	{
		return this.hasEquippable() ? this.equippableComponent : EquippableComponentMock.useDefault();
	}

	@Override
	public void setEquippable(@Nullable EquippableComponent equippableComponent)
	{
		this.equippableComponent = equippableComponent;
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
		return this.foodComponent != null;
	}

	@Override
	public @NotNull FoodComponent getFood()
	{
		return this.hasFood() ? this.foodComponent : FoodComponentMock.useDefault();
	}

	@Override
	public void setFood(@Nullable FoodComponent food)
	{
		this.foodComponent = food;
	}

	@ApiStatus.Internal
	protected String getTypeName()
	{
		return "UNSPECIFIC";
	}

	@Override
	public boolean hasTool()
	{
		return this.toolComponent != null;
	}

	@Override
	public @NotNull ToolComponent getTool()
	{
		return this.hasTool() ? this.toolComponent : ToolComponentMock.useDefault();
	}

	@Override
	public void setTool(@Nullable ToolComponent toolComponent)
	{
		this.toolComponent = toolComponent;
	}

	@Override
	public boolean hasJukeboxPlayable()
	{
		return this.jukeboxPlayableComponent != null;
	}

	@Override
	public @NotNull JukeboxPlayableComponent getJukeboxPlayable()
	{
		return this.hasJukeboxPlayable() ? this.jukeboxPlayableComponent : JukeboxPlayableComponentMock.useDefault();
	}

	@Override
	public void setJukeboxPlayable(@Nullable JukeboxPlayableComponent jukeboxPlayable)
	{
		this.jukeboxPlayableComponent = jukeboxPlayable;
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

	/**
	 * Check if the item meta has block data.
	 *
	 * @return {@code true} if has block data, otherwise {@code false}.
	 */
	public boolean hasBlockData()
	{
		return this.blockData != null;
	}

	/**
	 * Get the current item block data.
	 *
	 * @return The block data if available, otherwise {@code null}
	 */
	@Nullable
	public Map<String, String> getBlockData()
	{
		return this.blockData;
	}

	/**
	 * Set the block data in the item.
	 *
	 * @param blockData new block data.
	 */
	public void setBlockData(@Nullable Map<String, String> blockData)
	{
		this.blockData = blockData;
	}

	private static String toComponentString(Component component)
	{
		return GsonComponentSerializer.gson().serialize(component);
	}

}

