package org.mockbukkit.mockbukkit;

import com.destroystokyo.paper.SkinParts;
import io.papermc.paper.InternalAPIBridge;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import io.papermc.paper.world.damagesource.CombatEntry;
import io.papermc.paper.world.damagesource.FallLocationType;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.damage.DamageEffect;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.mockbukkit.mockbukkit.block.BiomeMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.world.damagesource.CombatEntryMock;

import java.util.function.Predicate;

@ApiStatus.Internal
@ApiStatus.Experimental
public class MockBukkitInternalAPIBridge implements InternalAPIBridge
{
	private static final Component DEFAULT_MANNEQUIN_DESCRIPTION = Component.translatable("entity.minecraft.mannequin.label");

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
	public CombatEntry createCombatEntry(@NonNull DamageSource damageSource, float damage, @Nullable FallLocationType fallLocationType, float fallDistance)
	{
		return CombatEntryMock.builder()
				.damageSource(damageSource)
				.damage(damage)
				.fallLocationType(fallLocationType)
				.fallDistance(fallDistance)
				.build();
	}

	@Override
	public Predicate<CommandSourceStack> restricted(Predicate<CommandSourceStack> predicate)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ResolvableProfile defaultMannequinProfile()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public SkinParts.Mutable allSkinParts()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NonNull Component defaultMannequinDescription()
	{
		return DEFAULT_MANNEQUIN_DESCRIPTION;
	}

}
