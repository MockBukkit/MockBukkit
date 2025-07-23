package io.papermc.paper.datacomponent.item;

import io.papermc.paper.registry.set.RegistryKeySet;
import org.bukkit.inventory.ItemType;

public record RepairableMock(RegistryKeySet<ItemType> types) implements Repairable
{

}
