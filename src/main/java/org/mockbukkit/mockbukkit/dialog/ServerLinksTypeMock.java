package org.mockbukkit.mockbukkit.dialog;

import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.type.ServerLinksType;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link ServerLinksType}: a button for each of the server's links.
 *
 * @param exitAction  The button at the bottom that leaves, if any.
 * @param columns     The number of columns.
 * @param buttonWidth The width of its buttons, from 1 to 1024.
 */
public record ServerLinksTypeMock(@Nullable ActionButton exitAction, int columns, int buttonWidth) implements ServerLinksType
{

	/**
	 * Constructs a new {@link ServerLinksTypeMock}, checked as Paper does.
	 */
	public ServerLinksTypeMock
	{
		DialogValidation.positive(columns, "columns");
		DialogValidation.range(buttonWidth, "buttonWidth", 1, 1024);
	}

}
