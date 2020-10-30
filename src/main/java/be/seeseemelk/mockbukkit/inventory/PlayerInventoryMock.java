package be.seeseemelk.mockbukkit.inventory;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class PlayerInventoryMock extends InventoryMock implements PlayerInventory
{
	protected static final int HOTBAR = 0;
	protected static final int SLOT_BAR = 9;
	protected static final int BOOTS = 36;
	protected static final int LEGGINGS = 37;
	protected static final int CHESTPLATE = 38;
	protected static final int HELMET = 39;
	protected static final int OFF_HAND = 40;
	private int mainHandSlot = 0;

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
	public ItemStack[] getStorageContents()
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
	public ItemStack[] getArmorContents()
	{
		return Arrays.copyOfRange(getContents(), BOOTS, BOOTS + 4);
	}

	@Override
	public ItemStack[] getExtraContents()
	{
		return Arrays.copyOfRange(getContents(), OFF_HAND, OFF_HAND + 1);
	}

	@Override
	public ItemStack getHelmet()
	{
		return getItem(HELMET);
	}

	@Override
	public ItemStack getChestplate()
	{
		return getItem(CHESTPLATE);
	}

	@Override
	public ItemStack getLeggings()
	{
		return getItem(LEGGINGS);
	}

	@Override
	public ItemStack getBoots()
	{
		return getItem(BOOTS);
	}

	@Override
	public void setArmorContents(ItemStack[] items)
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
	public void setExtraContents(ItemStack[] items)
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
		setItem(HELMET, helmet);
	}

	@Override
	public void setChestplate(ItemStack chestplate)
	{
		setItem(CHESTPLATE, chestplate);
	}

	@Override
	public void setLeggings(ItemStack leggings)
	{
		setItem(LEGGINGS, leggings);
	}

	@Override
	public void setBoots(ItemStack boots)
	{
		setItem(BOOTS, boots);
	}

	@Override
	public @NotNull ItemStack getItemInMainHand()
	{
		return notNull(getItem(SLOT_BAR + mainHandSlot));
	}

	@Override
	public void setItemInMainHand(ItemStack item)
	{
		setItem(SLOT_BAR + mainHandSlot, item);
	}

	@Override
	public @NotNull ItemStack getItemInOffHand()
	{
		return notNull(getItem(OFF_HAND));
	}

	@Override
	public void setItemInOffHand(ItemStack item)
	{
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

	/**
	 * Gets the {@link ItemStack} at the given equipment slot in the inventory.
	 *
	 * <p>Implementation note: only the contents of the main hand
	 * and the off hand are guaranteed not to be {@code null},
	 * despite the annotation present in the Bukkit api.</p>
	 *
	 * @param slot the slot to get the {@link ItemStack}
	 * @return the {@link ItemStack} in the given slot
	 */
	@Override
	public @Nullable ItemStack getItem(@NotNull EquipmentSlot slot)
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
			return new ItemStack(Material.AIR);
		}
	}

	@Override
	public void setItem(@NotNull EquipmentSlot slot, @Nullable ItemStack item)
	{
		switch (slot)
		{
		case CHEST:
			setChestplate(item);
			break;
		case FEET:
			setBoots(item);
			break;
		case HAND:
			setItemInMainHand(item);
			break;
		case HEAD:
			setHelmet(item);
			break;
		case LEGS:
			setLeggings(item);
			break;
		case OFF_HAND:
			setItemInOffHand(item);
			break;
		default:
			break;
		}
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

	private @NotNull ItemStack notNull(@Nullable ItemStack itemStack)
	{
		return itemStack == null ? new ItemStack(Material.AIR) : itemStack;
	}
}
