package org.mockbukkit.mockbukkit.attribute;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Mock implementation of {@link AttributeInstance}.
 */
public class AttributeInstanceMock implements AttributeInstance
{

	private final @NotNull Attribute attribute;
	private final double defaultValue;
	private double value;
	private final @NotNull List<AttributeModifier> modifiers;

	/**
	 * Constructs a new {@link AttributeInstanceMock} for the provided {@link Attribute} and with the specified value.
	 *
	 * @param attribute The Attribute this is an instance of.
	 * @param value     The value of the attribute.
	 */
	public AttributeInstanceMock(@NotNull Attribute attribute, double value)
	{
		Preconditions.checkNotNull(attribute, "Attribute cannot be null");
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
	public @Nullable AttributeModifier getModifier(@NotNull Key key)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeModifier(@NotNull Key key)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true,since = "1.21")
	public @Nullable AttributeModifier getModifier(@NotNull UUID uuid)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true,since = "1.21")
	public void removeModifier(@NotNull UUID uuid)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void addModifier(@NotNull AttributeModifier modifier)
	{
		Preconditions.checkNotNull(modifier, "Modifier cannot be null");
		modifiers.add(modifier);
	}

	@Override
	public void addTransientModifier(@NotNull AttributeModifier modifier)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeModifier(@NotNull AttributeModifier modifier)
	{
		Preconditions.checkNotNull(modifier, "Modifier cannot be null");
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
