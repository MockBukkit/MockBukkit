package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.type.DialogListType;
import io.papermc.paper.registry.set.RegistrySet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link DialogListType}: a button for each of other dialogs.
 *
 * @param dialogs     The dialogs it opens.
 * @param exitAction  The button at the bottom that leaves, if any.
 * @param columns     The number of columns.
 * @param buttonWidth The width of its buttons, from 1 to 1024.
 */
public record DialogListTypeMock(@NotNull RegistrySet<Dialog> dialogs, @Nullable ActionButton exitAction, int columns,
								 int buttonWidth) implements DialogListType
{

	/**
	 * Constructs a new {@link DialogListTypeMock}, checked as Paper does.
	 */
	public DialogListTypeMock
	{
		Preconditions.checkNotNull(dialogs, "Dialogs cannot be null");
		DialogValidation.positive(columns, "columns");
		DialogValidation.range(buttonWidth, "buttonWidth", 1, 1024);
	}

	/**
	 * Mock implementation of a {@link DialogListType.Builder}.
	 */
	public static final class Builder implements DialogListType.Builder
	{

		private final RegistrySet<Dialog> dialogs;
		private @Nullable ActionButton exitAction;
		private int columns = 2;
		private int buttonWidth = DialogValidation.BUTTON_WIDTH;

		/**
		 * Constructs a new {@link Builder}.
		 *
		 * @param dialogs The dialogs it opens.
		 */
		public Builder(@NotNull RegistrySet<Dialog> dialogs)
		{
			this.dialogs = Preconditions.checkNotNull(dialogs, "Dialogs cannot be null");
		}

		@Override
		public @NotNull Builder exitAction(@Nullable ActionButton exitAction)
		{
			this.exitAction = exitAction;
			return this;
		}

		@Override
		public @NotNull Builder columns(int columns)
		{
			this.columns = DialogValidation.positive(columns, "columns");
			return this;
		}

		@Override
		public @NotNull Builder buttonWidth(int buttonWidth)
		{
			this.buttonWidth = DialogValidation.range(buttonWidth, "buttonWidth", 1, 1024);
			return this;
		}

		@Override
		public @NotNull DialogListType build()
		{
			return new DialogListTypeMock(dialogs, exitAction, columns, buttonWidth);
		}

	}

}
