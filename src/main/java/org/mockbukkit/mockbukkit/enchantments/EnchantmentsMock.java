package org.mockbukkit.mockbukkit.enchantments;

import com.google.common.base.Preconditions;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Used to keep track of all vanilla enchantments.
 */
@ApiStatus.Internal
public final class EnchantmentsMock
{

	/**
	 * Registers all vanilla enchantments.
	 */
	public static void registerDefaultEnchantments()
	{
		register("protection");
		register("fire_protection");
		register("feather_falling");
		register("blast_protection");
		register("respiration");
		register("projectile_protection");
		register("aqua_affinity");
		register("thorns");
		register("depth_strider");
		register("frost_walker");
		register("binding_curse");
		register("sharpness");
		register("smite");
		register("bane_of_arthropods");
		register("knockback");
		register("fire_aspect");
		register("looting");
		register("sweeping");
		register("efficiency");
		register("silk_touch");
		register("unbreaking");
		register("fortune");
		register("power");
		register("punch");
		register("flame");
		register("infinity");
		register("luck_of_the_sea");
		register("lure");
		register("loyalty");
		register("impaling");
		register("riptide");
		register("channeling");
		register("multishot");
		register("quick_charge");
		register("piercing");
		register("mending");
		register("vanishing_curse");
		register("swift_sneak");
	}

	/**
	 * Registers an enchantment.
	 *
	 * @param name Name of the enchantment.
	 */
	private static void register(@NotNull String name)
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		NamespacedKey key = NamespacedKey.minecraft(name);
		if (Enchantment.getByKey(key) == null)
			Enchantment.registerEnchantment(new EnchantmentMock(key, name));
	}

	private EnchantmentsMock()
	{
		throw new UnsupportedOperationException("Utility class");
	}

}
