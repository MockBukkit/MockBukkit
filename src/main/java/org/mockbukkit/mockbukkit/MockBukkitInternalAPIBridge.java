package org.mockbukkit.mockbukkit;

import io.papermc.paper.InternalAPIBridge;
import io.papermc.paper.world.damagesource.CombatEntry;
import io.papermc.paper.world.damagesource.FallLocationType;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.damage.DamageEffect;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.block.BiomeMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

@ApiStatus.Internal
@ApiStatus.Experimental
public class MockBukkitInternalAPIBridge implements InternalAPIBridge
{

	private @Nullable BiomeMock customBiome = null;

	@Override
	@ApiStatus.Experimental
	public DamageEffect getDamageEffect(@NotNull String key)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.21.5")
	@ApiStatus.ScheduledForRemoval(inVersion = "1.22")
	public Biome constructLegacyCustomBiome()
	{
		if (customBiome == null)
		{
			customBiome = new BiomeMock(NamespacedKey.fromString("mockbukkit:custom"));
		}

		return customBiome;
	}

	@Override
	public CombatEntry createCombatEntry(LivingEntity entity, DamageSource damageSource, float damage)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public CombatEntry createCombatEntry(DamageSource damageSource, float damage, @org.jspecify.annotations.Nullable FallLocationType fallLocationType, float fallDistance)
	{
		throw new UnimplementedOperationException();
	}

}
