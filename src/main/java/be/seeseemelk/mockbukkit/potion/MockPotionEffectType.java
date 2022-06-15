package be.seeseemelk.mockbukkit.potion;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

/**
 * This {@link MockPotionEffectType} mocks an actual {@link PotionEffectType} by taking an id, a name, whether it is
 * instant and a RGB {@link Color} variable.
 *
 * @author TheBusyBiscuit
 */
public class MockPotionEffectType extends PotionEffectType
{

	private final int id;
	private final String name;
	private final boolean instant;
	private final Color color;
	private final Map<Attribute, AttributeModifier> attributeModifiers;

	public MockPotionEffectType(NamespacedKey key, int id, String name, boolean instant, Color color)
	{
		super(id, key);

		this.id = id;
		this.name = name;
		this.instant = instant;
		this.color = color;
		this.attributeModifiers = new EnumMap<>(Attribute.class);
	}

	@Deprecated
	@Override
	public double getDurationModifier()
	{
		// This is deprecated and always returns 1.0
		return 1.0;
	}

	@Override
	public @NotNull String getName()
	{
		return name;
	}

	@Override
	public boolean isInstant()
	{
		return instant;
	}

	@Override
	public @NotNull Color getColor()
	{
		return color;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof PotionEffectType)
		{
			// It would make sense to compare the NamespacedKey here but Spigot stil compares ids
			return id == ((PotionEffectType) obj).getId();
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		return id;
	}

	/**
	 * Adds an attribute modifier to this potion effect type.
	 *
	 * @param attribute The attribute to modify.
	 * @param modifier  The modifier to apply.
	 */
	public void addAttributeModifier(Attribute attribute, AttributeModifier modifier)
	{
		this.attributeModifiers.put(attribute, modifier);
	}

	@Override
	public @NotNull Map<Attribute, AttributeModifier> getEffectAttributes()
	{
		return ImmutableMap.copyOf(this.attributeModifiers);
	}

	@Override
	public double getAttributeModifierAmount(@NotNull Attribute attribute, int effectAmplifier)
	{
		Preconditions.checkArgument(effectAmplifier >= 0, "effectAmplifier must be greater than or equal to 0");
		Preconditions.checkArgument(attributeModifiers.containsKey(attribute), attribute + " is not present on " + this.getKey());
		return getAttributeModifierValue(effectAmplifier, attributeModifiers.get(attribute));
	}

	/**
	 * Gets the modifier value with an amplifier.
	 *
	 * @param amplifier The amplifier.
	 * @param modifier  The modifier.
	 * @return The amplified modifier value.
	 */
	private double getAttributeModifierValue(int amplifier, AttributeModifier modifier)
	{
		return modifier.getAmount() * (double) (amplifier + 1);
	}

	@Override
	public @NotNull Category getEffectCategory()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull String translationKey()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
