package org.mockbukkit.mockbukkit.damage;

import com.google.common.base.Preconditions;
import net.kyori.adventure.pointer.Pointers;
import org.bukkit.Location;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class DamageSourceBuilderMock implements DamageSource.Builder
{

	private final DamageType damageType;
	private final Pointers.Builder damageContext = Pointers.builder();
	private Entity causingEntity;
	private Entity directEntity;
	private Location damageLocation;

	public DamageSourceBuilderMock(@NotNull DamageType damageType)
	{
		Preconditions.checkArgument(damageType != null, "DamageType cannot be null");
		this.damageType = damageType;
	}

	@Override
	public DamageSource.@NotNull Builder withCausingEntity(@NotNull Entity entity)
	{
		Preconditions.checkArgument(entity != null, "Entity cannot be null");
		this.causingEntity = entity;
		return this;
	}

	@Override
	public DamageSource.@NotNull Builder withDirectEntity(@NotNull Entity entity)
	{
		Preconditions.checkArgument(entity != null, "Entity cannot be null");
		this.directEntity = entity;
		return this;
	}

	@Override
	public DamageSource.@NotNull Builder withDamageLocation(@NotNull Location location)
	{
		Preconditions.checkArgument(location != null, "Location cannot be null");
		this.damageLocation = location;
		return this;
	}

	@Override
	public DamageSource.@NotNull Builder withDamageContext(@NotNull Consumer<Pointers.Builder> consumer)
	{
		Preconditions.checkArgument(consumer != null, "Consumer cannot be null");
		consumer.accept(this.damageContext);
		return this;
	}

	@Override
	public @NotNull DamageSource build()
	{
		return new DamageSourceMock(this.damageType, this.causingEntity, this.directEntity, this.damageLocation, this.damageContext.build());
	}

}
