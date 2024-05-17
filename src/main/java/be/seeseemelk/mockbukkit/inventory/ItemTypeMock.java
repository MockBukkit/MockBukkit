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

	private ItemTypeMock(NamespacedKey namespacedKey)
	{
		this.namespacedKey = namespacedKey;
	}

	@ApiStatus.Internal
	public static ItemType from(JsonObject jsonObject)
	{
		return new ItemTypeMock(NamespacedKey.fromString(jsonObject.get("key").getAsString()));
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
		throw new UnimplementedOperationException();
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
		throw new UnimplementedOperationException();
	}

	@Override
	public short getMaxDurability()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isEdible()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isRecord()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isFuel()
	{
		throw new UnimplementedOperationException();
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
		throw new UnimplementedOperationException();
	}

}
