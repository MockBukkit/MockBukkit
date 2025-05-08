package org.mockbukkit.mockbukkit.inventory.meta;

import com.destroystokyo.paper.Namespaced;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import io.papermc.paper.datacomponent.item.DamageResistant;
import io.papermc.paper.datacomponent.item.Enchantable;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.datacomponent.item.TooltipDisplay;
import io.papermc.paper.registry.keys.tags.DamageTypeTagKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
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
import org.mockbukkit.mockbukkit.exception.ItemMetaInitException;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.persistence.PersistentDataContainerMock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mock implementation of an {@link ItemMeta}, {@link Damageable}, and {@link Repairable}.
 */
public class ItemMetaMock implements ItemMeta, Damageable, Repairable
{

	private static final int ABSOLUTE_MAX_STACK_SIZE = 99;
	private Map<DataComponentType, Object> data;
	private @NotNull PersistentDataContainerMock persistentDataContainer = new PersistentDataContainerMock();

	/**
	 * Constructs a new {@link ItemMetaMock}.
	 */
	public ItemMetaMock()
	{
		this.data = new HashMap<>();
	}

	@ApiStatus.Internal
	public ItemMetaMock(Map<DataComponentType, Object> data)
	{
		this.data = data;
	}

	/**
	 * Use ItemStack#getData instead. This allows illegal operations
	 *
	 * @return A map of all the item meta-data
	 */
	@ApiStatus.Internal
	public Map<DataComponentType, Object> getData()
	{
		return this.data;
	}

	/**
	 * Constructs a new {@link ItemMetaMock}, copying the data from another.
	 *
	 * @param meta The meta to copy.
	 */
	public ItemMetaMock(@NotNull ItemMeta meta)
	{
		if (!(meta instanceof ItemMetaMock metaMock))
		{
			throw new IllegalArgumentException("Expected a meta mock instance!");
		}
		this.data = copy(metaMock.data);

		this.persistentDataContainer = metaMock.persistentDataContainer;
	}

