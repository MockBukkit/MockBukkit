package be.seeseemelk.mockbukkit.adventure;

public interface Language
{

	String getOrDefault(String key, String fallback);

	boolean has(String key);

	boolean isRightToLeft();

	enum LanguageType
	{
		ENGLISH("en-us");

		private final String languageCode;

		LanguageType(String languageCode)
		{
			this.languageCode = languageCode;
		}

		public String getLanguageCode()
		{
			return languageCode;
		}

		public String getResourceName()
		{
			return getLanguageCode() + ".json";
		}
	}

}
