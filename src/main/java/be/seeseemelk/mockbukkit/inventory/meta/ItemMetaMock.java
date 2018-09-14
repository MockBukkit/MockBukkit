package be.seeseemelk.mockbukkit.inventory.meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class ItemMetaMock implements ItemMeta
{
	private String displayName = null;
	private List<String> lore = null;

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
	 * Checks if the display name of this item meta is equal to
	 * the display name of another one.
	 * @param meta The other item meta to check against.
	 * @return {@code true} if both display names are equal, {@code false} if they're not.
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
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ItemMeta)
		{
			ItemMeta meta = (ItemMeta) obj;
			return isLoreEquals(meta) && isDisplayNameEqual(meta);
		}
		else
		{
			return false;
		}
	}

	@Override
	public ItemMetaMock clone()
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
	
	/**
	 * Asserts if the lore contains the given lines in order.
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
					throw new AssertionError(String.format("Line %d should be '%s' but was '%s'", i, lines.get(i), lore.get(i)));
				}
			}
		}
		else if (lore != null)
		{
			throw new AssertionError(String.format("Lore contained %d lines but should contain %d lines", lore.size(), lines.size()));
		}
		else
		{
			throw new AssertionError("No lore was set");
		}
	}

	/**
	 * Asserts if the lore contains the given lines in order.
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
	public Map<String, Object> serialize()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasLocalizedName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getLocalizedName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLocalizedName(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasEnchants()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasEnchant(Enchantment ench)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getEnchantLevel(Enchantment ench)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Map<Enchantment, Integer> getEnchants()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removeEnchant(Enchantment ench)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasConflictingEnchant(Enchantment ench)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void addItemFlags(ItemFlag... itemFlags)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeItemFlags(ItemFlag... itemFlags)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Set<ItemFlag> getItemFlags()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasItemFlag(ItemFlag flag)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isUnbreakable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setUnbreakable(boolean unbreakable)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}