	private static <K, V> Map<K, V> copy(Map<K, V> input)
	{
		Map<K, V> output = new HashMap<>(input);
		output.keySet()
				.forEach(key -> input.computeIfPresent(key, (ignored, value) ->
				{
					if (value instanceof List<?> list)
					{
						return (V) new ArrayList<>(list);
					}
					if (value instanceof Map<?, ?> map)
					{
						return (V) new HashMap<>(map);
					}
					return value;
				}));
		return output;
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

	protected <T> @Nullable T get(DataComponentType.Valued<T> dataComponentType)
	{
		return (T) data.get(dataComponentType);
	}

	protected <T> @NotNull T getOrDefault(DataComponentType.Valued<T> dataComponentType, @NotNull T defaultT)
	{
		T value = get(dataComponentType);
		return value == null ? defaultT : value;
	}

	protected boolean get(DataComponentType.NonValued dataComponentType)
	{
		return data.containsKey(dataComponentType);
	}

	protected <T> void set(DataComponentType.Valued<T> dataComponentType, @Nullable T value)
	{
		data.put(dataComponentType, value);
	}

	protected void set(DataComponentType.NonValued dataComponentType)
	{
		data.put(dataComponentType, true);
	}

	protected void unset(DataComponentType dataComponentType)
	{
		data.remove(dataComponentType);
	}

	protected boolean has(DataComponentType dataComponentType)
	{
		return data.containsKey(dataComponentType);
	}

	@Override
	public boolean hasCustomName()
	{
		return data.containsKey(DataComponentTypes.CUSTOM_NAME);
	}

	@Override
	public @Nullable Component customName()
	{
		return get(DataComponentTypes.CUSTOM_NAME);
	}

	@Override
	public void customName(@Nullable Component component)
	{
		if (component == null)
		{
			unset(DataComponentTypes.CUSTOM_NAME);
		}
		else
		{
			set(DataComponentTypes.CUSTOM_NAME, component);
		}
	}

	@Override
	public @NotNull String getDisplayName()
	{
		Component customName = get(DataComponentTypes.CUSTOM_NAME);
		return customName != null ? LegacyComponentSerializer.legacySection().serialize(customName) : "";
	}

	@Override
	public @NotNull BaseComponent @NotNull [] getDisplayNameComponent()
	{
		Component customName = get(DataComponentTypes.CUSTOM_NAME);
		Component component = customName != null ? customName : Component.empty();
		return BungeeComponentSerializer.get().serialize(component);
	}

	@Override
	public void setDisplayName(@Nullable String name)
	{
		if (name == null)
		{
			unset(DataComponentTypes.CUSTOM_NAME);
			return;
		}
		set(DataComponentTypes.CUSTOM_NAME, LegacyComponentSerializer.legacySection().deserialize(name));
	}

	@Override
	public void setDisplayNameComponent(BaseComponent @NotNull [] components)
	{
		set(DataComponentTypes.CUSTOM_NAME, BungeeComponentSerializer.get().deserialize(Arrays.stream(components).filter(Objects::nonNull).toArray(BaseComponent[]::new)));
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(data, persistentDataContainer);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof ItemMetaMock other))
		{
			return false;
		}
		return Objects.equals(this.data, other.data) && Objects.equals(this.persistentDataContainer, other.persistentDataContainer);
	}

	@Override
	public @NotNull ItemMetaMock clone()
	{
		try
		{
			ItemMetaMock meta = (ItemMetaMock) super.clone();
			meta.data = copy(data);
			meta.persistentDataContainer = new PersistentDataContainerMock(persistentDataContainer);
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
	public @NotNull Set<Namespaced> getDestroyableKeys()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public void setDestroyableKeys(@NotNull Collection<Namespaced> collection)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public @NotNull Set<Namespaced> getPlaceableKeys()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public void setPlaceableKeys(@NotNull Collection<Namespaced> collection)
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
		return data.containsKey(DataComponentTypes.LORE);
	}

	@Override
	public @Nullable List<Component> lore()
	{
		return !hasLore() ? null : get(DataComponentTypes.LORE).lines();
	}

	@Override
	public void lore(@Nullable List<? extends Component> lore)
	{
		if (lore != null && !lore.isEmpty())
		{
			set(DataComponentTypes.LORE, ItemLore.lore(lore));
		}
		else
		{
			unset(DataComponentTypes.LORE);
		}
	}

	@Override
	public @Nullable List<String> getLore()
	{
		ItemLore lore = get(DataComponentTypes.LORE);
		return lore == null ? null : lore.lines()
				.stream()
				.map(LegacyComponentSerializer.legacySection()::serialize)
				.toList();
	}

	@Override
	public @Nullable List<BaseComponent[]> getLoreComponents()
	{
		ItemLore lore = get(DataComponentTypes.LORE);
		return lore == null ? null : lore.lines().stream()
				.map(BungeeComponentSerializer.get()::serialize)
				.toList();
	}

	@Override
	public void setLore(@Nullable List<String> lore)
	{
		if (lore != null && !lore.isEmpty())
		{
			ItemLore loreToSet = ItemLore.lore(lore.stream()
					.map(LegacyComponentSerializer.legacySection()::deserialize)
					.toList()
			);
			set(DataComponentTypes.LORE, loreToSet);
		}
		else
		{
			data.remove(DataComponentTypes.LORE);
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
		ItemLore lore = get(DataComponentTypes.LORE);
		if (lore == null)
		{
			throw new AssertionError("No lore was set");
		}
		List<Component> loreLines = lore.lines();
		if (loreLines.size() != lines.size())
		{
			throw new AssertionError("Lore size mismatch: expected " + lines.size() + " but was " + loreLines.size());
		}
		for (int i = 0; i < loreLines.size(); i++)
		{
			if (loreLines.get(i).equals(lines.get(i)))
			{
				continue;
			}
			throw new AssertionError(String.format("Line %d should be '%s' but was '%s'", i, lines.get(i), loreLines.get(i)));
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
		Map<String, Object> output = new HashMap<>();
		for (Map.Entry<DataComponentType, Object> entry : data.entrySet())
		{
			NamespacedKey key = entry.getKey().getKey();
			output.put(key.getKey(), entry.getValue());
		}
		output.put("PublicBukkitValues", this.persistentDataContainer.serialize());
		return output;
	}

	public static @NotNull ItemMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		ItemMetaMock serialMock = new ItemMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

	protected void deserializeInternal(@NotNull Map<String, Object> args)
	{
		for (Map.Entry<String, Object> entry : args.entrySet())
		{
			if (entry.getKey().equals("PublicBukkitValues"))
			{
				this.persistentDataContainer = (PersistentDataContainerMock) entry.getValue();
				continue;
			}
			NamespacedKey key = NamespacedKey.minecraft(entry.getKey());
			// TODO proper nbt values
			data.put(Registry.DATA_COMPONENT_TYPE.get(key), entry.getValue());
		}
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
		return data.containsKey(DataComponentTypes.ENCHANTMENTS);
	}

	@Override
	public boolean hasEnchant(Enchantment ench)
	{
		if (!hasEnchants())
		{
			return false;
		}
		return get(DataComponentTypes.ENCHANTMENTS).enchantments().containsKey(ench);
	}

	@Override
	public int getEnchantLevel(Enchantment ench)
	{
		return hasEnchant(ench) ? get(DataComponentTypes.ENCHANTMENTS).enchantments().get(ench) : 0;
	}

	@Override
	public @NotNull Map<Enchantment, Integer> getEnchants()
	{
		return hasEnchants() ?
				ImmutableMap.copyOf(get(DataComponentTypes.ENCHANTMENTS).enchantments())
				: ImmutableMap.of();
	}

	@Override
	public boolean addEnchant(@NotNull Enchantment ench, int level, boolean ignoreLevelRestriction)
	{
		Preconditions.checkNotNull(ench);
		if ((ench.getMaxLevel() < level || level < ench.getStartLevel()) && !ignoreLevelRestriction)
		{
			return false;
		}
		ItemEnchantments itemEnchantments;
		if (!hasEnchants())
		{
			itemEnchantments = ItemEnchantments.itemEnchantments(Map.of(ench, level));
		}
		else
		{
			ItemEnchantments previousItemEnchantments = get(DataComponentTypes.ENCHANTMENTS);
			if (Objects.equals(previousItemEnchantments.enchantments().get(ench), level))
			{
				return false;
			}
			itemEnchantments = ItemEnchantments.itemEnchantments()
					.addAll(previousItemEnchantments.enchantments())
					.add(ench, level)
					.build();
		}
		set(DataComponentTypes.ENCHANTMENTS, itemEnchantments);
		return true;
	}

	@Override
	public boolean removeEnchant(Enchantment ench)
	{
		if (!hasEnchants())
		{
			return false;
		}
		ItemEnchantments itemEnchantments = get(DataComponentTypes.ENCHANTMENTS);
		Map<Enchantment, Integer> enchantments = itemEnchantments.enchantments();
		Map<Enchantment, Integer> newEnchantments = enchantments
				.entrySet()
				.stream()
				.filter(entry -> !entry.getKey().equals(ench))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		set(DataComponentTypes.ENCHANTMENTS, ItemEnchantments.itemEnchantments(newEnchantments));
		return enchantments.containsKey(ench);
	}

	@Override
	public void removeEnchantments()
	{
		unset(DataComponentTypes.ENCHANTMENTS);
	}

	@Override
	public boolean hasConflictingEnchant(Enchantment ench)
	{
		if (!hasEnchants())
		{
			return false;
		}
		return checkConflictingEnchants(get(DataComponentTypes.ENCHANTMENTS).enchantments(), ench);
	}

	@Override
	public void addItemFlags(ItemFlag... itemFlags)
	{
		TooltipDisplay tooltipDisplay = get(DataComponentTypes.TOOLTIP_DISPLAY);
		Set<DataComponentType> hidden;
		if (tooltipDisplay == null)
		{
			hidden = new HashSet<>();
		}
		else
		{
			hidden = new HashSet<>(tooltipDisplay.hiddenComponents());
		}
		Arrays.stream(itemFlags)
				.map(this::toDataComponentType)
				.filter(Objects::nonNull)
				.forEach(hidden::add);
		set(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplay.tooltipDisplay().hiddenComponents(hidden).build());
	}

	@Override
	public void removeItemFlags(ItemFlag... itemFlags)
	{
		TooltipDisplay tooltipDisplay = get(DataComponentTypes.TOOLTIP_DISPLAY);
		if (tooltipDisplay == null)
		{
			return;
		}
		Set<DataComponentType> hidden = new HashSet<>(tooltipDisplay.hiddenComponents());
		Arrays.stream(itemFlags)
				.map(this::toDataComponentType)
				.forEach(hidden::remove);
		set(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplay.tooltipDisplay().hiddenComponents(hidden).build());
	}

	private DataComponentType toDataComponentType(ItemFlag itemFlag)
	{
		return switch (itemFlag)
		{
			case HIDE_ENCHANTS -> DataComponentTypes.ENCHANTMENTS;
			case HIDE_ATTRIBUTES -> DataComponentTypes.ATTRIBUTE_MODIFIERS;
			case HIDE_UNBREAKABLE -> DataComponentTypes.UNBREAKABLE;
			case HIDE_DESTROYS -> DataComponentTypes.CAN_BREAK;
			case HIDE_PLACED_ON -> DataComponentTypes.CAN_PLACE_ON;
			case HIDE_ADDITIONAL_TOOLTIP -> null;
			case HIDE_DYE -> DataComponentTypes.DYED_COLOR;
			case HIDE_ARMOR_TRIM -> DataComponentTypes.TRIM;
			case HIDE_STORED_ENCHANTS -> DataComponentTypes.STORED_ENCHANTMENTS;
		};
	}

	private @Nullable ItemFlag toItemFlag(DataComponentType dataComponentType)
	{
		if (dataComponentType == DataComponentTypes.ENCHANTMENTS)
		{
			return ItemFlag.HIDE_ENCHANTS;
		}
		if (dataComponentType == DataComponentTypes.ATTRIBUTE_MODIFIERS)
		{
			return ItemFlag.HIDE_ATTRIBUTES;
		}
		if (dataComponentType == DataComponentTypes.UNBREAKABLE)
		{
			return ItemFlag.HIDE_UNBREAKABLE;
		}
		if (dataComponentType == DataComponentTypes.CAN_BREAK)
		{
			return ItemFlag.HIDE_DESTROYS;
		}
		if (dataComponentType == DataComponentTypes.CAN_PLACE_ON)
		{
			return ItemFlag.HIDE_PLACED_ON;
		}
		if (dataComponentType == DataComponentTypes.DYED_COLOR)
		{
			return ItemFlag.HIDE_DYE;
		}
		if (dataComponentType == DataComponentTypes.TRIM)
		{
			return ItemFlag.HIDE_ARMOR_TRIM;
		}
		if (dataComponentType == DataComponentTypes.STORED_ENCHANTMENTS)
		{
			return ItemFlag.HIDE_STORED_ENCHANTS;
		}
		return null;
	}

	@Override
	public @NotNull Set<ItemFlag> getItemFlags()
	{
		TooltipDisplay tooltipDisplay = get(DataComponentTypes.TOOLTIP_DISPLAY);
		if (tooltipDisplay == null)
		{
			return Set.of();
		}
		return tooltipDisplay.hiddenComponents().stream()
				.map(this::toItemFlag)
				.filter(Objects::nonNull)
				.collect(Collectors.toUnmodifiableSet());
	}

	@Override
	public boolean hasItemFlag(@NotNull ItemFlag flag)
	{
		return getItemFlags().contains(flag);
	}

	@Override
	public boolean isUnbreakable()
	{
		return data.containsKey(DataComponentTypes.UNBREAKABLE);
	}

	@Override
	public void setUnbreakable(boolean unbreakable)
	{
		set(DataComponentTypes.UNBREAKABLE);
	}

	@Override
	public boolean hasDamage()
	{
		return data.containsKey(DataComponentTypes.DAMAGE) && get(DataComponentTypes.DAMAGE) > 0;
	}

	@Override
	public int getDamage()
	{
		return !data.containsKey(DataComponentTypes.DAMAGE) ? 0 : get(DataComponentTypes.DAMAGE);
	}

	@Override
	public void setDamage(int damage)
	{
		Preconditions.checkState(damage >= 0, "damage cannot be negative");
		set(DataComponentTypes.DAMAGE, damage);
	}

	@Override
	public boolean hasDamageValue()
	{
		return data.containsKey(DataComponentTypes.DAMAGE);
	}

	@Override
	public void resetDamage()
	{
		unset(DataComponentTypes.DAMAGE);
	}

	@Override
	public boolean hasRepairCost()
	{
		return data.containsKey(DataComponentTypes.REPAIR_COST) && get(DataComponentTypes.REPAIR_COST) > 0;
	}

	@Override
	public int getRepairCost()
	{
		return data.containsKey(DataComponentTypes.REPAIR_COST) ? get(DataComponentTypes.REPAIR_COST) : 0;
	}

	@Override
	public void setRepairCost(int cost)
	{
		set(DataComponentTypes.REPAIR_COST, cost);
	}

	@Override
	public boolean hasAttributeModifiers()
	{
		return data.containsKey(DataComponentTypes.ATTRIBUTE_MODIFIERS);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers()
	{
		ItemAttributeModifiers itemAttributeModifiers = get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
		return itemAttributeModifiers != null
				? itemAttributeModifiers.modifiers().stream().collect(ImmutableSetMultimap.toImmutableSetMultimap(ItemAttributeModifiers.Entry::attribute, ItemAttributeModifiers.Entry::modifier))
				: null;
	}

	@Override
	public void setAttributeModifiers(@Nullable Multimap<Attribute, AttributeModifier> attributeModifiers)
	{

		if (attributeModifiers == null || attributeModifiers.isEmpty())
		{
			unset(DataComponentTypes.ATTRIBUTE_MODIFIERS);
			return;
		}
		ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.itemAttributes();
		attributeModifiers.entries()
				.forEach(entry -> builder.addModifier(entry.getKey(), entry.getValue()));
		set(DataComponentTypes.ATTRIBUTE_MODIFIERS, builder.build());
	}

	@Override
	public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers(@NotNull EquipmentSlot slot)
	{
		ItemAttributeModifiers modifiers = get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
		if (modifiers == null)
		{
			return ImmutableSetMultimap.of();
		}
		return modifiers.modifiers()
				.stream()
				.filter(entry -> entry.modifier().getSlot().equals(slot))
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(ItemAttributeModifiers.Entry::attribute, ItemAttributeModifiers.Entry::modifier));
	}

	@Override
	public Collection<AttributeModifier> getAttributeModifiers(@NotNull Attribute attribute)
	{
		Preconditions.checkNotNull(attribute, "Attribute cannot be null");
		ItemAttributeModifiers modifiers = get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
		if (modifiers == null)
		{
			return null;
		}
		List<AttributeModifier> output = modifiers.modifiers()
				.stream()
				.filter(entry -> entry.attribute().equals(attribute))
				.map(ItemAttributeModifiers.Entry::modifier)
				.collect(Collectors.toUnmodifiableList());
		if (output.isEmpty())
		{
			return null;
		}
		return output;
	}

	@Override
	public boolean addAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier attributeModifier)
	{
		Preconditions.checkNotNull(attribute, "Attribute cannot be null");
		Preconditions.checkNotNull(attributeModifier, "AttributeModifier cannot be null");
		ItemAttributeModifiers modifiers = get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
		if (modifiers == null)
		{
			set(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes().addModifier(attribute, attributeModifier).build());
			return true;
		}
		else
		{
			ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.itemAttributes();
			modifiers.modifiers().forEach(modifier -> builder.addModifier(modifier.attribute(), modifier.modifier()));
			builder.addModifier(attribute, attributeModifier);
			ItemAttributeModifiers newModifiers = builder.build();
			set(DataComponentTypes.ATTRIBUTE_MODIFIERS, newModifiers);
			return newModifiers.modifiers().equals(modifiers.modifiers());
		}
	}

	@Override
	public boolean removeAttributeModifier(@NotNull Attribute attribute)
	{
		Preconditions.checkNotNull(attribute, "Attribute cannot be null");
		ItemAttributeModifiers modifiers = get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
		if (modifiers == null)
		{
			return false;
		}
		List<ItemAttributeModifiers.Entry> entries = modifiers.modifiers();
		List<ItemAttributeModifiers.Entry> newEntries = entries.stream()
				.filter(entry -> !entry.attribute().equals(attribute))
				.toList();
		ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.itemAttributes();
		newEntries.forEach(entry -> builder.addModifier(entry.attribute(), entry.modifier()));
		set(DataComponentTypes.ATTRIBUTE_MODIFIERS, builder.build());
		return newEntries.size() != entries.size();
	}

	@Override
	public boolean removeAttributeModifier(@Nullable EquipmentSlot slot)
	{
		// Match against null because as of 1.13, AttributeModifiers without a set slot are active in any slot.
		ItemAttributeModifiers modifiers = get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
		if (modifiers == null)
		{
			return false;
		}
		List<ItemAttributeModifiers.Entry> entries = modifiers.modifiers();
		List<ItemAttributeModifiers.Entry> newEntries = entries.stream()
				.filter(entry -> !entry.getGroup().test(slot))
				.toList();
		ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.itemAttributes();
		newEntries.forEach(entry -> builder.addModifier(entry.attribute(), entry.modifier()));
		set(DataComponentTypes.ATTRIBUTE_MODIFIERS, builder.build());
		return newEntries.size() != entries.size();
	}

	@Override
	public boolean removeAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier)
	{
		Preconditions.checkNotNull(attribute, "Attribute cannot be null");
		Preconditions.checkNotNull(modifier, "AttributeModifier cannot be null");

		ItemAttributeModifiers modifiers = get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
		if (modifiers == null)
		{
			return false;
		}
		List<ItemAttributeModifiers.Entry> entries = modifiers.modifiers();
		List<ItemAttributeModifiers.Entry> newEntries = entries.stream()
				.filter(entry -> !entry.attribute().equals(attribute) || !entry.modifier().equals(modifier))
				.toList();
		ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.itemAttributes();
		newEntries.forEach(entry -> builder.addModifier(entry.attribute(), entry.modifier()));
		set(DataComponentTypes.ATTRIBUTE_MODIFIERS, builder.build());
		return newEntries.size() != entries.size();
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
		return data.containsKey(DataComponentTypes.CUSTOM_MODEL_DATA);
	}

	@Override
	public int getCustomModelData()
	{
		Preconditions.checkState(hasCustomModelData(), "We don't have CustomModelData! Check hasCustomModelData first!");
		return get(DataComponentTypes.CUSTOM_MODEL_DATA).floats().get(0).intValue();
	}

	@Override
	public @NotNull CustomModelDataComponent getCustomModelDataComponent()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCustomModelData(@Nullable Integer data)
	{
		if (data == null)
		{
			unset(DataComponentTypes.CUSTOM_MODEL_DATA);
		}
		else
		{
			set(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData()
					.addFloat(data.floatValue()).build());
		}
	}

	@Override
	public boolean hasCustomModelDataComponent()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCustomModelDataComponent(@Nullable CustomModelDataComponent customModelDataComponent)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasEnchantable()
	{
		return data.containsKey(DataComponentTypes.ENCHANTABLE);
	}

	@Override
	public int getEnchantable()
	{
		Preconditions.checkState(this.hasEnchantable(), "We don't have Enchantable! Check hasEnchantable first!");
		return get(DataComponentTypes.ENCHANTABLE).value();
	}

	@Override
	public void setEnchantable(@Nullable Integer data)
	{
		Preconditions.checkArgument(data == null || data > 0, "Enchantability must be positive"); // Paper
		set(DataComponentTypes.ENCHANTABLE, Enchantable.enchantable(data));
	}

	@Override
	public void setVersion(int version)
	{
		// No use yet
	}

	@Override
	public boolean hasMaxDamage()
	{
		return data.containsKey(DataComponentTypes.MAX_DAMAGE);
	}

	@Override
	public int getMaxDamage()
	{
		Preconditions.checkState(this.hasMaxDamage(), "We don't have max_damage! Check hasMaxDamage first!");
		return get(DataComponentTypes.MAX_DAMAGE);
	}

	@Override
	public void setMaxDamage(@Nullable Integer maxDamage)
	{
		if (maxDamage == null)
		{
			unset(DataComponentTypes.MAX_DAMAGE);
		}
		else
		{
			set(DataComponentTypes.MAX_DAMAGE, maxDamage);
		}
	}

	@Override
	public boolean hasItemName()
	{
		return data.containsKey(DataComponentTypes.ITEM_NAME);
	}

	@Override
	public @NotNull Component itemName()
	{
		return !hasItemName() ? Component.empty() : get(DataComponentTypes.ITEM_NAME);
	}

	@Override
	public void itemName(@Nullable Component name)
	{
		if (name == null)
		{
			unset(DataComponentTypes.ITEM_NAME);
		}
		else
		{
			set(DataComponentTypes.ITEM_NAME, name);
		}
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
			unset(DataComponentTypes.ITEM_NAME);
		}
		else
		{
			set(DataComponentTypes.ITEM_NAME, LegacyComponentSerializer.legacySection().deserialize(name));
		}
	}

	@Override
	public boolean isHideTooltip()
	{
		TooltipDisplay tooltipDisplay = get(DataComponentTypes.TOOLTIP_DISPLAY);
		if (tooltipDisplay == null)
		{
			return false;
		}
		return tooltipDisplay.hideTooltip();
	}

	@Override
	public void setHideTooltip(boolean hideTooltip)
	{
		TooltipDisplay tooltipDisplay = get(DataComponentTypes.TOOLTIP_DISPLAY);
		if (tooltipDisplay == null)
		{
			set(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplay.tooltipDisplay().hideTooltip(hideTooltip).build());
		}
		else
		{
			set(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplay.tooltipDisplay().hiddenComponents(tooltipDisplay.hiddenComponents()).hideTooltip(hideTooltip).build());
		}
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
		return data.containsKey(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE);
	}

	@Override
	public @NotNull Boolean getEnchantmentGlintOverride()
	{
		Preconditions.checkState(this.hasEnchantmentGlintOverride(), "We don't have enchantment_glint_override! Check hasEnchantmentGlintOverride first!");
		return get(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE);
	}

	@Override
	public void setEnchantmentGlintOverride(@Nullable Boolean override)
	{
		if (override == null)
		{
			unset(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE);
		}
		else
		{
			set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, override);
		}
	}

	@Override
	public boolean isFireResistant()
	{
		DamageResistant damageResistant = get(DataComponentTypes.DAMAGE_RESISTANT);
		if (damageResistant == null)
		{
			return false;
		}
		return damageResistant.types().equals(DamageTypeTagKeys.IS_FIRE);
	}

	@Override
	public void setFireResistant(boolean fireResistant)
	{
		set(DataComponentTypes.DAMAGE_RESISTANT, DamageResistant.damageResistant(DamageTypeTagKeys.IS_FIRE));
	}

	@Override
	public boolean hasMaxStackSize()
	{
		return data.containsKey(DataComponentTypes.MAX_STACK_SIZE);
	}

	@Override
	public int getMaxStackSize()
	{
		Preconditions.checkState(hasMaxStackSize(), "We don't have max_stack_size! Check hasMaxStackSize first!");
		return get(DataComponentTypes.MAX_STACK_SIZE);
	}

	@Override
	public void setMaxStackSize(@Nullable Integer max)
	{
		Preconditions.checkArgument(max == null || max > 0, "max_stack_size must be > 0");
		Preconditions.checkArgument(max == null || max <= ABSOLUTE_MAX_STACK_SIZE, "max_stack_size must be <= 99");
		if (max == null)
		{
			unset(DataComponentTypes.MAX_STACK_SIZE);
		}
		else
		{
			set(DataComponentTypes.MAX_STACK_SIZE, max);
		}
	}

	@Override
	public boolean hasRarity()
	{
		return data.containsKey(DataComponentTypes.RARITY);
	}

	@Override
	public @NotNull ItemRarity getRarity()
	{
		Preconditions.checkState(this.hasRarity(), "We don't have rarity! Check hasRarity first!");
		return get(DataComponentTypes.RARITY);
	}

	@Override
	public void setRarity(@Nullable ItemRarity rarity)
	{
		if (rarity == null)
		{
			unset(DataComponentTypes.RARITY);
		}
		else
		{
			set(DataComponentTypes.RARITY, rarity);
		}
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

