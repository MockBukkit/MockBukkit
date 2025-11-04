package org.mockbukkit.mockbukkit.exception;

import org.mockbukkit.mockbukkit.BuildParameters;

/**
 * This exception provides a detailed diagnostic message to help developers identify
 * and resolve compatibility issues during test execution.
 * </p>
 * <p>
 * The message includes:
 * <ul>
 *   <li>The version of MockBukkit currently in use</li>
 *   <li>The Paper API version MockBukkit was built against</li>
 *   <li>A recommendation to align the plugin's Paper API version with MockBukkit</li>
 * </ul>
 * </p>
 *
 * @see BuildParameters#MOCK_BUKKIT_VERSION
 * @see BuildParameters#PAPER_API_FULL_VERSION
 */
public class IncompatiblePaperVersionException extends RuntimeException
{

	private static final String TEMPLATE_MESSAGE = """
			Version Mismatch!

			################### MockBukkit Version Mismatch ###################

			This may indicate a version mismatch between your project's Paper API
			and the version supported by this MockBukkit build.

			🔍 MockBukkit %s (%s) was built against Paper API version %s
			✅ Please ensure your plugin is compiled against the same version to maintain compatibility.

			###################################################################
			""";

	/**
	 * Constructs a new {@code IncompatiblePaperVersionException} with a formatted message
	 * indicating the detected version mismatch, and includes the original cause of failure.
	 *
	 */
	public IncompatiblePaperVersionException(Throwable cause)
	{
		super(String.format(TEMPLATE_MESSAGE, BuildParameters.MOCK_BUKKIT_VERSION, BuildParameters.BUILD_NUMBER_STRING, BuildParameters.PAPER_API_FULL_VERSION), cause);
	}

}
