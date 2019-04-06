package be.seeseemelk.mockbukkit.attribute;

import static org.junit.Assert.*;

import org.bukkit.attribute.Attribute;
import org.junit.Before;
import org.junit.Test;

public class AttributeInstanceMockTest
{
	private AttributeInstanceMock attribute;

	@Before
	public void setUp()
	{
		attribute = new AttributeInstanceMock(Attribute.GENERIC_FLYING_SPEED, 5.0);
	}

	@Test
	public void getAttribute_Constructor_CorrectAttribute()
	{
		assertEquals(Attribute.GENERIC_FLYING_SPEED, attribute.getAttribute());
	}
	
	@Test
	public void getValues_Constructor_ExactValue()
	{
		assertEquals(5.0, attribute.getBaseValue(), 0);
		assertEquals(5.0, attribute.getValue(), 0);
		assertEquals(5.0, attribute.getDefaultValue(), 0);
	}
	
	@Test
	public void setValue_Constructor_ValueSetExactly()
	{
		attribute.setBaseValue(8.0);
		assertEquals(8.0, attribute.getBaseValue(), 0);
		assertEquals(8.0, attribute.getValue(), 0);
		assertEquals(5.0, attribute.getDefaultValue(), 0);
	}

}
