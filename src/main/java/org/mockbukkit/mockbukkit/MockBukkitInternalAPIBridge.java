package org.mockbukkit.mockbukkit;

import com.destroystokyo.paper.SkinParts;
import com.google.common.base.Preconditions;
import io.papermc.paper.InternalAPIBridge;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import io.papermc.paper.entity.poi.PoiType;
import io.papermc.paper.entity.poi.PoiTypeMock;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.world.damagesource.CombatEntry;
import io.papermc.paper.world.damagesource.FallLocationType;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.Tag;
import org.bukkit.attribute.Attributable;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.damage.DamageEffect;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pose;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import org.mockbukkit.mockbukkit.block.BiomeMock;
import org.mockbukkit.mockbukkit.damage.DamageSourceBuilderMock;
import org.mockbukkit.mockbukkit.exception.ItemSerializationException;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.mockbukkit.mockbukkit.plugin.lifecycle.event.LifecycleEventManagerMock;
import org.mockbukkit.mockbukkit.util.UnsafeValuesMock;
import org.mockbukkit.mockbukkit.world.damagesource.CombatEntryMock;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;

@NullMarked
@ApiStatus.Internal
@ApiStatus.Experimental
@SuppressWarnings("UnstableApiUsage")
public class MockBukkitInternalAPIBridge implements InternalAPIBridge
{
	private static final Component DEFAULT_MANNEQUIN_DESCRIPTION = Component.translatable("entity.minecraft.mannequin.label");

	private @Nullable BiomeMock customBiome = null;

	@Override
	@ApiStatus.Experimental
	public DamageEffect getDamageEffect(String key)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTranslationKey(EntityType type)
	{
		Preconditions.checkArgument(type.getName() != null, "Invalid name of EntityType %s for translation key", type);
		return formatTranslatable("entity", type);
	}

	@Override
	public SpawnCategory getSpawnCategory(EntityType entityType)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ItemStack deserializeItem(byte[] data)
	{
		Preconditions.checkNotNull(data, "null cannot be deserialized");
		Preconditions.checkArgument(data.length > 0, "cannot deserialize nothing");
		final ByteArrayInputStream bai = new ByteArrayInputStream(data);
		try
		{
			final ObjectInputStream ois = new BukkitObjectInputStream(bai);
			if (bai.available() <= 0)
			{
				return ItemStack.empty();
			}
			Map<String, Object> stack = (Map<String, Object>) ois.readObject();
			return UnsafeValuesMock.deserializeStackStatic(stack);
		}
		catch (IOException | ClassNotFoundException e)
		{
			throw new ItemSerializationException(e);
		}
	}

	@Override
	public boolean hasDefaultEntityAttributes(NamespacedKey entityKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Attributable getDefaultEntityAttributes(NamespacedKey entityKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getStatisticCriteriaKey(Statistic statistic)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public LifecycleEventManager<Plugin> createPluginLifecycleEventManager(JavaPlugin plugin, BooleanSupplier registrationCheck)
	{
		return new LifecycleEventManagerMock<>(plugin, registrationCheck);
	}

	@Override
	public ItemStack createEmptyStack()
	{
		return ItemStackMock.empty();
	}

	@Override
	public Component resolveWithContext(Component component, @org.jspecify.annotations.Nullable CommandSender context, @org.jspecify.annotations.Nullable Entity scoreboardSubject, boolean bypassPermissions) throws IOException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ComponentFlattener componentFlattener()
	{
		return ComponentFlattener.basic();
	}

	@Override
	public PoiType.Occupancy createOccupancy(String enumNameEntry)
	{
		return new PoiTypeMock.OccupancyMock(enumNameEntry);
	}

	@Override
	public DamageSource.Builder createDamageSourceBuilder(DamageType damageType)
	{
		return new DamageSourceBuilderMock(damageType);
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
	public CombatEntry createCombatEntry(DamageSource damageSource, float damage, @Nullable FallLocationType fallLocationType, float fallDistance)
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
	public Component defaultMannequinDescription()
	{
		return DEFAULT_MANNEQUIN_DESCRIPTION;
	}

	@Override
	public <MODERN, LEGACY> GameRule<LEGACY> legacyGameRuleBridge(GameRule<MODERN> rule,
																  Function<LEGACY, MODERN> fromLegacyToModern,
																  Function<MODERN, LEGACY> toLegacyFromModern,
																  Class<LEGACY> legacyClass)
	{
		Preconditions.checkNotNull(rule, "The rule can't be null!");
		return new GameRuleMock.LegacyGameRuleWrapperMock<>(legacyClass, rule.getKey(), rule.translationKey(), fromLegacyToModern, toLegacyFromModern);
	}

	@Override
	public Set<Pose> validMannequinPoses()
	{
		return Set.of(Pose.STANDING, Pose.SNEAKING, Pose.SWIMMING, Pose.FALL_FLYING, Pose.SLEEPING);
	}

	private <T extends Keyed & Translatable> String formatTranslatable(String prefix, T translatable, boolean fromItemStack)
	{
		// enforcing Translatable is not necessary, but translating only makes sense when the object is really translatable by design.
		String value = translatable.key().value();
		if (translatable instanceof Material material)
		{
			// replace wall_hanging string check with Tag check (when implemented)
			if (value.contains("wall_hanging") || Tag.WALL_SIGNS.isTagged(material) || value.endsWith("wall_banner") || value.endsWith("wall_torch") || value.endsWith("wall_skull") || value.endsWith("wall_head"))
			{
				value = value.replace("wall_", "");
			}
			final Set<Material> emptyEffects = Set.of(Material.POTION, Material.SPLASH_POTION, Material.TIPPED_ARROW, Material.LINGERING_POTION);
			if (fromItemStack && emptyEffects.contains(material))
			{
				value += ".effect.empty";
			}
		}
		return String.format("%s.%s.%s", prefix, translatable.key().namespace(), value);
	}

	private <T extends Keyed & Translatable> String formatTranslatable(String prefix, T translatable)
	{
		return formatTranslatable(prefix, translatable, false);
	}

}
