package be.seeseemelk.mockbukkit.inventory.meta;

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
	
	public ItemMetaMock()
	{
		
	}
	
	public ItemMetaMock(ItemMeta meta)
	{
		if (meta.hasDisplayName())
		{
			displayName = meta.getDisplayName();
		}
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

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ItemMeta)
		{
			ItemMeta meta = (ItemMeta) obj;
			if (displayName != null)
			{
				return displayName.equals(meta.getDisplayName());
			}
			else
			{
				return !meta.hasDisplayName();
			}
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
			return meta;
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error(e);
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
	public boolean hasLore()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public List<String> getLore()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLore(List<String> lore)
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

















