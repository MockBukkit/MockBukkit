package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of an {@link ActionButton}.
 *
 * @param label   The label of the button.
 * @param tooltip Its tooltip, if any.
 * @param width   Its width, from 1 to 1024.
 * @param action  What a click does, if anything.
 */
public record ActionButtonMock(@NotNull Component label, @Nullable Component tooltip, int width,
							   @Nullable DialogAction action) implements ActionButton
{

	/**
	 * Constructs a new {@link ActionButtonMock}, checked as Paper does.
	 */
	public ActionButtonMock
	{
		Preconditions.checkNotNull(label, "Label cannot be null");
		DialogValidation.range(width, "width", 1, 1024);
	}

	/**
	 * Mock implementation of an {@link ActionButton.Builder}.
	 */
	public static final class Builder implements ActionButton.Builder
	{

		private final Component label;
		private @Nullable Component tooltip;
		private int width = DialogValidation.BUTTON_WIDTH;
		private @Nullable DialogAction action;

		/**
		 * Constructs a new {@link Builder} for a button of this label.
		 *
		 * @param label The label of the button.
		 */
		public Builder(@NotNull Component label)
		{
			this.label = Preconditions.checkNotNull(label, "Label cannot be null");
		}

		@Override
		public @NotNull Builder tooltip(@Nullable Component tooltip)
		{
			this.tooltip = tooltip;
			return this;
		}

		@Override
		public @NotNull Builder width(int width)
		{
			this.width = DialogValidation.range(width, "width", 1, 1024);
			return this;
		}

		@Override
		public @NotNull Builder action(@Nullable DialogAction action)
		{
			this.action = action;
			return this;
		}

		@Override
		public @NotNull ActionButton build()
		{
			return new ActionButtonMock(label, tooltip, width, action);
		}

	}

}
