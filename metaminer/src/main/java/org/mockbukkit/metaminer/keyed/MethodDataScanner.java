package org.mockbukkit.metaminer.keyed;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang.ClassUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.metaminer.json.ElementFactory;

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
			"implToString", "wait", "getDeclaringClass", "notify", "notifyAll", "getClass", "values");

	public static JsonObject findMethodData(Object object)
	{
		Class<?> kClass = object.getClass();
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
				JsonElement returnValue = getReturnValue(returnType, method, object);
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

	static @Nullable JsonElement getReturnValue(@NotNull Class<?> returnType, @NotNull Method method, @Nullable Object object) throws InvocationTargetException, IllegalAccessException
	{
		Preconditions.checkArgument(returnType != null, "The return type can't be null!");
		Preconditions.checkArgument(method != null, "The method can't be null!");

		// Convert the primitive type into the respective wrapper
		returnType = ClassUtils.primitiveToWrapper(returnType);

		if (Void.TYPE == returnType)
		{
			return null;
		}

		Object functionReturn = method.invoke(object);
		return ElementFactory.toJson(functionReturn);
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
