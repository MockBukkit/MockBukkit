package org.mockbukkit.mockbukkit.util;

import com.google.common.base.Preconditions;
import lombok.experimental.UtilityClass;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@UtilityClass
public class NbtParser
{

	@Nullable
	public static Boolean parseBoolean(@Nullable Object object)
	{
		if (object == null)
		{
			return null;
		}

		if (object instanceof Boolean bool)
		{
			return bool;
		}

		if (object instanceof Number number)
		{
			return number.intValue() == 1;
		}

		if (object instanceof String string)
		{
			return Boolean.parseBoolean(string);
		}

		throw new NumberFormatException(String.format("Cannot parse double: %s", object.getClass().getName()));
	}

	public static boolean parseBoolean(@Nullable Object object, boolean defaultValue)
	{
		@Nullable Boolean integer = parseBoolean(object);
		return integer == null ? defaultValue : integer;
	}

	@Nullable
	public static Double parseDouble(@Nullable Object object)
	{
		if (object == null)
		{
			return null;
		}

		if (object instanceof Number number)
		{
			return number.doubleValue();
		}

		if (object instanceof String string)
		{
			return Double.parseDouble(string);
		}

		throw new NumberFormatException(String.format("Cannot parse double: %s", object.getClass().getName()));
	}

	@Nullable
	public static Integer parseInteger(@Nullable Object object)
	{
		if (object == null)
		{
			return null;
		}

		if (object instanceof Number number)
		{
			return number.intValue();
		}

		if (object instanceof String string)
		{
			return Integer.parseInt(string);
		}

		throw new NumberFormatException(String.format("Cannot parse integer: %s", object.getClass().getName()));
	}

	public static int parseInteger(@Nullable Object object, int defaultValue)
	{
		@Nullable Integer integer = parseInteger(object);
		return integer == null ? defaultValue : integer;
	}

	@Nullable
	public static Byte parseByte(@Nullable Object object)
	{
		if (object == null)
		{
			return null;
		}

		if (object instanceof Number number)
		{
			return number.byteValue();
		}

		if (object instanceof String string)
		{
			return Byte.parseByte(string);
		}

		throw new NumberFormatException(String.format("Cannot parse byte: %s", object.getClass().getName()));
	}

	@Nullable
	public static String parseString(@Nullable Object object)
	{
		if (object == null)
		{
			return null;
		}

		if (object instanceof String string)
		{
			return string;
		}

		throw new NumberFormatException(String.format("Cannot parse string: %s", object.getClass().getName()));
	}

	@Nullable
	public static <K, V> Map<K, V> parseMap(@Nullable Object object,
											@NotNull Function<Object, K> keyFunction,
											@NotNull Function<Object, V> valueFunction)
	{
		Preconditions.checkNotNull(keyFunction, "The key function cannot be null");
		Preconditions.checkNotNull(keyFunction, "The value function cannot be null");

		if (object == null)
		{
			return null;
		}

		if (object instanceof Map<?, ?> map)
		{
			Map<K, V> returnMap = new LinkedHashMap<>();
			for (val entry : map.entrySet())
			{
				K key = keyFunction.apply(entry.getKey());
				V value = valueFunction.apply(entry.getValue());
				returnMap.put(key, value);
			}
			return returnMap;
		}

		throw new NumberFormatException(String.format("Cannot parse map: %s", object.getClass().getName()));
	}

	@Nullable
	public static <V> Map<String, V> parseMap(@Nullable Object object, @NotNull Function<Object, V> function)
	{
		return parseMap(object, String.class::cast, function);
	}

	@Nullable
	public static <V> List<V> parseList(@Nullable Object object, @NotNull Function<Object, V> function)
	{
		if (object == null)
		{
			return null;
		}

		if (object instanceof Collection<?> list)
		{
			List<V> returnMap = new LinkedList<>();
			for (val entry : list)
			{
				V value = function.apply(entry);
				returnMap.add(value);
			}
			return returnMap;
		}

		throw new NumberFormatException(String.format("Cannot parse list: %s", object.getClass().getName()));
	}

	@Nullable
	public static <V> Set<V> parseSet(@Nullable Object object, @NotNull Function<Object, V> function)
	{
		if (object == null)
		{
			return null;
		}

		if (object instanceof Collection<?> list)
		{
			Set<V> returnMap = new HashSet<>();
			for (val entry : list)
			{
				V value = function.apply(entry);
				returnMap.add(value);
			}
			return returnMap;
		}

		throw new NumberFormatException(String.format("Cannot parse set: %s", object.getClass().getName()));
	}

	@Nullable
	public static <E> E parseWithCast(@Nullable Object object, @NotNull Function<String, E> castFunction)
	{
		if (object == null)
		{
			return null;
		}

		if (object instanceof String name)
		{
			return castFunction.apply(name);
		}

		throw new NumberFormatException(String.format("Cannot parse value as string: %s", object.getClass().getName()));
	}

	@Nullable
	public static <E extends Enum<?>> E parseEnum(@Nullable Object object, @NotNull Class<E> clazz)
	{
		if (object == null)
		{
			return null;
		}

		if (clazz.isInstance(object))
		{
			return clazz.cast(object);
		}

		return parseWithCast(object, name ->
		{
			for (E enumItem : clazz.getEnumConstants())
			{
				if (enumItem.name().equalsIgnoreCase(name))
				{
					return enumItem;
				}

			}

			throw new IllegalArgumentException(String.format("Cannot parse enum '%s' in class '%s'", object, clazz.getName()));
		});
	}

	// Special cases

	@Nullable
	public static Component parseComponent(@Nullable Object object)
	{
		String s = parseString(object);
		if (s == null)
		{
			return null;
		}

		return GsonComponentSerializer.gson().deserialize(s);
	}

	@Nullable
	public static NamespacedKey parseNamespacedKey(@Nullable Object object)
	{
		String s = parseString(object);
		if (s == null)
		{
			return null;
		}

		return NamespacedKey.fromString(s);
	}

}
