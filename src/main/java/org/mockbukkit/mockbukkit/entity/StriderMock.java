package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Strider;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of an {@link Strider}.
 *
 * @see AnimalsMock
 */
public class StriderMock extends AnimalsMock implements Strider
{
	private static final NamespacedKey SUFFOCATING_MODIFIER_ID = NamespacedKey.minecraft("suffocating");
	private static final AttributeModifier SUFFOCATING_MODIFIER = new AttributeModifier(SUFFOCATING_MODIFIER_ID, -0.3400000035762787D, AttributeModifier.Operation.ADD_NUMBER);

	private boolean cold = false;
	private boolean hasSaddle = false;

	private int maxBoostTime = 0;
	private int currentBoostTime = 0;

	/**
	 * Constructs a new {@link Strider} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public StriderMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isShivering()
	{
		return this.cold;
	}

	@Override
	public void setShivering(boolean cold)
	{
		this.cold = cold;

		AttributeInstance speedAttribute = getAttribute(Attribute.MOVEMENT_SPEED);
		if (speedAttribute != null)
		{
			if (cold)
			{
				speedAttribute.addModifier(SUFFOCATING_MODIFIER);
			} else
			{
				speedAttribute.removeModifier(SUFFOCATING_MODIFIER);
			}
		}
	}

	@Override
	public boolean hasSaddle()
	{
		return this.hasSaddle;
	}

	@Override
	public void setSaddle(boolean hasSaddle)
	{
		this.hasSaddle = hasSaddle;
	}

	@Override
	public int getBoostTicks()
	{
		return this.maxBoostTime;
	}

	@Override
	public void setBoostTicks(int ticks)
	{
		Preconditions.checkArgument(ticks >= 0, "ticks must be >= 0");
		this.maxBoostTime = ticks;
	}

	@Override
	public int getCurrentBoostTicks()
	{
		return this.currentBoostTime;
	}

	@Override
	public void setCurrentBoostTicks(int ticks)
	{
		int max = getBoostTicks();
		Preconditions.checkArgument(ticks >= 0 && ticks <= max, "boost ticks must not exceed 0 or %s (inclusive)", max);

		this.currentBoostTime = ticks;
	}

	@Override
	public @NotNull Material getSteerMaterial()
	{
		return Material.WARPED_FUNGUS_ON_A_STICK;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.STRIDER;
	}

}
