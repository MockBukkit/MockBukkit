package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.input.BooleanDialogInput;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link BooleanDialogInput}: a checkbox.
 *
 * @param key     The key of its value.
 * @param label   Its label.
 * @param initial Whether it starts checked.
 * @param onTrue  What a command template reads when it is checked.
 * @param onFalse What a command template reads when it is not.
 */
public record BooleanDialogInputMock(@NotNull String key, @NotNull Component label, boolean initial, @NotNull String onTrue,
									 @NotNull String onFalse) implements BooleanDialogInput
{

	/**
	 * Constructs a new {@link BooleanDialogInputMock}, checked as Paper does.
	 */
	public BooleanDialogInputMock
	{
		DialogValidation.inputKey(key);
		Preconditions.checkNotNull(label, "Label cannot be null");
		Preconditions.checkNotNull(onTrue, "On true cannot be null");
		Preconditions.checkNotNull(onFalse, "On false cannot be null");
	}

	/**
	 * Mock implementation of a {@link BooleanDialogInput.Builder}.
	 */
	public static final class Builder implements BooleanDialogInput.Builder
	{

		private final String key;
		private final Component label;
		private boolean initial;
		private String onTrue = "true";
		private String onFalse = "false";

		/**
		 * Constructs a new {@link Builder}.
		 *
		 * @param key   The key of its value.
		 * @param label Its label.
		 */
		public Builder(@NotNull String key, @NotNull Component label)
		{
			this.key = DialogValidation.inputKey(key);
			this.label = Preconditions.checkNotNull(label, "Label cannot be null");
		}

		@Override
		public @NotNull Builder initial(boolean initial)
		{
			this.initial = initial;
			return this;
		}

		@Override
		public @NotNull Builder onTrue(@NotNull String onTrue)
		{
			this.onTrue = Preconditions.checkNotNull(onTrue, "On true cannot be null");
			return this;
		}

		@Override
		public @NotNull Builder onFalse(@NotNull String onFalse)
		{
			this.onFalse = Preconditions.checkNotNull(onFalse, "On false cannot be null");
			return this;
		}

		@Override
		public @NotNull BooleanDialogInput build()
		{
			return new BooleanDialogInputMock(key, label, initial, onTrue, onFalse);
		}

	}

}
