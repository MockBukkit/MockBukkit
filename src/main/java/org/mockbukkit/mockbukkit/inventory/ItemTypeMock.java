package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.papermc.paper.datacomponent.DataComponentType;
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
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@SuppressWarnings({ "UnstableApiUsage", "unchecked" })
public class ItemTypeMock<M extends ItemMeta> implements ItemType.Typed<M>
{

	private final NamespacedKey namespacedKey;
	private final @Nullable NamespacedKey blockType;
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
	private final int burnDuration;
	private final @NotNull Set<DataComponentType> defaultDataTypes;
	private final @NotNull Map<DataComponentType, Object> defaultData;

	private ItemTypeMock(NamespacedKey namespacedKey, int maxStackSize, short maxDurability,
						 boolean edible, boolean hasRecord, boolean fuel, @Nullable NamespacedKey blockType, String translationKey,
						 Class<M> metaClass, ItemRarity rarity, CreativeCategory creativeCategory, boolean isCompostable,
						 BigDecimal compostChance, int burnDuration, Set<DataComponentType> defaultDataTypes,
						 Map<DataComponentType, Object> defaultData)
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
		this.burnDuration = burnDuration;
		this.defaultDataTypes = Set.copyOf(defaultDataTypes);
		this.defaultData = Map.copyOf(defaultData);
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
		NamespacedKey blockType = jsonObject.has("blockType") ? NamespacedKey.fromString(jsonObject.get("blockType").getAsString()) : null;
		String translationKey = jsonObject.get("translationKey").getAsString();
		ItemRarity rarity = ItemRarity.valueOf(jsonObject.get("itemRarity").getAsString());
		CreativeCategory creativeCategory = CreativeCategory.valueOf(jsonObject.get("creativeCategory").getAsString());
		boolean isCompostable = jsonObject.get("compostable").getAsBoolean();
		int burnDuration = jsonObject.get("burnDuration").getAsInt();

		BigDecimal compostChance = BigDecimal.ZERO;
		if (isCompostable)
		{
			compostChance = BigDecimal.valueOf(jsonObject.get("compostChance").getAsFloat());
		}

