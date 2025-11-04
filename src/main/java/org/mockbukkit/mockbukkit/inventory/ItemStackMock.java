package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.persistence.PersistentDataContainerView;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.mockbukkit.mockbukkit.exception.IncompatiblePaperVersionException;
import org.mockbukkit.mockbukkit.exception.ItemMetaInitException;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;
import org.mockbukkit.mockbukkit.persistence.PersistentDataContainerViewMock;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;


@DelegateDeserialization(ItemStack.class)
public class ItemStackMock extends ItemStack
{

	private static final String FIELD_AMOUNT = "amount";
	private static final String FIELD_MATERIAL = "type";

	@NonNull
	@ApiStatus.Internal
	public static ItemStackMock fromJson(@NonNull JsonObject json)
	{
		Preconditions.checkNotNull(json, "json cannot be null");

		int amountValue = json.get(FIELD_AMOUNT).getAsInt();
		NamespacedKey materialKey = NamespacedKey.fromString(json.get(FIELD_MATERIAL).getAsString());
		Preconditions.checkArgument(materialKey != null, "Material field does not exist");

		try
		{
			Material materialValue = Material.valueOf(materialKey.getKey().toUpperCase(Locale.ROOT));
			return new ItemStackMock(materialValue, amountValue);
		} catch (IllegalArgumentException e)
		{
			throw new IncompatiblePaperVersionException(e);
		}
	}

	private final Map<DataComponentType, Object> components = new HashMap<>();

	private ItemType type = ItemTypeMock.AIR;
	private int amount = 1;
	private ItemMeta itemMeta;
	private short durability = -1;

	private static final ItemStackMock EMPTY = new ItemStackMock((Void) null);
	private static final String ITEM_META_INITIALIZATION_ERROR = "Failed to instanciate item meta class ";

	//Utility
	protected ItemStackMock()
	{
	}

	public ItemStackMock(@NotNull Material type)
	{
		this(type, 1);
	}

	public ItemStackMock(@NotNull ItemStack stack) throws IllegalArgumentException
	{
		this.type = stack.getType().asItemType();
		this.amount = stack.getAmount();
		this.durability = initDurability(this.type);
		setItemMeta(stack.getItemMeta());
	}

	public ItemStackMock(@NotNull Material type, int amount)
	{
		this.type = type.asItemType();
		this.amount = amount;
		this.durability = initDurability(this.type);
		this.itemMeta = findItemMeta(type);
	}

	private ItemStackMock(@Nullable Void v)
	{
		this.type = ItemTypeMock.AIR;
		this.durability = initDurability(type);
		this.amount = 0;
		this.itemMeta = null;
	}

	private ItemStackMock(@NotNull ItemType type)
	{
		this.type = type;
		this.durability = initDurability(type);
		this.itemMeta = findItemMeta(type.asMaterial());
	}

	/**
	 * By some reason paper differentiates between an item with durability set and one without durability set
	 */
	private short initDurability(ItemType type)
	{
		if (type == null || type.getMaxDurability() == 0)
		{
			return -1;
		}
		return 0;
	}

	@Override
	public void setType(@NotNull Material type)
	{
		if (!type.isItem() || type.isAir())
		{
			this.type = ItemType.AIR;
			this.itemMeta = null;
			this.durability = initDurability(this.type);
			return;
		}
		if (type != this.type.asMaterial())
		{
			this.type = type.asItemType();
			if (this.itemMeta == null)
			{
				this.itemMeta = findItemMeta(type);
			}
			else
			{
				this.itemMeta = Bukkit.getItemFactory().asMetaFor(this.itemMeta, type);
			}
			if (this.durability == 0)
			{
				this.durability = initDurability(this.type);
				((Damageable) this.itemMeta).resetDamage();
			}
			else
			{
				setDurability(this.durability);
			}
		}
	}

	@NotNull
	public Material getType()
	{
		return this.type.asMaterial();
	}

	@Override
	public int getAmount()
	{
		return this.amount;
	}

	@Override
	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	@Override
	public boolean isEmpty()
	{
		return this == EMPTY || this.type == ItemTypeMock.AIR || this.amount <= 0;
	}

