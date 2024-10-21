package org.mockbukkit.mockbukkit.adventure;

import com.google.gson.JsonObject;

public class JsonBackedLanguage implements Language
{

	private final JsonObject translations;
	private final boolean rightToLeft;

	/**
	 * Accepts the following data of json objects:
	 *
	 * <pre>
	 * {@code
	 * {
	 *     "rightToLeft": true
	 *     "translations": {
	 *         "foo": "translated foo"
	 *         ...
	 *     }
	 * }
	 * }
	 * </pre>
	 *
	 * @param jsonObject A json object containing all the necessary data and in the right format
	 */
	public JsonBackedLanguage(JsonObject jsonObject)
	{
		this.rightToLeft = jsonObject.get("rightToLeft").getAsBoolean();
		this.translations = jsonObject.get("translations").getAsJsonObject();
	}

	@Override
	public String getOrDefault(String key, String fallback)
	{
		return translations.has(key) ? translations.get(key).getAsString() : fallback;
	}

	@Override
	public boolean has(String key)
	{
		return translations.has(key);
	}

	@Override
	public boolean isRightToLeft()
	{
		return rightToLeft;
	}

}
