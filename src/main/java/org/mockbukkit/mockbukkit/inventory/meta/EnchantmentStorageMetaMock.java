package org.mockbukkit.mockbukkit.inventory.meta;

import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock implementation of an {@link EnchantmentStorageMeta}.
 *
 * @see ItemMetaMock
 */
public class EnchantmentStorageMetaMock extends ItemMetaMock implements EnchantmentStorageMeta
{

	/**
	 * Constructs a new {@link EnchantmentStorageMetaMock}.
	 */
	public EnchantmentStorageMetaMock()
	{
		super();
	}

	@ApiStatus.Internal
	public EnchantmentStorageMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	/**
	 * Constructs a new {@link EnchantmentStorageMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public EnchantmentStorageMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
	}

	@Override
	public @NotNull EnchantmentStorageMetaMock clone()
	{
		return (EnchantmentStorageMetaMock) super.clone();
	}

	@Override
	public boolean addStoredEnchant(@NotNull Enchantment ench, int level, boolean ignoreLevelRestriction)
	{
		if (!ignoreLevelRestriction && level < ench.getStartLevel())
		{
			return false;
		}

		if (!ignoreLevelRestriction && level > ench.getMaxLevel())
		{
			return false;
		}
		ItemEnchantments itemEnchantments = get(DataComponentTypes.STORED_ENCHANTMENTS);
		if (itemEnchantments == null)
		{
			set(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.itemEnchantments(Map.of(ench, level)));
			return true;
		}
		Map<Enchantment, Integer> enchantments = new HashMap<>(itemEnchantments.enchantments());
		if (enchantments.containsKey(ench) && enchantments.get(ench) == level)
		{
			return false;
		}
		enchantments.put(ench, level);
		set(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.itemEnchantments(enchantments));
		return true;
	}

	@Override
	public int getStoredEnchantLevel(@NotNull Enchantment ench)
	{
		ItemEnchantments itemEnchantments = get(DataComponentTypes.STORED_ENCHANTMENTS);
		if (itemEnchantments == null)
		{
			return 0;
		}
		return itemEnchantments.enchantments().getOrDefault(ench, 0);
	}

	@Override
	public @NotNull Map<Enchantment, Integer> getStoredEnchants()
	{

		ItemEnchantments itemEnchantments = get(DataComponentTypes.STORED_ENCHANTMENTS);
		if (itemEnchantments == null)
		{
			return Map.of();
		}
		return itemEnchantments.enchantments();
	}

	@Override
	public boolean hasConflictingStoredEnchant(@NotNull Enchantment ench)
	{
		for (Enchantment enchantment : getStoredEnchants().keySet())
		{
			if (enchantment.conflictsWith(ench))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasStoredEnchant(@NotNull Enchantment ench)
	{
		return getStoredEnchants().containsKey(ench);
	}

	@Override
	public boolean hasStoredEnchants()
	{
		return !getStoredEnchants().isEmpty();
	}

	@Override
	public boolean removeStoredEnchant(@NotNull Enchantment ench) throws IllegalArgumentException
	{
		ItemEnchantments itemEnchantments = get(DataComponentTypes.STORED_ENCHANTMENTS);
		if (itemEnchantments == null)
		{
			return false;
		}
		Map<Enchantment, Integer> enchantments = new HashMap<>(itemEnchantments.enchantments());
		boolean removed = enchantments.remove(enchantments) != null;
		set(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.itemEnchantments(enchantments));
		return removed;
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized EnchantmentStorageMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the EnchantmentStorageMetaMock class.
	 */
	public static @NotNull EnchantmentStorageMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		EnchantmentStorageMetaMock serialMock = new EnchantmentStorageMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

	@Override
	protected String getTypeName()
	{
		return "ENCHANTED";
	}

}
