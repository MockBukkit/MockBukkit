package be.seeseemelk.mockbukkit.attribute;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.bukkit.attribute.Attribute;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Mocks the {@code net.minecraft.world.entity.ai.attributes.Attributes} class with the default Attribute values.
 */
public class AttributesMock
{

	private static final Map<Attribute, Double> DEFAULT_ATTRIBUTE_VALUES = ImmutableMap.ofEntries(
			Map.entry(Attribute.GENERIC_MAX_HEALTH, 20.0), Map.entry(Attribute.GENERIC_FOLLOW_RANGE, 32.0),
			Map.entry(Attribute.GENERIC_KNOCKBACK_RESISTANCE, 0.0),
			Map.entry(Attribute.GENERIC_MOVEMENT_SPEED, 0.699999988079071),
			Map.entry(Attribute.GENERIC_FLYING_SPEED, 0.4000000059604645),
			Map.entry(Attribute.GENERIC_ATTACK_DAMAGE, 2.0), Map.entry(Attribute.GENERIC_ATTACK_KNOCKBACK, 0.0),
			Map.entry(Attribute.GENERIC_ATTACK_SPEED, 4.0), Map.entry(Attribute.GENERIC_ARMOR, 0.0),
			Map.entry(Attribute.GENERIC_ARMOR_TOUGHNESS, 0.0), Map.entry(Attribute.GENERIC_LUCK, 0.0),
			Map.entry(Attribute.HORSE_JUMP_STRENGTH, 0.7), Map.entry(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS, 0.0));

	/**
	 * Gets the default value of an {@link Attribute}.
	 *
	 * @param attribute The attribute to get.
	 * @return The default value of the attribute.
	 */
	public static double getDefaultValue(@NotNull Attribute attribute)
	{
		Preconditions.checkNotNull(attribute, "Attribute cannot be null");
		return DEFAULT_ATTRIBUTE_VALUES.get(attribute);
	}

	private AttributesMock()
	{
		throw new UnsupportedOperationException("Utility class");
	}

}
