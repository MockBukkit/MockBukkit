package be.seeseemelk.mockbukkit.inventory.meta;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * This mocks the implementation of the {@link Enchantment} class. Tests would fail otherwise since the constants found
 * in that class only work during the server's runtime.
 *
 * @author TheBusyBiscuit
 *
 */
class EnchantmentMock extends Enchantment
{

	private final String name;

	@SuppressWarnings("deprecation")
	public EnchantmentMock(@NotNull String name)
	{
		super(NamespacedKey.randomKey());
		this.name = name;
	}

	@Override
	public @NotNull String getName()
	{
		return name;
	}

	@Override
	public boolean isTreasure()
	{
		return false;
	}

	@Override
	public boolean isCursed()
	{
		return false;
	}

	@Override
	public int getStartLevel()
	{
		return 1;
	}

	@Override
	public int getMaxLevel()
	{
		return 3;
	}

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull EnchantmentTarget getItemTarget()
	{
		return EnchantmentTarget.ALL;
	}

	@Override
	public boolean canEnchantItem(@NotNull ItemStack item)
	{
		return true;
	}

	@Override
	public boolean conflictsWith(@NotNull Enchantment other)
	{
		// We just let it only conflict with itself, it's enough for our tests
		return other == this;
	}

}
