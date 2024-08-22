package be.seeseemelk.mockbukkit.adventure;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	 * @param instance The new language instance to use
	 */
	public static void setInstance(Language instance)
	{
		Preconditions.checkNotNull(instance);
		Languages.instance = instance;
	}

	/**
	 * Load language
	 * @param language Language type to load
	 * @return A language instance
	 */
	public static Language loadLanguage(Language.LanguageType language)
	{
		String resourceName = "/translations/" + language.getResourceName();
		try (InputStream inputStream = MockBukkit.class.getResourceAsStream(resourceName))
		{
			if (inputStream == null)
			{
				throw new IllegalStateException("Could not find internal resource: " + resourceName);
			}
			JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(inputStream));
			return new JsonBackedLanguage(jsonElement.getAsJsonObject());
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

}
