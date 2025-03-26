package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentBuilder;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.inventory.ItemRarity;
import io.papermc.paper.inventory.tooltip.TooltipContext;
import io.papermc.paper.persistence.PersistentDataContainerView;
import io.papermc.paper.registry.set.RegistryKeySet;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Material;
import org.bukkit.UndefinedNullability;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.persistence.PersistentDataContainer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Helper class that emulates the Bukkit mirror behaviour at CraftItemStack#asCraftMirror(net.minecraft.world.item.ItemStack original).
 *
 * @see ItemStack
 */
@ApiStatus.Internal
public final class ItemStackMirror extends ItemStack
{

	/**
	 * Creates a mirror of the {@link ItemStack}. Any change applied to this {@link ItemStack} will also
	 * affect the original {@link ItemStack}.
	 * <p/>
	 * This emulates the behaviour from <code>CraftItemStack#asCraftMirror(net.minecraft.world.item.ItemStack original)</code>.
	 *
	 * @param item The original item.
	 *
	 * @return The mirror item.
	 */
	@NotNull
	public static ItemStack create(@NonNull ItemStack item)
	{
		Preconditions.checkArgument(item != null, "item cannot be null");

		if (item instanceof ItemStackMirror mirror)
		{
			return mirror;
		}
		return new ItemStackMirror(item);
	}

	private final ItemStack itemStack;

	private ItemStackMirror(@NotNull ItemStack item)
	{
		Preconditions.checkArgument(item != null, "item cannot be null");
		this.itemStack = item;
	}

	@Override
	public @NotNull ItemStack add()
	{
		return itemStack.add();
	}

	@Override
	public @NotNull ItemStack add(int qty)
	{
		return itemStack.add(qty);
	}

	@Override
	public void addEnchantment(@NotNull Enchantment ench, int level)
	{
		itemStack.addEnchantment(ench, level);
	}

	@Override
	public void addEnchantments(@NotNull Map<Enchantment, Integer> enchantments)
	{
		itemStack.addEnchantments(enchantments);
	}

	@Override
	public void addItemFlags(@NotNull ItemFlag... itemFlags)
	{
		itemStack.addItemFlags(itemFlags);
	}

	@Override
	public void addUnsafeEnchantment(@NotNull Enchantment ench, int level)
	{
		itemStack.addUnsafeEnchantment(ench, level);
	}

	@Override
	public void addUnsafeEnchantments(@NotNull Map<Enchantment, Integer> enchantments)
	{
		itemStack.addUnsafeEnchantments(enchantments);
	}

	@Override
	public @NotNull HoverEvent<HoverEvent.ShowItem> asHoverEvent(@NotNull UnaryOperator<HoverEvent.ShowItem> op)
	{
		return itemStack.asHoverEvent(op);
	}

	@Override
	public @NotNull ItemStack asOne()
	{
		return itemStack.asOne();
	}

	@Override
	public @NotNull ItemStack asQuantity(int qty)
	{
		return itemStack.asQuantity(qty);
	}

	@Override
	public boolean canRepair(@NotNull ItemStack toBeRepaired)
	{
		return itemStack.canRepair(toBeRepaired);
	}

	@Override
	public @NotNull ItemStack clone()
	{
		return itemStack.clone();
	}

	@Override
	public @NotNull @Unmodifiable List<Component> computeTooltipLines(@NotNull TooltipContext tooltipContext, @Nullable Player player)
	{
		return itemStack.computeTooltipLines(tooltipContext, player);
	}

	@Override
	public boolean containsEnchantment(@NotNull Enchantment ench)
	{
		return itemStack.containsEnchantment(ench);
	}

	@ApiStatus.Experimental
	@Override
	public void copyDataFrom(@NotNull ItemStack source, @NotNull Predicate<@NotNull DataComponentType> filter)
	{
		itemStack.copyDataFrom(source, filter);
	}

	@Override
	public @NotNull ItemStack damage(int amount, @NotNull LivingEntity livingEntity)
	{
		return itemStack.damage(amount, livingEntity);
	}

	public static @NotNull ItemStack deserialize(@NotNull Map<String, Object> args)
	{
		return ItemStack.deserialize(args);
	}

	public static @NotNull ItemStack deserializeBytes(byte @NotNull [] bytes)
	{
		return ItemStack.deserializeBytes(bytes);
	}

	public static @NotNull ItemStack @NotNull [] deserializeItemsFromBytes(byte @NotNull [] bytes)
	{
		return ItemStack.deserializeItemsFromBytes(bytes);
	}

	@Override
	public @NotNull Component displayName()
	{
		return itemStack.displayName();
	}

	@Override
	public boolean editMeta(@NotNull Consumer<? super ItemMeta> consumer)
	{
		return itemStack.editMeta(consumer);
	}

