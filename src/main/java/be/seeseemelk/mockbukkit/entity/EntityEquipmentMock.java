package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
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
 */
public class EntityEquipmentMock implements EntityEquipment
{

	private final LivingEntityMock holder;

	private final Map<EquipmentSlot, Float> dropChances = new EnumMap<>(EquipmentSlot.class);

	private ItemStack itemInMainHand = new ItemStack(Material.AIR);
	private ItemStack itemInOffHand = new ItemStack(Material.AIR);

	private ItemStack helmet = new ItemStack(Material.AIR);
	private ItemStack chestPlate = new ItemStack(Material.AIR);
	private ItemStack leggings = new ItemStack(Material.AIR);
	private ItemStack boots = new ItemStack(Material.AIR);

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
		case HEAD -> setHelmet(item, silent);
		case CHEST -> setChestplate(item, silent);
		case LEGS -> setLeggings(item, silent);
		case FEET -> setBoots(item, silent);
		case HAND -> setItemInMainHand(item, silent);
		case OFF_HAND -> setItemInOffHand(item, silent);
			default ->
			// This should never be reached unless Mojang adds new slots
			throw new UnimplementedOperationException("EquipmentSlot '" + slot + "' has no implementation!");
		}
	}

	@Override
	public @NotNull ItemStack getItem(@NotNull EquipmentSlot slot)
	{
		return switch (slot)
		{
		case CHEST -> getChestplate();
		case FEET -> getBoots();
		case HAND -> getItemInMainHand();
		case HEAD -> getHelmet();
		case LEGS -> getLeggings();
		case OFF_HAND -> getItemInOffHand();
			default ->
			// This should never be reached unless Mojang adds new slots
			throw new UnimplementedOperationException("EquipmentSlot '" + slot + "' has no implementation!");
		};
	}

	@Override
	public @NotNull ItemStack getItemInMainHand()
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
	public @NotNull ItemStack getItemInOffHand()
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
	public @NotNull ItemStack getItemInHand()
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
		Preconditions.checkNotNull(items, "The provided items must not be null.");

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
		setDropChance(EquipmentSlot.HAND, chance);
	}

	@Override
	public float getItemInMainHandDropChance()
	{
		return this.dropChances.get(EquipmentSlot.HAND);
	}

	@Override
	public void setItemInMainHandDropChance(float chance)
	{
		setDropChance(EquipmentSlot.HAND, chance);
	}

	@Override
	public float getItemInOffHandDropChance()
	{
		return this.dropChances.get(EquipmentSlot.OFF_HAND);
	}

	@Override
	public void setItemInOffHandDropChance(float chance)
	{
		setDropChance(EquipmentSlot.OFF_HAND, chance);
	}

	@Override
	public float getHelmetDropChance()
	{
		return this.dropChances.get(EquipmentSlot.HEAD);
	}

	@Override
	public void setHelmetDropChance(float chance)
	{
		setDropChance(EquipmentSlot.HEAD, chance);
	}

	@Override
	public float getChestplateDropChance()
	{
		return this.dropChances.get(EquipmentSlot.CHEST);
	}

	@Override
	public void setChestplateDropChance(float chance)
	{
		setDropChance(EquipmentSlot.CHEST, chance);
	}

	@Override
	public float getLeggingsDropChance()
	{
		return this.dropChances.get(EquipmentSlot.LEGS);
	}

	@Override
	public void setLeggingsDropChance(float chance)
	{
		setDropChance(EquipmentSlot.LEGS, chance);
	}

	@Override
	public float getBootsDropChance()
	{
		return this.dropChances.get(EquipmentSlot.FEET);
	}

	@Override
	public void setBootsDropChance(float chance)
	{
		setDropChance(EquipmentSlot.FEET, chance);
	}

	@Override
	public Entity getHolder()
	{
		return holder;
	}

	@Override
	public void setDropChance(@NotNull EquipmentSlot slot, float chance)
	{
		Preconditions.checkArgument(holder instanceof Mob, "Cannot set drop chance for non-Mob entity");

		this.dropChances.put(slot, chance);
	}

	@Override
	public float getDropChance(@NotNull EquipmentSlot slot)
	{
		return this.dropChances.get(slot);
	}

}
