package org.mockbukkit.mockbukkit;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.GameRule;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@NullMarked
@SuppressWarnings("NonExtendableApiUsage")
public class GameRuleMock<T> extends GameRule<T>
{

	private final Class<T> type;
	private final NamespacedKey key;
	private final String translationKey;
	private final T defaultValue;

	public GameRuleMock(Class<T> type, NamespacedKey key, String translationKey, T defaultValue)
	{
		this.type = type;
		this.key = key;
		this.translationKey = translationKey;
		this.defaultValue = defaultValue;
	}

	@Override
	@Deprecated(since = "1.21.11", forRemoval = true)
	public String getName()
	{
		return getKey().asString();
	}

	@Override
	public Class<T> getType()
	{
		return this.type;
	}

	@Override
	public T getDefaultValue()
	{
		return this.defaultValue;
	}

	@Override
	public String translationKey()
	{
		return this.translationKey;
	}

	@Override
	public NamespacedKey getKey()
	{
		return this.key;
	}

	@Override
	public String toString()
	{
		return key().asString();
	}

	/**
	 * Deserialize {@link GameRule} from the JSON file.
	 *
	 * @param json 	The json object to deserialize.
	 * @param <T>   The {@link GameRule} type.
	 * @return The deserialized rule.
	 */
	@SuppressWarnings("unchecked")
	public static <T> GameRuleMock<T> from(JsonObject json)
	{
		Preconditions.checkNotNull(json, "json can't be null");

		// key
		String rawKey = Preconditions.checkNotNull(
				json.getAsJsonPrimitive("key"),
				"'key' is missing"
		).getAsString();
		NamespacedKey key = Preconditions.checkNotNull(
				NamespacedKey.fromString(rawKey),
				"'key' is not in a valid format"
		);

		// translationKey
		String translationKey = Preconditions.checkNotNull(
				json.getAsJsonPrimitive("translationKey"),
				"'translationKey' is missing"
		).getAsString();

		// type
		String typeName = Preconditions.checkNotNull(
				json.getAsJsonPrimitive("type"),
				"'type' is missing"
		).getAsString();
		Class<?> rawClass;
		try
		{
			rawClass = Class.forName(typeName);
		} catch (ClassNotFoundException e)
		{
			throw new IllegalArgumentException("Unknown type: " + typeName, e);
		}

		Class<T> type = (Class<T>) rawClass;

		// default value
		T defaultValue;
		JsonElement defaultValueJson = json.getAsJsonPrimitive("defaultValue");
		if (Integer.class.equals(type))
		{
			defaultValue = type.cast(defaultValueJson.getAsInt());
		}
		else if (Boolean.class.equals(type))
		{
			defaultValue = type.cast(defaultValueJson.getAsBoolean());
		}
		else
		{
			throw new IllegalArgumentException(String.format("Default value in game rule %s has unknown type %s", key.asString(), type.getName()));
		}

		return new GameRuleMock<>(type, key, translationKey, defaultValue);
	}

	public static class LegacyGameRuleWrapperMock<LEGACY, MODERN> extends GameRuleMock<LEGACY>
	{
		private final Function<LEGACY, MODERN> fromLegacyToModern;
		private final Function<MODERN, LEGACY> toLegacyFromModern;

		public LegacyGameRuleWrapperMock(Class<LEGACY> typeOverride,
										 NamespacedKey key,
										 String translationKey,
										 Function<LEGACY, MODERN> fromLegacyToModern,
										 Function<MODERN, LEGACY> toLegacyFromModern)
		{
			super(typeOverride, key, translationKey, getDefaultValue(typeOverride));
			this.fromLegacyToModern = fromLegacyToModern;
			this.toLegacyFromModern = toLegacyFromModern;
		}

		public Function<LEGACY, MODERN> getFromLegacyToModern()
		{
			return this.fromLegacyToModern;
		}

		public Function<MODERN, LEGACY> getToLegacyFromModern()
		{
			return this.toLegacyFromModern;
		}

		public static <T> T getDefaultValue(Class<T> type)
		{
			if (Integer.class.equals(type))
			{
				return type.cast(0);
			}
			else if (Boolean.class.equals(type))
			{
				return type.cast(false);
			}
			else
			{
				throw new UnsupportedOperationException("Unknown type: " + type.getName());
			}
		}
	}

}
