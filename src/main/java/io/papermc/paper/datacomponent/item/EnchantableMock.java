package io.papermc.paper.datacomponent.item;


import org.checkerframework.checker.index.qual.Positive;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record EnchantableMock(@Positive int value) implements Enchantable
{

}
