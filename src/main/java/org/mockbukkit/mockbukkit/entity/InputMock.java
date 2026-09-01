package org.mockbukkit.mockbukkit.entity;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.Input;

/**
 * A simple {@link Input} holding which movement keys a player is pressing. Bukkit only exposes {@code Input} as an
 * interface with no implementation, so tests that need to drive
 * {@link org.bukkit.entity.Player#getCurrentInput()} have nothing to hand it otherwise.
 *
 * @param forward  Whether the forward key is pressed.
 * @param backward Whether the backward key is pressed.
 * @param left     Whether the left key is pressed.
 * @param right    Whether the right key is pressed.
 * @param jump     Whether the jump key is pressed.
 * @param sneak    Whether the sneak key is pressed.
 * @param sprint   Whether the sprint key is pressed.
 */
@Builder
public record InputMock(@Getter boolean forward, @Getter boolean backward, @Getter boolean left,
						@Getter boolean right, @Getter boolean jump, @Getter boolean sneak, @Getter boolean sprint) implements Input
{

	/**
	 * An input with nothing pressed, which is what a player who is standing still reports.
	 *
	 * @return An input with every key released.
	 */
	public static InputMock none()
	{
		return builder().build();
	}

	/**
	 * Builds an {@link InputMock}. Every key defaults to released, so only the pressed ones need naming. Each key has a
	 * no argument shorthand that presses it, next to a setter that takes the value explicitly.
	 */
	public static class InputMockBuilder
	{

		/**
		 * Presses the forward key.
		 *
		 * @return This builder.
		 */
		public InputMockBuilder forward()
		{
			return forward(true);
		}

		/**
		 * Sets whether the forward key is pressed.
		 *
		 * @param forward Whether the key is pressed.
		 * @return This builder.
		 */
		public InputMockBuilder forward(boolean forward)
		{
			this.forward = forward;
			return this;
		}

		/**
		 * Presses the backward key.
		 *
		 * @return This builder.
		 */
		public InputMockBuilder backward()
		{
			return backward(true);
		}

		/**
		 * Sets whether the backward key is pressed.
		 *
		 * @param backward Whether the key is pressed.
		 * @return This builder.
		 */
		public InputMockBuilder backward(boolean backward)
		{
			this.backward = backward;
			return this;
		}

		/**
		 * Presses the left key.
		 *
		 * @return This builder.
		 */
		public InputMockBuilder left()
		{
			return left(true);
		}

		/**
		 * Sets whether the left key is pressed.
		 *
		 * @param left Whether the key is pressed.
		 * @return This builder.
		 */
		public InputMockBuilder left(boolean left)
		{
			this.left = left;
			return this;
		}

		/**
		 * Presses the right key.
		 *
		 * @return This builder.
		 */
		public InputMockBuilder right()
		{
			return right(true);
		}

		/**
		 * Sets whether the right key is pressed.
		 *
		 * @param right Whether the key is pressed.
		 * @return This builder.
		 */
		public InputMockBuilder right(boolean right)
		{
			this.right = right;
			return this;
		}

		/**
		 * Presses the jump key.
		 *
		 * @return This builder.
		 */
		public InputMockBuilder jump()
		{
			return jump(true);
		}

		/**
		 * Sets whether the jump key is pressed.
		 *
		 * @param jump Whether the key is pressed.
		 * @return This builder.
		 */
		public InputMockBuilder jump(boolean jump)
		{
			this.jump = jump;
			return this;
		}

		/**
		 * Presses the sneak key.
		 *
		 * @return This builder.
		 */
		public InputMockBuilder sneak()
		{
			return sneak(true);
		}

		/**
		 * Sets whether the sneak key is pressed.
		 *
		 * @param sneak Whether the key is pressed.
		 * @return This builder.
		 */
		public InputMockBuilder sneak(boolean sneak)
		{
			this.sneak = sneak;
			return this;
		}

		/**
		 * Presses the sprint key.
		 *
		 * @return This builder.
		 */
		public InputMockBuilder sprint()
		{
			return sprint(true);
		}

		/**
		 * Sets whether the sprint key is pressed.
		 *
		 * @param sprint Whether the key is pressed.
		 * @return This builder.
		 */
		public InputMockBuilder sprint(boolean sprint)
		{
			this.sprint = sprint;
			return this;
		}

	}

}
