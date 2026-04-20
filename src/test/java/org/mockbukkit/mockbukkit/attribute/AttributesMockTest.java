package org.mockbukkit.mockbukkit.attribute;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class AttributesMockTest
{

	@Test
	void testConstructorIsPrivate() throws NoSuchMethodException
	{
		Constructor<AttributesMock> constructor = AttributesMock.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
		assertInstanceOf(UnsupportedOperationException.class, exception.getCause());
		assertTrue(exception.getCause().getMessage().contains("Utility class"));
	}

	@Test
	void testCreateFromJson()
	{
		String json = """
				{
					"key": "minecraft:armor",
					"name": "ARMOR",
					"ordinal": 8,
					"sentiment": "POSITIVE",
					"translationKey": "attribute.name.armor"
				}
				""";
		JsonObject parsedJson = JsonParser.parseString(json).getAsJsonObject();
		AttributeMock attribute = AttributeMock.from(parsedJson);
		assertInstanceOf(AttributeMock.class, attribute);
		assertEquals(NamespacedKey.fromString("minecraft:armor"), attribute.getKey());
		assertEquals("ARMOR", attribute.name());
		assertEquals(8, attribute.ordinal());
		assertEquals(Attribute.Sentiment.POSITIVE, attribute.getSentiment());
		assertEquals("attribute.name.armor", attribute.translationKey());
	}
}
