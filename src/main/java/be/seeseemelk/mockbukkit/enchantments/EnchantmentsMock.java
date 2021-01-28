package be.seeseemelk.mockbukkit.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

public final class EnchantmentsMock
{
	private EnchantmentsMock() {}

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
	}

	private static void register(String name)
	{
		NamespacedKey key = NamespacedKey.minecraft(name);
		if (Enchantment.getByKey(key) == null)
			Enchantment.registerEnchantment(new EnchantmentMock(key, name));
	}
}
