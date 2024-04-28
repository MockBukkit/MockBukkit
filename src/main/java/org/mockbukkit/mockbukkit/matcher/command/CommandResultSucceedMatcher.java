package org.mockbukkit.mockbukkit.matcher.command;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.command.CommandResult;

public class CommandResultSucceedMatcher extends TypeSafeMatcher<CommandResult>
{

	@Override
	protected boolean matchesSafely(CommandResult commandResult)
	{
		return commandResult.hasSucceeded();
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have a success code");
	}

	@Override
	public void describeMismatchSafely(CommandResult commandResult, Description description)
	{
		description.appendValue("had success code ").appendValue(commandResult.hasSucceeded());
	}

	/**
	 * @return A matcher which matches with any command result with a success code
	 */
	public static CommandResultSucceedMatcher hasSucceeded()
	{
		return new CommandResultSucceedMatcher();
	}

}
