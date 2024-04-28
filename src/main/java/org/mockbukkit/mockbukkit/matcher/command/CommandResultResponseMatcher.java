package org.mockbukkit.mockbukkit.matcher.command;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.command.CommandResult;

public class CommandResultResponseMatcher extends TypeSafeMatcher<CommandResult>
{

	private final String response;
	private String senderMessage = null;

	public CommandResultResponseMatcher(String response)
	{
		this.response = response;
	}

	@Override
	protected boolean matchesSafely(CommandResult commandResult)
	{
		this.senderMessage = commandResult.getSender().nextMessage();
		return response.equals(senderMessage);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have the following message be the last message sent to receiver ").appendValue(response);
	}

	@Override
	protected void describeMismatchSafely(CommandResult commandResult, Description mismatchDescription)
	{
		mismatchDescription.appendText("was ").appendValue(senderMessage);
	}

	/**
	 *
	 * @param response The response the command result should have
	 * @return A matcher which matches with any command result with specified response
	 */
	public static @NotNull CommandResultResponseMatcher hasResponse(String response)
	{
		return new CommandResultResponseMatcher(response);
	}

}
