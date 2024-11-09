package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.collect.ImmutableMap;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
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

	private @NotNull Map<Enchantment, Integer> storedEnchantments = new HashMap<>();

	/**
	 * Constructs a new {@link EnchantmentStorageMetaMock}.
	 */
	public EnchantmentStorageMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link EnchantmentStorageMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public EnchantmentStorageMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);

		if(meta instanceof EnchantmentStorageMeta enchantMeta)
		{
			this.storedEnchantments = new HashMap<>(enchantMeta.getStoredEnchants());
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + storedEnchantments.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!super.equals(obj))
		{
			return false;
		}
		if (!(obj instanceof EnchantmentStorageMetaMock other))
		{
			return false;
		}
		return storedEnchantments.equals(other.storedEnchantments);
	}

	@Override
	public @NotNull EnchantmentStorageMetaMock clone()
	{
		EnchantmentStorageMetaMock mock = (EnchantmentStorageMetaMock) super.clone();
		mock.storedEnchantments = new HashMap<>(storedEnchantments);
		return mock;
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

		Integer prev = storedEnchantments.put(ench, level);
		return prev == null || prev.intValue() != level;
	}

	@Override
	public int getStoredEnchantLevel(@NotNull Enchantment ench)
	{
		return storedEnchantments.getOrDefault(ench, 0);
	}

	@Override
	public @NotNull Map<Enchantment, Integer> getStoredEnchants()
	{
		// This is inline with CraftBukkit's default implementation, it only returns an
		// immutable copy, never the original.
		return ImmutableMap.copyOf(storedEnchantments);
	}

	@Override
	public boolean hasConflictingStoredEnchant(@NotNull Enchantment ench)
	{
		for (Enchantment enchantment : storedEnchantments.keySet())
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
		return storedEnchantments.containsKey(ench);
	}

	@Override
	public boolean hasStoredEnchants()
	{
		return !storedEnchantments.isEmpty();
	}

	@Override
	public boolean removeStoredEnchant(@NotNull Enchantment ench) throws IllegalArgumentException
	{
		return storedEnchantments.remove(ench) != null;
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
		if (args.containsKey("stored-enchantments"))
		{
			//noinspection unchecked
			serialMock.storedEnchantments = ((Map<String, Integer>) args.get("stored-enchantments")).entrySet().stream()
					.collect(ImmutableMap.toImmutableMap(entry -> getEnchantment(entry.getKey()), Map.Entry::getValue));
		}
		return serialMock;
	}

	/**
	 * Serializes the properties of an EnchantmentStorageMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the EnchantmentStorageMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		serialized.put("stored-enchantments", this.storedEnchantments.entrySet().stream()
				.collect(ImmutableMap.toImmutableMap(entry -> getEnchantmentKey(entry.getKey()), Map.Entry::getValue)));
		return serialized;
	}

	private static String getEnchantmentKey(Enchantment enchantment)
	{
		return enchantment.getKey().getKey();
	}

	private static Enchantment getEnchantment(String key)
	{
		NamespacedKey namespacedKey = NamespacedKey.minecraft(key);
		return Registry.ENCHANTMENT.get(namespacedKey);
	}

	@Override
	protected String getTypeName()
	{
		return "ENCHANTED";
	}

}
