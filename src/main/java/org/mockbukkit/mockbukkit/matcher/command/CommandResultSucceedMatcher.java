package org.mockbukkit.mockbukkit.matcher.command;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.command.CommandResult;

public class CommandResultSucceedMatcher extends TypeSafeMatcher<CommandResult>
{

	@Override
	protected boolean matchesSafely(CommandResult item)
	{
		return item.hasSucceeded();
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have a success code");
	}

	public static CommandResultSucceedMatcher hasSucceeded(){
		return new CommandResultSucceedMatcher();
	}
}
