package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import net.kyori.adventure.text.event.ClickEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link DialogAction.StaticAction}: a click event the client runs as it is.
 *
 * @param value The click event.
 */
public record StaticActionMock(@NotNull ClickEvent value) implements DialogAction.StaticAction
{

	/**
	 * Constructs a new {@link StaticActionMock}, checked as Paper does.
	 */
	public StaticActionMock
	{
		Preconditions.checkNotNull(value, "Value cannot be null");
		Preconditions.checkArgument(value.action().readable() || value.action() == ClickEvent.Action.SHOW_DIALOG,
				"action must be readable or show_dialog");
	}

}
