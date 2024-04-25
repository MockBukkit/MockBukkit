package org.mockbukkit.mockbukkit.matcher.command;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.command.MessageTarget;

public class MessageTargetReceivedAnyMessage extends TypeSafeMatcher<MessageTarget>
{

	private String nextMessage = null;

	@Override
	protected boolean matchesSafely(MessageTarget item)
	{
		this.nextMessage = item.nextMessage();
		return nextMessage != null;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to receive any message");
	}

	@Override
	protected void describeMismatchSafely(MessageTarget item, Description description)
	{
		description.appendText("was ").appendValue(nextMessage);
	}

	public static MessageTargetReceivedAnyMessage hasReceivedAny()
	{
		return new MessageTargetReceivedAnyMessage();
	}

}
