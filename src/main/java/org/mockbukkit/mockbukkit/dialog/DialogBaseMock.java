package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Mock implementation of a {@link DialogBase}.
 *
 * @param title              The title of the dialog.
 * @param externalTitle      The title on the button that opens it, if any.
 * @param canCloseWithEscape Whether escape closes it.
 * @param pause              Whether it pauses a single-player game.
 * @param afterAction        What happens after one of its actions.
 * @param body               Its bodies.
 * @param inputs             Its inputs.
 */
public record DialogBaseMock(@NotNull Component title, @Nullable Component externalTitle, boolean canCloseWithEscape, boolean pause,
							 @NotNull DialogAfterAction afterAction, @NotNull List<DialogBody> body,
							 @NotNull List<DialogInput> inputs) implements DialogBase
{

	/**
	 * Constructs a new {@link DialogBaseMock}, checked as Paper does.
	 */
	public DialogBaseMock
	{
		Preconditions.checkNotNull(title, "Title cannot be null");
		Preconditions.checkNotNull(afterAction, "After action cannot be null");
		Preconditions.checkArgument(!pause || afterAction != DialogAfterAction.NONE,
				"Dialogs that pause the game must use after_action values that unpause it after user action!");
		body = List.copyOf(body);
		inputs = List.copyOf(inputs);
	}

	/**
	 * Mock implementation of a {@link DialogBase.Builder}.
	 */
	public static final class Builder implements DialogBase.Builder
	{

		private final Component title;
		private @Nullable Component externalTitle;
		private boolean canCloseWithEscape = true;
		private boolean pause = true;
		private DialogAfterAction afterAction = DialogAfterAction.CLOSE;
		private List<? extends DialogBody> body = List.of();
		private List<? extends DialogInput> inputs = List.of();

		/**
		 * Constructs a new {@link Builder} for a dialog of this title.
		 *
		 * @param title The title of the dialog.
		 */
		public Builder(@NotNull Component title)
		{
			this.title = Preconditions.checkNotNull(title, "Title cannot be null");
		}

		@Override
		public @NotNull Builder externalTitle(@Nullable Component externalTitle)
		{
			this.externalTitle = externalTitle;
			return this;
		}

		@Override
		public @NotNull Builder canCloseWithEscape(boolean canCloseWithEscape)
		{
			this.canCloseWithEscape = canCloseWithEscape;
			return this;
		}

		@Override
		public @NotNull Builder pause(boolean pause)
		{
			this.pause = pause;
			return this;
		}

		@Override
		public @NotNull Builder afterAction(@NotNull DialogAfterAction afterAction)
		{
			this.afterAction = Preconditions.checkNotNull(afterAction, "After action cannot be null");
			return this;
		}

		@Override
		public @NotNull Builder body(@NotNull List<? extends DialogBody> body)
		{
			this.body = List.copyOf(body);
			return this;
		}

		@Override
		public @NotNull Builder inputs(@NotNull List<? extends DialogInput> inputs)
		{
			this.inputs = List.copyOf(inputs);
			return this;
		}

		@Override
		public @NotNull DialogBase build()
		{
			return new DialogBaseMock(title, externalTitle, canCloseWithEscape, pause, afterAction, List.copyOf(body), List.copyOf(inputs));
		}

	}

}
