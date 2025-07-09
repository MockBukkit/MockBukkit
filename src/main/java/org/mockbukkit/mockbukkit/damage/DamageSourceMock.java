package org.mockbukkit.mockbukkit.damage;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DamageSourceMock implements DamageSource
{

	@Getter
	private final DamageType damageType;
	@Getter
	private final Entity causingEntity;
	@Getter
	private final Entity directEntity;
	private final Location damageLocation;

	public DamageSourceMock(DamageType damageType, Entity causingEntity, Entity directEntity, Location damageLocation)
	{
		this.damageType = damageType;
		this.causingEntity = causingEntity;
		this.directEntity = directEntity;
		this.damageLocation = damageLocation;
	}

	@Override
	public @Nullable Location getDamageLocation()
	{
		return damageLocation != null ? damageLocation.clone() : null;
	}

	@Override
	public @Nullable Location getSourceLocation()
	{
		if (this.damageLocation != null)
		{
			return getDamageLocation();
		}
		return this.directEntity != null ? this.directEntity.getLocation() : null;
	}

	@Override
	public boolean isIndirect()
	{
		return this.causingEntity != this.directEntity;
	}

	@Override
	public float getFoodExhaustion()
	{
		return this.damageType.getExhaustion();
	}

	@Override
	public boolean scalesWithDifficulty()
	{
		return switch (this.damageType.getDamageScaling())
		{
			case ALWAYS -> true;
			case WHEN_CAUSED_BY_LIVING_NON_PLAYER ->
					this.causingEntity instanceof LivingEntity && !(this.causingEntity instanceof Player);
			default -> false;
		};
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}

		return obj instanceof DamageSource other
				&& Objects.equals(this.getDamageType(), other.getDamageType())
				&& Objects.equals(this.getCausingEntity(), other.getCausingEntity())
				&& Objects.equals(this.getDirectEntity(), other.getDirectEntity())
				&& Objects.equals(this.getDamageLocation(), other.getDamageLocation());

	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result = 31 * result + this.damageType.hashCode();
		result = 31 * result + (this.getCausingEntity() != null ? this.getCausingEntity().hashCode() : 0);
		result = 31 * result + (this.getDirectEntity() != null ? this.getDirectEntity().hashCode() : 0);
		result = 31 * result + (this.getDamageLocation() != null ? this.getDamageLocation().hashCode() : 0);
		return result;
	}

}
