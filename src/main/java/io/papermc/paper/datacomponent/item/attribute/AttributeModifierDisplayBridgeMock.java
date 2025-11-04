package io.papermc.paper.datacomponent.item.attribute;

import net.kyori.adventure.text.ComponentLike;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "UnstableApiUsage" })
public class AttributeModifierDisplayBridgeMock implements AttributeModifierDisplayBridge
{

	@Override
	public AttributeModifierDisplay.Default reset()
	{
		return new AttributeModifierDisplayMock.DefaultMock();
	}

	@Override
	public AttributeModifierDisplay.Hidden hidden()
	{
		return new AttributeModifierDisplayMock.HiddenMock();
	}

	@Override
	public AttributeModifierDisplay.OverrideText override(ComponentLike text)
	{
		return new AttributeModifierDisplayMock.OverrideTextMock(text.asComponent());
	}

}
