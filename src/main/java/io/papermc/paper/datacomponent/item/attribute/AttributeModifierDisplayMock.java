package io.papermc.paper.datacomponent.item.attribute;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

@NullMarked
@UtilityClass
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class AttributeModifierDisplayMock implements AttributeModifierDisplay
{

	record HiddenMock() implements Hidden
	{
	}

	record DefaultMock() implements Default
	{
	}

	record OverrideTextMock(Component text) implements OverrideText
	{
	}

}
