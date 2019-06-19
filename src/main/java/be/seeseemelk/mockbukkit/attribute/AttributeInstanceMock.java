package be.seeseemelk.mockbukkit.attribute;

import java.util.Collection;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class AttributeInstanceMock implements AttributeInstance
{
	private final Attribute attribute;
	private final double defaultValue;
	private double value;

	public AttributeInstanceMock(Attribute attribute, double value)
	{
		this.attribute = attribute;
		this.defaultValue = value;
		this.value = value;
	}

	@Override
	public Attribute getAttribute()
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
	public Collection<AttributeModifier> getModifiers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void addModifier(AttributeModifier modifier)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void removeModifier(AttributeModifier modifier)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