	@Override
	public boolean setItemMeta(@Nullable ItemMeta itemMeta)
	{
		if (itemMeta == null || ItemType.AIR.equals(this.type))
		{
			this.itemMeta = findItemMeta(getType());
			this.durability = initDurability(this.type);
			return true;
		}
		if (!Bukkit.getItemFactory().isApplicable(itemMeta, this))
		{
			return false;
		}

		itemMeta = Bukkit.getItemFactory().asMetaFor(itemMeta, this);
		if (itemMeta == null)
		{
			return true;
		}
		this.itemMeta = itemMeta;

		if (this.itemMeta instanceof Damageable damageable)
		{
			short defaultDurability = initDurability(this.type);
			if (!damageable.hasDamageValue())
			{
				durability = defaultDurability;
			}
			else
			{
				short value = (short) Math.min(Short.MAX_VALUE, damageable.getDamage());
				setDurability(value);
				if (durability == defaultDurability)
				{
					damageable.resetDamage();
				}
			}
		}
		return true;
	}

	@Override
	public ItemMeta getItemMeta()
	{
		return this.itemMeta != null ? this.itemMeta.clone() : null;
	}

	@Override
	public boolean hasItemMeta()
	{
		return this.itemMeta != null && !Bukkit.getItemFactory().equals(itemMeta, null);
	}

	@Override
	public @NotNull Component effectiveName()
	{
		// TODO:
		throw new UnimplementedOperationException();
	}

	@Override
	public int getMaxStackSize()
	{
		if (this.itemMeta == null)
		{
			return 0;
		}

		return this.itemMeta.hasMaxStackSize() ? this.itemMeta.getMaxStackSize() : this.type.getMaxStackSize();
	}

	@Override
	public short getDurability()
	{
		if (this.type == ItemType.AIR)
		{
			return -1;
		}

		return (short) Math.max(this.durability, 0);
	}

	@Override
	public void setDurability(short durability)
	{
		short oldDurability = this.durability;
		this.durability = (short) Math.min(Math.max(durability, 0), this.type.getMaxDurability());
		if ((this.itemMeta instanceof Damageable damageable) && this.durability != oldDurability)
		{
			damageable.setDamage(this.durability);
		}
	}

	@Override
	public void addUnsafeEnchantment(@NotNull Enchantment ench, int level)
	{
		Preconditions.checkArgument(ench != null, "Enchantment cannot be null");

		if (this.itemMeta != null)
		{
			this.itemMeta.addEnchant(ench, level, true);
		}
	}

	@Override
	public int getEnchantmentLevel(Enchantment ench)
	{
		Preconditions.checkArgument(ench != null, "Enchantment cannot be null");

		final ItemMeta meta = this.itemMeta;
		Preconditions.checkNotNull(meta, "Meta must not be null");

		return meta.getEnchantLevel(ench);
	}

	@Override
	public @NotNull Map<Enchantment, Integer> getEnchantments()
	{
		return this.hasItemMeta() ? itemMeta.getEnchants() : Map.of();
	}

	@Override
	public boolean isSimilar(@Nullable ItemStack stack)
	{
		if (stack == null)
		{
			return false;
		}
		if (!(stack instanceof final ItemStackMock bukkit))
		{
			return stack.isSimilar(this);
		}
		if (this == bukkit)
		{
			return true;
		}
		return this.type == bukkit.type && hasSimilarItemMeta(stack.getItemMeta());
	}

	/**
	 * Validate that the item meta is equal for both items.
	 *
	 * @param itemMeta The item meta to be validated.
	 *
	 * @return {@code true} if equal, or {@code false} otherwise.
	 */
	private boolean hasSimilarItemMeta(@Nullable ItemMeta itemMeta)
	{
		if (this.itemMeta == null)
		{
			return itemMeta == null;
		}

		return this.itemMeta.equals(itemMeta);
	}

	private final PersistentDataContainerView pdcView = new PersistentDataContainerViewMock()
	{
		@Override
		public <P, C> @Nullable C get(@NotNull NamespacedKey key, @NotNull PersistentDataType<P, C> type)
		{
			if (itemMeta == null)
			{
				return null;
			}

			return itemMeta.getPersistentDataContainer().get(key, type);
		}

		@Override
		public @NotNull Set<NamespacedKey> getKeys()
		{
			if (itemMeta == null)
			{
				return Set.of();
			}

			return itemMeta.getPersistentDataContainer().getKeys();
		}
	};

	@Override
	public @NotNull PersistentDataContainerView getPersistentDataContainer()
	{
		return pdcView;
	}

