package be.seeseemelk.mockbukkit.inventory.meta;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Multimap;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.persistence.PersistentDataContainerMock;

public class ItemMetaMock implements ItemMeta, Damageable, Repairable
{

	/*
	 * If you add a new field, you need to add it to #hashCode, #equals, #serialize, and #deserialize.
	 * If it's a mutable object, it also needs to be handled in #clone.
	 */
	private String displayName = null;
	private List<String> lore = null;
	private int damage = 0;
	private int repairCost = 0;
	private Map<Enchantment, Integer> enchants = new HashMap<>();
	private Set<ItemFlag> hideFlags = EnumSet.noneOf(ItemFlag.class);
	private PersistentDataContainer persistentDataContainer = new PersistentDataContainerMock();
	private boolean unbreakable = false;
	private Integer customModelData = null;

	public ItemMetaMock()
	{
	}

	public ItemMetaMock(@NotNull ItemMeta meta)
	{
		unbreakable = meta.isUnbreakable();
		enchants = new HashMap<>(meta.getEnchants());
		customModelData = meta.hasCustomModelData() ? meta.getCustomModelData() : null;
		hideFlags.addAll(meta.getItemFlags());

		if (meta.hasDisplayName())
		{
			displayName = meta.getDisplayName();
		}
		if (meta.hasLore())
		{
			lore = meta.getLore();
		}
		if (meta instanceof Damageable)
		{
			this.damage = ((Damageable) meta).getDamage();
		}
		if (meta instanceof Repairable)
		{
			this.repairCost = ((Repairable) meta).getRepairCost();
		}
		if (meta instanceof ItemMetaMock)
		{
			this.persistentDataContainer = ((ItemMetaMock) meta).persistentDataContainer;
		}
	}

	static boolean checkConflictingEnchants(Map<Enchantment, Integer> enchantments, Enchantment ench)
	{
		if (enchantments == null || enchantments.isEmpty())
			return false;

		Iterator<Enchantment> var2 = enchantments.keySet().iterator();

		Enchantment enchant;
		do
		{
			if (!var2.hasNext())
				return false;
			enchant = var2.next();
		}
		while (!enchant.conflictsWith(ench));

		return true;
	}

