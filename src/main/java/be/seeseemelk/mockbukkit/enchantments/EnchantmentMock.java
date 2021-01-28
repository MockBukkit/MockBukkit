package be.seeseemelk.mockbukkit.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnchantmentMock extends Enchantment
{
	private String name;
	private int maxLevel;
	private int startLevel;
	private EnchantmentTarget itemTarget;
	private boolean isTreasure;
	private boolean isCursed;

	public EnchantmentMock(
	    @NotNull NamespacedKey key,
	    String name
	)
	{
		super(key);
		this.name = name;
	}

	@Override
	public @NotNull String getName()
	{
		return name;
	}

	@Override
	public int getMaxLevel()
	{
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel)
	{
		this.maxLevel = maxLevel;
	}

	@Override
	public int getStartLevel()
	{
		return startLevel;
	}

	public void setStartLevel(int startLevel)
	{
		this.startLevel = startLevel;
	}

	@Override
	public @NotNull EnchantmentTarget getItemTarget()
	{
		return itemTarget;
	}

	public void setItemTarget(EnchantmentTarget itemTarget)
	{
		this.itemTarget = itemTarget;
	}

	@Override
	public boolean isTreasure()
	{
		return isTreasure;
	}

	public void setTreasure(boolean isTreasure)
	{
		this.isTreasure = isTreasure;
	}

	@Override
	public boolean isCursed()
	{
		return isCursed;
	}

	public void setCursed(boolean isCursed)
	{
		this.isCursed = isCursed;
	}

	@Override
	public boolean conflictsWith(@NotNull Enchantment other)
	{
		return false;
	}

	@Override
	public boolean canEnchantItem(@NotNull ItemStack item)
	{
		return false;
	}

}