		Class<? extends ItemMeta> metaClass = parseMetaClass(jsonObject);
		Set<DataComponentType> defaultDataTypes = parseDefaultDataTypes(jsonObject);
		Map<DataComponentType, Object> defaultData = parseDefaultData(jsonObject);

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
				compostChance,
				burnDuration,
				defaultDataTypes,
				defaultData
		);
	}

	private static Class<? extends ItemMeta> parseMetaClass(JsonObject jsonObject)
	{
		String metaClassKey = "metaClass";
		if (!jsonObject.has(metaClassKey))
		{
			return null;
		}

		String metaClassAsString = jsonObject.get(metaClassKey).getAsString();
		if (metaClassAsString.equals("BlockDataMeta") || metaClassAsString.equals("MusicInstrumentMeta"))
		{
			//Unimplemented Meta class, falling back to ItemMeta
			return ItemMetaMock.class;
		}

		try
		{
			String metaClassName = "org.mockbukkit.mockbukkit.inventory.meta." + metaClassAsString + "Mock";
			return (Class<? extends ItemMeta>) Class.forName(metaClassName);
		}
		catch (ClassNotFoundException e)
		{
			throw new IllegalStateException("Could not find class: " + metaClassAsString);
		}
	}

	private static Set<DataComponentType> parseDefaultDataTypes(JsonObject jsonObject)
	{
		Set<DataComponentType> defaultDataTypes = new HashSet<>();
		if (jsonObject.has("defaultDataTypes"))
		{
			JsonArray typesArray = jsonObject.getAsJsonArray("defaultDataTypes");
			for (JsonElement element : typesArray)
			{
				NamespacedKey typeKey = NamespacedKey.fromString(element.getAsString());
				DataComponentType type = Registry.DATA_COMPONENT_TYPE.get(typeKey);
				if (type != null)
				{
					defaultDataTypes.add(type);
				}
			}
		}
		return defaultDataTypes;
	}

	private static Map<DataComponentType, Object> parseDefaultData(JsonObject jsonObject)
	{
		Map<DataComponentType, Object> defaultData = new HashMap<>();
		if (jsonObject.has("defaultData"))
		{
			JsonObject dataObject = jsonObject.getAsJsonObject("defaultData");
			for (Map.Entry<String, JsonElement> entry : dataObject.entrySet())
			{
				NamespacedKey typeKey = NamespacedKey.fromString(entry.getKey());
				DataComponentType type = Registry.DATA_COMPONENT_TYPE.get(typeKey);
				if (type != null)
				{
					Object value = deserializeComponent(type, entry.getValue());
					if (value != null)
					{
						defaultData.put(type, value);
					}
				}
			}
		}
		return defaultData;
	}

	private static @Nullable Object deserializeComponent(DataComponentType type, JsonElement json)
	{
		if (json.isJsonPrimitive())
		{
			com.google.gson.JsonPrimitive primitive = json.getAsJsonPrimitive();
			if (primitive.isNumber())
			{
				return primitive.getAsNumber();
			}
			if (primitive.isBoolean())
			{
				return primitive.getAsBoolean();
			}
			if (primitive.isString())
			{
				return primitive.getAsString();
			}
		}
		// Complex components should use their respective Mock deserialize method if available
		// For now, we return the raw map if it's an object
		if (json.isJsonObject())
		{
			// Convert JsonObject to Map
			return new com.google.gson.Gson().fromJson(json, Map.class);
		}
		return null;
	}

	@NotNull
	@Override
	public Typed<ItemMeta> typed()
	{
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public <M2 extends ItemMeta> Typed<M2> typed(@NotNull Class<M2> itemMetaType)
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
		return new ItemStackMock(Registry.MATERIAL.get(this.getKey()), amount);
	}

	@Override
	public boolean hasBlockType()
	{
		return this.blockType != null;
	}

	@Override
	public @NotNull BlockType getBlockType()
	{
		if (this == AIR)
		{
			return BlockType.AIR;
		}
		Preconditions.checkArgument(this.blockType != null, "The item type %s has no corresponding block type", this.getKey());
		BlockType block = Registry.BLOCK.get(this.blockType);
		Preconditions.checkState(block != null && block != ItemType.AIR, "The item type %s has no corresponding item type", this.getKey());
		return block;
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
	public int getBurnDuration()
	{
		return burnDuration;
	}

	@Override
	public boolean isCompostable()
	{
		return this.isCompostable;
	}

	@Override
	public float getCompostChance()
	{
		Preconditions.checkArgument(this.isCompostable(), "The item type %s is not compostable", this.getKey());
		return this.compostChance.floatValue();
	}

	@Override
	public @Nullable ItemType getCraftingRemainingItem()
	{
		return null;
	}

	@Override
	public @NotNull @Unmodifiable Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers()
	{
		return com.google.common.collect.ImmutableMultimap.of();
	}

	@Override
	public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot)
	{
		return com.google.common.collect.ImmutableMultimap.of();
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
	public <T> @Nullable T getDefaultData(DataComponentType.@NotNull Valued<T> valued)
	{
		if (this.defaultData.containsKey(valued))
		{
			return (T) this.defaultData.get(valued);
		}
		// Fallbacks for common values that we already have in fields
		if (valued.getKey().equals(NamespacedKey.minecraft("max_stack_size")))
		{
			return (T) Integer.valueOf(this.maxStackSize);
		}
		if (valued.getKey().equals(NamespacedKey.minecraft("rarity")))
		{
			return (T) this.rarity;
		}
		if (valued.getKey().equals(NamespacedKey.minecraft("max_damage")) && this.maxDurability > 0)
		{
			return (T) Integer.valueOf(this.maxDurability);
		}
		return null;
	}

	@Override
	public boolean hasDefaultData(@NotNull DataComponentType dataComponentType)
	{
		return this.defaultDataTypes.contains(dataComponentType);
	}

	@Override
	public @Unmodifiable @NotNull Set<DataComponentType> getDefaultDataTypes()
	{
		return java.util.Collections.unmodifiableSet(this.defaultDataTypes);
	}

	@Override
	public @NotNull String translationKey()
	{
		return translationKey;
	}

}
