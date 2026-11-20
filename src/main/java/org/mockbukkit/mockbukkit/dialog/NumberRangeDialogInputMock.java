package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.input.NumberRangeDialogInput;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link NumberRangeDialogInput}: a slider.
 *
 * @param key         The key of its value.
 * @param width       Its width, from 1 to 1024.
 * @param label       Its label.
 * @param labelFormat The translation key of its label with the value.
 * @param start       Its lowest value.
 * @param end         Its highest value.
 * @param initial     Its first value, if any: the middle of the range otherwise.
 * @param step        The step between two values, if any.
 */
public record NumberRangeDialogInputMock(@NotNull String key, int width, @NotNull Component label, @NotNull String labelFormat, float start,
										 float end, @Nullable Float initial, @Nullable Float step) implements NumberRangeDialogInput
{

	/**
	 * Constructs a new {@link NumberRangeDialogInputMock}, checked as Paper does.
	 */
	public NumberRangeDialogInputMock
	{
		DialogValidation.inputKey(key);
		DialogValidation.range(width, "width", 1, 1024);
		Preconditions.checkNotNull(label, "Label cannot be null");
		Preconditions.checkNotNull(labelFormat, "Label format cannot be null");
		if (initial != null)
		{
			DialogValidation.range(initial, "initial", Math.min(start, end), Math.max(start, end));
		}
		if (step != null)
		{
			DialogValidation.positive(step, "step");
		}
	}

	/**
	 * The value the slider shows when the dialog opens: its initial value, else the middle of its range.
	 *
	 * @return The first value.
	 */
	public float firstValue()
	{
		return initial != null ? initial : (start + end) / 2;
	}

	/**
	 * Mock implementation of a {@link NumberRangeDialogInput.Builder}.
	 */
	public static final class Builder implements NumberRangeDialogInput.Builder
	{

		private final String key;
		private final Component label;
		private final float start;
		private final float end;
		private int width = DialogValidation.MESSAGE_WIDTH;
		private String labelFormat = "options.generic_value";
		private @Nullable Float initial;
		private @Nullable Float step;

		/**
		 * Constructs a new {@link Builder}.
		 *
		 * @param key   The key of its value.
		 * @param label Its label.
		 * @param start Its lowest value.
		 * @param end   Its highest value.
		 */
		public Builder(@NotNull String key, @NotNull Component label, float start, float end)
		{
			this.key = DialogValidation.inputKey(key);
			this.label = Preconditions.checkNotNull(label, "Label cannot be null");
			this.start = start;
			this.end = end;
		}

		@Override
		public @NotNull Builder width(int width)
		{
			this.width = DialogValidation.range(width, "width", 1, 1024);
			return this;
		}

		@Override
		public @NotNull Builder labelFormat(@NotNull String labelFormat)
		{
			this.labelFormat = Preconditions.checkNotNull(labelFormat, "Label format cannot be null");
			return this;
		}

		@Override
		public @NotNull Builder initial(@Nullable Float initial)
		{
			this.initial = initial == null ? null : DialogValidation.range(initial, "initial", Math.min(start, end), Math.max(start, end));
			return this;
		}

		@Override
		public @NotNull Builder step(@Nullable Float step)
		{
			this.step = step == null ? null : DialogValidation.positive(step, "step");
			return this;
		}

		@Override
		public @NotNull NumberRangeDialogInput build()
		{
			return new NumberRangeDialogInputMock(key, width, label, labelFormat, start, end, initial, step);
		}

	}

}
