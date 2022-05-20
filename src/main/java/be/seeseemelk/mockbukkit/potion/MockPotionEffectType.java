package be.seeseemelk.mockbukkit.potion;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

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

	public MockPotionEffectType(NamespacedKey key, int id, String name, boolean instant, Color color)
	{
		super(id, key);

		this.id = id;
		this.name = name;
		this.instant = instant;
		this.color = color;
	}

	@Deprecated
	@Override
	public double getDurationModifier()
	{
		// This is deprecated and always returns 1.0
		return 1.0;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public boolean isInstant()
	{
		return instant;
	}

	@Override
	public Color getColor()
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

	@Override
	public @NotNull Map<Attribute, AttributeModifier> getEffectAttributes()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getAttributeModifierAmount(@NotNull Attribute attribute, int effectAmplifier)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
