package org.mockbukkit.mockbukkit.matcher.command;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.command.CommandResult;
import org.mockbukkit.mockbukkit.command.MessageTarget;

import java.lang.reflect.Field;

public class CommandResultResponseMatcher extends TypeSafeMatcher<CommandResult>
{

	private final String response;
	private String senderMessage = null;

	public CommandResultResponseMatcher(String response)
	{
		this.response = response;
	}

	@Override
	protected boolean matchesSafely(CommandResult item)
	{
		try
		{
			Field currentItemField = item.getClass().getDeclaredField("sender");
			currentItemField.setAccessible(true);
			MessageTarget sender = (MessageTarget) currentItemField.get(item);
			this.senderMessage = sender.nextMessage();
			return response.equals(senderMessage);
		}
		catch (NoSuchFieldException | IllegalAccessException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have the following message be the last message sent to receiver ").appendValue(response);
	}

	@Override
	protected void describeMismatchSafely(CommandResult item, Description mismatchDescription)
	{
		mismatchDescription.appendText("was ").appendValue(senderMessage);
	}

	public static CommandResultResponseMatcher hasResponse(String response)
	{
		return new CommandResultResponseMatcher(response);
	}

}
