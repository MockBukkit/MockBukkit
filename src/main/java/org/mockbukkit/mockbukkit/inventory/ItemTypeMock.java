package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.math.BigDecimal;
import java.util.function.Consumer;

@SuppressWarnings({ "UnstableApiUsage", "unchecked" })
public class ItemTypeMock<M extends ItemMeta> implements ItemType.Typed<M>
{

	private final NamespacedKey namespacedKey;
	private final boolean blockType;
	private final int maxStackSize;
	private final short maxDurability;
	private final boolean edible;
	private final boolean hasRecord;
	private final boolean fuel;
	private final String translationKey;
	private final Class<M> metaClass;
	private final ItemRarity rarity;
	private final CreativeCategory creativeCategory;
	private final boolean isCompostable;
	private final BigDecimal compostChance;

	private ItemTypeMock(NamespacedKey namespacedKey, int maxStackSize, short maxDurability,
						 boolean edible, boolean hasRecord, boolean fuel, boolean blockType, String translationKey,
						 Class<M> metaClass, ItemRarity rarity, CreativeCategory creativeCategory,boolean isCompostable,
						 BigDecimal compostChance)
	{
		this.namespacedKey = namespacedKey;
		this.maxStackSize = maxStackSize;
		this.maxDurability = maxDurability;
		this.edible = edible;
		this.hasRecord = hasRecord;
		this.fuel = fuel;
		this.blockType = blockType;
		this.translationKey = translationKey;
		this.metaClass = metaClass;
		this.rarity = rarity;
		this.creativeCategory = creativeCategory;
		this.isCompostable = isCompostable;
		this.compostChance = compostChance;
	}

	@ApiStatus.Internal
	public static ItemType from(JsonObject jsonObject)
	{
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		int maxStackSize = jsonObject.get("maxStackSize").getAsInt();
		short maxDurability = jsonObject.get("maxDurability").getAsShort();
		boolean edible = jsonObject.get("edible").getAsBoolean();
		boolean hasRecord = jsonObject.get("record").getAsBoolean();
		boolean fuel = jsonObject.get("fuel").getAsBoolean();
		boolean blockType = jsonObject.get("blockType").getAsBoolean();
		String translationKey = jsonObject.get("translationKey").getAsString();
		ItemRarity rarity = ItemRarity.valueOf(jsonObject.get("rarity").getAsString());
		CreativeCategory creativeCategory = CreativeCategory.valueOf(jsonObject.get("creativeCategory").getAsString());
		boolean isCompostable = jsonObject.get("compostable").getAsBoolean();
		BigDecimal compostChance = new BigDecimal(0);
		if (isCompostable)
		{
			compostChance = BigDecimal.valueOf(jsonObject.get("compostChance").getAsFloat());
		}

		Class<? extends ItemMeta> metaClass = null;
		String metaClassKey = "metaClass";
		if (jsonObject.has(metaClassKey))
		{
			String metaClassAsString = jsonObject.get(metaClassKey).getAsString();

			try
			{
				if (
						metaClassAsString.equals("BlockStateMeta")
								|| metaClassAsString.equals("BlockDataMeta")
								|| metaClassAsString.equals("EnchantmentStorageMeta")
								|| metaClassAsString.equals("MusicInstrumentMeta")
				)
				{
					//Unimplemented Meta class, falling back to ItemMeta
					metaClass = ItemMetaMock.class;
				}
				else
				{
					String metaClassName =
							"org.mockbukkit.mockbukkit.inventory.meta."
									+ jsonObject.get(metaClassKey).getAsString()
									+ "Mock";
					metaClass = (Class<? extends ItemMeta>) Class.forName(metaClassName);
				}
			}
			catch (ClassNotFoundException e)
			{
				throw new IllegalStateException("Could not find class: " + jsonObject.get(metaClassKey).getAsString());
			}
		}

		return new ItemTypeMock<>(
				key,
				maxStackSize,
				maxDurability,
				edible,
				hasRecord,
				fuel,
				blockType,
				translationKey,
				metaClass,
				rarity,
				creativeCategory,
				isCompostable,
				compostChance
		);
	}

	@NotNull
	@Override
	public Typed<ItemMeta> typed()
	{
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public <M extends ItemMeta> Typed<M> typed(@NotNull Class<M> itemMetaType)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack createItemStack()
	{
		return this.createItemStack(1);
	}

	@Override
	public @NotNull ItemStack createItemStack(int amount)
	{
		return new ItemStackMock(this.asMaterial(), amount);
	}

	@Override
	public boolean hasBlockType()
	{
		return this.blockType;
	}

	@Override
	public @NotNull BlockType getBlockType()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Class<M> getItemMetaClass()
	{
		if (this == ItemType.AIR)
		{
			throw new UnsupportedOperationException("Air does not have ItemMeta");
		}

		return this.metaClass;
	}

	@Override
	public @NotNull ItemStack createItemStack(@Nullable Consumer<? super M> metaConfigurator)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack createItemStack(int amount, @Nullable Consumer<? super M> metaConfigurator)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public int getMaxStackSize()
	{
		return this.maxStackSize;
	}

	@Override
	public short getMaxDurability()
	{
		return this.maxDurability;
	}

	@Override
	public boolean isEdible()
	{
		return this.edible;
	}

	@Override
	public boolean isRecord()
	{
		return this.hasRecord;
	}

	@Override
	public boolean isFuel()
	{
		return this.fuel;
	}

	@Override
	public boolean isCompostable()
	{
		return this.isCompostable;
	}

	@Override
	public float getCompostChance()
	{
		Preconditions.checkArgument(
				this.isCompostable(), "The item type " + this.getKey() + " is not compostable"
		);
		return this.compostChance.floatValue();
	}

	@Override
	public @Nullable ItemType getCraftingRemainingItem()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull @Unmodifiable Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable CreativeCategory getCreativeCategory()
	{
		return this.creativeCategory;
	}

	@Override
	public boolean isEnabledByFeature(@NotNull World world)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Material asMaterial()
	{
		return Registry.MATERIAL.get(this.namespacedKey);
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return namespacedKey;
	}

	@Override
	public @NotNull String getTranslationKey()
	{
		return translationKey;
	}

	@Override
	public @Nullable ItemRarity getItemRarity()
	{
		return this.rarity;
	}

	@Override
	public @NotNull String translationKey()
	{
		return translationKey;
	}

}
