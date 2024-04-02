package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.entity.HumanEntityMock;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Mock implementation of a {@link PlayerInventory}.
 *
 * @see InventoryMock
 */
public class PlayerInventoryMock extends InventoryMock implements PlayerInventory, EntityEquipment
{

	/**
	 * The starting slot of the hotbar.
	 */
	protected static final int HOTBAR = 0;
	/**
	 * The ending slot of the hotbar.
	 */
	protected static final int SLOT_BAR = 9;
	/**
	 * The slot boots are in.
	 */
	protected static final int BOOTS = 36;
	/**
	 * The slot leggings are in.
	 */
	protected static final int LEGGINGS = 37;
	/**
	 * The slot the chestplate is in.
	 */
	protected static final int CHESTPLATE = 38;
	/**
	 * The slot the helmet is in.
	 */
	protected static final int HELMET = 39;
	/**
	 * The slot of the offhand.
	 */
	protected static final int OFF_HAND = 40;
	private int mainHandSlot = 0;

	/**
	 * Constructs a new {@link PlayerInventoryMock}.
	 *
	 * @param holder The holder of the inventory.
	 */
	public PlayerInventoryMock(HumanEntity holder)
	{
		super(holder, InventoryType.PLAYER);
	}

	@Override
	public HumanEntity getHolder()
	{
		return (HumanEntity) super.getHolder();
	}

	@Override
	public ItemStack @NotNull [] getStorageContents()
	{
		return Arrays.copyOfRange(getContents(), 0, 36);
	}

