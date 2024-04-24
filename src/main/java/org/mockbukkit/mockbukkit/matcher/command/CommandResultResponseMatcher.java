package org.mockbukkit.mockbukkit.matcher.command;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.command.CommandResult;
import org.mockbukkit.mockbukkit.command.MessageTarget;

import java.lang.reflect.Field;

public class CommandResultResponseMatcher extends TypeSafeMatcher<CommandResult>
{

	private final String response;

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
			return response.equals(sender.nextMessage());
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
		description.appendText("to have the given message be the last message sent to sender");
	}

	public static CommandResultResponseMatcher hasResponse(String response)
	{
		return new CommandResultResponseMatcher(response);
	}

}
