package io.papermc.paper.datacomponent.item;

import io.papermc.paper.registry.tag.TagKey;
import org.bukkit.damage.DamageType;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record DamageResistantMock(TagKey<DamageType> types) implements DamageResistant
{

}
