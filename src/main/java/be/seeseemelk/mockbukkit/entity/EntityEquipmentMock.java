package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

/**
 * This mocks the {@link EntityEquipment} of a {@link LivingEntityMock}. Note that not every {@link LivingEntity} has
 * {@link EntityEquipment}, so only implement this where necessary.
 *
 * @author TheBusyBiscuit
 *
 */
public class EntityEquipmentMock implements EntityEquipment
{

	private final LivingEntityMock holder;

	private final Map<EquipmentSlot, Float> dropChances = new EnumMap<>(EquipmentSlot.class);

	private ItemStack itemInMainHand;
	private ItemStack itemInOffHand;

	private ItemStack helmet;
	private ItemStack chestPlate;
	private ItemStack leggings;
	private ItemStack boots;

	public EntityEquipmentMock(@NotNull LivingEntityMock holder)
	{
		this.holder = holder;
	}

	@Override
	public void setItem(@NotNull EquipmentSlot slot, @Nullable ItemStack item)
	{
		setItem(slot, item, false);
	}

	@Override
	public void setItem(@NotNull EquipmentSlot slot, @Nullable ItemStack item, boolean silent)
	{
		switch (slot)
		{
		case HEAD:
			setHelmet(item, silent);
			break;
		case CHEST:
			setChestplate(item, silent);
			break;
		case LEGS:
			setLeggings(item, silent);
			break;
		case FEET:
			setBoots(item, silent);
			break;
		case HAND:
			setItemInMainHand(item, silent);
			break;
		case OFF_HAND:
			setItemInOffHand(item, silent);
			break;
		default:
			// This should never be reached unless Mojang adds new slots
			throw new UnimplementedOperationException("EquipmentSlot '" + slot + "' has no implementation!");
		}
	}

	@Override
	public ItemStack getItem(@NotNull EquipmentSlot slot)
	{
		switch (slot)
		{
		case CHEST:
			return getChestplate();
		case FEET:
			return getBoots();
		case HAND:
			return getItemInMainHand();
		case HEAD:
			return getHelmet();
		case LEGS:
			return getLeggings();
		case OFF_HAND:
			return getItemInOffHand();
		default:
			// This should never be reached unless Mojang adds new slots
			throw new UnimplementedOperationException("EquipmentSlot '" + slot + "' has no implementation!");
		}
	}

	@Override
	public ItemStack getItemInMainHand()
	{
		return itemInMainHand;
	}

	@Override
	public void setItemInMainHand(ItemStack item)
	{
		setItemInMainHand(item, false);
	}

	@Override
	public void setItemInMainHand(ItemStack item, boolean silent)
	{
		this.itemInMainHand = item;
		// Sounds are not implemented here
	}

	@Override
	public ItemStack getItemInOffHand()
	{
		return itemInOffHand;
	}

	@Override
	public void setItemInOffHand(ItemStack item)
	{
		setItemInOffHand(item, false);
	}

	@Override
	public void setItemInOffHand(ItemStack item, boolean silent)
	{
		this.itemInOffHand = item;
		// Sounds are not implemented here
	}

	@Override
	@Deprecated
	public ItemStack getItemInHand()
	{
		return getItemInMainHand();
	}

	@Override
	@Deprecated
	public void setItemInHand(ItemStack stack)
	{
		setItemInMainHand(stack);
	}

	@Override
	public ItemStack getHelmet()
	{
		return helmet;
	}

	@Override
	public void setHelmet(ItemStack helmet)
	{
		setHelmet(helmet, false);
	}

	@Override
	public void setHelmet(ItemStack helmet, boolean silent)
	{
		this.helmet = helmet;
		// Sounds are not implemented here
	}

	@Override
	public ItemStack getChestplate()
	{
		return chestPlate;
	}

	@Override
	public void setChestplate(ItemStack chestplate)
	{
		setChestplate(chestplate, false);
	}

	@Override
	public void setChestplate(ItemStack chestplate, boolean silent)
	{
		this.chestPlate = chestplate;
		// Sounds are not implemented here
	}

	@Override
	public ItemStack getLeggings()
	{
		return leggings;
	}

	@Override
	public void setLeggings(ItemStack leggings)
	{
		setLeggings(leggings, false);
	}

	@Override
	public void setLeggings(ItemStack leggings, boolean silent)
	{
		this.leggings = leggings;
		// Sounds are not implemented here
	}

	@Override
	public ItemStack getBoots()
	{
		return boots;
	}

	@Override
	public void setBoots(ItemStack boots)
	{
		setBoots(boots, false);
	}

	@Override
	public void setBoots(ItemStack boots, boolean silent)
	{
		this.boots = boots;
		// Sounds are not implemented here
	}

	@Override
	public ItemStack[] getArmorContents()
	{
		return new ItemStack[]
		       { getBoots(), getLeggings(), getChestplate(), getHelmet() };
	}

	@Override
	public void setArmorContents(@NotNull ItemStack[] items)
	{
		Validate.notNull(items, "The provided items must not be null.");

		setBoots((items.length >= 1) ? items[0] : null);
		setLeggings((items.length >= 2) ? items[1] : null);
		setChestplate((items.length >= 3) ? items[2] : null);
		setHelmet((items.length >= 4) ? items[3] : null);
	}

	@Override
	public void clear()
	{
		setItemInMainHand(null);
		setItemInOffHand(null);

		setHelmet(null);
		setChestplate(null);
		setLeggings(null);
		setBoots(null);
	}

	@Override
	@Deprecated
	public float getItemInHandDropChance()
	{
		return this.dropChances.get(EquipmentSlot.HAND);
	}

	@Override
	@Deprecated
	public void setItemInHandDropChance(float chance)
	{
		this.dropChances.put(EquipmentSlot.HAND, chance);
	}

	@Override
	public float getItemInMainHandDropChance()
	{
		return this.dropChances.get(EquipmentSlot.HAND);
	}

	@Override
	public void setItemInMainHandDropChance(float chance)
	{
		this.dropChances.put(EquipmentSlot.HAND, chance);
	}

	@Override
	public float getItemInOffHandDropChance()
	{
		return this.dropChances.get(EquipmentSlot.OFF_HAND);
	}

	@Override
	public void setItemInOffHandDropChance(float chance)
	{
		this.dropChances.put(EquipmentSlot.OFF_HAND, chance);
	}

	@Override
	public float getHelmetDropChance()
	{
		return this.dropChances.get(EquipmentSlot.HEAD);
	}

	@Override
	public void setHelmetDropChance(float chance)
	{
		this.dropChances.put(EquipmentSlot.HEAD, chance);
	}

	@Override
	public float getChestplateDropChance()
	{
		return this.dropChances.get(EquipmentSlot.CHEST);
	}

	@Override
	public void setChestplateDropChance(float chance)
	{
		this.dropChances.put(EquipmentSlot.CHEST, chance);
	}

	@Override
	public float getLeggingsDropChance()
	{
		return this.dropChances.get(EquipmentSlot.LEGS);
	}

	@Override
	public void setLeggingsDropChance(float chance)
	{
		this.dropChances.put(EquipmentSlot.LEGS, chance);
	}

	@Override
	public float getBootsDropChance()
	{
		return this.dropChances.get(EquipmentSlot.FEET);
	}

	@Override
	public void setBootsDropChance(float chance)
	{
		this.dropChances.put(EquipmentSlot.FEET, chance);
	}

	@Override
	public Entity getHolder()
	{
		return holder;
	}

}
