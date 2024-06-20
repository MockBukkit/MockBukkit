package org.mockbukkit.mockbukkit.matcher.command;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.command.MessageTarget;

public class MessageTargetReceivedAnyMessageMatcher extends TypeSafeMatcher<MessageTarget>
{

	private String nextMessage = null;

	@Override
	protected boolean matchesSafely(MessageTarget messageTarget)
	{
		this.nextMessage = messageTarget.nextMessage();
		return nextMessage != null;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to receive any message");
	}

	@Override
	protected void describeMismatchSafely(MessageTarget messageTarget, Description description)
	{
		description.appendText("was ").appendValue(nextMessage);
	}

	/**
	 *
	 * @return A matcher which matches with any target that has received a message
	 */
	public static @NotNull MessageTargetReceivedAnyMessageMatcher hasReceivedAny()
	{
		return new MessageTargetReceivedAnyMessageMatcher();
	}

}
