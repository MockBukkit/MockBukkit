package be.seeseemelk.mockbukkit.entity;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

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
	public float getItemInHandDropChance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setItemInHandDropChance(float chance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getItemInMainHandDropChance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setItemInMainHandDropChance(float chance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getItemInOffHandDropChance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setItemInOffHandDropChance(float chance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getHelmetDropChance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHelmetDropChance(float chance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getChestplateDropChance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setChestplateDropChance(float chance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getLeggingsDropChance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLeggingsDropChance(float chance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getBootsDropChance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBootsDropChance(float chance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Entity getHolder()
	{
		return holder;
	}

}
