package org.mockbukkit.mockbukkit.adventure;

public interface Language
{

	/**
	 * @param key      The translation key
	 * @param fallback Fallback translation
	 * @return A translated message
	 */
	String getOrDefault(String key, String fallback);

	/**
	 * @param key The translation key
	 * @return True if translation of key exists
	 */
	boolean has(String key);

	/**
	 * @return Whether the language is rad from right to left
	 */
	boolean isRightToLeft();

	enum LanguageType
	{
		ENGLISH("en-us");

		private final String languageCode;

		LanguageType(String languageCode)
		{
			this.languageCode = languageCode;
		}

		/**
		 * @return The language code for the language
		 */
		public String getLanguageCode()
		{
			return languageCode;
		}

		/**
		 * @return The file name of the resource (excluding directories)
		 */
		public String getResourceName()
		{
			return getLanguageCode() + ".json";
		}
	}

}
