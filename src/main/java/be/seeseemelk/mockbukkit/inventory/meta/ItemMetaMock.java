package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.collect.ImmutableMap;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemMetaMock implements ItemMeta
{
	private String displayName = null;
	private List<String> lore = null;
	private Map<Enchantment, Integer> enchantments = null;
	private EnumSet<ItemFlag> flags = EnumSet.noneOf(ItemFlag.class);
	
	public ItemMetaMock()
	{
		
	}
	
	public ItemMetaMock(ItemMeta meta)
	{
		if (meta.hasDisplayName())
			displayName = meta.getDisplayName();
		if (meta.hasLore())
			lore = meta.getLore();
	}
	
	@Override
	public boolean hasDisplayName()
	{
		return displayName != null;
	}
	
	@Override
	public String getDisplayName()
	{
		return displayName;
	}
	
	@Override
	public void setDisplayName(String name)
	{
		displayName = name;
	}
	
	/**
	 * Checks if this items lore is equal to some other lore.
	 * 
	 * @param meta The other item meta whose lore should be compared.
	 * @return {@code true} if they are the same, {@code false} if they're not.
	 */
	private boolean isLoreEquals(ItemMeta meta)
	{
		if (lore == null)
			return !meta.hasLore();
		else if (!meta.hasLore())
			return false;
		
		List<String> otherLore = meta.getLore();
		if (lore.size() == otherLore.size())
		{
			for (int i = 0; i < lore.size(); i++)
			{
				if (!lore.get(i).equals(otherLore.get(i)))
					return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the display name of this item meta is equal to the display name of
	 * another one.
	 * 
	 * @param meta The other item meta to check against.
	 * @return {@code true} if both display names are equal, {@code false} if
	 *         they're not.
	 */
	private boolean isDisplayNameEqual(ItemMeta meta)
	{
		if (displayName != null)
		{
			if (meta.hasDisplayName())
				return displayName.equals(meta.getDisplayName());
			else
				return false;
		}
		else
		{
			return !meta.hasDisplayName();
		}
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + ((lore == null) ? 0 : lore.hashCode());
		result = prime * result + ((enchantments == null) ? 0 : enchantments.hashCode());
		result = prime * result + flags.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ItemMeta)
		{
			ItemMeta meta = (ItemMeta) obj;
			return isLoreEquals(meta) && isDisplayNameEqual(meta) &&
					((enchantments != null) ? enchantments.equals(meta.getEnchants()) : meta.getEnchants().isEmpty()) &&
					flags.equals(meta.getItemFlags());
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public ItemMeta clone()
	{
		try
		{
			ItemMetaMock meta = (ItemMetaMock) super.clone();
			meta.displayName = displayName;
			meta.lore = lore;
			return meta;
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error(e);
		}
	}
	
	@Override
	public boolean hasLore()
	{
		return lore != null;
	}
	
	@Override
	public List<String> getLore()
	{
		return new ArrayList<>(lore);
	}
	
	@Override
	public void setLore(List<String> lore)
	{
		this.lore = new ArrayList<>(lore);
	}
	
	private Spigot spigot;
	
	/**
	 * Asserts if the lore contains the given lines in order.
	 * 
	 * @param lines The lines the lore should contain
	 */
	public void assertLore(List<String> lines)
	{
		if (lore != null && lore.size() == lines.size())
		{
			for (int i = 0; i < lore.size(); i++)
			{
				if (!lore.get(i).equals(lines.get(i)))
				{
					throw new AssertionError(
							String.format("Line %d should be '%s' but was '%s'", i, lines.get(i), lore.get(i)));
				}
			}
		}
		else if (lore != null)
		{
			throw new AssertionError(
					String.format("Lore contained %d lines but should contain %d lines", lore.size(), lines.size()));
		}
		else
		{
			throw new AssertionError("No lore was set");
		}
	}
	
	@Override
	public Map<String, Object> serialize()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	static boolean checkConflictingEnchants(Map<Enchantment, Integer> enchantments, Enchantment enchantment)
	{
		if (enchantments != null && !enchantments.isEmpty())
		{
			for (Enchantment otherEnchantment : enchantments.keySet())
			{
				if (otherEnchantment.conflictsWith(enchantment))
					return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean hasEnchants()
	{
		return enchantments != null && !enchantments.isEmpty();
	}
	
	@Override
	public boolean hasEnchant(Enchantment ench)
	{
		// TODO Auto-generated method stub
		if (!hasEnchants())
			return false;
		return enchantments.containsKey(ench);
	}
	
	@Override
	public int getEnchantLevel(Enchantment ench)
	{
		if (!hasEnchants())
			return -1;
		return enchantments.getOrDefault(ench, -1);
	}
	
	@Override
	public Map<Enchantment, Integer> getEnchants()
	{
		return hasEnchants() ? ImmutableMap.copyOf(enchantments) : ImmutableMap.of();
	}
	
	@Override
	public boolean addEnchant(Enchantment ench, int level, boolean ignoreRestrictions)
	{
		// Copied from CraftItemMeta
		if (enchantments == null)
			enchantments = new HashMap<>(4);
		if (!ignoreRestrictions && (level < ench.getStartLevel() || level > ench.getMaxLevel()))
		{
			return false;
		}
		else
		{
			Integer old = enchantments.put(ench, level);
			return old == null || old != level;
		}
	}
	
	@Override
	public boolean removeEnchant(Enchantment ench)
	{
		boolean b = hasEnchants() && enchantments.remove(ench) != null;
		if (enchantments != null && enchantments.isEmpty())
		{
			enchantments = null;
		}
		
		return b;
	}
	
	@Override
	public void addItemFlags(ItemFlag... hideFlags)
	{
		for (ItemFlag flag : hideFlags)
			flags.add(flag);
		
	}
	
	public void removeItemFlags(ItemFlag... hideFlags)
	{	
		for (ItemFlag flag : hideFlags)
			flags.remove(flag);
	}
	
	public Set<ItemFlag> getItemFlags()
	{
		return Collections.unmodifiableSet(flags);
	}
	
	public boolean hasItemFlag(ItemFlag flag)
	{
		return flags.contains(flag);
	}
	
	/**
	 * Asserts if the lore contains the given lines in order.
	 *
	 * @param lines The lines the lore should contain
	 */
	public void assertLore(String... lines)
	{
		assertLore(Arrays.asList(lines));
	}
	
	/**
	 * Asserts that the item meta contains no lore.
	 *
	 * @throws AssertionError if the item meta contains some lore.
	 */
	public void assertHasNoLore() throws AssertionError
	{
		if (lore != null && lore.size() != 0)
		{
			throw new AssertionError("Lore was set but shouldn't have been set");
		}
	}
	
	@Override
	public Spigot spigot()
	{
		if (spigot == null)
			spigot = new Spigot();
		
		return spigot;
		
	}
	
	public class Spigot extends ItemMeta.Spigot
	{
		private boolean unbreakable;
		
		@Override
		public boolean isUnbreakable()
		{
			return unbreakable;
		}
		
		@Override
		public void setUnbreakable(boolean unbreakable)
		{
			this.unbreakable = unbreakable;
		}
	}
	
	@Override
	public boolean hasConflictingEnchant(Enchantment ench)
	{
		return checkConflictingEnchants(this.enchantments, ench);
	}
	
}
