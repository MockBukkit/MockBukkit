package org.mockbukkit.mockbukkit.entity;

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
public record InputMock(boolean forward, boolean backward, boolean left, boolean right, boolean jump, boolean sneak,
						boolean sprint) implements Input
{

	/**
	 * An input with nothing pressed, which is what a player who is standing still reports.
	 *
	 * @return An input with every key released.
	 */
	public static InputMock none()
	{
		return new InputMock(false, false, false, false, false, false, false);
	}

	@Override
	public boolean isForward()
	{
		return this.forward;
	}

	@Override
	public boolean isBackward()
	{
		return this.backward;
	}

	@Override
	public boolean isLeft()
	{
		return this.left;
	}

	@Override
	public boolean isRight()
	{
		return this.right;
	}

	@Override
	public boolean isJump()
	{
		return this.jump;
	}

	@Override
	public boolean isSneak()
	{
		return this.sneak;
	}

	@Override
	public boolean isSprint()
	{
		return this.sprint;
	}

}
