package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.input.SingleOptionDialogInput;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Mock implementation of a {@link SingleOptionDialogInput}: a button that cycles through its options.
 *
 * @param key          The key of its value.
 * @param width        Its width, from 1 to 1024.
 * @param entries      Its options, at least one, at most one initially chosen.
 * @param label        Its label.
 * @param labelVisible Whether its label is drawn.
 */
public record SingleOptionDialogInputMock(@NotNull String key, int width, @NotNull List<OptionEntry> entries, @NotNull Component label,
										  boolean labelVisible) implements SingleOptionDialogInput
{

	/**
	 * Constructs a new {@link SingleOptionDialogInputMock}, checked as Paper does.
	 */
	public SingleOptionDialogInputMock
	{
		DialogValidation.inputKey(key);
		DialogValidation.range(width, "width", 1, 1024);
		Preconditions.checkNotNull(label, "Label cannot be null");
		Preconditions.checkArgument(!entries.isEmpty(), "entries must not be empty");
		Preconditions.checkArgument(entries.stream().filter(OptionEntry::initial).count() <= 1, "only 1 option can be initially selected");
		entries = List.copyOf(entries);
	}

	/**
	 * The option chosen when the dialog opens: the initial one, else the first.
	 *
	 * @return The first option.
	 */
	public @NotNull OptionEntry firstEntry()
	{
		return entries.stream().filter(OptionEntry::initial).findFirst().orElse(entries.getFirst());
	}

	/**
	 * Mock implementation of a {@link SingleOptionDialogInput.Builder}.
	 */
	public static final class Builder implements SingleOptionDialogInput.Builder
	{

		private final String key;
		private final Component label;
		private final List<OptionEntry> entries;
		private int width = DialogValidation.MESSAGE_WIDTH;
		private boolean labelVisible = true;

		/**
		 * Constructs a new {@link Builder}.
		 *
		 * @param key     The key of its value.
		 * @param label   Its label.
		 * @param entries Its options.
		 */
		public Builder(@NotNull String key, @NotNull Component label, @NotNull List<OptionEntry> entries)
		{
			this.key = DialogValidation.inputKey(key);
			this.label = Preconditions.checkNotNull(label, "Label cannot be null");
			Preconditions.checkNotNull(entries, "Entries cannot be null");
			Preconditions.checkArgument(!entries.isEmpty(), "entries must not be empty");
			Preconditions.checkArgument(entries.stream().filter(OptionEntry::initial).count() <= 1, "only 1 option can be initially selected");
			this.entries = List.copyOf(entries);
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
		public @NotNull SingleOptionDialogInput build()
		{
			return new SingleOptionDialogInputMock(key, width, entries, label, labelVisible);
		}

	}

}
