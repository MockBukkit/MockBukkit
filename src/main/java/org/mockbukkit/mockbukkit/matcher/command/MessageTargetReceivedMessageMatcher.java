package org.mockbukkit.mockbukkit.matcher.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.command.MessageTarget;


public class MessageTargetReceivedMessageMatcher extends TypeSafeMatcher<MessageTarget>
{

	private final Component expected;
	private Component nextMessage;

	public MessageTargetReceivedMessageMatcher(Component expected)
	{
		this.expected = expected;
	}

	@Override
	protected boolean matchesSafely(MessageTarget messageTarget)
	{
		this.nextMessage = messageTarget.nextComponentMessage();
		return expected.equals(nextMessage);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to receive the following message ").appendValue(expected);
	}

	@Override
	protected void describeMismatchSafely(MessageTarget messageTarget, Description description)
	{
		description.appendText("was ").appendValue(nextMessage);
	}

	/**
	 *
	 * @param expected The message required for a match
	 * @return A matcher which matches with any message target that has received the specified message
	 */
	public static MessageTargetReceivedMessageMatcher hasReceived(Component expected)
	{
		return new MessageTargetReceivedMessageMatcher(expected);
	}

	/**
	 *
	 * @param expected The message required for a match
	 * @return A matcher which matches with any message target that has received the specified message
	 */
	public static MessageTargetReceivedMessageMatcher hasReceived(String expected)
	{
		return new MessageTargetReceivedMessageMatcher(LegacyComponentSerializer.legacySection().deserialize(expected));
	}

}
