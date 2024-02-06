package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
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

	/**
	 * Constructs a new {@link ArrowMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected ArrowMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setBasePotionData(@NotNull PotionData data)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull PotionData getBasePotionData()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBasePotionType(@NotNull PotionType type)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull PotionType getBasePotionType()
	{
		throw new UnimplementedOperationException();
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
