package org.mockbukkit.metaminer.keyed;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodDataScanner
{

	private static final Pattern WORDS_TO_REPLACE = Pattern.compile("((^get)|(^has)|(^is))([A-Z])");
	private static final Set<String> METHOD_NAME_BLACKLIST = Set.of("hashCode", "toString", "getKeyOrNull", "implHashCode",
			"implToString", "wait", "getDeclaringClass", "notify", "notifyAll", "getClass");

	public static JsonObject findMethodData(Keyed keyed)
	{
		Class<? extends Keyed> kClass = keyed.getClass();
		JsonObject output = new JsonObject();
		for (Method method : kClass.getMethods())
		{
			if (METHOD_NAME_BLACKLIST.contains(method.getName()))
			{
				continue;
			}
			String simplifiedMethodName = simplifyMethodName(method.getName());
			if (method.getGenericParameterTypes().length > 0)
			{
				continue;
			}
			Class<?> returnType = method.getReturnType();
			try
			{
				JsonElement returnValue = getReturnValue(returnType, method, keyed);
				if (returnValue != null)
				{
					output.add(simplifiedMethodName, returnValue);
				}
			}
			catch (Exception ignored)
			{
				// Anything can go wrong here, might as well catch anything
			}
		}
		return output;
	}

	private static @Nullable JsonElement getReturnValue(Class<?> returnType, Method method, Keyed object) throws InvocationTargetException, IllegalAccessException
	{
		if (returnType == Void.TYPE)
		{
			return null;
		}
		if (returnType == boolean.class)
		{
			return new JsonPrimitive((Boolean) method.invoke(object));
		}
		if (returnType == int.class)
		{
			return new JsonPrimitive((Integer) method.invoke(object));
		}
		if (returnType == short.class)
		{
			return new JsonPrimitive((Short) method.invoke(object));
		}
		if (returnType == long.class)
		{
			return new JsonPrimitive((Long) method.invoke(object));
		}
		if (returnType == double.class)
		{
			return new JsonPrimitive((Double) method.invoke(object));
		}
		if (returnType == float.class)
		{
			return new JsonPrimitive((Float) method.invoke(object));
		}
		if (returnType == byte.class)
		{
			return new JsonPrimitive((Byte) method.invoke(object));
		}
		if (returnType == String.class)
		{
			return new JsonPrimitive((String) method.invoke(object));
		}
		if (Keyed.class.isAssignableFrom(returnType))
		{
			return new JsonPrimitive(((Keyed) method.invoke(object)).key().asString());
		}
		if (Component.class.isAssignableFrom(returnType))
		{
			return GsonComponentSerializer.gson().serializeToTree((Component) method.invoke(object));
		}
		if (returnType.isEnum())
		{
			return new JsonPrimitive(((Enum<?>) method.invoke(object)).name());
		}
		return null;
	}

	private static String simplifyMethodName(String name)
	{
		Matcher matcher = WORDS_TO_REPLACE.matcher(name);
		if (matcher.find())
		{
			return matcher.group(5).toLowerCase(Locale.ROOT) + matcher.replaceAll("");
		}
		return name;
	}

}
