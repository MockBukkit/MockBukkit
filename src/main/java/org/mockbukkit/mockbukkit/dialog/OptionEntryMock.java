package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.input.SingleOptionDialogInput;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link SingleOptionDialogInput.OptionEntry}.
 *
 * @param id      The value sent when it is chosen.
 * @param display What the player reads, if not its id.
 * @param initial Whether it is chosen when the dialog opens.
 */
public record OptionEntryMock(@NotNull String id, @Nullable Component display, boolean initial) implements SingleOptionDialogInput.OptionEntry
{

	/**
	 * Constructs a new {@link OptionEntryMock}.
	 */
	public OptionEntryMock
	{
		Preconditions.checkNotNull(id, "Id cannot be null");
	}

}
