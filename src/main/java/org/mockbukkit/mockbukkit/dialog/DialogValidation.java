package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * The checks Paper makes on the values of a dialog, shared by its mocks.
 */
final class DialogValidation
{

	/**
	 * The width of a button when none is given.
	 */
	static final int BUTTON_WIDTH = 150;
	/**
	 * The width of a message or an input when none is given.
	 */
	static final int MESSAGE_WIDTH = 200;

	private static final Pattern INPUT_KEY = Pattern.compile("[a-zA-Z0-9_]+");

	private DialogValidation()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	static int range(int value, @NotNull String name, int min, int max)
	{
		Preconditions.checkArgument(value >= min && value <= max, "%s must be between %s and %s, was %s", name, min, max, value);
		return value;
	}

	static float range(float value, @NotNull String name, float min, float max)
	{
		Preconditions.checkArgument(value >= min && value <= max, "%s must be between %s and %s, was %s", name, min, max, value);
		return value;
	}

	static int positive(int value, @NotNull String name)
	{
		Preconditions.checkArgument(value > 0, "%s must be positive, was %s", name, value);
		return value;
	}

	static float positive(float value, @NotNull String name)
	{
		Preconditions.checkArgument(value > 0, "%s must be positive, was %s", name, value);
		return value;
	}

	static @NotNull String inputKey(@NotNull String key)
	{
		Preconditions.checkNotNull(key, "Key cannot be null");
		Preconditions.checkArgument(INPUT_KEY.matcher(key).matches(), "key must be a valid input name");
		return key;
	}

}
