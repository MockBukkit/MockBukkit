package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * This mocks the implementation of the {@link Enchantment} class. Tests would fail otherwise since the constants found
 * in that class only work during the server's runtime.
 *
 * @author TheBusyBiscuit
 */
class EnchantmentMock extends Enchantment
{

	private final @NotNull String name;

	@SuppressWarnings("deprecation")
	public EnchantmentMock(@NotNull String name)
	{
		super(NamespacedKey.randomKey());
		this.name = name;
	}

	@Override
	@Deprecated
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
	public @NotNull Component displayName(int level)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isTradeable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isDiscoverable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getMinModifiedCost(int level)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getMaxModifiedCost(int level)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EnchantmentRarity getRarity()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Set<EquipmentSlot> getActiveSlots()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean conflictsWith(@NotNull Enchantment other)
	{
		// We just let it only conflict with itself, it's enough for our tests
		return other == this;
	}

	@Override
	public @NotNull String translationKey()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
