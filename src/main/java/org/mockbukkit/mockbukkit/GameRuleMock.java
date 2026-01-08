package org.mockbukkit.mockbukkit;

import com.google.common.base.Preconditions;
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

	public GameRuleMock(Class<T> type, NamespacedKey key, String translationKey)
	{
		this.type = type;
		this.key = key;
		this.translationKey = translationKey;
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
	public String translationKey()
	{
		return this.translationKey;
	}

	@Override
	public NamespacedKey getKey()
	{
		return this.key;
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

		return new GameRuleMock<>(type, key, translationKey);
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
			super(typeOverride, key, translationKey);
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
	}

}
