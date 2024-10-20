package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.potion.PotionUtils;
import org.bukkit.Color;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ArrowMock extends AbstractArrowMock implements Arrow
{

	private PotionType potionType;

	/**
	 * Constructs a new {@link ArrowMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ArrowMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setBasePotionData(@Nullable PotionData data)
	{
		setBasePotionType(PotionUtils.fromBukkit(data));
	}

	@Override
	public @Nullable PotionData getBasePotionData()
	{
		return PotionUtils.toBukkit(getBasePotionType());
	}

	@Override
	public void setBasePotionType(@Nullable PotionType type)
	{
		this.potionType = type;
	}

	@Override
	public @Nullable PotionType getBasePotionType()
	{
		return this.potionType;
	}

	@Override
	public @Nullable Color getColor()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setColor(@Nullable Color color)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasCustomEffects()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull List<PotionEffect> getCustomEffects()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addCustomEffect(@NotNull PotionEffect effect, boolean overwrite)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removeCustomEffect(@NotNull PotionEffectType type)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasCustomEffect(@Nullable PotionEffectType type)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void clearCustomEffects()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.ARROW;
	}

}
