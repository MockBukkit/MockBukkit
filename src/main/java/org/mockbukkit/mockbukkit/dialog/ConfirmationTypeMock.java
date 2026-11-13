package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.type.ConfirmationType;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link ConfirmationType}: a yes and a no.
 *
 * @param yesButton The button that confirms.
 * @param noButton  The button that refuses.
 */
public record ConfirmationTypeMock(@NotNull ActionButton yesButton, @NotNull ActionButton noButton) implements ConfirmationType
{

	/**
	 * Constructs a new {@link ConfirmationTypeMock}.
	 */
	public ConfirmationTypeMock
	{
		Preconditions.checkNotNull(yesButton, "Yes button cannot be null");
		Preconditions.checkNotNull(noButton, "No button cannot be null");
	}

}
