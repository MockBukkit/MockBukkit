package org.mockbukkit.mockbukkit.dialog;

import io.papermc.paper.registry.data.dialog.input.TextDialogInput;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link TextDialogInput.MultilineOptions}.
 *
 * @param maxLines The most lines, if limited.
 * @param height   The height of the field, from 1 to 512, if set.
 */
public record MultilineOptionsMock(@Nullable Integer maxLines, @Nullable Integer height) implements TextDialogInput.MultilineOptions
{

	/**
	 * Constructs a new {@link MultilineOptionsMock}, checked as Paper does.
	 */
	public MultilineOptionsMock
	{
		if (maxLines != null)
		{
			DialogValidation.positive(maxLines, "maxLines");
		}
		if (height != null)
		{
			DialogValidation.range(height, "height", 1, 512);
		}
	}

}
