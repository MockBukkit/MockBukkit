package io.papermc.paper.datacomponent.item;

import io.papermc.paper.registry.set.RegistryKeySet;
import org.bukkit.entity.EntityType;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "UnstableApiUsage", "NonExtendableApiUsage" })
public record MobVisibilityMock(RegistryKeySet<EntityType> targetingEntityTypes, float visibility) implements MobVisibility
{
}
