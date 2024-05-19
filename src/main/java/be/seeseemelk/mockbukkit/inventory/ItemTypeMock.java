package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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

public class ItemTypeMock implements ItemType
{

	private final NamespacedKey namespacedKey;
	private final boolean blockType;
	private final int maxStackSize;
	private final short maxDurability;
	private final boolean edible;
	private final boolean record;
	private final boolean fuel;
	private final String translationKey;

	private ItemTypeMock(NamespacedKey namespacedKey, int maxStackSize, short maxDurability,
						 boolean edible, boolean record, boolean fuel, boolean blockType, String translationKey)
	{
		this.namespacedKey = namespacedKey;
		this.maxStackSize = maxStackSize;
		this.maxDurability = maxDurability;
		this.edible = edible;
		this.record = record;
		this.fuel = fuel;
		this.blockType = blockType;
		this.translationKey = translationKey;
	}

	@ApiStatus.Internal
	public static ItemType from(JsonObject jsonObject)
	{
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		int maxStackSize = jsonObject.get("maxStackSize").getAsInt();
		short maxDurability = jsonObject.get("maxDurability").getAsShort();
		boolean edible = jsonObject.get("edible").getAsBoolean();
		boolean record = jsonObject.get("record").getAsBoolean();
		boolean fuel = jsonObject.get("fuel").getAsBoolean();
		boolean blockType = jsonObject.get("blockType").getAsBoolean();
		String translationKey = jsonObject.get("translationKey").getAsString();

		return new ItemTypeMock(key, maxStackSize, maxDurability, edible, record, fuel, blockType, translationKey);
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
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack createItemStack(int amount)
	{
		throw new UnimplementedOperationException();
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
	public @NotNull Class<? extends ItemMeta> getItemMetaClass()
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
		return this.record;
	}

	@Override
	public boolean isFuel()
	{
		return this.fuel;
	}

	@Override
	public @Nullable ItemType getCraftingRemainingItem()
	{
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
		throw new UnimplementedOperationException();
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

}
