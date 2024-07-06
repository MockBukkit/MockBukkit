package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.meta.ItemMetaMock;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.function.Consumer;

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

	private ItemTypeMock(NamespacedKey namespacedKey, int maxStackSize, short maxDurability,
						 boolean edible, boolean hasRecord, boolean fuel, boolean blockType, String translationKey,
						 Class<M> metaClass)
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
		Class<? extends ItemMeta> metaClass = null;
		if (jsonObject.has("metaClass"))
		{
			String metaClassAsString = jsonObject.get("metaClass").getAsString();

			try
			{
				if (
						metaClassAsString.equals("BlockStateMeta")
								|| metaClassAsString.equals("BlockDataMeta")
								|| metaClassAsString.equals("EnchantmentStorageMeta")
								|| metaClassAsString.equals("MusicInstrumentMeta")
								|| metaClassAsString.equals("OminousBottleMeta")
								|| metaClassAsString.equals("BundleMeta")
				)
				{
					//Unimplemented Meta class, falling back to ItemMeta
					metaClass = ItemMetaMock.class;
				}
				else
				{
					String metaClassName =
							"be.seeseemelk.mockbukkit.inventory.meta."
									+ jsonObject.get("metaClass").getAsString()
									+ "Mock";
					metaClass = (Class<? extends ItemMeta>) Class.forName(metaClassName);
				}
			}
			catch (ClassNotFoundException e)
			{
				throw new IllegalStateException("Could not find class: " + jsonObject.get("metaClass").getAsString());
			}
		}
		return new ItemTypeMock(key, maxStackSize, maxDurability, edible, hasRecord, fuel, blockType, translationKey, metaClass);
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
		return null;
	}

	@Override
	public @NotNull ItemStack createItemStack(int amount, @Nullable Consumer<? super M> metaConfigurator)
	{
		return null;
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getCompostChance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable ItemType getCraftingRemainingItem()
	{
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
		throw new UnimplementedOperationException();
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
	public @NotNull String translationKey()
	{
		return translationKey;
	}

}
