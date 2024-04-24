package org.mockbukkit.mockbukkit.matcher.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.command.MessageTarget;


public class MessageTargetReceivedMessageMatcher extends TypeSafeMatcher<MessageTarget>
{

	private final Component expected;

	public MessageTargetReceivedMessageMatcher(Component expected)
	{
		this.expected = expected;
	}

	@Override
	protected boolean matchesSafely(MessageTarget item)
	{
		return expected.equals(item.nextComponentMessage());
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to receive specific message");
	}

	public static MessageTargetReceivedMessageMatcher hasReceived(Component expected)
	{
		return new MessageTargetReceivedMessageMatcher(expected);
	}

	public static MessageTargetReceivedMessageMatcher hasReceived(String expected)
	{
		return new MessageTargetReceivedMessageMatcher(LegacyComponentSerializer.legacySection().deserialize(expected));
	}

}
