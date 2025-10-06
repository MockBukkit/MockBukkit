package org.mockbukkit.mockbukkit.block.data;

import com.google.gson.JsonElement;
import org.bukkit.Axis;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApiStatus.Internal
public class BlockDataLimitation<T, U>
{

	private final T value;
	private final BiPredicate<T, U> allowedValuesFunction;

	BlockDataLimitation(T value, BiPredicate<T, U> allowedValuesFunction)
	{
		this.value = value;
		this.allowedValuesFunction = allowedValuesFunction;
	}

	public boolean isAllowed(U t)
	{
		return allowedValuesFunction.test(value, t);
	}

	public T getValue()
	{
		return value;
	}

	static <T, U> BlockDataLimitation<T, U> fromValue(T value, BiPredicate<T, U> allowed)
	{
		return new BlockDataLimitation<>(value, allowed);
	}

	static BlockDataLimitation<Integer, Integer> fromValueGreaterThan(Integer minValue)
	{
		return new BlockDataLimitation<>(minValue, (min, actual) -> min >= actual);
	}

	static BlockDataLimitation<Integer, Integer> fromValueLesserThan(Integer maxValue)
	{
		return new BlockDataLimitation<>(maxValue, (max, actual) -> max <= actual);
	}

	static <T> BlockDataLimitation<Set<T>, T> fromSet(Set<T> tSet)
	{
		return new BlockDataLimitation<>(tSet, Set::contains);
	}

	public static class Type<T, U>
	{

		public static final Type<Integer, Integer> MAX_AGE = new Type<>("maxAge", jsonElement -> BlockDataLimitation.fromValueLesserThan(jsonElement.getAsInt()));
		public static final Type<Integer, Integer> MAX_POWER = new Type<>("maxPower", jsonElement -> BlockDataLimitation.fromValueLesserThan(jsonElement.getAsInt()));
		public static final Type<Integer, Integer> MAX_STAGE = new Type<>("maxStage", jsonElement -> BlockDataLimitation.fromValueLesserThan(jsonElement.getAsInt()));
		public static final Type<Integer, Integer> MAX_LEVEL = new Type<>("maxLevel", jsonElement -> BlockDataLimitation.fromValueLesserThan(jsonElement.getAsInt()));
		public static final Type<Integer, Integer> MAX_DUSTED = new Type<>("maxDusted", jsonElement -> BlockDataLimitation.fromValueLesserThan(jsonElement.getAsInt()));
		public static final Type<Integer, Integer> MIN_LEVEL = new Type<>("minLevel", jsonElement -> BlockDataLimitation.fromValueGreaterThan(jsonElement.getAsInt()));
		public static final Type<Integer, Integer> MIN_EGGS = new Type<>("minEggs", jsonElement -> BlockDataLimitation.fromValueGreaterThan(jsonElement.getAsInt()));
		public static final Type<Integer, Integer> MAX_EGGS = new Type<>("maxEggs", jsonElement -> BlockDataLimitation.fromValueLesserThan(jsonElement.getAsInt()));
		public static final Type<Integer, Integer> MAX_HATCH = new Type<>("maxHatch", jsonElement -> BlockDataLimitation.fromValueLesserThan(jsonElement.getAsInt()));
		public static final Type<Integer, Integer> MAX_MOISTURE = new Type<>("maxMoisture", jsonElement -> BlockDataLimitation.fromValueLesserThan(jsonElement.getAsInt()));
		public static final Type<Integer, Integer> MAX_OCCUPIED_SLOTS = new Type<>("maxOccupiedSlots", jsonElement -> BlockDataLimitation.fromValueLesserThan(jsonElement.getAsInt()));
		public static final Type<Set<BlockFace>, BlockFace> FACES = new Type<>("faces", jsonElement -> BlockDataLimitation.fromSet(
				jsonElement.getAsJsonArray()
						.asList()
						.stream()
						.map(JsonElement::getAsString)
						.map(string -> string.toUpperCase(Locale.ROOT))
						.map(BlockFace::valueOf)
						.collect(Collectors.toUnmodifiableSet())
		));
		public static final Type<Set<Axis>, Axis> AXES = new Type<>("axes", jsonElement -> BlockDataLimitation.fromSet(
				jsonElement.getAsJsonArray()
						.asList()
						.stream()
						.map(JsonElement::getAsString)
						.map(string -> string.toUpperCase(Locale.ROOT))
						.map(Axis::valueOf)
						.collect(Collectors.toUnmodifiableSet())
		));

		private final String key;
		private final Function<JsonElement, BlockDataLimitation<T, U>> constructor;
		private static final Map<String, Type<?, ?>> KEYS = compileKeys();

		private static Map<String, Type<?, ?>> compileKeys()
		{
			return Arrays.stream(Type.class.getDeclaredFields())
					.filter(field -> field.getType().isAssignableFrom(Type.class))
					.filter(field -> Modifier.isStatic(field.getModifiers()))
					.map(field ->
					{
						try
						{
							return field.get(null);
						}
						catch (IllegalAccessException e)
						{
							throw new RuntimeException(e);
						}
					})
					.map(object -> (Type<?, ?>) object)
					.collect(Collectors.toMap(Type::getKey, type -> type));
		}

		Type(String key, Function<JsonElement, BlockDataLimitation<T, U>> constructor)
		{
			this.key = key;
			this.constructor = constructor;
		}

		public String getKey()
		{
			return key;
		}

		public BlockDataLimitation<T, U> newLimitation(JsonElement jsonElement)
		{
			return constructor.apply(jsonElement);
		}

		public static Type<?, ?> fromKey(String key)
		{
			return KEYS.get(key);
		}

	}

}
