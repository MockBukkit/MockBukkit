package org.mockbukkit.mockbukkit.world.damagesource;

import io.papermc.paper.world.damagesource.CombatEntry;
import io.papermc.paper.world.damagesource.FallLocationType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.bukkit.damage.DamageSource;
import org.jspecify.annotations.Nullable;

@Builder
@RequiredArgsConstructor
public class CombatEntryMock implements CombatEntry
{
	private final DamageSource damageSource;
	private final float damage;
	private final @Nullable FallLocationType fallLocationType;
	private final float fallDistance;

	@Override
	public DamageSource getDamageSource()
	{
		return this.damageSource;
	}

	@Override
	public float getDamage()
	{
		return this.damage;
	}

	@Override
	public @Nullable FallLocationType getFallLocationType()
	{
		return this.fallLocationType;
	}

	@Override
	public float getFallDistance()
	{
		return this.fallDistance;
	}

}