	@Override
	public <M extends ItemMeta> boolean editMeta(@NotNull Class<M> metaClass, @NotNull Consumer<? super M> consumer)
	{
		return itemStack.editMeta(metaClass, consumer);
	}

	@Override
	public boolean editPersistentDataContainer(@NotNull Consumer<PersistentDataContainer> consumer)
	{
		return itemStack.editPersistentDataContainer(consumer);
	}

	@Override
	public @NotNull Component effectiveName()
	{
		return itemStack.effectiveName();
	}

	public static @NotNull ItemStack empty()
	{
		return ItemStack.empty();
	}

	@Override
	public @NotNull ItemStack enchantWithLevels(int levels, boolean allowTreasure, @NotNull Random random)
	{
		return itemStack.enchantWithLevels(levels, allowTreasure, random);
	}

	@Override
	public @NotNull ItemStack enchantWithLevels(int levels, @NotNull RegistryKeySet<@NotNull Enchantment> keySet, @NotNull Random random)
	{
		return itemStack.enchantWithLevels(levels, keySet, random);
	}

	@Override
	public @NotNull ItemStack ensureServerConversions()
	{
		return itemStack.ensureServerConversions();
	}

	@Override
	public boolean equals(Object obj)
	{
		return itemStack.equals(obj);
	}

	@Override
	public int getAmount()
	{
		return itemStack.getAmount();
	}

	@Deprecated(forRemoval = true, since = "1.13")
	@Override
	public @Nullable MaterialData getData()
	{
		return itemStack.getData();
	}

	@Contract(pure = true)
	@ApiStatus.Experimental
	@Override
	public <T> @Nullable T getData(DataComponentType.@NotNull Valued<T> type)
	{
		return itemStack.getData(type);
	}

	@Contract(value = "_, !null -> !null", pure = true)
	@ApiStatus.Experimental
	@Override
	public <T> @Nullable T getDataOrDefault(DataComponentType.@NotNull Valued<? extends T> type, @Nullable T fallback)
	{
		return itemStack.getDataOrDefault(type, fallback);
	}

	@Contract("-> new")
	@ApiStatus.Experimental
	@Override
	public @Unmodifiable Set<@NotNull DataComponentType> getDataTypes()
	{
		return itemStack.getDataTypes();
	}

	@Deprecated(since = "1.13")
	@Override
	public short getDurability()
	{
		return itemStack.getDurability();
	}

	@Override
	public int getEnchantmentLevel(@NotNull Enchantment ench)
	{
		return itemStack.getEnchantmentLevel(ench);
	}

	@Override
	public @NotNull Map<Enchantment, Integer> getEnchantments()
	{
		return itemStack.getEnchantments();
	}

	@Deprecated(forRemoval = true)
	@Override
	public @Nullable String getI18NDisplayName()
	{
		return itemStack.getI18NDisplayName();
	}

	@Override
	public @NotNull Set<ItemFlag> getItemFlags()
	{
		return itemStack.getItemFlags();
	}

	@UndefinedNullability
	@Override
	public ItemMeta getItemMeta()
	{
		return itemStack.getItemMeta();
	}

	@Deprecated(forRemoval = true)
	@Override
	public @Nullable List<String> getLore()
	{
		return itemStack.getLore();
	}

	@Deprecated(forRemoval = true)
	@Override
	public int getMaxItemUseDuration()
	{
		return itemStack.getMaxItemUseDuration();
	}

	@Override
	public int getMaxItemUseDuration(@NotNull LivingEntity entity)
	{
		return itemStack.getMaxItemUseDuration(entity);
	}

	@Override
	public int getMaxStackSize()
	{
		return itemStack.getMaxStackSize();
	}

	@Override
	public @NotNull PersistentDataContainerView getPersistentDataContainer()
	{
		return itemStack.getPersistentDataContainer();
	}

	@Deprecated(forRemoval = true, since = "1.20.5")
	@Override
	public @NotNull ItemRarity getRarity()
	{
		return itemStack.getRarity();
	}

	@Deprecated(forRemoval = true)
	@Override
	public @NotNull String getTranslationKey()
	{
		return itemStack.getTranslationKey();
	}

	@Override
	public @NotNull Material getType()
	{
		return itemStack.getType();
	}

	@Contract(pure = true)
	@ApiStatus.Experimental
	@Override
	public boolean hasData(@NotNull DataComponentType type)
	{
		return itemStack.hasData(type);
	}

	@Override
	public int hashCode()
	{
		return itemStack.hashCode();
	}

	@Override
	public boolean hasItemFlag(@NotNull ItemFlag flag)
	{
		return itemStack.hasItemFlag(flag);
	}

	@Override
	public boolean hasItemMeta()
	{
		return itemStack.hasItemMeta();
	}

	@ApiStatus.Experimental
	@Override
	public boolean isDataOverridden(@NotNull DataComponentType type)
	{
		return itemStack.isDataOverridden(type);
	}

