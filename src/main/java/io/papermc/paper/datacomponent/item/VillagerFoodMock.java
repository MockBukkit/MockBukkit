package io.papermc.paper.datacomponent.item;

import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings("NonExtendableApiUsage")
public record VillagerFoodMock(int nutrition) implements VillagerFood
{
}
