package org.mockbukkit.metaminer.json;

import com.google.gson.JsonObject;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

public class ItemMetaElementFactory
{
	/**
	 * Converts a item meta into a JsonElement.
	 *
	 * @param itemMeta The item meta to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable ItemMeta itemMeta)
	{
		if (itemMeta == null)
		{
			return null;
		}

		JsonObject json = new JsonObject();

		json.add("customName", ComponentElementFactory.toJson(itemMeta.customName()));
		json.add("lore", CollectionElementFactory.toJson(itemMeta.lore(), ComponentElementFactory::toJson));
		json.add("tooltipStyle", KeyedElementFactory.toJson(itemMeta.getTooltipStyle()));
		json.add("itemModel", KeyedElementFactory.toJson(itemMeta.getItemModel()));

		json.addProperty("unbreakable", itemMeta.isUnbreakable());
		json.addProperty("glider", itemMeta.isGlider());

		if (itemMeta.hasCustomModelData())
		{
			json.addProperty("customModelData", itemMeta.getCustomModelData());
		}
		if (itemMeta.hasDamageResistant())
		{
			json.add("damageResistant", KeyedElementFactory.toJson(itemMeta.getDamageResistant()));
		}
		if (itemMeta.hasMaxStackSize())
		{
			json.addProperty("maxStackSize", itemMeta.getMaxStackSize());
		}
		if (itemMeta.hasRarity())
		{
			json.add("rarity", EnumElementFactory.toJson(itemMeta.getRarity()));
		}

		// TODO: Improve the item meta information

		return json;
	}

	private ItemMetaElementFactory()
	{
		// Hide the public constructor
	}
}
