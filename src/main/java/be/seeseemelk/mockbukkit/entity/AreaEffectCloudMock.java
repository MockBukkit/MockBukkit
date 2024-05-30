package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AreaEffectCloudMock extends EntityMock implements AreaEffectCloud
{

	private int duration = 600;
	private int waitTime = 20;
	private int reapplicationDelay = 20;
	private int durationOnUse = 0;
	private float radius = 3.0f;
	private float radiusOnUse = 0.0f;
	private float radiusPerTick = 0.0f;
	private Particle particle = Particle.SPELL_MOB;
	private PotionData basePotionData = new PotionData(PotionType.UNCRAFTABLE);
	private final List<PotionEffect> customEffects = new ArrayList<>();
	private int color = 0;
	private ProjectileSource source = null;
	private UUID ownerId = null;

	/**
	 * Constructs a new {@link AreaEffectCloudMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public AreaEffectCloudMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public int getDuration()
	{
		return this.duration;
	}

	@Override
	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	@Override
	public int getWaitTime()
	{
		return this.waitTime;
	}

	@Override
	public void setWaitTime(int waitTime)
	{
		this.waitTime = waitTime;
	}

	@Override
	public int getReapplicationDelay()
	{
		return this.reapplicationDelay;
	}

	@Override
	public void setReapplicationDelay(int delay)
	{
		this.reapplicationDelay = delay;
	}

	@Override
	public int getDurationOnUse()
	{
		return this.durationOnUse;
	}

	@Override
	public void setDurationOnUse(int duration)
	{
		this.durationOnUse = duration;
	}

	@Override
	public float getRadius()
	{
		return this.radius;
	}

	@Override
	public void setRadius(float radius)
	{
		this.radius = radius;
	}

	@Override
	public float getRadiusOnUse()
	{
		return this.radiusOnUse;
	}

	@Override
	public void setRadiusOnUse(float radius)
	{
		this.radiusOnUse = radius;
	}

	@Override
	public float getRadiusPerTick()
	{
		return this.radiusPerTick;
	}

	@Override
	public void setRadiusPerTick(float radius)
	{
		this.radiusPerTick = radius;
	}

	@Override
	public @NotNull Particle getParticle()
	{
		return this.particle;
	}

	@Override
	public void setParticle(@NotNull Particle particle)
	{
		this.setParticle(particle, null);
	}

	@Override
	public <T> void setParticle(@NotNull Particle particle, @Nullable T data)
	{
		Preconditions.checkNotNull(particle, "Particle cannot be null");
		// We ignore the data for now since we don't have a way to process it
		this.particle = particle;
	}

	@Override
	public void setBasePotionData(@NotNull PotionData data)
	{
		Preconditions.checkNotNull(data, "PotionData cannot be null");
		this.basePotionData = data;
	}

	@Override
	public @NotNull PotionData getBasePotionData()
	{
		return this.basePotionData;
	}

	@Override
	public void setBasePotionType(@NotNull PotionType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull PotionType getBasePotionType()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasCustomEffects()
	{
		PotionEffectType effectType = this.basePotionData.getType().getEffectType();
		if (effectType != null)
		{
			return customEffects.stream().anyMatch(effect -> !effect.getType().equals(effectType));
		}
		else
		{
			return !this.customEffects.isEmpty();
		}
	}

	@Override
	public @NotNull List<PotionEffect> getCustomEffects()
	{
		return this.customEffects;
	}

	@Override
	public boolean addCustomEffect(@NotNull PotionEffect effect, boolean overwrite)
	{
		PotionEffect existingEffect = this.customEffects.stream().filter(e -> e.getType().equals(effect.getType()))
				.findFirst().orElse(null);

		if (existingEffect != null)
		{
			if (overwrite)
			{
				this.customEffects.remove(existingEffect);
				this.customEffects.add(effect);
				return true;
			}
			else
			{
				return false;
			}
		}

		this.customEffects.add(effect);
		return true;

	}

	@Override
	public boolean removeCustomEffect(@NotNull PotionEffectType type)
	{
		PotionEffect existingEffect = this.customEffects.stream().filter(e -> e.getType().equals(type)).findFirst()
				.orElse(null);

		if (existingEffect != null)
		{
			this.customEffects.remove(existingEffect);
			return true;
		}
		return false;
	}

	@Override
	public boolean hasCustomEffect(@Nullable PotionEffectType type)
	{
		return this.customEffects.stream().anyMatch(effect -> effect.getType().equals(type));
	}

	@Override
	public void clearCustomEffects()
	{
		this.customEffects.clear();
	}

	@Override
	public @NotNull Color getColor()
	{
		return Color.fromRGB(this.color);
	}

	@Override
	public void setColor(@NotNull Color color)
	{
		Preconditions.checkNotNull(color, "Color cannot be null");
		this.color = color.asRGB();
	}

	@Override
	public @Nullable ProjectileSource getSource()
	{
		return this.source;
	}

	@Override
	public void setSource(@Nullable ProjectileSource source)
	{
		this.source = source;
	}

	@Override
	public @Nullable UUID getOwnerUniqueId()
	{
		return this.ownerId;
	}

	@Override
	public void setOwnerUniqueId(@Nullable UUID ownerUuid)
	{
		this.ownerId = ownerUuid;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.AREA_EFFECT_CLOUD;
	}

}