	@Override
	public boolean hasDisplayName()
	{
		return nonNull(displayName);
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
	 * they're not.
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
		result = prime * result + ((customModelData == null) ? 0 : customModelData.hashCode());
		result = prime * result + (enchants.isEmpty() ? 0 : enchants.hashCode());
		result = prime * result + (hasRepairCost() ? this.repairCost : 0);
		result = prime * result + (!persistentDataContainer.isEmpty() ? persistentDataContainer.hashCode() : 0);
		result = prime * result + (hideFlags.isEmpty() ? 0 : hideFlags.hashCode());
		result = prime * result + Boolean.hashCode(unbreakable);
		result = prime * result + (hasDamage() ? this.damage : 0);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ItemMeta))
		{
			return false;
		}

		ItemMeta meta = (ItemMeta) obj;

		if (!isDisplayNameEqual(meta))
		{
			return false;
		}
		if (!isLoreEquals(meta))
		{
			return false;
		}
		if (obj instanceof Damageable)
		{
			Damageable damageable = (Damageable) obj;
			if (hasDamage() != damageable.hasDamage() || hasDamage() && getDamage() != damageable.getDamage())
			{
				return false;
			}
		}
		else if (hasDamage())
		{
			return false;
		}
		// Comparing using equals is fine - AbstractMap#equals only cares about content, not Map implementation.
		if (!enchants.equals(meta.getEnchants()))
		{
			return false;
		}
		// Same as above - AbstractSet#equals only compares content.
		if (!hideFlags.equals(meta.getItemFlags()))
		{
			return false;
		}
		// MockPDC does care about PDC impl, but this is in line with CB's equality comparison.
		if (!persistentDataContainer.equals(meta.getPersistentDataContainer()))
		{
			return false;
		}
		if (unbreakable != meta.isUnbreakable())
		{
			return false;
		}
		if (hasCustomModelData() != meta.hasCustomModelData()
		        || hasCustomModelData() && getCustomModelData() != meta.getCustomModelData())
		{
			return false;
		}

		return true;
	}

	@Override
	public ItemMetaMock clone()
	{
		try
		{
			ItemMetaMock meta = (ItemMetaMock) super.clone();
			meta.displayName = displayName;
			if (lore != null)
			{
				meta.lore = new ArrayList<>(lore);
			}

			meta.unbreakable = unbreakable;
			meta.customModelData = customModelData;
			meta.enchants = new HashMap<>(enchants);
			meta.persistentDataContainer = new PersistentDataContainerMock((PersistentDataContainerMock) persistentDataContainer);
			meta.damage = damage;
			meta.repairCost = repairCost;
			meta.hideFlags = EnumSet.copyOf(hideFlags);
			return meta;
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
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
		if (lore != null && !lore.isEmpty())
		{
			throw new AssertionError("Lore was set but shouldn't have been set");
		}
	}

	/**
	 * Serializes the properties of an ItemMetaMock to a HashMap.
	 * Unimplemented methods have values of null.
	 * @return A HashMap of String, Object pairs representing the ItemMetaMock.
	 */
	@Override
	public Map<String, Object> serialize()
	{
		// Make new map and add relevant properties to it.
		Map<String, Object> map = new HashMap<>();
		map.put("displayName", this.displayName);
		map.put("lore", this.lore);
		map.put("localizedName", null); // Not implemented.
		map.put("enchants", this.enchants);
		map.put("itemFlags", this.hideFlags);
		map.put("unbreakable", this.unbreakable);
		map.put("attributeModifiers", null); // Not implemented.
		map.put("customTagContainer", null); // Not implemented.
		map.put("customModelData", this.customModelData);
		map.put("persistentDataContainer", this.persistentDataContainer);
		map.put("damage", this.damage);
		map.put("repairCost", this.repairCost);
		// Return map
		return map;
	}

	/**
	 * Required method for Bukkit deserialization.
	 * @param args A serialized ItemMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the ItemMetaMock class.
	 */
	@SuppressWarnings("unchecked")
	public static ItemMetaMock deserialize(Map<String, Object> args)
	{
		ItemMetaMock serialMock = new ItemMetaMock();

		serialMock.displayName = (String) args.get("displayName");
		serialMock.lore = (List<String>) args.get("lore");
		// serialMock.setLocalizedName(); // localizedName is unimplemented in mock
		serialMock.enchants = (Map<Enchantment, Integer>) args.get("enchants");
		serialMock.hideFlags = (Set<ItemFlag>) args.get("itemFlags");
		serialMock.unbreakable = (boolean) args.get("unbreakable");
		// serialMock.setAttributeModifiers(); // AttributeModifiers are unimplemented in mock
		// customTagContainer is also unimplemented in mock.
		serialMock.customModelData = (Integer) args.get("customModelData");
		serialMock.persistentDataContainer = (PersistentDataContainer) args.get("persistentDataContainer");
		serialMock.damage = (Integer) args.get("damage");
		serialMock.repairCost = (Integer) args.get("repairCost");
		return serialMock;
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
		return !enchants.isEmpty();
	}

	@Override
	public boolean hasEnchant(Enchantment ench)
	{
		return enchants.containsKey(ench);
	}

	@Override
	public int getEnchantLevel(Enchantment ench)
	{
		return hasEnchant(ench) ? enchants.get(ench) : 0;
	}

	@Override
	public Map<Enchantment, Integer> getEnchants()
	{
		return Collections.unmodifiableMap(enchants);
	}

	@Override
	public boolean addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction)
	{
		Integer existingLevel = this.enchants.get(ench);
		if (nonNull(existingLevel) && existingLevel.equals(level))
		{
			return false; // Already exists with the same level
		}

		if (ignoreLevelRestriction || (level >= ench.getStartLevel() && level <= ench.getMaxLevel()))
		{
			this.enchants.put(ench, level);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean removeEnchant(Enchantment ench)
	{
		return nonNull(this.enchants.remove(ench));
	}

	@Override
	public boolean hasConflictingEnchant(Enchantment ench)
	{
		boolean b = this.hasEnchants() && enchants.remove(ench) != null;
		if (enchants != null && enchants.isEmpty())
		{
			enchants = null;
		}

		return b;
	}

	@Override
	public void addItemFlags(ItemFlag... itemFlags)
	{
		hideFlags.addAll(Arrays.asList(itemFlags));
	}

	@Override
	public void removeItemFlags(ItemFlag... itemFlags)
	{
		hideFlags.removeAll(Arrays.asList(itemFlags));
	}

	@Override
	public Set<ItemFlag> getItemFlags()
	{
		return Collections.unmodifiableSet(hideFlags);
	}

	@Override
	public boolean hasItemFlag(ItemFlag flag)
	{
		return hideFlags.contains(flag);
	}

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

	@Override
	public boolean hasDamage()
	{
		return damage > 0;
	}

	@Override
	public int getDamage()
	{
		return damage;
	}

	@Override
	public void setDamage(int damage)
	{
		this.damage = damage;
	}

	@Override
	public boolean hasRepairCost()
	{
		return repairCost > 0;
	}

	@Override
	public int getRepairCost()
	{
		return repairCost;
	}

	@Override
	public void setRepairCost(int cost)
	{
		this.repairCost = cost;
	}

	@Override
	public boolean hasAttributeModifiers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Collection<AttributeModifier> getAttributeModifiers(Attribute attribute)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addAttributeModifier(Attribute attribute, AttributeModifier modifier)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removeAttributeModifier(Attribute attribute)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removeAttributeModifier(EquipmentSlot slot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removeAttributeModifier(Attribute attribute, AttributeModifier modifier)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@SuppressWarnings("deprecation")
	public org.bukkit.inventory.meta.tags.CustomItemTagContainer getCustomTagContainer()
	{
		// This was replaced by PersistentDataContainer!
		throw new UnimplementedOperationException();
	}

	@Override
	public PersistentDataContainer getPersistentDataContainer()
	{
		return this.persistentDataContainer;
	}

	@Override
	public boolean hasCustomModelData()
	{
		return this.customModelData != null;
	}

	@Override
	public int getCustomModelData()
	{
		return this.customModelData;
	}

	@Override
	public void setCustomModelData(@Nullable Integer data)
	{
		this.customModelData = data;
	}

	@Override
	public void setVersion(int version)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
