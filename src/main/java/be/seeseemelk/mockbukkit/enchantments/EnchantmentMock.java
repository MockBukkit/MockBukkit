package be.seeseemelk.mockbukkit.enchantments;

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

public class EnchantmentMock extends Enchantment
{
	private String name;
	private int maxLevel;
	private int startLevel;
	private EnchantmentTarget itemTarget;
	private boolean isTreasure;
	private boolean isCursed;

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
