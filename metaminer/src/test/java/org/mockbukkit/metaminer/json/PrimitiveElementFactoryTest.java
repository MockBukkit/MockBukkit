package org.mockbukkit.metaminer.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimitiveElementFactoryTest
{

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void givenBoolean(boolean expectedValue)
	{
		JsonElement actual = PrimitiveElementFactory.toJson(expectedValue);
		JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
		assertTrue(primitive.isBoolean());
		assertEquals(expectedValue, primitive.getAsBoolean());
	}

	@Test
	void givenShort()
	{
		JsonElement actual = PrimitiveElementFactory.toJson((short) 20);
		JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
		assertTrue(primitive.isNumber());
		assertEquals(20, primitive.getAsShort());
	}

	@Test
	void givenInt()
	{
		JsonElement actual = PrimitiveElementFactory.toJson(10);
		JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
		assertTrue(primitive.isNumber());
		assertEquals(10, primitive.getAsInt());
	}

	@Test
	void givenLong()
	{
		JsonElement actual = PrimitiveElementFactory.toJson(30L);
		JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
		assertTrue(primitive.isNumber());
		assertEquals(30L, primitive.getAsLong());
	}

	@Test
	void givenDouble()
	{
		JsonElement actual = PrimitiveElementFactory.toJson(40.0D);
		JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
		assertTrue(primitive.isNumber());
		assertEquals(40.0D, primitive.getAsDouble());
	}

	@Test
	void givenFloat()
	{
		JsonElement actual = PrimitiveElementFactory.toJson(50.0F);
		JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
		assertTrue(primitive.isNumber());
		assertEquals(50.0F, primitive.getAsFloat());
	}

	@Test
	void givenByte()
	{
		JsonElement actual = PrimitiveElementFactory.toJson((byte) 60);
		JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
		assertTrue(primitive.isNumber());
		assertEquals(60, primitive.getAsByte());
	}

	@Test
	void givenChar()
	{
		JsonElement actual = PrimitiveElementFactory.toJson('A');
		JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
		assertTrue(primitive.isString());
		assertEquals("A", primitive.getAsString());
	}

	@Test
	void givenString()
	{
		JsonElement actual = PrimitiveElementFactory.toJson("This is a string");
		JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
		assertTrue(primitive.isString());
		assertEquals("This is a string", primitive.getAsString());
	}

}
