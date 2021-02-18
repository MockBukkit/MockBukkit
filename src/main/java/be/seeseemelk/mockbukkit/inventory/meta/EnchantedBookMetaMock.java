package be.seeseemelk.mockbukkit.inventory.meta;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableMap;

/**
 * This {@link ItemMetaMock} mocks the implementation of {@link EnchantmentStorageMeta}.
 * It keeps an internal {@link HashMap} for all stored {@link Enchantment Enchantments}.
 *
 * @author TheBusyBiscuit
 *
 */
public class EnchantedBookMetaMock extends ItemMetaMock implements EnchantmentStorageMeta
{

	private Map<Enchantment, Integer> storedEnchantments = new HashMap<>();

	public EnchantedBookMetaMock()
	{
		super();
	}

	public EnchantedBookMetaMock(EnchantmentStorageMeta meta)
	{
		super(meta);

		this.storedEnchantments = new HashMap<>(meta.getStoredEnchants());
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
		if (!(obj instanceof EnchantedBookMetaMock))
		{
			return false;
		}
		EnchantedBookMetaMock other = (EnchantedBookMetaMock) obj;
		return storedEnchantments.equals(other.storedEnchantments);
	}

	@Override
	public EnchantedBookMetaMock clone()
	{
		EnchantedBookMetaMock mock = (EnchantedBookMetaMock) super.clone();
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

}
