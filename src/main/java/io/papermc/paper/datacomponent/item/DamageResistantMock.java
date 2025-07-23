package io.papermc.paper.datacomponent.item;

import io.papermc.paper.registry.tag.TagKey;
import org.bukkit.damage.DamageType;

public record DamageResistantMock(TagKey<DamageType> types) implements DamageResistant
{

}
