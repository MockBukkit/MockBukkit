package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.type.MultiActionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Mock implementation of a {@link MultiActionType}: a grid of buttons.
 *
 * @param actions    Its buttons, at least one.
 * @param exitAction The button at the bottom that leaves, if any.
 * @param columns    The number of columns.
 */
public record MultiActionTypeMock(@NotNull List<ActionButton> actions, @Nullable ActionButton exitAction, int columns) implements MultiActionType
{

	/**
	 * Constructs a new {@link MultiActionTypeMock}, checked as Paper does.
	 */
	public MultiActionTypeMock
	{
		Preconditions.checkArgument(!actions.isEmpty(), "actions cannot be empty");
		DialogValidation.positive(columns, "columns");
		actions = List.copyOf(actions);
	}

	/**
	 * Mock implementation of a {@link MultiActionType.Builder}.
	 */
	public static final class Builder implements MultiActionType.Builder
	{

		private final List<ActionButton> actions;
		private @Nullable ActionButton exitAction;
		private int columns = 2;

		/**
		 * Constructs a new {@link Builder}.
		 *
		 * @param actions Its buttons, at least one.
		 */
		public Builder(@NotNull List<ActionButton> actions)
		{
			Preconditions.checkNotNull(actions, "Actions cannot be null");
			Preconditions.checkArgument(!actions.isEmpty(), "actions cannot be empty");
			this.actions = List.copyOf(actions);
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
		public @NotNull MultiActionType build()
		{
			return new MultiActionTypeMock(actions, exitAction, columns);
		}

	}

}
