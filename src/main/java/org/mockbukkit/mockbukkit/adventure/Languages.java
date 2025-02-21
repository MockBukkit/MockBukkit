package org.mockbukkit.mockbukkit.adventure;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import org.mockbukkit.mockbukkit.util.ResourceLoader;

public class Languages
{

	private static Language instance = loadLanguage(Language.LanguageType.ENGLISH);

	/**
	 * @return The language translation instance used when processing translatable components
	 */
	public static Language getInstance()
	{
		return instance;
	}

	/**
	 * Modify the language translation instance used
	 *
	 * @param instance The new language instance to use
	 */
	public static void setInstance(Language instance)
	{
		Preconditions.checkNotNull(instance);
		Languages.instance = instance;
	}

	/**
	 * Load language
	 *
	 * @param language Language type to load
	 * @return A language instance
	 */
	public static Language loadLanguage(Language.LanguageType language)
	{
		String resourceName = "/translations/" + language.getResourceName();
		JsonElement jsonElement = ResourceLoader.loadResource(resourceName);
		return new JsonBackedLanguage(jsonElement.getAsJsonObject());
	}

	private Languages()
	{
		throw new UnsupportedOperationException("Utility class");
	}

}
