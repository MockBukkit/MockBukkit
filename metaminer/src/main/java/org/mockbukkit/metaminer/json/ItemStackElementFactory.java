package org.mockbukkit.metaminer.json;

import com.google.gson.JsonObject;
import io.papermc.paper.datacomponent.DataComponentType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemStackElementFactory
{

	/**
	 * Converts a item stack into a JsonElement.
	 *
	 * @param itemStack The item stack to be converted.
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable ItemStack itemStack)
	{
		return toJson(itemStack, false);
	}

	/**
	 * Converts a item stack into a JsonElement.
	 *
	 * @param itemStack   The item stack to be converted.
	 * @param includeMeta If should include meta.
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable ItemStack itemStack, boolean includeMeta)
	{
		if (itemStack == null)
		{
			return null;
		}

		JsonObject json = new JsonObject();

		json.add("type", KeyedElementFactory.toJson(itemStack.getType()));
		json.addProperty("amount", itemStack.getAmount());

		var meta = ItemMetaElementFactory.toJson(itemStack.getItemMeta());
		if (includeMeta && meta != null && !meta.isEmpty())
		{
			json.add("meta", meta);
		}

		JsonObject components = new JsonObject();
		for (DataComponentType type : itemStack.getDataTypes())
		{
			if (type instanceof DataComponentType.Valued<?> valued)
			{
				Object value = itemStack.getData(valued);
				com.google.gson.JsonElement element = ElementFactory.toJson(value);
				if (element != null)
				{
					components.add(type.getKey().toString(), element);
				}
			}
		}

		if (!components.isEmpty())
		{
			json.add("components", components);
		}

		return json;
	}

	private ItemStackElementFactory()
	{
		// Hide the public constructor
	}

}
