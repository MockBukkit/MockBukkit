package be.seeseemelk.mockbukkit.enchantments;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
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
 * Mock implementation of an {@link Enchantment}.
 */
public class EnchantmentMock extends Enchantment
{

	private final @NotNull String name;
	private int maxLevel;
	private int startLevel;
	private EnchantmentTarget itemTarget;
	private boolean isTreasure;
	private boolean isCursed;

	/**
	 * Constructs a new {@link EnchantmentMock} with the provided {@link NamespacedKey} and name.
	 *
	 * @param key  The key for the enchantment.
	 * @param name The name of the enchantment.
	 */
	public EnchantmentMock(@NotNull NamespacedKey key, @NotNull String name)
	{
		super(key);
		Preconditions.checkNotNull(key, "Key cannot be null");
		Preconditions.checkNotNull(name, "Name cannot be null");
		this.name = name;
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
	public int getMaxModifiedCost(int level)
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
	public @NotNull String translationKey()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull String getName()
	{
		return name;
	}

	@Override
	public int getMaxLevel()
	{
		return this.maxLevel;
	}

	/**
	 * Sets the return value of {@link #getMaxLevel()}.
	 *
	 * @param maxLevel The max level.
	 * @see #getMaxLevel()
	 */
	public void setMaxLevel(int maxLevel)
	{
		this.maxLevel = maxLevel;
	}

	@Override
	public int getStartLevel()
	{
		return this.startLevel;
	}

	/**
	 * Sets the return value of {@link #getStartLevel()}.
	 *
	 * @param startLevel The start level.
	 * @see #getStartLevel()
	 */
	public void setStartLevel(int startLevel)
	{
		this.startLevel = startLevel;
	}

	@Override
	public @NotNull EnchantmentTarget getItemTarget()
	{
		return this.itemTarget;
	}

	/**
	 * Sets the return value of {@link #getItemTarget()}.
	 *
	 * @param itemTarget The item target.
	 * @see #getItemTarget()
	 */
	public void setItemTarget(@NotNull EnchantmentTarget itemTarget)
	{
		Preconditions.checkNotNull(itemTarget, "EnchantmentTarget cannot be null");
		this.itemTarget = itemTarget;
	}

	@Override
	public boolean isTreasure()
	{
		return this.isTreasure;
	}

	/**
	 * Sets the return value of {@link #isTreasure()}.
	 *
	 * @param isTreasure Whether the enchantment is treasure.
	 * @see #isTreasure()
	 */
	public void setTreasure(boolean isTreasure)
	{
		this.isTreasure = isTreasure;
	}

	@Override
	public boolean isCursed()
	{
		return this.isCursed;
	}

	/**
	 * Sets the return value of {@link #isCursed()}.
	 *
	 * @param isCursed Whether the enchantment is cursed.
	 * @see #isCursed()
	 */
	public void setCursed(boolean isCursed)
	{
		this.isCursed = isCursed;
	}

	@Override
	public boolean conflictsWith(@NotNull Enchantment other)
	{
		Preconditions.checkNotNull(other, "Enchantment cannot be null");
		return false;
	}

	@Override
	public boolean canEnchantItem(@NotNull ItemStack item)
	{
		Preconditions.checkNotNull(item, "Item cannot be null");
		return false;
	}

}