	@Override
	public boolean isEmpty()
	{
		return itemStack.isEmpty();
	}

	@Override
	public boolean isRepairableBy(@NotNull ItemStack repairMaterial)
	{
		return itemStack.isRepairableBy(repairMaterial);
	}

	@Override
	public boolean isSimilar(@Nullable ItemStack stack)
	{
		return itemStack.isSimilar(stack);
	}

	@Override
	public @Nullable List<Component> lore()
	{
		return itemStack.lore();
	}

	@Override
	public void lore(@Nullable List<? extends Component> lore)
	{
		itemStack.lore(lore);
	}

	@ApiStatus.Experimental
	@Override
	public boolean matchesWithoutData(@NotNull ItemStack item, @NotNull Set<@NotNull DataComponentType> excludeTypes)
	{
		return itemStack.matchesWithoutData(item, excludeTypes);
	}

	@ApiStatus.Experimental
	@Override
	public boolean matchesWithoutData(@NotNull ItemStack item, @NotNull Set<@NotNull DataComponentType> excludeTypes, boolean ignoreCount)
	{
		return itemStack.matchesWithoutData(item, excludeTypes, ignoreCount);
	}

	@Contract(value = "_ -> new", pure = true)
	public static @NotNull ItemStack of(@NotNull Material type)
	{
		return ItemStack.of(type);
	}

	@Contract(value = "_, _ -> new", pure = true)
	public static @NotNull ItemStack of(@NotNull Material type, int amount)
	{
		return ItemStack.of(type, amount);
	}

	@Override
	public int removeEnchantment(@NotNull Enchantment ench)
	{
		return itemStack.removeEnchantment(ench);
	}

	@Override
	public void removeEnchantments()
	{
		itemStack.removeEnchantments();
	}

	@Override
	public void removeItemFlags(@NotNull ItemFlag... itemFlags)
	{
		itemStack.removeItemFlags(itemFlags);
	}

	@ApiStatus.Experimental
	@Override
	public void resetData(@NotNull DataComponentType type)
	{
		itemStack.resetData(type);
	}

	@Override
	public @NotNull Map<String, Object> serialize()
	{
		return itemStack.serialize();
	}

	@Override
	public byte @NotNull [] serializeAsBytes()
	{
		return itemStack.serializeAsBytes();
	}

	public static byte @NotNull [] serializeItemsAsBytes(@NotNull Collection<ItemStack> items)
	{
		return ItemStack.serializeItemsAsBytes(items);
	}

	public static byte @NotNull [] serializeItemsAsBytes(@Nullable ItemStack @NotNull [] items)
	{
		return ItemStack.serializeItemsAsBytes(items);
	}

	@Override
	public void setAmount(int amount)
	{
		itemStack.setAmount(amount);
	}

	@Deprecated(forRemoval = true, since = "1.13")
	@Override
	public void setData(@Nullable MaterialData data)
	{
		itemStack.setData(data);
	}

	@ApiStatus.Experimental
	@Override
	public void setData(DataComponentType.@NotNull NonValued type)
	{
		itemStack.setData(type);
	}

	@ApiStatus.Experimental
	@Override
	public <T> void setData(DataComponentType.@NotNull Valued<T> type, @NotNull T value)
	{
		itemStack.setData(type, value);
	}

	@ApiStatus.Experimental
	@Override
	public <T> void setData(DataComponentType.@NotNull Valued<T> type, @NotNull DataComponentBuilder<T> valueBuilder)
	{
		itemStack.setData(type, valueBuilder);
	}

	@Deprecated(since = "1.13")
	@Override
	public void setDurability(short durability)
	{
		itemStack.setDurability(durability);
	}

	@Override
	public boolean setItemMeta(@Nullable ItemMeta itemMeta)
	{
		return itemStack.setItemMeta(itemMeta);
	}

	@Deprecated(forRemoval = true)
	@Override
	public void setLore(@Nullable List<String> lore)
	{
		itemStack.setLore(lore);
	}

	@Deprecated(forRemoval = true)
	@Override
	public void setType(@NotNull Material type)
	{
		itemStack.setType(type);
	}

	@Override
	public @NotNull ItemStack subtract()
	{
		return itemStack.subtract();
	}

	@Override
	public @NotNull ItemStack subtract(int qty)
	{
		return itemStack.subtract(qty);
	}

	@Override
	public String toString()
	{
		return itemStack.toString();
	}

	@Override
	public @NotNull String translationKey()
	{
		return itemStack.translationKey();
	}

	@ApiStatus.Experimental
	@Override
	public void unsetData(@NotNull DataComponentType type)
	{
		itemStack.unsetData(type);
	}

	@Contract(value = "_ -> new", pure = true)
	@Override
	public @NotNull ItemStack withType(@NotNull Material type)
	{
		return itemStack.withType(type);
	}

}
