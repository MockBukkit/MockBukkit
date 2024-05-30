package be.seeseemelk.mockbukkit.attribute;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AttributeInstanceMockTest
{

	@Test
	void getAttribute_Constructor_CorrectAttribute()
	{
		AttributeInstanceMock attribute = new AttributeInstanceMock(Attribute.GENERIC_FLYING_SPEED, 5.0);

		assertEquals(Attribute.GENERIC_FLYING_SPEED, attribute.getAttribute());
	}

	@Test
	void getValues_Constructor_ExactValue()
	{
		AttributeInstanceMock attribute = new AttributeInstanceMock(Attribute.GENERIC_FLYING_SPEED, 5.0);

		assertEquals(5.0, attribute.getBaseValue(), 0);
		assertEquals(5.0, attribute.getValue(), 0);
		assertEquals(5.0, attribute.getDefaultValue(), 0);
	}

	@Test
	void setValue_Constructor_ValueSetExactly()
	{
		AttributeInstanceMock attribute = new AttributeInstanceMock(Attribute.GENERIC_FLYING_SPEED, 5.0);

		attribute.setBaseValue(8.0);

		assertEquals(8.0, attribute.getBaseValue(), 0);
		assertEquals(8.0, attribute.getValue(), 0);
		assertEquals(5.0, attribute.getDefaultValue(), 0);
	}

	@Test
	void getModifiers_Constructor_EmptyList()
	{
		AttributeInstanceMock attribute = new AttributeInstanceMock(Attribute.GENERIC_FLYING_SPEED, 5.0);

		assertEquals(0, attribute.getModifiers().size());
	}

	@Test
	void addModifier_ModifierAdded()
	{
		AttributeInstanceMock attribute = new AttributeInstanceMock(Attribute.GENERIC_FLYING_SPEED, 5.0);
		AttributeModifier modifier = new AttributeModifier("test_attribute", 1.0,
				AttributeModifier.Operation.ADD_NUMBER);

		attribute.addModifier(modifier);

		assertEquals(1, attribute.getModifiers().size());
		assertEquals(modifier, attribute.getModifiers().stream().findFirst().orElse(null));
	}

	@Test
	void removeModifier_ModifierRemoved()
	{
		AttributeInstanceMock attribute = new AttributeInstanceMock(Attribute.GENERIC_FLYING_SPEED, 5.0);
		AttributeModifier modifier = new AttributeModifier("test_attribute", 1.0,
				AttributeModifier.Operation.ADD_NUMBER);
		attribute.addModifier(modifier);

		attribute.removeModifier(modifier);

		assertEquals(0, attribute.getModifiers().size());
	}

	@Test
	void removeModifier_CorrectModifierRemoved()
	{
		AttributeInstanceMock attribute = new AttributeInstanceMock(Attribute.GENERIC_FLYING_SPEED, 5.0);
		AttributeModifier modifier1 = new AttributeModifier("test_attribute", 1.0,
				AttributeModifier.Operation.ADD_NUMBER);
		AttributeModifier modifier2 = new AttributeModifier("test_attribute_2", 2.0,
				AttributeModifier.Operation.ADD_NUMBER);
		attribute.addModifier(modifier1);
		attribute.addModifier(modifier2);

		attribute.removeModifier(modifier1);

		assertEquals(1, attribute.getModifiers().size());
		assertEquals(modifier2, attribute.getModifiers().stream().findFirst().orElse(null));
	}

}
