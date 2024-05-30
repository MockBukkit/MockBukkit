package be.seeseemelk.mockbukkit.potion;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import org.bukkit.Color;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

/**
 * This {@link MockPotionEffectType} mocks an actual {@link PotionEffectType} by taking an id, a name, whether it is
 * instant and an RGB {@link Color} variable.
 *
 * @author TheBusyBiscuit
 */
public class MockPotionEffectType extends PotionEffectType
{

	private final int id;
	private final String name;
	private final boolean instant;
	private final Color color;
	private final @NotNull Map<Attribute, AttributeModifier> attributeModifiers;
	private final NamespacedKey key;
	private final Category category;

	/**
	 * @param key      The namespaced key representing this effect
	 * @param id       The magic number representing this effect
	 * @param name     The name of this effect
	 * @param instant  Whether the effect is instant or not
	 * @param color    The color of the effect
	 * @param category The category of the effect
	 */
	public MockPotionEffectType(@NotNull NamespacedKey key, int id, @NotNull String name, boolean instant,
			@NotNull Color color, @NotNull Category category)
	{
		super();

		this.key = Preconditions.checkNotNull(key);
		this.id = id;
		this.name = Preconditions.checkNotNull(name);
		this.instant = instant;
		this.color = Preconditions.checkNotNull(color);
		this.attributeModifiers = new EnumMap<>(Attribute.class);
		this.category = Preconditions.checkNotNull(category);
	}

	/**
	 * Constructs a new {@link MockPotionEffectType} with the provided parameters.
	 *
	 * @param key     The key of the effect type.
	 * @param id      The numerical ID of the effect type.
	 * @param name    The name of the effect type.
	 * @param instant Whether the effect type is instantly applied.
	 * @param color   The color of the effect type.
	 */
	public MockPotionEffectType(@NotNull NamespacedKey key, int id, String name, boolean instant, Color color)
	{
		this(key, id, name, instant, color, Category.NEUTRAL);
	}

	/**
	 * @param data Json data
	 * @deprecated Use {@link #MockPotionEffectType(NamespacedKey, int, String, boolean, Color, Category)} instead.
	 */
	@Deprecated(forRemoval = true)
	public MockPotionEffectType(JsonObject data)
	{
		this(NamespacedKey.fromString(data.get("key").getAsString()), data.get("id").getAsInt(),
				data.get("name").getAsString(), data.get("instant").getAsBoolean(),
				Color.fromRGB(data.get("rgb").getAsInt()), Category.valueOf(data.get("category").getAsString()));
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
	@Deprecated(since = "1.20")
	public int getId()
	{
		return this.id;
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

	@Override
	public @NotNull PotionEffect createEffect(int duration, int amplifier)
	{
		return new PotionEffect(this, isInstant() ? 1 : (int) (duration * getDurationModifier()), amplifier);
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
			// It would make sense to compare the NamespacedKey here but Spigot stil
			// compares ids
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
		Preconditions.checkArgument(attributeModifiers.containsKey(attribute),
				attribute + " is not present on " + this.getKey());
		return getAttributeModifierValue(effectAmplifier, attributeModifiers.get(attribute));
	}

	/**
	 * Gets the modifier value with an amplifier.
	 *
	 * @param amplifier The amplifier.
	 * @param modifier  The modifier.
	 * @return The amplified modifier value.
	 */
	private double getAttributeModifierValue(int amplifier, @NotNull AttributeModifier modifier)
	{
		return modifier.getAmount() * (double) (amplifier + 1);
	}

	@Override
	public @NotNull Category getEffectCategory()
	{
		return this.category;
	}

	@Override
	public @NotNull String translationKey()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@ApiStatus.Internal
	public static Keyed from(JsonObject data)
	{
		NamespacedKey key = NamespacedKey.fromString(data.get("key").getAsString());
		int id = data.get("id").getAsInt();
		String name = data.get("name").getAsString();
		boolean instant = data.get("instant").getAsBoolean();
		Color color = Color.fromRGB(data.get("rgb").getAsInt());
		Category category = Category.valueOf(data.get("category").getAsString());
		return new MockPotionEffectType(key, id, name, instant, color, category);
	}

	@Override
	public @NotNull String getTranslationKey()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
