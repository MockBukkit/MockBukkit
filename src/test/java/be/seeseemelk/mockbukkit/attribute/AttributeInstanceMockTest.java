package be.seeseemelk.mockbukkit.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.bukkit.attribute.Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttributeInstanceMockTest
{
	private AttributeInstanceMock attribute;

	@BeforeEach
	public void setUp()
	{
		attribute = new AttributeInstanceMock(Attribute.GENERIC_FLYING_SPEED, 5.0);
	}

	@Test
	void getAttribute_Constructor_CorrectAttribute()
	{
		assertEquals(Attribute.GENERIC_FLYING_SPEED, attribute.getAttribute());
	}

	@Test
	void getValues_Constructor_ExactValue()
	{
		assertEquals(5.0, attribute.getBaseValue(), 0);
		assertEquals(5.0, attribute.getValue(), 0);
		assertEquals(5.0, attribute.getDefaultValue(), 0);
	}

	@Test
	void setValue_Constructor_ValueSetExactly()
	{
		attribute.setBaseValue(8.0);
		assertEquals(8.0, attribute.getBaseValue(), 0);
		assertEquals(8.0, attribute.getValue(), 0);
		assertEquals(5.0, attribute.getDefaultValue(), 0);
	}

}
