package org.mockbukkit.mockbukkit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class contains constants that are injected during the build process.
 * The values are loaded from a properties file that is processed by Blossom.
 */
public class BuildParameters
{

	private static final Properties PROPERTIES = new Properties();

	static
	{
		try (InputStream is = BuildParameters.class.getResourceAsStream("/mockbukkit-build.properties"))
		{
			if (is != null)
			{
				PROPERTIES.load(is);
			}
		}
		catch (IOException e)
		{
			// Fallback to empty properties
		}
	}

	/**
	 * The version of MockBukkit.
	 */
	public static final String MOCK_BUKKIT_VERSION = PROPERTIES.getProperty("mockBukkitVersion", "UNKNOWN");
	/**
	 * The full version of the Paper API MockBukkit was built against.
	 */
	public static final String PAPER_API_FULL_VERSION = PROPERTIES.getProperty("paperApiFullVersion", "UNKNOWN");
	/**
	 * The version of the Paper API.
	 */
	public static final String PAPER_API_VERSION = PROPERTIES.getProperty("paperApiVersion", "UNKNOWN");
	/**
	 * The time at which the build was executed.
	 */
	public static final long BUILD_TIME = Long.parseLong(PROPERTIES.getProperty("buildTime", "0"));
	/**
	 * The git branch the build was started from.
	 */
	public static final String BRANCH = PROPERTIES.getProperty("branch", "UNKNOWN");
	/**
	 * The git commit hash at the time of the build.
	 */
	public static final String COMMIT = PROPERTIES.getProperty("commit", "UNKNOWN");
	/**
	 * The build number (from CI).
	 */
	public static final String BUILD_NUMBER_STRING = PROPERTIES.getProperty("buildNumber", "");

	private BuildParameters()
	{
		throw new UnsupportedOperationException("Utility class");
	}

}
