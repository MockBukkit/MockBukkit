package io.papermc.paper.datacomponent.item.attribute;

import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SuppressWarnings("UnstableApiUsage")
class AttributeModifierDisplayBridgeMockTest
{
	private final AttributeModifierDisplayBridgeMock bridge = new AttributeModifierDisplayBridgeMock();

	@Test
	void givenHidden()
	{
		AttributeModifierDisplay.Hidden actual = bridge.hidden();
		assertNotNull(actual);
	}

	@Test
	void givenDefault()
	{
		AttributeModifierDisplay.Default actual = bridge.reset();
		assertNotNull(actual);
	}

	@Test
	void givenOverrideText()
	{
		Component component = Component.text("Hello");
		AttributeModifierDisplay.OverrideText actual = bridge.override(component);
		assertNotNull(actual);
		assertEquals(component, actual.text());
	}

}
