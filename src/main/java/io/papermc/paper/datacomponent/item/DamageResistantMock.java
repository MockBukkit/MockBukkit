package io.papermc.paper.datacomponent.item;

import io.papermc.paper.registry.set.RegistryKeySet;
import org.bukkit.damage.DamageType;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record DamageResistantMock(RegistryKeySet<DamageType> types) implements DamageResistant
{

}