	@Override
	public boolean editPersistentDataContainer(@NotNull Consumer<PersistentDataContainer> consumer)
	{
		// TODO:
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack withType(@NotNull Material type)
	{
		ItemStackMock item = new ItemStackMock(type, getAmount());
		if (this.durability != -1)
		{
			item.setDurability(this.durability);
		}
		item.setItemMeta(this.itemMeta);

		return item;
	}

	@Override
	public boolean containsEnchantment(@NotNull Enchantment ench)
	{
		if (this.itemMeta == null)
		{
			return false;
		}

		return this.itemMeta.getEnchants().containsKey(ench);
	}

	@Override
	public int removeEnchantment(@NotNull Enchantment ench)
	{
		if (this.itemMeta == null)
		{
			return 0;
		}

		int level = this.itemMeta.getEnchantLevel(ench);
		this.itemMeta.removeEnchant(ench);
		return level;
	}

	@Override
	public void removeEnchantments()
	{
		if (this.itemMeta == null)
		{
			return;
		}

		this.itemMeta.removeEnchantments();
	}

	@Override
	public int getMaxItemUseDuration(@NotNull LivingEntity entity)
	{
		//TODO
		throw new UnimplementedOperationException();
	}

	public static ItemStack empty()
	{
		return EMPTY.clone();
	}

	@SuppressWarnings("MethodDoesntCallSuperMethod")
	@Override
	public @NotNull ItemStack clone()
	{
		return new ItemStackMock(this);
	}

	@Override
	public @Nullable <T> T getData(DataComponentType.@NotNull Valued<T> type)
	{
		return (T) this.components.get(type);
	}

	@Override
	public boolean hasData(@NotNull DataComponentType type)
	{
		return this.components.containsKey(type);
	}

	@Override
	public @Unmodifiable Set<@NotNull DataComponentType> getDataTypes()
	{
		return this.components.keySet();
	}

	@Override
	public <T> void setData(DataComponentType.@NotNull Valued<T> type, @NonNull T value)
	{
		this.components.put(type, value);
	}

	@Override
	public void setData(DataComponentType.@NotNull NonValued type)
	{
		this.components.put(type, null);
	}

	@Override
	public void unsetData(@NotNull DataComponentType type)
	{
		this.components.remove(type);
	}

	@Override
	public void resetData(@NotNull DataComponentType type)
	{
		// TODO:
		throw new UnimplementedOperationException();
	}

	@Override
	public void copyDataFrom(@NotNull ItemStack source, @NotNull Predicate<@NotNull DataComponentType> filter)
	{
		Preconditions.checkArgument(source != null, "source cannot be null");
		Preconditions.checkArgument(filter != null, "filter cannot be null");

		if (isEmpty() || source.isEmpty())
		{
			return;
		}

		// TODO:
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isDataOverridden(@NotNull DataComponentType type)
	{
		return !isEmpty() && this.components.containsKey(type);
	}

	@Override
	public boolean matchesWithoutData(@NotNull ItemStack item, @NotNull Set<@NotNull DataComponentType> excludeTypes, boolean ignoreCount)
	{
		// TODO:
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof ItemStack stack))
		{
			return false;
		}
		if (stack instanceof ItemStackMock bukkit)
		{
			return isSimilar(bukkit) && this.getAmount() == bukkit.getAmount() && this.getDurability() == bukkit.getDurability() && Objects.equals(this.getItemMeta(), bukkit.getItemMeta());
		}
		else
		{
			// will delegate back to this method / no stack overflow as obj then will be item stack mock instance
			return stack.equals(this);
		}
	}

	@Override
	public int hashCode()
	{
		if (type == ItemType.AIR && this != EMPTY)
		{
			return EMPTY.hashCode();
		}
		else
		{
			int hash = Objects.hash(type, durability, lore(), getEnchantments());
			hash = hash * 31 + this.getAmount();
			return hash;
		}
	}

	private static @Nullable ItemMeta findItemMeta(Material material)
	{
		if (!material.isItem() || material == Material.AIR)
		{
			return null;
		}
		final Class<? extends ItemMeta> itemMetaClass = material.asItemType().getItemMetaClass();
		if (ItemMetaMock.class.isAssignableFrom(itemMetaClass))
		{
			try
			{
				for (var ctor : itemMetaClass.getDeclaredConstructors())
				{
					if (ctor.getParameterCount() == 1 && ctor.getParameters()[0].getType() == Material.class)
					{
						return (ItemMeta) ctor.newInstance(material);
					}
				}
				return itemMetaClass.getConstructor().newInstance();
			}
			catch (InstantiationException | IllegalAccessException | InvocationTargetException |
				   NoSuchMethodException e)
			{
				throw new ItemMetaInitException(ITEM_META_INITIALIZATION_ERROR + itemMetaClass, e);
			}
		}
		return new ItemMetaMock();
	}

	@NotNull
	public static ItemStack deserialize(@NotNull Map<String, Object> args)
	{
		return Bukkit.getUnsafe().deserializeStack(args);
	}

}
