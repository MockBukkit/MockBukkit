package org.mockbukkit.metaminer.keyed;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MethodDataScannerTest
{

	@Nested
	class GetReturnValue
	{

		@Nested
		class Primitives
		{


			@Test
			void givenVoidType() throws InvocationTargetException, IllegalAccessException
			{
				JsonElement actual = MethodDataScanner.getReturnValue(Void.TYPE, getMethod("voidMethod"), null);
				assertNull(actual);
			}

			@ParameterizedTest
			@ValueSource(classes = {
				boolean.class,
				Boolean.class,
			})
			void givenBoolean(Class<?> clazz) throws InvocationTargetException, IllegalAccessException
			{
				JsonElement actual = MethodDataScanner.getReturnValue(clazz, getMethod("booleanMethod"), null);
				JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
				assertTrue(primitive.isBoolean());
				assertFalse(primitive.getAsBoolean());
			}

			@ParameterizedTest
			@ValueSource(classes = {
				int.class,
				Integer.class,
			})
			void givenInt(Class<?> clazz) throws InvocationTargetException, IllegalAccessException
			{
				JsonElement actual = MethodDataScanner.getReturnValue(clazz, getMethod("intMethod"), null);
				JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
				assertTrue(primitive.isNumber());
				assertEquals(10, primitive.getAsInt());
			}

			@ParameterizedTest
			@ValueSource(classes = {
				short.class,
				Short.class,
			})
			void givenShort(Class<?> clazz) throws InvocationTargetException, IllegalAccessException
			{
				JsonElement actual = MethodDataScanner.getReturnValue(clazz, getMethod("shortMethod"), null);
				JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
				assertTrue(primitive.isNumber());
				assertEquals(20, primitive.getAsShort());
			}

			@ParameterizedTest
			@ValueSource(classes = {
				long.class,
				Long.class,
			})
			void givenLong(Class<?> clazz) throws InvocationTargetException, IllegalAccessException
			{
				JsonElement actual = MethodDataScanner.getReturnValue(clazz, getMethod("longMethod"), null);
				JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
				assertTrue(primitive.isNumber());
				assertEquals(30, primitive.getAsLong());
			}

			@ParameterizedTest
			@ValueSource(classes = {
				double.class,
				Double.class,
			})
			void givenDouble(Class<?> clazz) throws InvocationTargetException, IllegalAccessException
			{
				JsonElement actual = MethodDataScanner.getReturnValue(clazz, getMethod("doubleMethod"), null);
				JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
				assertTrue(primitive.isNumber());
				assertEquals(40.0D, primitive.getAsDouble());
			}

			@ParameterizedTest
			@ValueSource(classes = {
				float.class,
				Float.class,
			})
			void givenFloat(Class<?> clazz) throws InvocationTargetException, IllegalAccessException
			{
				JsonElement actual = MethodDataScanner.getReturnValue(clazz, getMethod("floatMethod"), null);
				JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
				assertTrue(primitive.isNumber());
				assertEquals(50.0F, primitive.getAsFloat());
			}

			@ParameterizedTest
			@ValueSource(classes = {
				byte.class,
				Byte.class,
			})
			void givenByte(Class<?> clazz) throws InvocationTargetException, IllegalAccessException
			{
				JsonElement actual = MethodDataScanner.getReturnValue(clazz, getMethod("byteMethod"), null);
				JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
				assertTrue(primitive.isNumber());
				assertEquals(60, primitive.getAsByte());
			}

			@Test
			void givenChar() throws InvocationTargetException, IllegalAccessException
			{
				JsonElement actual = MethodDataScanner.getReturnValue(Character.class, getMethod("charMethod"), null);
				JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
				assertTrue(primitive.isString());
				assertEquals("A", primitive.getAsString());
			}

			@Test
			void givenString() throws InvocationTargetException, IllegalAccessException
			{
				JsonElement actual = MethodDataScanner.getReturnValue(String.class, getMethod("stringMethod"), null);
				JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
				assertTrue(primitive.isString());
				assertEquals("This is a string", primitive.getAsString());
			}

		}

		@Test
		void givenKeyed() throws InvocationTargetException, IllegalAccessException
		{
			JsonElement actual = MethodDataScanner.getReturnValue(NamespacedKey.class, getMethod("namespacedKeyMethod"), null);
			JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
			assertTrue(primitive.isString());
			assertEquals("minecraft:test", primitive.getAsString());
		}

		@Test
		void givenComponent() throws InvocationTargetException, IllegalAccessException
		{
			JsonElement actual = MethodDataScanner.getReturnValue(Component.class, getMethod("componentMethod"), null);
			JsonObject object = assertInstanceOf(JsonObject.class, actual);
			assertEquals("{\"color\":\"red\",\"text\":\"Hello world!\"}", object.toString());
		}

		@Test
		void givenEnum() throws InvocationTargetException, IllegalAccessException
		{
			JsonElement actual = MethodDataScanner.getReturnValue(DayOfWeek.class, getMethod("enumMethod"), null);
			JsonPrimitive primitive = assertInstanceOf(JsonPrimitive.class, actual);
			assertEquals("FRIDAY", primitive.getAsString());
		}

	}

	private static @NotNull Method getMethod(@NotNull String methodName)
	{
		Preconditions.checkArgument(methodName != null, "The method name can't be null");
		try
		{
			return UnderTestClass.class.getDeclaredMethod(methodName);
		}
		catch (NoSuchMethodException e)
		{
			throw new IllegalArgumentException(String.format("Method with name %s does not exist.", methodName));
		}
	}

	static class UnderTestClass
	{

		static void voidMethod()
		{
			// Nothing to do here
		}

		static boolean booleanMethod()
		{
			return false;
		}

		static int intMethod()
		{
			return 10;
		}

		static short shortMethod()
		{
			return 20;
		}

		static long longMethod()
		{
			return 30L;
		}

		static double doubleMethod()
		{
			return 40.0D;
		}

		static float floatMethod()
		{
			return 50.0F;
		}

		static byte byteMethod()
		{
			return 60;
		}

		static char charMethod()
		{
			return 'A';
		}

		static String stringMethod()
		{
			return "This is a string";
		}

		static NamespacedKey namespacedKeyMethod()
		{
			return NamespacedKey.minecraft("test");
		}

		static Component componentMethod()
		{
			return Component.text("Hello world!").color(NamedTextColor.RED);
		}

		static DayOfWeek enumMethod()
		{
			return DayOfWeek.FRIDAY;
		}

	}

}
