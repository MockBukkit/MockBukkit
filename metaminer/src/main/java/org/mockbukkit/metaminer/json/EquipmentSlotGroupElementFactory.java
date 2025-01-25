package org.mockbukkit.metaminer.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.jetbrains.annotations.Nullable;

public class EquipmentSlotGroupElementFactory
{
	/**
	 * Converts a equipmentSlotGroup element into a JsonElement.
	 *
	 * @param equipmentSlotGroup The equipmentSlotGroup to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonElement toJson(@Nullable EquipmentSlotGroup equipmentSlotGroup)
	{
		if (equipmentSlotGroup == null)
		{
			return null;
		}

		return new JsonPrimitive(equipmentSlotGroup.toString());
	}

	private EquipmentSlotGroupElementFactory()
	{
		// Hide the public constructor
	}
}
