package be.seeseemelk.mockbukkit.attribute;

import com.google.common.base.Preconditions;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AttributeInstanceMock implements AttributeInstance
{

	private final Attribute attribute;
	private final double defaultValue;
	private double value;
	private final List<AttributeModifier> modifiers;

	public AttributeInstanceMock(Attribute attribute, double value)
	{
		this.attribute = attribute;
		this.defaultValue = value;
		this.value = value;
		modifiers = new ArrayList<>();
	}

	@Override
	public @NotNull Attribute getAttribute()
	{
		return attribute;
	}

	@Override
	public double getBaseValue()
	{
		return value;
	}

	@Override
	public void setBaseValue(double value)
	{
		this.value = value;
	}

	@Override
	public @NotNull Collection<AttributeModifier> getModifiers()
	{
		return modifiers;
	}

	@Override
	public void addModifier(@NotNull AttributeModifier modifier)
	{
		Preconditions.checkArgument(modifier != null, "modifier");
		modifiers.add(modifier);
	}

	@Override
	public void removeModifier(@NotNull AttributeModifier modifier)
	{
		Preconditions.checkArgument(modifier != null, "modifier");
		modifiers.remove(modifier);
	}

	@Override
	public double getValue()
	{
		return getBaseValue();
	}

	@Override
	public double getDefaultValue()
	{
		return defaultValue;
	}

}
