package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.input.TextDialogInput;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link TextDialogInput}: a text field.
 *
 * @param key          The key of its value.
 * @param width        Its width, from 1 to 1024.
 * @param label        Its label.
 * @param labelVisible Whether its label is drawn.
 * @param initial      Its first text.
 * @param maxLength    The longest text it takes.
 * @param multiline    Its lines, if it has several.
 */
public record TextDialogInputMock(@NotNull String key, int width, @NotNull Component label, boolean labelVisible, @NotNull String initial,
								  int maxLength, @Nullable MultilineOptions multiline) implements TextDialogInput
{

	/**
	 * Constructs a new {@link TextDialogInputMock}, checked as Paper does.
	 */
	public TextDialogInputMock
	{
		DialogValidation.inputKey(key);
		DialogValidation.range(width, "width", 1, 1024);
		Preconditions.checkNotNull(label, "Label cannot be null");
		Preconditions.checkNotNull(initial, "Initial cannot be null");
		DialogValidation.positive(maxLength, "maxLength");
	}

	/**
	 * Mock implementation of a {@link TextDialogInput.Builder}.
	 */
	public static final class Builder implements TextDialogInput.Builder
	{

		private final String key;
		private final Component label;
		private int width = DialogValidation.MESSAGE_WIDTH;
		private boolean labelVisible = true;
		private String initial = "";
		private int maxLength = 32;
		private @Nullable MultilineOptions multiline;

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
		public @NotNull Builder width(int width)
		{
			this.width = DialogValidation.range(width, "width", 1, 1024);
			return this;
		}

		@Override
		public @NotNull Builder labelVisible(boolean labelVisible)
		{
			this.labelVisible = labelVisible;
			return this;
		}

		@Override
		public @NotNull Builder initial(@NotNull String initial)
		{
			this.initial = Preconditions.checkNotNull(initial, "Initial cannot be null");
			return this;
		}

		@Override
		public @NotNull Builder maxLength(int maxLength)
		{
			this.maxLength = DialogValidation.positive(maxLength, "maxLength");
			return this;
		}

		@Override
		public @NotNull Builder multiline(@Nullable MultilineOptions multiline)
		{
			this.multiline = multiline;
			return this;
		}

		@Override
		public @NotNull TextDialogInput build()
		{
			return new TextDialogInputMock(key, width, label, labelVisible, initial, maxLength, multiline);
		}

	}

}
