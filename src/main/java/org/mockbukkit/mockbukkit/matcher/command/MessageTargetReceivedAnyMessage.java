package org.mockbukkit.mockbukkit.matcher.command;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.command.MessageTarget;

public class MessageTargetReceivedAnyMessage extends TypeSafeMatcher<MessageTarget>
{

	@Override
	protected boolean matchesSafely(MessageTarget item)
	{
		return item.nextMessage() == null;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to receive any message");
	}

	public static MessageTargetReceivedAnyMessage hasReceivedAny()
	{
		return new MessageTargetReceivedAnyMessage();
	}

}
