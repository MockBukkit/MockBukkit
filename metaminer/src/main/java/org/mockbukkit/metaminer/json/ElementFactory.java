package org.mockbukkit.metaminer.json;

import com.google.gson.JsonElement;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ElementFactory
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ElementFactory.class);
	private static final Set<String> LOGGED_NAMES = new HashSet<>();


	@Nullable
	public static JsonElement toJson(@Nullable Object object)
	{
		if (object == null)
		{
			return null;
		}

		Class<?> returnType = object.getClass();
		// Check for primitives
		if (Boolean.class == returnType)
		{
			return PrimitiveElementFactory.toJson((Boolean) object);
		}
		if (Number.class.isAssignableFrom(returnType))
		{
			return PrimitiveElementFactory.toJson((Number) object);
		}
		if (Character.class == returnType)
		{
			return PrimitiveElementFactory.toJson((Character) object);
		}
		if (String.class == returnType)
		{
			return PrimitiveElementFactory.toJson((String) object);
		}
		// Check for others
		if (Component.class.isAssignableFrom(returnType))
		{
			return ComponentElementFactory.toJson((Component) object);
		}
		// Check for bukkit
		if (Color.class.isAssignableFrom(returnType))
		{
			return ColorElementFactory.toJson((Color) object);
		}
		if (PotionEffect.class.isAssignableFrom(returnType))
		{
			return PotionEffectElementFactory.toJson((PotionEffect) object);
		}
		if (EquipmentSlotGroup.class.isAssignableFrom(returnType))
		{
			return EquipmentSlotGroupElementFactory.toJson((EquipmentSlotGroup) object);
		}
		if (Attributable.class.isAssignableFrom(returnType))
		{
			return AttributableElementFactory.toJson((Attributable) object);
		}
		if (AttributeInstance.class.isAssignableFrom(returnType))
		{
			return AttributeInstanceElementFactory.toJson((AttributeInstance) object);
		}
		if (AttributeModifier.class.isAssignableFrom(returnType))
		{
			return AttributeModifierElementFactory.toJson((AttributeModifier) object);
		}
		if (Keyed.class.isAssignableFrom(returnType))
		{
			return KeyedElementFactory.toJson((Keyed) object);
		}
		// Java variants
		if (returnType.isEnum())
		{
			return EnumElementFactory.toJson((Enum<?>) object);
		}
		if (Collection.class.isAssignableFrom(returnType))
		{
			return CollectionElementFactory.toJson((Collection<?>) object);
		}
		if (Map.class.isAssignableFrom(returnType))
		{
			return MapElementFactory.toJson((Map<?, ?>) object);
		}
		if (Optional.class.isAssignableFrom(returnType))
		{
			return ElementFactory.toJson(((Optional<?>) object).orElse(null));
		}

		logUnknownType(returnType);
		return null;
	}

	/**
	 * Helper method to log the class and avoid the duplication.
	 *
	 * @param returnType Log a unknown type.
	 */
	private static void logUnknownType(Class<?> returnType)
	{
		String name = returnType.getName();
		if (LOGGED_NAMES.contains(name))
		{
			return;
		}

		LOGGED_NAMES.add(name);
		LOGGER.info("Serializer for class '{}' is not implemented, skipping...", name);
	}

	private ElementFactory()
	{
		// Hide the public constructor
	}

}