	@Override
	public void setStorageContents(ItemStack[] items) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException("setStorageContests has not been implemented for Player Inventories");
	}

	@Override
	public ItemStack @NotNull [] getArmorContents()
	{
		return Arrays.copyOfRange(getContents(), BOOTS, BOOTS + 4);
	}

	@Override
	public ItemStack @NotNull [] getExtraContents()
	{
		return Arrays.copyOfRange(getContents(), OFF_HAND, OFF_HAND + 1);
	}

	@Override
	public @Nullable ItemStack getHelmet()
	{
		return getItem(HELMET);
	}

	@Override
	public @Nullable ItemStack getChestplate()
	{
		return getItem(CHESTPLATE);
	}

	@Override
	public @Nullable ItemStack getLeggings()
	{
		return getItem(LEGGINGS);
	}

	@Override
	public @Nullable ItemStack getBoots()
	{
		return getItem(BOOTS);
	}

	@Override
	public void setArmorContents(ItemStack @Nullable [] items)
	{
		if (items == null)
			throw new NullPointerException("ItemStack was null");
		else if (items.length > 4)
			throw new IllegalArgumentException("ItemStack array too large (max: 4, was: " + items.length + ")");
		items = (items.length == 4) ? items : Arrays.copyOf(items, 4);
		setItem(BOOTS, items[0]);
		setItem(LEGGINGS, items[1]);
		setItem(CHESTPLATE, items[2]);
		setItem(HELMET, items[3]);
	}

	@Override
	public float getItemInHandDropChance()
	{
		return 1;
	}

	@Override
	public void setItemInHandDropChance(float chance)
	{
		throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
	}

	@Override
	public float getItemInMainHandDropChance()
	{
		return 1;
	}

	@Override
	public void setItemInMainHandDropChance(float chance)
	{
		throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
	}

	@Override
	public float getItemInOffHandDropChance()
	{
		return 1;
	}

	@Override
	public void setItemInOffHandDropChance(float chance)
	{
		throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
	}

	@Override
	public float getHelmetDropChance()
	{
		return 1;
	}

	@Override
	public void setHelmetDropChance(float chance)
	{
		throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
	}

	@Override
	public float getChestplateDropChance()
	{
		return 1;
	}

	@Override
	public void setChestplateDropChance(float chance)
	{
		throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
	}

	@Override
	public float getLeggingsDropChance()
	{
		return 1;
	}

	@Override
	public void setLeggingsDropChance(float chance)
	{
		throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
	}

	@Override
	public float getBootsDropChance()
	{
		return 1;
	}

	@Override
	public void setBootsDropChance(float chance)
	{
		throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
	}

	@Override
	public void setExtraContents(ItemStack @Nullable [] items)
	{
		if (items == null)
			throw new NullPointerException("ItemStack was null");
		else if (items.length > 1)
			throw new IllegalArgumentException("ItemStack array too large (max: 4, was: " + items.length + ")");
		items = (items.length == 1) ? items : Arrays.copyOf(items, 1);
		setItem(OFF_HAND, items[0]);
	}

	@Override
	public void setHelmet(ItemStack helmet)
	{
		setHelmet(helmet, false);
	}

	@Override
	public void setHelmet(@Nullable ItemStack helmet, boolean silent)
	{
		setItem(HELMET, helmet);
		// Sounds are not implemented here
	}

	@Override
	public void setChestplate(ItemStack chestplate)
	{
		setChestplate(chestplate, false);
	}

	@Override
	public void setChestplate(@Nullable ItemStack chestplate, boolean silent)
	{
		setItem(CHESTPLATE, chestplate);
		// Sounds are not implemented here
	}

	@Override
	public void setLeggings(ItemStack leggings)
	{
		setLeggings(leggings, false);
	}

	@Override
	public void setLeggings(@Nullable ItemStack leggings, boolean silent)
	{
		setItem(LEGGINGS, leggings);
		// Sounds are not implemented here
	}

	@Override
	public void setBoots(ItemStack boots)
	{
		setBoots(boots, false);
	}

	@Override
	public void setBoots(@Nullable ItemStack boots, boolean silent)
	{
		setItem(BOOTS, boots);
		// Sounds are not implemented here
	}

	@Override
	public @NotNull ItemStack getItemInMainHand()
	{
		return notNull(getItem(HOTBAR + mainHandSlot));
	}

	@Override
	public void setItemInMainHand(ItemStack item)
	{
		setItemInMainHand(item, false);
	}

	@Override
	public void setItemInMainHand(@Nullable ItemStack item, boolean silent)
	{
		if (getItemInMainHand().getType() == Material.SHIELD)
		{
			((HumanEntityMock) this.getHolder()).setBlocking(false);
		}
		setItem(HOTBAR + mainHandSlot, item);
		// Sounds are not implemented here
	}

	@Override
	public @NotNull ItemStack getItemInOffHand()
	{
		return notNull(getItem(OFF_HAND));
	}

	@Override
	public void setItemInOffHand(ItemStack item)
	{
		setItemInOffHand(item, false);
		// Sounds are not implemented here
	}

	@Override
	public void setItemInOffHand(@Nullable ItemStack item, boolean silent)
	{
		if (getItemInOffHand().getType() == Material.SHIELD)
		{
			((HumanEntityMock) this.getHolder()).setBlocking(false);
		}
		setItem(OFF_HAND, item);
	}

	@Deprecated
	@Override
	public @NotNull ItemStack getItemInHand()
	{
		return getItemInMainHand();
	}

	@Deprecated
	@Override
	public void setItemInHand(ItemStack stack)
	{
		setItemInMainHand(stack);
	}

	@Override
	public @NotNull ItemStack getItem(@NotNull EquipmentSlot slot)
	{
		return switch (slot)
		{
			case CHEST -> notNull(getChestplate());
			case FEET -> notNull(getBoots());
			case HAND -> getItemInMainHand();
			case HEAD -> notNull(getHelmet());
			case LEGS -> notNull(getLeggings());
			case OFF_HAND -> getItemInOffHand();
		};
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
		case CHEST -> setChestplate(item);
		case FEET -> setBoots(item);
		case HAND -> setItemInMainHand(item);
		case HEAD -> setHelmet(item);
		case LEGS -> setLeggings(item);
		case OFF_HAND -> setItemInOffHand(item);
		}
		// Sounds are not implemented here
	}

	@Override
	public int getHeldItemSlot()
	{
		return mainHandSlot;
	}

	@Override
	public void setHeldItemSlot(int slot)
	{
		if (slot < 0 || slot > 8)
			throw new ArrayIndexOutOfBoundsException("Slot should be within [0-8] (was: " + slot + ")");
		mainHandSlot = slot;
	}

	@Override
	public float getDropChance(@NotNull EquipmentSlot slot)
	{
		return 1;
	}

	@Override
	public void setDropChance(@NotNull EquipmentSlot slot, float chance)
	{
		throw new UnsupportedOperationException("Cannot set drop chance for PlayerInventory");
	}

	/**
	 * @param itemStack The item.
	 * @return A new ItemStack of AIR if the provided item is null, otherwise the provided item.
	 */
	private static @NotNull ItemStack notNull(@Nullable ItemStack itemStack)
	{
		return itemStack == null ? new ItemStack(Material.AIR) : itemStack;
	}

}
